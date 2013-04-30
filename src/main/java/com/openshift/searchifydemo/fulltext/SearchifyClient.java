package com.openshift.searchifydemo.fulltext;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flaptor.indextank.apiclient.IndexAlreadyExistsException;
import com.flaptor.indextank.apiclient.IndexTankClient;
import com.flaptor.indextank.apiclient.IndexTankClient.Index;
import com.flaptor.indextank.apiclient.IndexTankClient.IndexConfiguration;
import com.flaptor.indextank.apiclient.IndexTankClient.Query;
import com.flaptor.indextank.apiclient.IndexTankClient.SearchResults;
import com.flaptor.indextank.apiclient.MaximumIndexesExceededException;

@Service
public class SearchifyClient {

	private IndexTankClient client;
	private static final String INDEX_NAME = "test_index";

	@Autowired
	public SearchifyClient(IndexTankClient indexTankClient) {
		this.client = indexTankClient;
	}

	@PostConstruct
	public Index createIndex() {
		IndexConfiguration configuration = new IndexConfiguration();
		configuration.enablePublicSearch(false);
		try {
			Index index = client.createIndex(INDEX_NAME, configuration);
			while (!index.hasStarted()) {
				Thread.sleep(300);
			}
			return index;
		} catch (IndexAlreadyExistsException e) {
			System.out
					.println("Index already exists so skipping this exception");
			return client.getIndex("test_index");
		} catch (MaximumIndexesExceededException e) {
			throw new RuntimeException("You have exceeded the limit ", e);
		} catch (Exception e) {
			throw new RuntimeException("Unable to create index because of  ", e);
		}
	}
	
	public void addToIndex(String documentId, Map<String, String> fields){
		try {
			Index index = client.getIndex(INDEX_NAME);
			index.addDocument(documentId, fields);
		} catch (Exception e) {
			throw new RuntimeException(
					"Exception occured while adding document to index .. ", e);
		}
	}
	
	public Set<String> search(String query) {
		Set<String> documentIds = new LinkedHashSet<String>();
		try {
			Index index = client.getIndex(INDEX_NAME);
			SearchResults results = index.search(Query.forString(query));
			System.out.println("Matches: " + results.matches);
			for (Map<String, Object> document : results.results) {
			    System.out.println("doc id: " + document.get("docid"));
			    documentIds.add((String)document.get("docid"));
			}
		} catch (Exception e) {
			throw new RuntimeException(
					"Exception occured while searching for results with query "
							+ query, e);
		}
		return documentIds;
	}

}
