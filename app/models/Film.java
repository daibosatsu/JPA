package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.beans.Field;

import play.data.Form;
import play.data.validation.Constraints.Required;
import play.db.jpa.JPA;
import views.html.helper.form;

@Entity
@NamedQuery(name = "Film.findAll", query = "select f from Film f")
public class Film {

	@Id
	@GeneratedValue
	@Field
	public Long id;
	@Required
	@Field
	public String title;
	@Field
	public String description;

	public String validate() {
		String result = null;
		if (title != null && title.contains("dcm")) {
			result = "damn, please be polite";
		}
		return result;
	}

	public static List<Film> all() {
		List<Film> all = new ArrayList<Film>();
		all.addAll(JPA.em().createNamedQuery("Film.findAll", Film.class)
				.getResultList());
		return all;
	}

	public static Film create(Film film) {
		film = JPA.em().merge(film);
		return film;
	}
}
