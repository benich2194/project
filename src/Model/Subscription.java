package Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import utils.E_Periods;

/**
 * Class Subscription ~ represent a single Subscription in the company Each
 * Subscription belongs to a specific Customer
 * 
 * @author Java Course Team 2018 - Shai Gutman
 * @author University Of Haifa - Israel
 */
public class Subscription {
	// -------------------------------Class Members------------------------------
	private int id;
	private Customer customer;
	private Receptionist receptionist;
	private E_Periods period;
	private Date startDate;
	private Set<Match> matches;

	// -------------------------------Constructors------------------------------
	public Subscription(int id, Customer customer, Receptionist receptionist, E_Periods period, Date startDate) {
		this.id = id;
		this.customer = customer;
		this.receptionist = receptionist;
		this.period = period;
		this.startDate = startDate;
		this.matches=new HashSet<Match>();
	}

	public Subscription(int id) {
		this.id = id;
	}

	// -------------------------------Getters And Setters------------------------------
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<Match> getMatches() {
		return matches;
	}

	public void setMatches(Set<Match> matches) {
		this.matches = matches;
	}

	public Receptionist getReceptionist() {
		return receptionist;
	}
	
	public void setReceptionist(Receptionist receptionist) {
		this.receptionist = receptionist;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer resCustomer) {
		this.customer = resCustomer;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public E_Periods getPeriod() {
		return period;
	}

	public void setPeriod(E_Periods period) {
		this.period = period;
	}

	// -------------------------------More Methods------------------------------
	/**
	 * This method calculate the last date the subscription is valid for
	 * 
	 * @see utils.E_Periods
	 * @return lastDate of the subscription if no nulls, null otherwise
	 */
	public Date getLastDay() {
		if(this.startDate==null) {//if start date is null, return null
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.startDate);
		cal.add(Calendar.MONTH, this.getPeriod().getNumber());//using calendar instance, adds to month the period of subscription
		return cal.getTime();// calculates finish date and returns it
	}

	/**
	 * This method adds a new match to the matches array
	 * only iF the match doesn't already exists in the Subscription's matches array, match is valid 
	 * and the time doesn't overlap with any other match in the array
	 * 
	 * @param match
	 * @return true if the match was added successfully, false otherwise
	 */
	public boolean addMatch(Match match) {
		if(match==null) {//if match is null,return false
			return false;
		}
		if(matches.contains(match)) {//if match already exists, return false
			return false;
		}
		for(Match m:matches) {
			if((m.getStartDateTime().before(match.getFinishDateTime())||m.getStartDateTime().equals(match.getFinishDateTime()))&&(match.getFinishDateTime().before(m.getFinishDateTime())||match.getFinishDateTime().equals(m.getFinishDateTime()))) {
				return false;//if match overlaps with other matches,return false
			}
		}
		if(!matches.add(match)) {//if unable to add match, return false
			return false;
		}
		return true;//return true
	}
	
	/**
	 * This method removes a given match from the matches array.
	 * 
	 * @param match
	 * @return true if match was removed successfully, false otherwise
	 */
	public boolean removeMatch(Match match) {
		if(match==null) {//if match is null, return false
			return false;
		}
		if(!matches.contains(match)) {//if matches does not exist, return false
			return false;
		}
		if(!matches.remove(match)) {//if unable to remove match, return false
			return false;
		}
		return true;//return true
	}

	// -------------------------------hashCode equals & toString------------------------------
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Subscription other = (Subscription) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		DateFormat dft = new SimpleDateFormat("dd/MM/yyyy;HH:mm");
		return "Subscription | id: " + id + ", customer: " + customer.getFirstName() + " " + customer.getLastName() 
				+ ", receptionist: " + receptionist.getFirstName() + " " + receptionist.getLastName() 
				+ ", period: " + period + ", start date: " + dft.format(startDate);
	}

}
