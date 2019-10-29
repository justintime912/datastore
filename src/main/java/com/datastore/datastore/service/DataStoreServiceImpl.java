package com.datastore.datastore.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datastore.datastore.StoreException;
import com.datastore.datastore.common.DataStoreConstants;
import com.datastore.datastore.dao.DataStoreDAO;
import com.datastore.datastore.model.DataStoreDTO;

@Service
public class DataStoreServiceImpl implements DataStoreService {

	@Autowired
	DataStoreDAO dataStoreDAO;
	
	@Override
	public String createEntry(String key, Object value, Optional<Integer> timeToLive) throws StoreException {
		Optional<String> existingKey = dataStoreDAO.getAllKeys().stream().filter(entryKey -> entryKey.equals(key)).findFirst();
		if(existingKey.isPresent()) {
			throw new StoreException(DataStoreConstants.ResponseMessageStatus.KEY_ALREADY_EXISTS.name());
		}
		DataStoreDTO dataStoreDTO = new DataStoreDTO();
		dataStoreDTO.setValue(value);
		dataStoreDTO.setKeyCreatedTime(LocalDateTime.now());
		if(timeToLive.isPresent()) {
			dataStoreDTO.setTimeToLive(timeToLive.get());
			dataStoreDTO.setTimeToLivePresent(true);
		}
		
		return dataStoreDAO.createEntry(key, dataStoreDTO);
		
	}

	@Override
	public DataStoreDTO readEntry(String key) throws StoreException {
		
		return dataStoreDAO.readEntry(key);
	}

	@Override
	public DataStoreDTO deleteEntry(String key) throws StoreException {
		
		return dataStoreDAO.deleteEntry(key);
	}

}
