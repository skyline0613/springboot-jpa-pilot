package com.bran.app.jpa.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.bran.app.jpa.model.ClaimMain;

import jakarta.transaction.Transactional;

public interface ClaimMainRepository extends JpaRepository<ClaimMain, Long> {
  List<ClaimMain> findByPublished(boolean published);

  List<ClaimMain> findByTitleContaining(String title);
  
  //@EntityGraph(attributePaths = {}, type = EntityGraphType.LOAD)
  @Query("SELECT m FROM ClaimMain m WHERE m.id = :id")
  Optional<ClaimMain> findByIdWithoutComments(@Param("id") Long id);
  
  
  @EntityGraph(attributePaths = {"details"})
  @Query("SELECT m FROM ClaimMain m WHERE m.id = :id")
  Optional<ClaimMain> findByIdWithComments(@Param("id") Long id);
  
  //JPQL
  @Query("SELECT m FROM ClaimMain m WHERE m.description=:desc")
  List<ClaimMain> findByDesc(@Param("desc") String description);  
  
  
  @Query("select u from ClaimMain u where u.description like %?1")
  List<ClaimMain> findByDescriptionEndsWith(String description);
  
  @Query(nativeQuery = true, value = "SELECT * FROM Claim_Main WHERE description = ?1")
  List<ClaimMain> findByDescription(String description);
  
  @Query(nativeQuery = true, value = "SELECT * FROM Claim_Main WHERE description = ?1")
  List<Map<String, Object>> findRawMapByDesc(String description); 
  
  @Transactional
  @Modifying
  @Query("update ClaimMain u set u.description = ?1 where u.id = ?2")
  int updateDesc(String desc, long id);
}
