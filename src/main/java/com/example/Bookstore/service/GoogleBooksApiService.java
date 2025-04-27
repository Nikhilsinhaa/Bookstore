package com.example.Bookstore.service;

	import org.springframework.beans.factory.annotation.Value;
	import org.springframework.stereotype.Service;
	import org.springframework.web.client.RestTemplate;
	import com.fasterxml.jackson.databind.JsonNode;
	import com.fasterxml.jackson.databind.ObjectMapper;
	import org.springframework.stereotype.Service;
	import java.io.IOException;

	@Service
	public class GoogleBooksApiService {

		@Value("${google.books.api.key}")
		private String apiKey;

	    private final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?q=";

	    private final RestTemplate restTemplate = new RestTemplate();

	    public String fetchBookDetails(String title) {
	        String url = BASE_URL + title + "&key=" + apiKey;
	        return restTemplate.getForObject(url, String.class); // Returns JSON response
	    }
	    
	    public JsonNode fetchCoverDetails(String title) {
	        String url = BASE_URL + title + "&key=" + apiKey;
	        String jsonResponse = restTemplate.getForObject(url, String.class);

	        ObjectMapper mapper = new ObjectMapper();
	        try {
	            return mapper.readTree(jsonResponse);
	        } catch (IOException e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	}



