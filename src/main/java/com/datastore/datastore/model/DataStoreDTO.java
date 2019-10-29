package com.datastore.datastore.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DataStoreDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 905765160354955498L;

	private Object value;
	
	private LocalDateTime keyCreatedTime;
	private int timeToLive;
	private boolean isTimeToLivePresent;
	
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public LocalDateTime getKeyCreatedTime() {
		return keyCreatedTime;
	}
	public void setKeyCreatedTime(LocalDateTime keyCreatedTime) {
		this.keyCreatedTime = keyCreatedTime;
	}
	public long getTimeToLive() {
		return timeToLive;
	}
	public void setTimeToLive(int timeToLive) {
		this.timeToLive = timeToLive;
	}
	public boolean isTimeToLivePresent() {
		return isTimeToLivePresent;
	}
	public void setTimeToLivePresent(boolean isTimeToLivePresent) {
		this.isTimeToLivePresent = isTimeToLivePresent;
	}

}
