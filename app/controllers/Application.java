package controllers;

import models.Film;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {

	static Form<Film> _form = form(Film.class);

	public static Result index() {
		return redirect(routes.Application.list());
	}

	@Transactional
	public static Result list() {
		return ok(index.render(Film.all(), _form));
	}

	@Transactional
	public static Result createFilm() {
		Form<Film> film = _form.bindFromRequest();
		Film _film = null;
		if (!film.hasErrors() && (_film = Film.create(film.get())) != null) {
			SolrFactory.add(_film);
			return redirect(routes.Application.list());
		} else {
			return badRequest(index.render(Film.all(), film));
		}
	}

	public static Result listByID(Long id) {
		
		return ok(id + "");
	}

}