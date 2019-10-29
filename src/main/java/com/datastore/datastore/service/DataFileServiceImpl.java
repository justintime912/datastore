package com.datastore.datastore.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.datastore.datastore.StoreException;
import com.datastore.datastore.common.DataStoreConstants;
import com.datastore.datastore.model.DataStoreDTO;


@Service
public class DataFileServiceImpl implements DataFileService{
	
	@Value("${datastore.location}")
	private String dataStoreLocation;
	
	private Map<String,DataStoreDTO> dataMap = new ConcurrentHashMap<>();
	private static Object lock = new Object();
	
	@PostConstruct
    public void init() {
		this.initializeMap();
    }
	
	private void initializeMap() {
		try {
			FileInputStream fi = new FileInputStream(new File(dataStoreLocation));
			try(ObjectInputStream oi = new ObjectInputStream(fi)){
				dataMap = (Map<String, DataStoreDTO>) oi.readObject();
			}
			
		} catch (IOException | ClassNotFoundException e) {
			//dataMap would be set to empty hashMap!
			dataMap = new HashMap<>();
		}
		
	}
	
	@Override
	public void writeToDisk(Map<String,DataStoreDTO> dataMap) throws StoreException {
		File dataStoreFile = new File(dataStoreLocation);
		
		if(!dataStoreFile.exists()) {
			try {
				dataStoreFile.createNewFile();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}else {
			long totalSpaceOccupied = dataStoreFile.length();
			if(totalSpaceOccupied > DataStoreConstants.ONE_GB_SIZE) {
				throw new StoreException(DataStoreConstants.ResponseMessageStatus.SIZE_EXCEEDED.name());
			}
		}
		
		try(FileOutputStream f = new FileOutputStream(dataStoreFile)) {
			ObjectOutputStream o = new ObjectOutputStream(f);
			o.writeObject(dataMap);
			synchronized(lock) {
				this.dataMap  = dataMap;
			}
			
		}catch( FileNotFoundException ex) {
			throw new StoreException(ex.getMessage());	
		}catch ( IOException io) {
			throw new StoreException(io.getMessage());
		}
	}

	/**
	 *Returns a copy of the map
	 */
	@Override
	public Map<String, DataStoreDTO> getDataMap(){
		Map<String,DataStoreDTO> dataMapCopy = new HashMap<>();
		
		this.dataMap.forEach((key,value)->{
			DataStoreDTO valueCopy = new DataStoreDTO();
			BeanUtils.copyProperties(value, valueCopy);
			dataMapCopy.put(key, valueCopy);
		});
		return dataMapCopy;
	}

	@Override
	public DataStoreDTO deleteEntry(String key) throws StoreException {
		Map<String, DataStoreDTO> dataMap2 = this.getDataMap();
		DataStoreDTO remove = dataMap2.remove(key);
		this.writeToDisk(dataMap2);
		return remove;
		 
	}

}
