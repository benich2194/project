package Controller;

import Model.*;
import utils.*;

import java.net.URL;
import java.util.*;

/**
 * This SysData object ~ represents the class system
 * 
 * @author Java Course Team 2018 - Shai Gutman
 * @author University Of Haifa - Israel
 */
@SuppressWarnings("rawtypes")
public class SysData {
	// -------------------------------Class Members------------------------------
	private static SysData instance;
	private HashMap<Integer, Coach> coaches;
	private HashMap<Integer, Receptionist> receptionists;
	private HashMap<Integer, Player> players;
	private HashMap<Integer, Stadium> stadiums;
	private HashMap<Integer, Team> teams;
	private HashMap<String, Customer> customers;
	private HashMap<Integer, Match> matches;
	private HashSet<Trophy> trophies;

	// -------------------------------Constructors------------------------------
	private SysData() {
		coaches = new HashMap<>();
		receptionists = new HashMap<>();
		players = new HashMap<>();
		stadiums = new HashMap<>();
		teams = new HashMap<>();
		customers = new HashMap<>();
		matches = new HashMap<>();
		trophies = new HashSet<>();
	}

	// -----------------------------------------Getters--------------------------------------
	public HashMap<Integer, Coach> getCoachs() {
		return coaches;
	}

	public HashMap<Integer, Receptionist> getReceptionists() {
		return receptionists;
	}

	public HashMap<Integer, Player> getPlayers() {
		return players;
	}

	public HashMap<Integer, Team> getTeams() {
		return teams;
	}

	public HashMap<Integer, Stadium> getStadiums() {
		return stadiums;
	}

	public HashMap<String, Customer> getCustomers() {
		return customers;
	}

	public HashMap<Integer, Match> getMatchs() {
		return matches;
	}

	public static SysData getInstance() {
		if (instance == null)
			instance = new SysData();
		return instance;
	}

	// -------------------------------Add && Remove
	// Methods------------------------------
	/**
	 * This method adds a new stadium to the JavaLeague only if all the parameters
	 * are valid and the stadium doesn't already exist in the system
	 * 
	 * @param id
	 * @param name
	 * @param capacity
	 * @param city
	 * @param street
	 * @param houseNumber
	 * @param phoneNumber
	 * @return true if the stadium was added successfully, false otherwise
	 */
	public boolean addStadium(int id, String name, int capacity, E_Cities city, String street, int houseNumber,
			String[] phoneNumber) {
		if (id < 0 || name == null || capacity < 1 || city == null || street == null || houseNumber < 0
				|| phoneNumber == null) {// if one of parameter is not valid, return false
			return false;
		}
		if (stadiums.containsKey(id)) {// if stadium already exists, return false
			return false;
		}
		stadiums.put(id, new Stadium(id, name, new Address(city, street, houseNumber, phoneNumber), capacity));// add
																												// stadium
																												// to
																												// stadium
																												// map
		return true;// return true
	} // ~ END OF addStadium

	/**
	 * This method adds a new team to the JavaLeague only if all the parameters are
	 * valid and the team doesn't already exist in the system IMPORTENT: In order to
	 * add a new team, the team must be added to it's stadium first. Don't forget to
	 * roll-back :)
	 * 
	 * @param id
	 * @param name
	 * @param maxNumOfPlayers
	 * @param minNumOfPlayers
	 * @param value
	 * @param level
	 * @param stadiumId
	 * @return true if the team was added successfully, false otherwise
	 */
	public boolean addTeam(int id, String name, int value, E_Levels level, int stadiumId) {
		if (id < 0 || name == null || value < 0 || level == null || !stadiums.containsKey(stadiumId)) {// if one of
																										// parameters is
																										// invalid or
																										// stadium does
																										// not exist,
																										// return false
			return false;
		}
		if (teams.containsKey(id)) {// if team already exists, return false
			return false;
		}
		Team myTeam = new Team(id, name, value, level, stadiums.get(stadiumId));// create new team
		if (!stadiums.get(stadiumId).addTeam(myTeam)) {// if cannot add to stadium, return false
			return false;
		}
		teams.put(id, myTeam);// add team
		return true;// return true
	} // ~ END OF addTeam

	/**
	 * This method adds a new coach to the JavaLeague only if all the parameters are
	 * valid and the coach doesn't already exist in the system
	 * 
	 * @param id
	 * @param firstName
	 * @param lastName
	 * @param birthdate
	 * @param startWorkingDate
	 * @param level
	 * @param teamId
	 * @param address
	 * @return true if the coach was added successfully, false otherwise
	 */
	public boolean addCoach(int id, String firstName, String lastName, Date birthdate, Date startWorkingDate,
			E_Levels level, Address address) {
		if (id < 0 || firstName == null || lastName == null || birthdate == null || startWorkingDate == null
				|| level == null || address == null) {// if one of the paramters is invalid,return false
			return false;
		}
		if (birthdate.after(startWorkingDate)) {// if coach was born after start working date, return false
			return false;
		}
		if (coaches.containsKey(id)) {// if coach exists, return false
			return false;
		}
		Coach myCoach = new Coach(id, firstName, lastName, birthdate, startWorkingDate, address, level);// create coach
		coaches.put(id, myCoach);// add coach to coaches array
		return true;// return true
	} // ~ END OF addCoach

	/**
	 * This method adds a new player to the JavaLeague only if all the parameters
	 * are valid and the player doesn't already exist in the system
	 * 
	 * @param id
	 * @param firstName
	 * @param lastName
	 * @param birthdate
	 * @param startWorkingDate
	 * @param level
	 * @param value
	 * @param isRightLegKicker
	 * @param position
	 * @param address
	 * @return true if the player was added successfully, false otherwise
	 */
	public boolean addPlayer(int id, String firstName, String lastName, Date birthdate, Date startWorkingDate,
			E_Levels level, long value, Boolean isRightLegKicker, E_Position position, Address address) {
		if (id < 0 || firstName == null || lastName == null || birthdate == null || startWorkingDate == null
				|| level == null || value < 0 || position == null || address == null) {// if one of the parameters is
																						// invalid, return false
			return false;
		}
		if (players.containsKey(id)) {// if player exists, return false
			return false;
		}
		if (birthdate.after(startWorkingDate)) {// if birthdate is after start working date, return false
			return false;
		}
		Date today = new Date();
		if (birthdate.after(today)) {// if still wasn't born, return false
			return false;
		}
		Player myPlayer = new Player(id, firstName, lastName, birthdate, startWorkingDate, address, level, value,
				isRightLegKicker, position);// create player
		players.put(id, myPlayer);// add player
		return true;// return true
	} // ~ END OF addPlayer

	/**
	 * This method adds a new receptionist to the JavaLeague only if all the
	 * parameters are valid and the receptionist doesn't already exist in the system
	 * 
	 * @param id
	 * @param firstName
	 * @param lastName
	 * @param birthdate
	 * @param startWorkingDate
	 * @param stadiumId
	 * @param address
	 * @return true if the receptionist was added successfully, false otherwise
	 */
	public boolean addReceptionist(int id, String firstName, String lastName, Date birthdate, Date startWorkingDate,
			Address address) {
		if (id < 0 || firstName == null || lastName == null || birthdate == null || startWorkingDate == null
				|| address == null) {// if one of the parameters is invalid, return false
			return false;
		}
		if (birthdate.after(startWorkingDate) || birthdate.after(new Date())) {// if birthdate is after startworkingdate
																				// or birthdate is after today,return
																				// false
			return false;
		}
		if (receptionists.containsKey(id)) {// if receptionist already exists, return false
			return false;
		}
		receptionists.put(id, new Receptionist(id, firstName, lastName, birthdate, startWorkingDate, address));// add
																												// receptionist
		return true;// return true
	} // ~ END OF addReceptionist

	/**
	 * This method adds a new customer to the JavaLeague only if all the parameters
	 * are valid and the customer doesn't already exist in the system IMPORTENT:
	 * Every customer has a favorite team, if the team doesn't exist in the system
	 * the customer cannot be added :(
	 * 
	 * @param id
	 * @param firstName
	 * @param lastName
	 * @param birthDate
	 * @param level
	 * @param email
	 * @param favoriteTeamId
	 * @param address
	 * @return true if the customer was added successfully, false otherwise
	 */
	public boolean addCustomer(String id, String firstName, String lastName, Date birthDate, E_Levels level, URL email,
			int favoriteTeamId, Address address) {
		if (id.length() < Constants.ID_NUMBER_SIZE || firstName == null || lastName == null || birthDate == null
				|| level == null || email == null || address == null) {// if one of the parameters is invalid,return
																		// false
			return false;
		}
		if (!teams.containsKey(favoriteTeamId)) {// if favorite team doesnt exist, return false
			return false;
		}
		if (customers.containsKey(id)) {// if customer already exists, return false
			return false;
		}
		if (birthDate.after(new Date())) {// if was born after today, return false
			return false;
		}
		customers.put(id,
				new Customer(id, firstName, lastName, birthDate, level, email, address, teams.get(favoriteTeamId)));
		return true;
	}// ~ END OF addCustomer

	/**
	 * This method adds an existing coach to an existing team of the JavaLeague only
	 * if all the parameters are valid and the both coach & team already exist in
	 * the system IMPORTENT: If the given coach already belongs to another team,
	 * transfer the coach to the given team.
	 * 
	 * @param coachId
	 * @param teamId
	 * @return true if the coach was added successfully to team, false otherwise
	 */
	public boolean addCoachToTeam(int coachId, int teamId) {
		if (!coaches.containsKey(coachId) || !teams.containsKey(teamId)) {// if one of them doesnt exist, return false
			return false;
		}
		if (teams.get(teamId).getCoach() != null) {// if team already has a coach, return false
			return false;
		}
		if (coaches.get(coachId).getCurrentTeam() != null
				&& coaches.get(coachId).getCurrentTeam().equals(teams.get(teamId))) {// if the coach current team is
																						// this team, return false
			return false;
		}
		if (coaches.get(coachId).getCurrentTeam() != null) {// if the coach already has a team, add him to team than
															// transfer coach team
			if (!teams.get(teamId).registerCoach(coaches.get(coachId))) {
				return false;
			}
			if (!coaches.get(coachId).transferTo(teams.get(teamId))) {
				teams.get(teamId).setCoach(null);
				return false;
			}
			return true;// return true
		} else {
			coaches.get(coachId).setCurrentTeam(teams.get(teamId));// if he doesn't have a team, set a team to coach and
																	// add to team register coach
			if (!teams.get(teamId).registerCoach(coaches.get(coachId))) {
				coaches.get(coachId).setCurrentTeam(null);// if failed, rollback and sets coach current team to null
				return false;// return false
			}
			return true;// return true
		}
	}// ~ END OF addCoachToTeam

	/**
	 * This method adds an existing player to an existing team of the JavaLeague
	 * only if all the parameters are valid and the both player & team already exist
	 * in the system IMPORTENT: If the given player already belongs to another team,
	 * transfer the player to the given team.
	 * 
	 * @param playerId
	 * @param teamId
	 * @return true if the player was added successfully to team, false otherwise
	 */
	public boolean addPlayerToTeam(int playerId, int teamId) {
		if (!players.containsKey(playerId) || !teams.containsKey(teamId)) {// if player or team does not exist, return
																			// false
			return false;
		}
		if (teams.get(teamId).getPlayers().size() == Constants.MAX_PLAYERS_FOR_TEAM) {// if maximum players has reached,
																						// return false
			return false;
		}
		if (players.get(playerId).getCurrentTeam() != null
				&& players.get(playerId).getCurrentTeam().equals(teams.get(teamId))) {// if already in the team, return
																						// false
			return false;
		}
		if (players.get(playerId).getCurrentTeam() != null
				&& players.get(playerId).getCurrentTeam().getId() != teamId) {// if player has a team already, transfer
																				// him to this one.if failed, return
																				// false,if succesful, return true;
			if (!players.get(playerId).transferTo(teams.get(teamId))) {
				return false;
			}
			return true;
		} else {// if player does not have a team, add him.if failed ,return false
			if (!teams.get(teamId).addPlayer(players.get(playerId))) {
				return false;
			}
			players.get(playerId).setCurrentTeam(teams.get(teamId));// set player's current team to this team and return
																	// true
			return true;
		}
	}// ~ END OF addCoachToTeam

	/**
	 * This method adds an existing player to an existing team first players only if
	 * all the parameters are valid and the both player & team already exist in the
	 * system
	 * 
	 * @param playerId
	 * @param teamId
	 * @return true if the player was added successfully to team fir players, false
	 *         otherwise
	 */
	public boolean addPlayerToTeamFirstPlayers(int playerId, int teamId) {
		if (players.get(playerId) == null || teams.get(teamId) == null) {// if player or team does not exist, return
																			// false
			return false;
		}
		int counter = 0;
		for (boolean b : teams.get(teamId).getPlayers().values()) {// counts how many first team players
			if (b == true) {
				counter++;
			}
		}
		if (counter == Constants.NUM_OF_FIRST_TEAM_PLAYERS) {// if maximum first team players has reached, return false
			return false;
		}
		if (players.get(playerId).getCurrentTeam() != null
				&& players.get(playerId).getCurrentTeam().equals(teams.get(teamId))
				&& teams.get(teamId).getPlayers().get(players.get(playerId)) == false) {// if player already exist but
																						// not as first team player,
																						// replace him and re add him as
																						// first team player
			if (!teams.get(teamId).replacePlayerOfFirstTeam(players.get(playerId), players.get(playerId))) {
				return false;
			}
			return true;
		}
		if (players.get(playerId).getCurrentTeam() != null) {// if player already has a team and it is not this team,
																// cannot add him so return false
			return false;
		}
		if (!teams.get(teamId).addPlayerToFirstTeam(players.get(playerId))) {// if cannot add player to first team
																				// players, return false
			return false;
		}
		return true;

	}// ~ END OF addPlayerToTeamFirstPlayers

	/**
	 * This method adds an existing receptionist to an existing stadium of the
	 * JavaLeague only if all the parameters are valid and the both receptionis &
	 * stadium already exist in the system
	 * 
	 * @param receptionistId
	 * @param stadiumId
	 * @return true if the receptionist was added successfully to stadium, false
	 *         otherwise
	 */
	public boolean addReceptionistToStadium(int receptionistId, int stadiumId) {
		if (!receptionists.containsKey(receptionistId) || !stadiums.containsKey(stadiumId)) {// if receptionist or
																								// stadium does not
																								// exist, return false
			return false;
		}
		if (stadiums.get(stadiumId).getReceptionists().size() == Constants.MAX_RESEPTIONISTS_FOR_STADIUM) {// if there
																											// are
																											// maximum
																											// receptionists
																											// in
																											// stadium,
																											// return
																											// false
			return false;
		}
		if (receptionists.get(receptionistId) != null
				&& receptionists.get(receptionistId).getWorkingStadium() != null) {// if receptionist alreadhy has a
																					// working stadium, remove from
																					// there and add to this stadium
			if (!stadiums.get(stadiumId).addReceptionist(receptionists.get(receptionistId))) {
				return false;
			}
			if (!receptionists.get(receptionistId).getWorkingStadium()
					.removeReceptionist(receptionists.get(receptionistId))) {
				stadiums.get(stadiumId).removeReceptionist(receptionists.get(receptionistId));// rollback if failed to
																								// add
				return false;
			}
			receptionists.get(receptionistId).setWorkingStadium(stadiums.get(stadiumId));// sets to this stadium as
																							// working stadium
			return true;
		}
		if (!stadiums.get(stadiumId).addReceptionist(receptionists.get(receptionistId))) {// if cannot add receptionist,
																							// return false
			return false;
		}
		receptionists.get(receptionistId).setWorkingStadium(stadiums.get(stadiumId));// set receptionist working stadium
																						// to this stadium
		return true;// return true
	}// ~ END OF addReceptionistToStadium

	/**
	 * This method adds a new subscription to an existing customer of the JavaLeague
	 * only if all the parameters are valid and the customer already exist in the
	 * system IMPORTENT: Every subscription was sold by a receptionist, hence it's
	 * very important that the receprionist belongs to a stadium. ALSO IMPORTENT:
	 * Subscription must be added to all the relevant arrays, Don't forget to
	 * roll-back :)
	 * 
	 * @param subscriptionId
	 * @param customerId
	 * @param receptionistId
	 * @param period
	 * @param startDate
	 * @return true if the suscription was added successfully to customer, false
	 *         otherwise
	 */
	public boolean addSubscriptionToCustomer(int subscriptionId, String customerId, int receptionistId,
			E_Periods period, Date startDate) {
		if (subscriptionId < 0 || customerId.length() != Constants.ID_NUMBER_SIZE || receptionistId < 0
				|| period == null || startDate == null) {// if one of the parameters is invalid, return false
			return false;
		}
		if (!customers.containsKey(customerId)) {// if customer doesnt exist, return false
			return false;
		}
		if (!receptionists.containsKey(receptionistId)
				|| receptionists.get(receptionistId).getWorkingStadium() == null) {// if receptionist doesn't exist or
																					// doesn't have a stadium, return
																					// false
			return false;
		}
		if (receptionists.get(receptionistId).getSubscriptions().contains(new Subscription(subscriptionId))) {// if
																												// receptionist
																												// already
																												// sold
																												// this
																												// subscription,
																												// return
																												// false
			return false;
		}
		if (customers.get(customerId) != null && customers.get(customerId).getSubscriptions() != null
				& customers.get(customerId).getSubscriptions().contains(new Subscription(subscriptionId))) {// if
																											// customer
																											// already
																											// bought
																											// this
																											// subscription,
																											// return
																											// false
			return false;
		}
		if (!receptionists.get(receptionistId).addSubscription(new Subscription(subscriptionId,
				customers.get(customerId), receptionists.get(receptionistId), period, startDate))) {// if cannot add
																									// subscription to
																									// receptionist,
																									// return false
			return false;
		}
		if (customers.get(customerId) != null
				&& !customers.get(customerId).addSubscription(new Subscription(subscriptionId,
						customers.get(customerId), receptionists.get(receptionistId), period, startDate))) {// if cannot
																											// add
																											// subscription
																											// to
																											// customer,
																											// rollback
																											// receptionist
																											// and
																											// return
																											// false
			receptionists.get(receptionistId).removeSubscription(new Subscription(subscriptionId));
			return false;
		}
		return true;// return true

	}// ~ END OF addSubscriptionToCustomer

	/**
	 * This method adds a new match to the JavaLeague only if all the parameters are
	 * valid and both teams already exist in the system VERY IMPORTENT: Match must
	 * be added to all the relevant arrays, Don't forget to roll-back :)
	 * 
	 * @param id
	 * @param dateTime
	 * @param extraTime
	 * @param homeTeamId
	 * @param awayTeamId
	 * @param homeTeamScore
	 * @param awayTeamScore
	 * @return true if the match was added successfully, false otherwise
	 */
	public boolean addMatch(int id, Date dateTime, int extraTime, int homeTeamId, int awayTeamId, int homeTeamScore,
			int awayTeamScore) {
		if (id < 0 || dateTime == null || extraTime < 0 || homeTeamId < 0 || awayTeamId < 0 || homeTeamScore < 0
				|| awayTeamScore < 0) {// if one of the parameters is invalid,return false
			return false;
		}
		if (!teams.containsKey(homeTeamId) || !teams.containsKey(awayTeamId)) {// if teams do not exist, return false
			return false;
		}
		if (teams.get(homeTeamId).equals(teams.get(awayTeamId))) {// if the teams are equal, return false
			return false;
		}
		if (matches.containsKey(id)) {// if match already exists, return false
			return false;
		}
		if (!teams.get(homeTeamId).addMatch(new Match(id, dateTime, extraTime, teams.get(homeTeamId), homeTeamScore,
				teams.get(awayTeamId), awayTeamScore))) {// if cannot add match to home team, return false
			return false;
		}
		if (!teams.get(awayTeamId).addMatch(new Match(id, dateTime, extraTime, teams.get(homeTeamId), homeTeamScore,
				teams.get(awayTeamId), awayTeamScore))) {// if cannot add match to away team, return false and rollback
															// previous
			teams.get(homeTeamId).removeMatch(new Match(id));
			return false;
		}
		if (teams.get(homeTeamId).getStadium() != null) {// if cannot add match to stadium, rollback previous and return
															// false
			if (!teams.get(homeTeamId).getStadium().addMatch(new Match(id, dateTime, extraTime, teams.get(homeTeamId),
					homeTeamScore, teams.get(awayTeamId), awayTeamScore))) {
				teams.get(homeTeamId).removeMatch(new Match(id));
				teams.get(awayTeamId).removeMatch(new Match(id));
				return false;
			}
		}
		matches.put(id, new Match(id, dateTime, extraTime, teams.get(homeTeamId), homeTeamScore, teams.get(awayTeamId),
				awayTeamScore));// add match to matches data structuce
		return true;// return true
	}// ~ END OF addMatch

	/**
	 * This method adds an existing customer to an existing match of the JavaLeague
	 * only if all the parameters are valid and both customer & match already exist
	 * in the system VERY IMPORTENT: Customer must be added to all the relevant
	 * arrays, Don't forget to roll-back :)
	 * 
	 * @param customerId
	 * @param matchId
	 * @return true if the customer was added successfully to match, false otherwise
	 */
	public boolean addCustomerToMatch(String customerId, int matchId) {
		if (Customer.checkId(customerId) == "0" || !matches.containsKey(matchId)) {// if customer id or match doesnt
																					// exist, return false
			return false;
		}
		Customer myCustomer = customers.get(customerId);
		Match myMatch = matches.get(matchId);
		if (myMatch == null || myCustomer == null || myMatch.getCrowd() == null) {// if match or customer is null,
																					// return false
			return false;
		}
		if (!myCustomer.addMatch(myMatch)) {// if cannot add match to customer subscription, return false
			return false;
		}
		if (!myMatch.addFan(myCustomer)) {// if cannot add customer to match crowd, rollback previous and return false
			myCustomer.removeMatch(myMatch);
			return false;
		}
		return true;// return true
	} // ~ END OF addCustomerToMatch

	/**
	 * This method removes an existing subscription from its customer only if all
	 * the parameters are valid and the subscription already exist in the system
	 * 
	 * @param subscriptionId
	 * @return
	 */
	public boolean removeSubscription(int subscriptionId) {
		int flag = 0;
		if (subscriptionId < 0) {// if id is less than 0, return false
			return false;
		}
		for (Receptionist r : receptionists.values()) {// checks if there is a receptionist that sold the subscription
														// to verify its existence, returns false if does not exist
			if (r != null) {
				for (Subscription s : r.getSubscriptions()) {
					if (s != null) {
						if (s.getId() == subscriptionId) {
							flag = 1;
						}
					}
				}
			}
		}
		if (flag != 1) {// if subscription does not exist, return false
			return false;
		}
		flag = 0;
		for (Customer c : customers.values()) {// if a customer has the subscription, remove it and raise flag to tell
												// it was removed from one of them if existed
			if (c != null) {
				if (c.removeSubscription(new Subscription(subscriptionId))) {
					flag = 1;
				}
			}
			if (flag == 1) {// returns true if the subscription was removed from a customer
				return true;
			}
		}
		return false;

	}// ~ END OF removeSubscription
		//

	/**
	 * This method adds a Trophy to the system only if all the parameters are valid
	 * and the Trophy does not already exist in the system
	 * 
	 * @param <T>
	 * 
	 * @param subscriptionId
	 * @return
	 */
	public <T> boolean addTrophy(E_Trophy trophy, T owner, Date trophyWinningDate) {
		if (trophy == null || owner == null || trophyWinningDate == null) {// if one of the parameters is invalid,
																			// return false
			return false;
		}
		if(!(owner instanceof Coach)&&!(owner instanceof Stadium)&&!(owner instanceof Player)&&!(owner instanceof Team)) {//if owner isn't coach,stadium,player or team return false
			return false;
		}
		if (trophies.contains(new Trophy<T>(trophy, owner, trophyWinningDate))) {// if trophy exists, return false
			return false;
		}
		if (trophy.equals(E_Trophy.COACH_OF_THE_YEAR)) {// if the trophy is to coach of the year
			if (!coaches.containsValue(owner)) {// if coach doesnt exist, return false
				return false;
			}
			Employee my = (Employee) owner;
			if (my.getStartWorkingDate().after(trophyWinningDate)) {// if coach started working after the prize was
																	// given, return false
				return false;
			}
		}
		if (trophy.equals(E_Trophy.PLAYER_OF_THE_YEAR)) {// if the trophy is for player of the year
			if (!players.containsValue(owner)) {// if player doesnt exist, return false
				return false;
			}
			Employee my = (Employee) owner;
			if (my.getStartWorkingDate().after(trophyWinningDate)) {// if player started working after prize was given,
																	// return false
				return false;
			}
		}
		if (trophy.equals(E_Trophy.STADIUM_OF_THE_YEAR)) {// if the trophy is for stadium
			if (!stadiums.containsValue(owner)) {// if stadium does not exist, return false
				return false;
			}
		}
		if (trophy.equals(E_Trophy.TEAM_OF_THE_YEAR)) {// if trophy is for team
			if (!teams.containsValue(owner)) {// if team does not exist, return false
				return false;
			}
		}
		if (!trophies.add(new Trophy<T>(trophy, owner, trophyWinningDate))) {// if cannot add trophy, return false
			return false;
		}
		return true;// return true
	}// ~ END OF addTrophy
		// -------------------------------Queries------------------------------
		// ===================================================
		// HW_2_Queries
		// ===================================================

	/**
	 * This query finds the "Super Play Maker" player, a "Super Play Maker" player
	 * is the player that has the largest value, is a right leg kicker, is in
	 * MIDFIELDER position and is in a team's first players. if there are more than
	 * one player the method returns the first player
	 * 
	 * @return player object if found, null otherwise
	 */
	public Player getSuperPlayerMaker(int teamId) {
		Player maxPlayer = null;
		for (Player p : teams.get(teamId).getPlayers().keySet()) {// for every player in this team
			if (p != null && teams.get(teamId) != null && teams.get(teamId).getPlayers() != null
					&& p.getPosition() != null) {// if all valid
				if (maxPlayer == null && teams.get(teamId).getPlayers().get(p)
						&& p.getPosition() == E_Position.MIDFIELDER && p.isRightLegKicker()) {// if its in the terms,
																								// set maxplayer to be
																								// p(if current maximum
																								// is null)
					maxPlayer = p;
				} else if (maxPlayer != null && p.getValue() > maxPlayer.getValue()
						&& teams.get(teamId).getPlayers().get(p) && p.getPosition() == E_Position.MIDFIELDER
						&& p.isRightLegKicker()) {// if its in the terms, all is valid and value is bigger than maximum,
													// set new maximum
					maxPlayer = p;
				}
			}
		}
		return maxPlayer;// return superPlayerMaker
	}

	/**
	 * This query returns an array with the "Super Play Maker" player from all the
	 * teams, a "Super Play Maker" player is as defined in the first query. the
	 * returned ArrayList must be sorted by player's value.
	 * 
	 * @return player array if found, null otherwise
	 */
	public ArrayList<Player> getAllSuperPlayerMakers() {
		ArrayList<Player> superPlayers = new ArrayList<Player>();// initialize arraylist of players
		Player p = null;
		for (Team t : teams.values()) {// uses query1 to get playermaker from every team and adds to arraylist
			if (t != null) {
				p = getSuperPlayerMaker(t.getId());
				if (p != null) {
					superPlayers.add(p);
				}
			}
		}
		Collections.sort(superPlayers, new Comparator<Player>() {// sorts using comparator, sorting by value
			public int compare(Player h1, Player h2) {
				return (int) (h1.getValue() - h2.getValue());
			}
		});
		return superPlayers;// returns superPlayers arrayList
	}

	/**
	 * This query returns the most popular position. Most popular position is the
	 * type that belongs to the highest number of players.
	 * 
	 * @return position object if found, null otherwise
	 */
	public E_Position getTheMostPopularPosition() {
		int values[] = new int[E_Position.values().length];// new int array, indexes resemble the place of E_position
															// values
		E_Position[] pos = E_Position.values();// new E_position array, every index value is a position
		for (Player p : players.values()) {// for every player, gets his position and adds to values array by 1 in the
											// same index as in E_position array
			for (int i = 0; i < pos.length; i++) {
				if (pos[i].equals(p.getPosition())) {
					values[i]++;
				}
			}
		}
		int max = 0;
		E_Position posit = null;// finds maximum value in the values array and sets posit to this E_position
								// value
		for (int i = 0; i < values.length; i++) {
			if (values[i] > max) {
				posit = pos[i];
				max = values[i];
			}
		}
		return posit;// returns posit
	}

	/**
	 * This query returns the most favored team. Most favored team is the team that
	 * has the highest number of customers that the team is their favorite team.
	 * 
	 * @return team object if found, null otherwise
	 */
	public Team getTheMostFavoredTeam() {
		int counter = 0;// initializing counter and max
		int max = 0;
		Team favored = null;
		HashMap<Team, Integer> favorites = new HashMap<Team, Integer>();// creating new hashmap of favorite teams so
																		// that later we can get maximum value
		for (Team t : teams.values()) {// for every team
			counter = 0;
			for (Customer c : customers.values()) {// for every customer
				if (c.getFavoriteTeam().equals(t)) {// counts how many customers think of a team as their favorite and
													// adds 1 to counter for each one
					counter++;
				}
			}
			favorites.put(t, counter);// add to favorites hashmap, key is team, value is how many fans
		}
		for (Team t : favorites.keySet()) {// finds maximum value in hashmap,sets favored to be the team with maximum
											// value
			if (favorites.get(t) > max) {
				max = favorites.get(t);
				favored = t;
			}
		}
		return favored;// returns most favored team
	}

	/**
	 * This query finds the most active city of a given stadium, the most active
	 * city is the city with the highest number of employees that are working in the
	 * given stadium.
	 * 
	 * @param stadiumId
	 * @return city object, null otherwise
	 */
	public E_Cities getTheMostActiveCity(int stadiumId) {
		E_Cities active = null;
		Stadium myStadium = stadiums.get(stadiumId);
		if (myStadium == null) {// if stadium does not exist, return null;
			return active;
		}
		int[] values = new int[E_Cities.values().length];// sets values array, each index represents the E_city value in
															// same position as the array below
		E_Cities[] cities = E_Cities.values();
		for (Receptionist r : myStadium.getReceptionists()) {// counts receptionists that belong to stadium from each
																// city and adds to city values array
			for (int i = 0; i < cities.length; i++) {
				if (cities[i].equals(r.getAddress().getCity())) {
					values[i]++;
				}
			}
		}
		for (Team t : myStadium.getTeams()) {// counts players from each team that belongs to stadium and adds to city
												// values array to correct city
			for (Player p : t.getPlayers().keySet()) {
				for (int i = 0; i < cities.length; i++) {
					if (cities[i].equals(p.getAddress().getCity())) {
						values[i]++;
					}
				}
			}
			for (int i = 0; i < cities.length; i++) {// counts how many coaches that belong to teams that belong to
														// stadium the cities and adds to values
				if (cities[i].equals(t.getCoach().getAddress().getCity())) {
					values[i]++;
				}
			}
		}
		int max = 0;
		for (int i = 0; i < values.length; i++) {// finds maximum value and sets the active city to be the city with
													// maximum value
			if (values[i] >= max) {
				max = values[i];
				active = cities[i];
			}
		}
		return active;// returns most active city
	}

	/**
	 * This query returns the entity that has won the most trophies.
	 * 
	 * @return object if found. null otherwise
	 */
	public Object getEntityWithMostTrophies() {
		ArrayList<Object> owners = new ArrayList<Object>();// creates array list to find out how many different owners
															// are there of each trophy
		for (Trophy t : trophies) {
			if (!owners.contains(t.getOwner())) {
				owners.add(t.getOwner());// each time there is a new owner that isnt in the array list, adds him to
											// arraylist
			}
		}
		Object[] entities = owners.toArray();// creates array of objects that resemble the owners
		int[] counters = new int[entities.length];// creates values array that every index represents the object at the
													// entities arrary to count how many times he got a trophy
		for (Trophy t : trophies) {
			for (int i = 0; i < entities.length; i++) {
				if (entities[i].equals(t.getOwner())) {// adds 1 to counters(values) array for each owner
					counters[i]++;
				}
			}
		}
		int maxValue = 0;
		int maxIndex = 0;
		for (int i = 0; i < counters.length; i++) {// finds owner with most trophies and stores its index
			if (counters[i] > maxValue) {
				maxValue = counters[i];
				maxIndex = i;
			}
		}
		return entities[maxIndex];// returns owner with maximum trophies
	}

	/**
	 * This query returns the home team of the match that contained the largest
	 * crowd of customers that their favorite team is the match's home team
	 * 
	 * @return team object if found, null otherwise
	 */
	public Team getTeamWithLargestHomeCrowd() {
		int fanfav = 0;
		Team topTeam = null;
		int max = 0;
		for (Match m : matches.values()) {// counts crowd for every team in every match
			for (Customer c : m.getCrowd().keySet()) {
				if (m.getCrowd().get(c) == true) {
					fanfav++;
				}
			}
			if (fanfav > max) {// if this team had the biggest crowd in a match, sets as topTeam
				max = fanfav;
				topTeam = m.getHomeTeam();
			}
			fanfav = 0;
		}
		return topTeam;// returns team with largest home crowd
	}

	/**
	 * This query returns all the customers that have a subscription to stadiun1 or
	 * stadium2 but not to both
	 * 
	 * @return array of customers if customers were found, empty list otherwise
	 */
	public ArrayList<Customer> getCustomersStadium1XORStadium2(int stud1, int stud2) {
		int flag1 = 0, flag2 = 0;// two flags, one if customer had subscription to stadium one and second if he
									// had subscription to stadium 2
		ArrayList<Customer> xor = new ArrayList<Customer>();// creates xor arraylist that stores every customers that
															// has subscription only to one of the stadiums
		for (Customer c : customers.values()) {
			for (Subscription s : c.getSubscriptions()) {
				if (s.getReceptionist().getWorkingStadium().getId() == stud1) {// if has sub for satdium 1, flag1=1
					flag1 = 1;
				}
				if (s.getReceptionist().getWorkingStadium().getId() == stud2) {// if has sub for stadium 2,flag2=1;
					flag2 = 1;
				}
			}
			if ((flag1 == 1 || flag2 == 1) && !(flag1 == 1 && flag2 == 1)) {// if belongs only to one, add to xor
																			// arraylist
				xor.add(c);
			}
			flag1 = 0;// sets flags to zero again
			flag2 = 0;
		}
		return xor;// returns xor arraylist
	}

	/**
	 * This query returns all the first players of the "Best Home Team" "Best Home
	 * Team" is the team with the highest Home Away winning rate returned players
	 * must be sorted by their value.
	 * 
	 * @return array of players if players were found, empty list otherwise
	 */
	public ArrayList<Player> getFirstPlayersOfBestHomeTeam() {
		ArrayList<Player> firstPlayers = new ArrayList<Player>();// initiazling player arraylist
		double max = 0;// initializing max value
		Team topTeam = null;// initializing team that will store the best home team
		for (Team t : teams.values()) {
			if (t.getHomeAwayWinningsRate() >= max) {
				max = t.getHomeAwayWinningsRate();// finds team with biggest homeaway winning rate
				topTeam = t;
			}
		}
		if (topTeam == null) {// if no such team, return null;
			return null;
		}
		for (Player p : topTeam.getPlayers().keySet()) {// for this team, add every first player to firstPlayers
														// arraylist
			if (topTeam.getPlayers().get(p) == true) {
				firstPlayers.add(p);
			}
		}
		Collections.sort(firstPlayers, new Comparator<Player>() {// sort arraylist by player value
			public int compare(Player h1, Player h2) {
				return (int) (h1.getValue() - h2.getValue());
			}
		});
		return firstPlayers;// return firstPlayers arraylist
	}
}// ~ END OF Class SysData
