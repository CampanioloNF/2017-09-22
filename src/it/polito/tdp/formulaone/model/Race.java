package it.polito.tdp.formulaone.model;

import java.time.LocalDate;
import java.time.LocalTime;


public class Race implements Comparable<Race>{
	
	private int raceId ;
	private int year ;
	private int round ;
	private int circuitId ; // refers to {@link Circuit}
	private String name ;
	private LocalDate date ;
	private LocalTime time ;
	private String url ;
	public Race(int raceId, int year, int round, int circuitId, String name, LocalDate date, LocalTime time,
			String url) {
		super();
		this.raceId = raceId;
		this.year = year;
		this.round = round;
		this.circuitId = circuitId;
		this.name = name;
		this.date = date;
		this.time = time;
		this.url = url;
	}
	public int getRaceId() {
		return raceId;
	}
	public void setRaceId(int raceId) {
		this.raceId = raceId;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getRound() {
		return round;
	}
	public void setRound(int round) {
		this.round = round;
	}
	public int getCircuitId() {
		return circuitId;
	}
	public void setCircuitId(int circuitId) {
		this.circuitId = circuitId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public LocalTime getTime() {
		return time;
	}
	public void setTime(LocalTime time) {
		this.time = time;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String toString() {
		return this.name;
	}
	@Override
	public int compareTo(Race r) {
		// TODO Auto-generated method stub
		return this.name.compareTo(r.name);
	}

}
