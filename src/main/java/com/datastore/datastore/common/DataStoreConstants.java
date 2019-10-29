package com.datastore.datastore.common;

public class DataStoreConstants {
	
	public static long ONE_GB_SIZE = 1024*1024*1024L; //1 GB
	public static long JSON_SIZE_LIMIT = 16*1024; //16 KB
	public static long KEY_LENGTH_LIMIT= 32; 
	
	public static enum ResponseMessageStatus{
		
		SUCCESS("Key created successfully in Store"),
		KEY_ALREADY_EXISTS("Key Already Exists"),
		VALUE_IS_INCORRECT_JSON("JSON supplied is incorrect"),
		KEY_NOT_PRESENT("Supplied Key is not present!"),
		NULL_KEY("Key supplied was null!"),
		SIZE_EXCEEDED("Store Space limit of 1 GB exceeded"),
		JSON_SIZE_EXCEEDED("JSON size cannot be more than 16KB"),
		KEY_SIZE_EXCEEDED("Key cannot be more than 32 characters");
		private String message;
		
		ResponseMessageStatus(String message){
			this.message=message;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	
	}

}
