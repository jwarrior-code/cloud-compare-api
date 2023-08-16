package com.cloudcompare.msazurecosts;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MsAzureCostsController {
	
//  private final PersonaService service;

	@GetMapping("/costs/azure")
	public ResponseEntity<String> getCostByParams(@RequestParam(defaultValue = "") String filters) {

		try {
			byte[] decodedbase64Filters = Base64.getUrlDecoder().decode(filters);
				String decodedFilters = new String(decodedbase64Filters, "UTF-8");
			
			JSONObject filtersObject = new JSONObject(decodedFilters);
			
			String baseURL = "https://prices.azure.com/api/retail/prices?$filter=";
			
			String filterParameters = filtersObject.keySet().stream().map(keyToFilter->keyToFilter+" eq '"+filtersObject.getString(keyToFilter)+"'").collect(Collectors.joining(" and "));
			System.out.println(filterParameters.toString());
			HttpClient client = HttpClient.newHttpClient();
	        HttpRequest request = HttpRequest.newBuilder()
	              .uri(URI.create(baseURL+URLEncoder.encode(filterParameters, StandardCharsets.UTF_8)))
	              .GET()
	              .build();
	        HttpResponse<String> response=client.send(request, BodyHandlers.ofString());
			
	        JSONObject responseJson = new JSONObject(response.body());
	        
			return ResponseEntity.ok(responseJson.getJSONArray("Items").toString());
		} catch (IOException | InterruptedException e) {
			return ResponseEntity.internalServerError().build();
		}

		
	}

}
