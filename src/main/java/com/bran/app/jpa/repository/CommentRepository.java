package com.bran.app.jpa.repository;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bran.app.jpa.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
  List<Comment> findByClaimMainId(Long postId);
  
  @Transactional
  void deleteByClaimMainId(long claimMainId);
}
