package it.polito.tdp.formulaone.model;

public class LapTime implements Comparable<LapTime>{

	private int raceId ; // refers to {@link Race}
	private int driverId ; // refers to {@link Driver}
	private int lap ;
	// NOT: only the combination of the 3 fields (raceId, driverId, lap) is guaranteed to be unique
	private int position ;
	private String time ; // printable version of lap time
	private int miliseconds ; // numerical version, sutable for computations
	
	private long index;
	
	public LapTime(int raceId, int driverId, int lap, int position, String time, int miliseconds, long indexPrima) {
		super();
		this.raceId = raceId;
		this.driverId = driverId;
		this.lap = lap;
		this.position = position;
		this.time = time;
		this.miliseconds = miliseconds;
		this.index = miliseconds + indexPrima;
	}
	public int getRaceId() {
		return raceId;
	}
	public void setRaceId(int raceId) {
		this.raceId = raceId;
	}
	public int getDriverId() {
		return driverId;
	}
	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}
	public int getLap() {
		return lap;
	}
	public void setLap(int lap) {
		this.lap = lap;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getMiliseconds() {
		return miliseconds;
	}
	public void setMiliseconds(int miliseconds) {
		this.miliseconds = miliseconds;
	}
	
	public long getIndex() {
		return index;
	}
	public void setIndex(long index) {
		this.index = index;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + driverId;
		result = prime * result + lap;
		result = prime * result + raceId;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LapTime other = (LapTime) obj;
		if (driverId != other.driverId)
			return false;
		if (lap != other.lap)
			return false;
		if (raceId != other.raceId)
			return false;
		return true;
	}
	@Override
	public int compareTo(LapTime lp) {
		// TODO Auto-generated method stub
		return (int) (this.index - lp.index);
	}
}
