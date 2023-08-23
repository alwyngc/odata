package com.example.odata.olingo.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="Author")
public class Author {
	
	 	@Id
	 	@GeneratedValue(
	 		    strategy = GenerationType.SEQUENCE,
	 		    generator = "seq_post"
	 		)
	 	@SequenceGenerator(
	 		    name = "seq_post",
	 		    allocationSize = 1,
	 		    initialValue = 10
	 		)
	    private Long authorId;

	 	@NotNull
	    private String firstName;

	 	
	    private String lastName;

	    @OneToMany(cascade = CascadeType.ALL,mappedBy = "author")
	    private List<Book> books;

		public Long getAuthorId() {
			return authorId;
		}

		public void setAuthorId(Long authorId) {
			this.authorId = authorId;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public List<Book> getBooks() {
			return books;
		}

		public void setBooks(List<Book> books) {
			this.books = books;
		}
	    
	    

}
