package com.datastore.datastore.service;

import java.util.TimerTask;

import com.datastore.datastore.StoreException;
import com.datastore.datastore.dao.DataStoreDAO;

public class DeleteTimerTask extends TimerTask {

	private DataStoreDAO dataStoreDAO;
	private String key;
	public DeleteTimerTask(DataStoreDAO dataStoreDAO, String key){
		super();
		this.dataStoreDAO = dataStoreDAO;
		this.key = key;
	}
	
	@Override
	public void run() {
		try {
			System.out.println("Deleting the key:" +key +  " now..");
			this.dataStoreDAO.deleteEntry(key);
		} catch (StoreException e) {
			e.printStackTrace();
		}

	}

}
