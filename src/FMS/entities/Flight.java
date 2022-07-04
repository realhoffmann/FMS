package FMS.entities;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import FMS.provided.Aircraft;
import FMS.provided.DateTime;
import FMS.provided.Passenger;
import FMS.provided.Staff;
import com.sun.jdi.IntegerValue;


public abstract class Flight implements Comparable<Flight> {
	private String flightID; //the number of this flight. example: OS501. must be non-null and non-empty.
	private String destination; //the destination of this flight as a three letter code. example: VIE. must be non-null and non-empty.
	private String origin; //the origin of this flight. example: VIE. must be non-null and non-empty.
	private DateTime departure; //the scheduled departure of this flight in UTC.
	private DateTime arrival; //the scheduled arrival of this flight in UTC.
	private Aircraft vessel; //the aircraft assigned to this flight, if any. when no aircraft has been assigned yet this is null.
	private java.util.Set<Staff> crew = new HashSet<>(); //the crew members of this flight.
	private java.util.Set<Passenger> passengers = new HashSet<>(); //the passengers added (listed) on this flight.

	public Flight(String flightID, String origin, String destination, DateTime departure, DateTime arrival){
		this.flightID = flightID;
		this.origin = origin;
		this.destination = destination;
		this.departure = departure;
		this.arrival = arrival;
	}
	public Flight(Flight f){
		this.flightID = f.flightID;
		this.origin = f.origin;
		this.destination = f.destination;
		this.departure = new DateTime(f.departure);
		this.arrival = new DateTime(f.arrival);
		this.vessel = new Aircraft(f.vessel);

		Set<Staff> crew = new HashSet<>();
		for(Staff s : f.crew){
			crew.add(new Staff(s));
		}
		Set<Passenger> passengers = new HashSet<>();
		for(Passenger p : f.passengers){
			passengers.add(new Passenger(p));
		}
	}
	private final String ensureNonNullNonEmpty(String str){
		if(str.isEmpty() || str == null){
			throw new IllegalArgumentException();
		}
		return str;
	}
	public final void setFlightID(String flightID){
		this.flightID = ensureNonNullNonEmpty(flightID);
	}
	public final void setDestination(String destination){
		this.destination = ensureNonNullNonEmpty(destination);
	}
	public String getOrigin(){
		return origin;
	}
	public final void setOrigin(String origin){
			this.origin = ensureNonNullNonEmpty(origin);
	}
	public DateTime getDeparture(){
		return new DateTime(departure);
	}
	public final void setDeparture(DateTime departure){
		this.departure = new DateTime(departure);
	}
	public final void setArrival(DateTime arrival){
		this.arrival = new DateTime(arrival);
	}

	public abstract int getBonusMiles();

	public int compareTo(Flight o){
		return this.flightID.compareTo(o.flightID);
	}

	@Override
	public String toString() {
		return String.format(
				"%5s from %3.3S (%s) to %3.3S (%s)" + " with a crew of %d and %d passengers "
						+ "<%s> boarding%scompleted \n%s\n%s",
				flightID, origin, departure, destination, arrival, 
				crew == null ? 0 : crew.size(),
				passengers == null ? 0 : passengers.size(),
				vessel == null ? "no vessel" : vessel.toString(),
				boardingCompleted() ? " " : " not ",
				crew,
				passengers);
	}
	public boolean add(Staff staff){
		if(!crew.contains(staff)) {
			crew.add(staff);
			return true;
		}else {
			return false;
		}
	}
	public boolean add(Passenger passenger){

		if(!passengers.contains(passenger) && (vessel == null || vessel.getCapactiy() >= passengers.size())){
			passengers.add(passenger);
			return true;
		}else {
			return false;
		}
	}

	public boolean readyToBoard(){
		if(crew.size() > 0){
			return true;
		}else
			return false;
	}
	public boolean boardingCompleted(){
		if(passengers.size() == 0) {
			return false;
		}
		for(Passenger p : passengers){
			if(!this.equals(p.getBoarded())){
				return false;
				}
			}
		return true;
	}
	public boolean board(Passenger p){
		if(readyToBoard() == true && passengers.contains(p)){
			return true;
		}else{
			return false;
		}
	}
	public String getFlightId(){
		return flightID;
	}
	public Flight setVessel(Aircraft vessel){
		this.vessel = vessel;
		return this;
	}


}
