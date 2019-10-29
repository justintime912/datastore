package com.datastore.datastore.service;

import java.util.Map;

import com.datastore.datastore.StoreException;
import com.datastore.datastore.model.DataStoreDTO;

public interface DataFileService {

	public void writeToDisk(Map<String,DataStoreDTO> dataMap) throws StoreException;
	public Map<String, DataStoreDTO> getDataMap();
	public DataStoreDTO deleteEntry(String key) throws StoreException;
	
}
