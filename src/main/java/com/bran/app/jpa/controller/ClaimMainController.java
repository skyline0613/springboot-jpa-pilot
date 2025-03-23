package com.bran.app.jpa.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bran.app.jpa.exception.ResourceNotFoundException;
import com.bran.app.jpa.model.ClaimMain;
import com.bran.app.jpa.repository.ClaimMainRepository;

import io.swagger.v3.oas.annotations.tags.Tag;

//@CrossOrigin(origins = "http://localhost:8081")
@Tag(name = "Claim Main", description = "理賠申請書 APIs")
@RestController
@RequestMapping("/api")
public class ClaimMainController {

	@Autowired
	ClaimMainRepository claimMainRepository;

	@GetMapping("/claim-main")
	public ResponseEntity<List<ClaimMain>> getAllClaimMain(@RequestParam(required = false) String title) {
		List<ClaimMain> claimMain = new ArrayList<ClaimMain>();

		if (title == null)
			claimMainRepository.findAll().forEach(claimMain::add);
		else
			claimMainRepository.findByTitleContaining(title).forEach(claimMain::add);

		if (claimMain.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(claimMain, HttpStatus.OK);
	}

	@GetMapping("/claim-main/desc")
	public ResponseEntity<List<ClaimMain>> getAllByDesc(@RequestParam(required = false) String desc) {
		List<ClaimMain> claimMain = new ArrayList<ClaimMain>();

		if (desc == null)
			claimMainRepository.findAll().forEach(claimMain::add);
		else
			//JPQL example
			claimMainRepository.findByDesc(desc).forEach(claimMain::add);

		if (claimMain.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(claimMain, HttpStatus.OK);
	}

	@GetMapping("/claim-main/{id}")
	public ResponseEntity<ClaimMain> getClaimMainById(@PathVariable("id") long id) {
		ClaimMain claimMain = claimMainRepository.findByIdWithoutComments(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found ClaimMain with id = " + id));

		return new ResponseEntity<>(claimMain, HttpStatus.OK);
	}

	@GetMapping("/claim-main/comments/{id}")
	public ResponseEntity<ClaimMain> getClaimMainWithDetailById(@PathVariable("id") long id) {
		ClaimMain claimMain = claimMainRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found ClaimMain with id = " + id));
		System.out.println(claimMain.getComments().size());
		return new ResponseEntity<>(claimMain, HttpStatus.OK);
	}

	@PostMapping("/claim-main")
	public ResponseEntity<ClaimMain> createClaimMain(@RequestBody ClaimMain claimMain) {
		ClaimMain _claimMain = claimMainRepository
				.save(new ClaimMain(claimMain.getTitle(), claimMain.getDescription(), true));
		return new ResponseEntity<>(_claimMain, HttpStatus.CREATED);
	}

	@PutMapping("/claim-main/{id}")
	public ResponseEntity<ClaimMain> updateClaimMain(@PathVariable("id") long id, @RequestBody ClaimMain claimMain) {
		ClaimMain _claimMain = claimMainRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found ClaimMain with id = " + id));

		_claimMain.setTitle(claimMain.getTitle());
		_claimMain.setDescription(claimMain.getDescription());
		_claimMain.setPublished(claimMain.isPublished());

		return new ResponseEntity<>(claimMainRepository.save(_claimMain), HttpStatus.OK);
	}

	@DeleteMapping("/claim-main/{id}")
	public ResponseEntity<HttpStatus> deleteClaimMain(@PathVariable("id") long id) {
		claimMainRepository.deleteById(id);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/claim-main")
	public ResponseEntity<HttpStatus> deleteAllClaimMain() {
		claimMainRepository.deleteAll();

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/claim-main/published")
	public ResponseEntity<List<ClaimMain>> findByPublished() {
		List<ClaimMain> claimMains = claimMainRepository.findByPublished(true);

		if (claimMains.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(claimMains, HttpStatus.OK);
	}
}
