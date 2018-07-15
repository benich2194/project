package Model;

import java.util.HashSet;
import java.util.Set;

import utils.Constants;

/**
 * Class Stadium ~ represent a single stadium of the league
 * 
 * @author Java Course Team 2018 - Shai Gutman
 * @author University Of Haifa - Israel
 */
public class Stadium {
	// -------------------------------Class Members------------------------------
	private int id;
	private String name;
	private Address address;
	private int capacity;
	private Set<Receptionist> receptionists;
	private Set<Team> teams;
	private Set<Match> matches;

	// -------------------------------Constructors------------------------------
	public Stadium(int id, String name, Address address, int capacity) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.capacity = capacity;
		this.teams = new HashSet<Team>();
		this.matches = new HashSet<Match>();
		this.receptionists = new HashSet<Receptionist>();
	}

	public Stadium(int id) {
		this.id = id;
	}

	// -------------------------------Getters And
	// Setters------------------------------

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public Set<Receptionist> getReceptionists() {
		return receptionists;
	}

	public void setReceptionists(Set<Receptionist> receptionists) {
		this.receptionists = receptionists;
	}

	public Set<Team> getTeams() {
		return teams;
	}

	public void setTeams(Set<Team> teams) {
		this.teams = teams;
	}

	public Set<Match> getMatches() {
		return matches;
	}

	public void setMatches(Set<Match> matches) {
		this.matches = matches;
	}

	// -------------------------------More Methods------------------------------
	/**
	 * This method adds a new receptionist to the receptionists array only if the
	 * given receptionist doesn't already exist in the Stadium's receptionists
	 * array.
	 * 
	 * @param receptionist
	 * @return true if the receptionist was added successfully, false otherwise
	 */
	public boolean addReceptionist(Receptionist receptionist) {
		if (receptionist == null) {// if receptionist is null, return false
			return false;
		}
		if (receptionists.size() >= Constants.MAX_RESEPTIONISTS_FOR_STADIUM) {// if no more room for receptionists in
																				// stadium return false;
			return false;
		}
		if (receptionists.contains(receptionist)) {// if receptionists array containts receptionist, return false
			return false;
		}
		if (!receptionists.add(receptionist)) {// if unable to add receptionist, return false
			return false;
		}
		return true;// return true
	}

	/**
	 * This method removes a given receptionist form the receptionists array.
	 * 
	 * @param receptionist
	 * @return true if the receptionist was removed successfully, false otherwise
	 */
	public boolean removeReceptionist(Receptionist receptionist) {
		if (receptionist == null) {// if receptionist is null, return false
			return false;
		}
		if (!receptionists.contains(receptionist)) {// if receptionists array does not contain receptionist, return
													// false
			return false;
		}
		if (!receptionists.remove(receptionist)) {// if unable to remove receptionist from receptionists array,return
													// false
			return false;
		}
		return true;// return true
	}

	/**
	 * This method adds a new team to the teams array only if the given team doesn't
	 * already exists in the Stadium's teams array.
	 * 
	 * @param team
	 * @return true if the team was added successfully, false otherwise
	 */
	public boolean addTeam(Team team) {
		if (team == null) {// if team is null, return false
			return false;
		}
		if (teams.size() >= Constants.MAX_TEAMS_FOR_STADIUM) {// if there is no more room for teams in the stadium,
																// return false
			return false;
		}
		if (teams.contains(team)) {// if team already exists, return false
			return false;
		}
		if (!teams.add(team)) {// if cannot add team, return false
			return false;
		}
		return true;// return true
	}

	/**
	 * This method removes a given team form the teams array.
	 * 
	 * @param team
	 * @return true if the team was removed successfully, false otherwise
	 */
	public boolean removeTeam(Team team) {
		if (team == null) {// if team is null, return false
			return false;
		}
		if (!teams.contains(team)) {// if team does not exists in teams array, return false
			return false;
		}
		if (!teams.remove(team)) {// if cannot remove team, return false
			return false;
		}
		return true;// return true
	}

	/**
	 * This method adds a new match to the matches array only iF the match doesn't
	 * already exists in the Stadium's matches array and the time doesn't overlap
	 * with any other match in the array
	 * 
	 * @param match
	 * @return true if the match was added successfully, false otherwise
	 */
	public boolean addMatch(Match match) {
		if (match == null) {// if match is null return false
			return false;
		}
		if (matches.contains(match)) {// if match already exists, return false
			return false;
		}
		for (Match m : matches) {
			if ((m.getStartDateTime().before(match.getFinishDateTime())
					|| m.getStartDateTime().equals(match.getFinishDateTime()))
					&& (match.getFinishDateTime().before(m.getFinishDateTime())
							|| match.getFinishDateTime().equals(m.getFinishDateTime()))) {
				return false;// if match overlaps with other matches,return false
			}
		}
		if (!matches.add(match)) {// if unable to add match, return false
			return false;
		}
		return true;// return true
	}

	/**
	 * This method removes a given match from the matches array.
	 * 
	 * @param match
	 * @return true if match was removed successfully, false otherwise
	 */
	public boolean removeMatch(Match match) {
		if (match == null) {// if match is null, return false
			return false;
		}
		if (!matches.contains(match)) {// if match does not exists, return false
			return false;
		}
		if (!matches.remove(match)) {// if cannot remove match, return false
			return false;
		}
		return true;// return true
	}

	// -------------------------------hashCode equals &
	// toString------------------------------
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
		Stadium other = (Stadium) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Stadium | id: " + id + ", name: " + name + ", capacity: " + capacity + ", address: " + address;
	}

}
