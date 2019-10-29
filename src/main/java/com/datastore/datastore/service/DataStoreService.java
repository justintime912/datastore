package com.datastore.datastore.service;

import java.util.Optional;

import com.datastore.datastore.StoreException;
import com.datastore.datastore.model.DataStoreDTO;

public interface DataStoreService {
	public String createEntry(String key, Object value, Optional<Integer> timeToLive) throws StoreException;
	public DataStoreDTO readEntry( String key) throws StoreException;
	public DataStoreDTO deleteEntry( String key) throws StoreException;
}
