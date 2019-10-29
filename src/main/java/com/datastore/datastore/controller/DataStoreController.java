package com.datastore.datastore.controller;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.datastore.datastore.StoreException;
import com.datastore.datastore.common.DataStoreConstants;
import com.datastore.datastore.service.DataStoreService;

@RequestMapping("/datastore")
@RestController
public class DataStoreController {
	
	@Autowired
	DataStoreService dataStoreService;

	@PostMapping("/createEntry")
	public ResponseEntity<?> createEntry(@RequestParam String key, @RequestBody Object value, @RequestParam Optional<Integer> timeToLive) throws StoreException{
		
		if(key==null) {
			ResponseEntity.badRequest().body(DataStoreConstants.ResponseMessageStatus.NULL_KEY);
		}
		else if(key.length() > DataStoreConstants.KEY_LENGTH_LIMIT) {
			ResponseEntity.badRequest().body(DataStoreConstants.ResponseMessageStatus.KEY_SIZE_EXCEEDED);
		}
		
		else if(value==null || !isJSONValid(value.toString())) {
			ResponseEntity.badRequest().body(DataStoreConstants.ResponseMessageStatus.VALUE_IS_INCORRECT_JSON);
		} else if ( isJSONTooLarge(value.toString())) {
			ResponseEntity.badRequest().body(DataStoreConstants.ResponseMessageStatus.JSON_SIZE_EXCEEDED);
		}
		
		
		String result = dataStoreService.createEntry(key, value,timeToLive);
		
		return ResponseEntity.ok(result);
		
	}
	
	private boolean isJSONTooLarge(String value) {
		return (value!=null && value.length() > DataStoreConstants.JSON_SIZE_LIMIT);
	}

	@PostMapping("/readEntry")
	public ResponseEntity<?> readEntry(@RequestParam String key) throws StoreException{
		
		if(key==null) {
			ResponseEntity.badRequest().body(DataStoreConstants.ResponseMessageStatus.NULL_KEY);
		}
		
		return ResponseEntity.ok(dataStoreService.readEntry(key).getValue());
		
	}
	
	@DeleteMapping("/deleteEntry")
	public ResponseEntity<?> deleteEntry(@RequestParam String key) throws StoreException{
		
		if(key==null) {
			ResponseEntity.badRequest().body(DataStoreConstants.ResponseMessageStatus.NULL_KEY);
		}
		
		return ResponseEntity.ok(dataStoreService.deleteEntry(key).getValue());
		
	}
	

	public boolean isJSONValid(String test) {
	    try {
	    	new JSONObject(test);
	        
	    } catch (JSONException ex) {
	       
	        try {
	            new JSONArray(test);
	        } catch (JSONException ex1) {
	            return false;
	        }
	    }
	    return true;
	}
}	
