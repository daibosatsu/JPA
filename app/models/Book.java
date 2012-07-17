package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.solr.client.solrj.beans.Field;

import play.data.validation.Constraints.Required;
import play.db.jpa.JPA;

@Entity
public class Book {

	@Id
	@GeneratedValue
	@Field
	public Long id;
	@Required
	@Field
	public String title;
	@Field
	public String description;
	
	public static Book add(Book book){
		
		book = JPA.em().merge(book);
		return book;
	}

}
