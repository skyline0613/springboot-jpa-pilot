package com.bran.app.jpa.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "claim_main")
public class ClaimMain {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "claim_main_generator")
  private long id;

  @Column(name = "title")
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "published")
  private Boolean published;
  
  @JsonIgnore
  @OneToMany(mappedBy = "claimMain", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Comment> comments;

  public ClaimMain() {

  }

  public ClaimMain(String title, String description, Boolean published) {
    this.title = title;
    this.description = description;
    this.published = published;
  }

  public long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Boolean isPublished() {
    return published;
  }

  public void setPublished(Boolean isPublished) {
    this.published = isPublished;
  }
  
	//@OneToMany(fetch=FetchType.LAZY, mappedBy = "claimMain") //FetchType.Lazy loads the entities only when necessary good when dealing with lots of records
	public List<Comment> getComments(){
		return this.comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}  

  @Override
  public String toString() {
    return "Tutorial [id=" + id + ", title=" + title + ", desc=" + description + ", published=" + published + "]";
  }

}
