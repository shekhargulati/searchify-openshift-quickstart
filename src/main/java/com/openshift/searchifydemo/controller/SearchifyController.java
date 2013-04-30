package com.openshift.searchifydemo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.openshift.searchifydemo.fulltext.SearchifyClient;

@Controller
@RequestMapping("/searchify")
public class SearchifyController {

	@Autowired
	private SearchifyClient searchifyClient;

	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addToIndex(@RequestBody Document document) {
		Map<String, String> fields = new HashMap<String, String>();
		fields.put("text", document.getText());
		searchifyClient.addToIndex(document.getId(), fields);

		ResponseEntity<String> responseEntity = new ResponseEntity<String>(
				HttpStatus.CREATED);
		return responseEntity;

	}

	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Set<String> search(@RequestParam("query") String query) {
		Set<String> documentIds = searchifyClient.search(query);
		return documentIds;
	}

}
