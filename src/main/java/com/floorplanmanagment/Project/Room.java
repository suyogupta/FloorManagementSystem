package com.floorplanmanagment.Project;

import java.sql.Timestamp;



import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Room {
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// @Id
	private int id;
	
	private int status;
	
	private int capacity;

	@Id
	private Timestamp timestamp;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setTimestamp(Timestamp timestamp)
	{
		this.timestamp = timestamp;
	}

	public Timestamp getTimestamp()
	{
		return this.timestamp;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}


	public int getStatus() {
		return status;
	}

	public Room(int id, int status, int capacity, Timestamp timestamp) {
		super();
		this.id = id;
		this.status = status;
		this.capacity = capacity;
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "Room [id=" + id + ", status=" + status + ", capacity=" + capacity + ", timestamp=" + timestamp.toString()+"]";
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Room() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
