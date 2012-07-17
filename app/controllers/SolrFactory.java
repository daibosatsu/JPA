package controllers;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import views.html.search;

import play.mvc.Controller;

public class SolrFactory extends Controller {

	private static final String solrUrl = "http://localhost:8983/solr";
	private static SolrServer server = null;
	static {

		if (SolrFactory.server == null) {
			server = new HttpSolrServer(solrUrl);
		}
	}

	private SolrFactory() {
	}

	static <T> void add(T t) {
		try {
			System.out.println("Adding new item to solr");
			server.addBean(t);
			server.commit();
		} catch (IOException | SolrServerException e) {
			e.printStackTrace();
		}
	}

	static String getItem(String queryString) {
		String result = "";
		SolrQuery solrQuery = new SolrQuery("*:*");
		solrQuery.addFilterQuery("title:(" + queryString + ")");
		solrQuery.addField("title");
		try {
			final QueryResponse queryResponse = server.query(solrQuery);
			SolrDocumentList solrDocumentList = queryResponse.getResults();
			for (SolrDocument solrDocument : solrDocumentList) {
				Iterator<String> names = solrDocument.getFieldNames()
						.iterator();
				String name = "";
				while (names.hasNext()) {
					name = names.next();
					result += name + " " + solrDocument.getFieldValue(name)
							+ ";";
				}
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Result index() {
		return ok(search.render());
	}

	public static String searchJS(String searchString) {
		return searchString;
	}

	public static Result search(String searchString) {
		System.out.println("Search String:" + searchString);
		// getItem(searchString);
		return ok(Json.toJson(getItem(searchString)));
	}
}
