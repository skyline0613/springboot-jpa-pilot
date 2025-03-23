package com.bran.app.jpa.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.bran.app.jpa.exception.ResourceNotFoundException;
import com.bran.app.jpa.model.Comment;
import com.bran.app.jpa.repository.ClaimMainRepository;
import com.bran.app.jpa.repository.CommentRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class CommentController {

  @Autowired
  private ClaimMainRepository claimMainRepository;

  @Autowired
  private CommentRepository commentRepository;

  @GetMapping("/claimMains/{claimMainId}/comments")
  public ResponseEntity<List<Comment>> getAllCommentsByTutorialId(@PathVariable(value = "claimMainId") Long claimMainId) {
    if (!claimMainRepository.existsById(claimMainId)) {
      throw new ResourceNotFoundException("Not found Tutorial with id = " + claimMainId);
    }

    List<Comment> comments = commentRepository.findByClaimMainId(claimMainId);
    return new ResponseEntity<>(comments, HttpStatus.OK);
  }

  @GetMapping("/comments/{id}")
  public ResponseEntity<Comment> getCommentsByTutorialId(@PathVariable(value = "id") Long id) {
    Comment comment = commentRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Comment with id = " + id));

    return new ResponseEntity<>(comment, HttpStatus.OK);
  }

  @PostMapping("/claimMains/{claimMainId}/comments")
  public ResponseEntity<Comment> createComment(@PathVariable(value = "claimMainId") Long claimMainId,
      @RequestBody Comment commentRequest) {
    Comment comment = claimMainRepository.findById(claimMainId).map(claimMain -> {
      commentRequest.setClaimMain(claimMain);
      return commentRepository.save(commentRequest);
    }).orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + claimMainId));

    return new ResponseEntity<>(comment, HttpStatus.CREATED);
  }

  @PutMapping("/comments/{id}")
  public ResponseEntity<Comment> updateComment(@PathVariable("id") long id, @RequestBody Comment commentRequest) {
    Comment comment = commentRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("CommentId " + id + "not found"));

    comment.setContent(commentRequest.getContent());

    return new ResponseEntity<>(commentRepository.save(comment), HttpStatus.OK);
  }

  @DeleteMapping("/comments/{id}")
  public ResponseEntity<HttpStatus> deleteComment(@PathVariable("id") long id) {
    commentRepository.deleteById(id);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
  
  @DeleteMapping("/claimMains/{claimMainId}/comments")
  public ResponseEntity<List<Comment>> deleteAllCommentsOfTutorial(@PathVariable(value = "claimMainId") Long claimMainId) {
    if (!claimMainRepository.existsById(claimMainId)) {
      throw new ResourceNotFoundException("Not found ClaimMain with id = " + claimMainId);
    }

    commentRepository.deleteByClaimMainId(claimMainId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
