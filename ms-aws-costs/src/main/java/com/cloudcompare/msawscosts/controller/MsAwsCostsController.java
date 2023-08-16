package com.cloudcompare.msawscosts.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MsAwsCostsController {

	@GetMapping("/costs/aws")
	public ResponseEntity<String> getCostByParams(
			@RequestParam(defaultValue = "") String filters) {

		try {
			byte[] decodedbase64Filters = Base64.getUrlDecoder().decode(filters);
				String decodedFilters = new String(decodedbase64Filters, "UTF-8");
			JSONObject filtersObject = new JSONObject(decodedFilters);
			
			String priceListOriginURL = getPriceListOriginURLByService(filtersObject.getString("serviceCode"));
			String priceListURL = getPriceListURLByRegionCode(priceListOriginURL,filtersObject.getString("region"));
			JSONArray priceList = getPriceListItemsByService(priceListURL,filtersObject.getJSONObject("productFilters"));
			
			return ResponseEntity.ok(priceList.toString());
		} catch (UnsupportedEncodingException e) {
			return ResponseEntity.internalServerError().build();
		}

	}
	
	private String getPriceListOriginURLByService(String service) {
		try {
            // Obtener el archivo JSON
            InputStream inputStream = new URL("https://pricing.us-east-1.amazonaws.com/offers/v1.0/aws/index.json").openStream();
            String jsonString = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            // Crear un objeto JSON a partir del archivo
            JSONObject json = new JSONObject(new JSONTokener(jsonString));

            // Obtener el objeto "products"
            JSONObject products = json.getJSONObject("offers");
            
            JSONObject filteredProduct = new JSONObject();

            for (String key : products.keySet()) {
                JSONObject product = products.getJSONObject(key);
                if (product.getString("offerCode").equals(service)) {//AmazonEC2 AmazonRDS
                    filteredProduct=product;
                    break;
                }
            }
            return filteredProduct.getString("currentRegionIndexUrl");
            
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
	}
	
	private String getPriceListURLByRegionCode(String priceListOriginURL, String regionCode) {
		try {
            // Obtener el archivo JSON
            InputStream inputStream = new URL("https://pricing.us-east-1.amazonaws.com"+priceListOriginURL).openStream();
            String jsonString = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            // Crear un objeto JSON a partir del archivo
            JSONObject json = new JSONObject(new JSONTokener(jsonString));

            // Obtener el objeto "products"
            JSONObject regions = json.getJSONObject("regions");
            
            JSONObject filteredRegion = new JSONObject();

            for (String key : regions.keySet()) {
                JSONObject region = regions.getJSONObject(key);
                if (region.getString("regionCode").equals(regionCode)) {//AmazonEC2 AmazonRDS
                    filteredRegion=region;
                    break;
                }
            }
            return filteredRegion.getString("currentVersionUrl");
            
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
	}
	
	private JSONArray getPriceListItemsByService(String priceListURL, JSONObject searchFilters) {
		try {
            // Obtener el archivo JSON
            InputStream inputStream = new URL("https://pricing.us-east-1.amazonaws.com"+priceListURL).openStream();
            String jsonString = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            // Crear un objeto JSON a partir del archivo
            JSONObject json = new JSONObject(new JSONTokener(jsonString));

            // Obtener el objeto "products"
            JSONObject products = json.getJSONObject("products");
            
            JSONArray filteredProducts = new JSONArray();

            for (String key : products.keySet()) {
                JSONObject product = products.getJSONObject(key);
                boolean addFilteredProduct=true;
                for (String keyToFilter : searchFilters.keySet()) {
                    if (!(product.getJSONObject("attributes").has(keyToFilter) && product.getJSONObject("attributes").getString(keyToFilter).equals(searchFilters.getString(keyToFilter)))) {//AmazonEC2 AmazonRDS
                    	addFilteredProduct=false;
                    	break;
                    }
                }
                if (addFilteredProduct) {//AmazonEC2 AmazonRDS
                	product.put("productPrice",getProductPriceBySKU(json, product.getString("sku")));
                	filteredProducts.put(product);
                }
            }
            
            return filteredProducts;
            
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
	}
	
	private String getProductPriceBySKU(JSONObject serviceResponse, String sku) {
		try {
            // Obtener el objeto "products"
            JSONObject prices = serviceResponse.getJSONObject("terms").getJSONObject("OnDemand");
            
            for (String key : prices.keySet()) {
                JSONObject priceObject = prices.getJSONObject(key);
                if (key.equals(sku)) {//AmazonEC2 AmazonRDS
                	String mainKey = priceObject.keySet().iterator().next();
                	JSONObject mainObject= priceObject.getJSONObject(mainKey);
                	String secondaryKey = mainObject.keySet().iterator().next();
                	JSONObject secondaryObject= mainObject.getJSONObject(secondaryKey);
                	String tertiaryKey = secondaryObject.keySet().iterator().next();
                	JSONObject tertiaryObject= secondaryObject.getJSONObject(tertiaryKey);
                	
                	return tertiaryObject.getJSONObject("pricePerUnit").getString("USD");
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}

}