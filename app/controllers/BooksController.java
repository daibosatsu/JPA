package controllers;

import models.Book;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.book;

public class BooksController extends Controller {

	static Form<Book> bookForm = form(Book.class);

	public static Result index(){
		return ok(book.render(bookForm));
	}
	
	@Transactional
	public static Result add() {
		final Form<Book> bookFromRequest = bookForm.bindFromRequest();
		Book _book = null;
		if (!bookFromRequest.hasErrors() && (_book = Book.add(bookFromRequest.get())) != null) {
			SolrFactory.add(_book);
			return ok(book.render(bookForm));
		} else {
			return badRequest(book.render(bookFromRequest));
		}
	}
}
