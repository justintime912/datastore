package com.datastore.datastore.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datastore.datastore.StoreException;
import com.datastore.datastore.common.DataStoreConstants;
import com.datastore.datastore.model.DataStoreDTO;
import com.datastore.datastore.service.DataFileService;
import com.datastore.datastore.service.DeleteTimerTask;

@Repository
public class DataStoreDAOImpl implements DataStoreDAO {
	
	@Autowired
	private DataFileService dataFileService;
	
	private List<Timer> schedulers = new ArrayList<>(); 
	
	@Override
	public String createEntry(String key, DataStoreDTO value) throws StoreException {
		Map<String, DataStoreDTO> keyValueMap = dataFileService.getDataMap();
		keyValueMap.put(key, value);
		//Store this map to File system
		dataFileService.writeToDisk(keyValueMap);
		
		//Schedule the timer
		if(value.isTimeToLivePresent()) {
			Timer timer = new Timer();
			timer.schedule(  new DeleteTimerTask(this,key), value.getTimeToLive()*1000);
			schedulers.add(timer);
		}
		
		String successMessage = String.format("Key %s created successfully in store", key);
		return successMessage;
		
	}

	@Override
	public DataStoreDTO readEntry(String key) throws StoreException {
		Map<String, DataStoreDTO> keyValueMap = dataFileService.getDataMap();
		boolean isKeyPresent = keyValueMap.containsKey(key);
		if(isKeyPresent) {
			return keyValueMap.get(key);
		}else {
			throw new StoreException(DataStoreConstants.ResponseMessageStatus.KEY_NOT_PRESENT.name());
		}
	}

	@Override
	public DataStoreDTO deleteEntry(String key) throws StoreException {
		
		Map<String, DataStoreDTO> keyValueMap = dataFileService.getDataMap();
		boolean isKeyPresent = keyValueMap.containsKey(key);
		if(isKeyPresent) {
			return dataFileService.deleteEntry(key);
		}else {
			throw new StoreException(DataStoreConstants.ResponseMessageStatus.KEY_NOT_PRESENT.name());
		}
		
	}

	@Override
	public Set<String> getAllKeys() {
		Map<String, DataStoreDTO> keyValueMap = dataFileService.getDataMap();
		Set<String> keySet = keyValueMap.keySet();
		Set<String> keySetCopy = new HashSet<>();
		keySet.forEach(key->keySetCopy.add(key));
		return keySetCopy;
	}

}
