package com.bran.app.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bran.app.jpa.model.ClaimMain;

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
}
