package com.datastore.datastore.dao;

import java.util.Set;

import com.datastore.datastore.StoreException;
import com.datastore.datastore.model.DataStoreDTO;

public interface DataStoreDAO {
	//This is a good example!
	public String createEntry(String key, DataStoreDTO value) throws StoreException;
	public DataStoreDTO readEntry( String key) throws StoreException;
	//public DataStoreDTO readEntry2( String key) throws StoreException;
	public DataStoreDTO deleteEntry( String key) throws StoreException;
	public Set<String> getAllKeys();
	
}
