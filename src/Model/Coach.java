package Model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import utils.E_Levels;

/**
 * Class Coach ~ represent a single Coach in the league, every coach has a
 * current team that being coached by that coach.
 * 
 * @author Java Course Team 2018 - Shai Gutman
 * @author University Of Haifa - Israel
 */
public class Coach extends Employee {
	// -------------------------------Class Members------------------------------
	private Team currentTeam;
	private E_Levels level;
	private Set<Team> teams;

	// -------------------------------Constructors------------------------------
	public Coach(int id, String firstName, String lastName, Date birthdate, Date startWorkingDate, Address address,
			E_Levels level) {
		super(id, firstName, lastName, birthdate, startWorkingDate, address);
		this.level = level;
		this.teams = new HashSet<Team>();
	}

	public Coach(int id) {
		super(id);
	}

	// -------------------------------Getters And
	// Setters------------------------------
	public Team getCurrentTeam() {
		return currentTeam;
	}

	public void setCurrentTeam(Team currentTeam) {
		this.currentTeam = currentTeam;
	}

	public E_Levels getLevel() {
		return level;
	}

	public void setLevel(E_Levels level) {
		this.level = level;
	}

	public Set<Team> getTeams() {
		return teams;
	}

	public void setTeams(Set<Team> teams) {
		this.teams = teams;
	}

	// -------------------------------More Methods------------------------------
	/**
	 * This method adds the coach to the given team and removes the coach from its
	 * current team only if the given team doesn't equal to the Coach's current
	 * team.
	 *
	 * @param team
	 * @return true if the coach was added successfully to team, false otherwise
	 */
	public boolean transferTo(Team team) {
		if (team == null) {// if team is null, return false
			return false;
		}
		if(team.getCoach()!=null&&team.getCoach().equals(this)) {//if coach already coaches this team, return false
			return false;
		}
		if (currentTeam != null && team.equals(currentTeam)) {// if this current team equals the new team, return false
			return false;
		}
		if (currentTeam != null && !addTeam(currentTeam)) {// if cannot add current team to past teams array, return
															// false
			return false;
		}
		if (teams.contains(team)) {// if the team was already coached, remove from past teams array
			teams.remove(team);
		}
		setCurrentTeam(team);// set current team to new team
		return true;// return true
	}

	/**
	 * This method adds a new team to the teams array only if the given team doesn't
	 * already exists in the Coach's teams array.
	 * 
	 * @param team
	 * @return true if the team was added successfully, false otherwise
	 */
	protected boolean addTeam(Team team) {
		if (team == null) {// if team is null,return false
			return false;
		}
		if (teams.contains(team)) {// if team exists already, return false
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
	protected boolean removeTeam(Team team) {
		if (team == null) {// if team is null, return false
			return false;
		}
		if (!teams.contains(team)) {// if team does not exist in teams array, return false
			return false;
		}
		if (!teams.remove(team)) {// if cannot remove team, return false
			return false;
		}
		return true;// return true
	}

}
