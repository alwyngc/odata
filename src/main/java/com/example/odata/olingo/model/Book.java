package com.example.odata.olingo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name="Book")
public class Book {
	
	 @Id
	 @GeneratedValue(
	 		    strategy = GenerationType.SEQUENCE,
	 		    generator = "seq_book"
	 		)
	 @SequenceGenerator(
	 		    name = "seq_book",
	 		    allocationSize = 1,
	 		    initialValue = 10
	 		)
	 private Long bookId;

	 @NotNull
	 private String title;
	 
	 @ManyToOne()
	 @JoinColumn(name="author_id")
	 private Author author;

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}
	 
}
