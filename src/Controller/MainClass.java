package Controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import Model.Address;
import Model.Coach;
import Model.Customer;
import Model.Match;
import Model.Player;
import Model.Receptionist;
import Model.Stadium;
import Model.Subscription;
import Model.Team;
import utils.*;

/**
 * This MainClass object - represents the program runner
 * 
 * @author Java Course Team 2018 - Shai Gutman
 * @author University Of Haifa - Israel
 */
public class MainClass {

	/**
	 * The command read from the input file
	 */
	private static String command;

	/**
	 * To check if the command updated the objects
	 */
	private static boolean isUpdated;

	/**
	 * The date format
	 */
	private static DateFormat df;

	/**
	 * The date & time format
	 */
	private static DateFormat dft;

	/**
	 * The main object for the program
	 */
	private static SysData sysData;

	/**
	 * Scanner for input
	 */
	private static Scanner input;

	private static Map<String, FileAppender> fileWriters = new HashMap<>();
	private static Map<String, Command> commands = new HashMap<>();

	static {
		// ADD IP
		commands.put("addStadium", (writer, args) -> {
			int i = 0;
			int id = Integer.parseInt(args[i++]);
			String name = args[i++];
			int capacity = Integer.parseInt(args[i++]);
			E_Cities city = E_Cities.valueOf(args[i++]);
			String street = args[i++];
			int houseNumber = Integer.parseInt(args[i++]);
			String[] phoneNumber = { args[i] };

			isUpdated = sysData.addStadium(id, name, capacity, city, street, houseNumber, phoneNumber);
			if (isUpdated) { // if adding successfully, then true returned,
				// the following message is written to the output file
				writer.write("Successfully added stadium: " + id + " | " + name);
			} else {
				writer.write("Failed to add new stadium");
			}
		});

		commands.put("addCoach", (writer, args) -> {
			int i = 0;
			int id = Integer.parseInt(args[i++]);
			String firstName = args[i++];
			String lastName = args[i++];
			Date birthDate = df.parse(args[i++]);
			Date startWorkingDate = df.parse(args[i++]);
			E_Levels level = E_Levels.valueOf(args[i++]);
			E_Cities city = E_Cities.valueOf(args[i++]);
			String street = args[i++];
			int housNumber = Integer.parseInt(args[i++]);
			String[] phoneNumber = { args[i] };
			Address address = new Address(city, street, housNumber, phoneNumber);

			isUpdated = sysData.addCoach(id, firstName, lastName, birthDate, startWorkingDate, level, address);
			if (isUpdated) { // if adding successfully, then true returned,
				// the following message is written to the output file
				writer.write("Successfully added coach: " + id + " | " + firstName + " " + lastName);
			} else {
				writer.write("Failed to add new coach");
			}
		});
		commands.put("addPlayer", (writer, args) -> {
			int i = 0;
			int id = Integer.parseInt(args[i++]);
			String firstName = args[i++];
			String lastName = args[i++];
			Date birthDate = df.parse(args[i++]);
			Date startWorkingDate = df.parse(args[i++]);
			E_Levels level = E_Levels.valueOf(args[i++]);
			long value = Long.parseLong(args[i++]);
			boolean isRightLegKicker = Boolean.valueOf(args[i++]);
			E_Position position = E_Position.valueOf(args[i++]);
			E_Cities city = E_Cities.valueOf(args[i++]);
			String street = args[i++];
			int housNumber = Integer.parseInt(args[i++]);
			String[] phoneNumber = { args[i] };
			Address address = new Address(city, street, housNumber, phoneNumber);

			isUpdated = sysData.addPlayer(id, firstName, lastName, birthDate, startWorkingDate, level, value, isRightLegKicker, position, address);
			if (isUpdated) { // if adding successfully, then true returned,
				// the following message is written to the output file
				writer.write("Successfully added player: " + id + " | " + firstName + " " + lastName);
			} else {
				writer.write("Failed to add new player");
			}
		});
		commands.put("addTeam", (writer, args) -> {
			int i = 0;
			int id = Integer.parseInt(args[i++]);
			String name = args[i++];
			int value = Integer.parseInt(args[i++]);
			E_Levels level = E_Levels.valueOf(args[i++]);
			int stadiumId = Integer.parseInt(args[i]);

			isUpdated = sysData.addTeam(id, name, value, level, stadiumId);
			if (isUpdated) { // if adding successfully, then true returned,
				// the following message is written to the output file
				writer.write("Successfully added new team: " + id + " | " + name);
			} else {
				writer.write("Failed to add new team");
			}
		});
		commands.put("addReceptionist", (writer, args) -> {
			int i = 0;
			int id = Integer.parseInt(args[i++]);
			String firstName = args[i++];
			String lastName = args[i++];
			Date birthDate = df.parse(args[i++]);
			Date startWorkingDate = df.parse(args[i++]);
			E_Cities city = E_Cities.valueOf(args[i++]);
			String street = args[i++];
			int housNumber = Integer.parseInt(args[i++]);
			String[] phoneNumber = { args[i] };
			Address address = new Address(city, street, housNumber, phoneNumber);

			isUpdated = sysData.addReceptionist(id, firstName, lastName, birthDate, startWorkingDate, address);
			if (isUpdated) { // if adding successfully, then true returned,
				// the following message is written to the output file
				writer.write("Successfully added receptionist: " + id + " | " + firstName + " " + lastName);
			} else {
				writer.write("Failed to add new receptionist");
			}
		});
		commands.put("addCustomer", (writer, args) -> {
			int i = 0;
			String id = args[i++];
			String firstName = args[i++];
			String lastName = args[i++];
			Date birthDate = df.parse(args[i++]);
			E_Levels level = E_Levels.valueOf(args[i++]);
			URL email = new URL(args[i++]);
			int favoriteTeamId = Integer.parseInt(args[i++]);
			E_Cities city = E_Cities.valueOf(args[i++]);
			String street = args[i++];
			int housNumber = Integer.parseInt(args[i++]);
			String[] phoneNumber = { args[i] };
			Address address = new Address(city, street, housNumber, phoneNumber);

			isUpdated = sysData.addCustomer(id, firstName, lastName, birthDate, level, email, favoriteTeamId, address);
			if (isUpdated) { // if adding successfully, then true returned,
				// the following message is written to the output file
				writer.write("Successfully added customer: " + id + " | " + firstName + " " + lastName);
			} else {
				writer.write("Failed to add new customer");
			}
		});
		commands.put("addSubscriptionToCustomer", (writer, args) -> {
			int i = 0;
			int id = Integer.parseInt(args[i++]);
			String customerId = args[i++];
			int receptionistId = Integer.parseInt(args[i++]);
			E_Periods period = E_Periods.valueOf(args[i++]);
			Date startDate = df.parse(args[i++]);
			isUpdated = sysData.addSubscriptionToCustomer(id, customerId, receptionistId, period, startDate);
			if (isUpdated) { // if adding successfully, then true returned,
				// the following message is written to the output file
				writer.write("Successfully added subscription: " + id + " to customer " + customerId);
			} else {
				writer.write("Failed to add new subscription");
			}
		});
		commands.put("addCoachToTeam", (writer, args) -> {
			int i = 0;
			int coachId = Integer.parseInt(args[i++]);
			int teamId = Integer.parseInt(args[i]);

			isUpdated = sysData.addCoachToTeam(coachId, teamId);
			if (isUpdated) { // if adding successfully, then true returned,
				// the following message is written to the output file
				writer.write("Successfully added coach: " + coachId + " to team " + teamId);
			} else {
				writer.write("Failed to add coach to team");
			}
		});
		commands.put("addPlayerToTeam", (writer, args) -> {
			int i = 0;
			int playerId = Integer.parseInt(args[i++]);
			int teamId = Integer.parseInt(args[i]);

			isUpdated = sysData.addPlayerToTeam(playerId, teamId);
			if (isUpdated) { // if adding successfully, then true returned,
				// the following message is written to the output file
				writer.write("Successfully added player: " + playerId + " to team " + teamId);
			} else {
				writer.write("Failed to add player to team");
			}
		});
		commands.put("addPlayerToTeamFirstPlayers", (writer, args) -> {
			int i = 0;
			int playerId = Integer.parseInt(args[i++]);
			int teamId = Integer.parseInt(args[i]);

			isUpdated = sysData.addPlayerToTeamFirstPlayers(playerId, teamId);
			if (isUpdated) { // if adding successfully, then true returned,
				// the following message is written to the output file
				writer.write("Successfully added player: " + playerId + " to team first players " + teamId);
			} else {
				writer.write("Failed to add player to team first players");
			}
		});
		commands.put("addReceptionistToStadium", (writer, args) -> {
			int i = 0;
			int receptionistId = Integer.parseInt(args[i++]);
			int stadiumId = Integer.parseInt(args[i]);

			isUpdated = sysData.addReceptionistToStadium(receptionistId, stadiumId);
			if (isUpdated) { // if adding successfully, then true returned,
				// the following message is written to the output file
				writer.write("Successfully added receptionist: " + receptionistId + " to stadium " + stadiumId);
			} else {
				writer.write("Failed to add receptionist to stadium");
			}
		});
		commands.put("addMatch", (writer, args) -> {
			int i = 0;
			int id = Integer.parseInt(args[i++]);
			Date dateTime = dft.parse(args[i++]);
			int extraTime = Integer.parseInt(args[i++]);
			int homeTeamId = Integer.parseInt(args[i++]);
			int awayTeamId = Integer.parseInt(args[i++]);
			int homeTeamScore = Integer.parseInt(args[i++]);
			int awayTeamScore = Integer.parseInt(args[i]);

			isUpdated = sysData.addMatch(id, dateTime, extraTime, homeTeamId, awayTeamId, homeTeamScore, awayTeamScore);
			if (isUpdated) { // if added successfully, then true returned,
				// the following message is written to the output file
				writer.write("Successfully added match: " + id + " between " + homeTeamId + " and " + awayTeamId);
			} else {
				writer.write("Failed to add match");
			}
		});
		commands.put("addCustomerToMatch", (writer, args) -> {
			int i = 0;
			String customerId = args[i++];
			int matchId = Integer.parseInt(args[i]);

			isUpdated = sysData.addCustomerToMatch(customerId, matchId);
			if (isUpdated) { // if added successfully, then true returned,
				// the following message is written to the output file
				writer.write("Successfully added customer: " + customerId + " to match " + matchId);
			} else {
				writer.write("Failed to add customer to match");
			}
		});
		commands.put("removeSubscription", (writer, args) -> {
			int i = 0;
			int subscriptionId = Integer.parseInt(args[i]);

			isUpdated = sysData.removeSubscription(subscriptionId);
			if (isUpdated) { // if canceling successfully, then true returned,
				// the following message is written to the output file
				writer.write("Successfully removed subscription: " + subscriptionId);
			} else {
				writer.write("Failed to remove subscription ");
			}
		});
		commands.put("addTrophy", (writer, args) -> {
			int i = 0;
			E_Trophy trophy = E_Trophy.valueOf(args[i++]);
			int ownerId = Integer.parseInt(args[i++]);
			Date trophyWinningDate = df.parse(args[i]);
			switch (trophy) {
				case STADIUM_OF_THE_YEAR:
					if (sysData.getStadiums().containsKey(ownerId)) {
						Stadium owner = sysData.getStadiums().get(ownerId);
						isUpdated = sysData.addTrophy(trophy, owner, trophyWinningDate);
					}
					break;
				case TEAM_OF_THE_YEAR:
					if (sysData.getTeams().containsKey(ownerId)) {
						Team owner = sysData.getTeams().get(ownerId);
						isUpdated = sysData.addTrophy(trophy, owner, trophyWinningDate);
					}
					break;
				case COACH_OF_THE_YEAR:
					if (sysData.getCoachs().containsKey(ownerId)) {
						Coach owner = sysData.getCoachs().get(ownerId);
						isUpdated = sysData.addTrophy(trophy, owner, trophyWinningDate);
					}
					break;
				case PLAYER_OF_THE_YEAR:
					if (sysData.getPlayers().containsKey(ownerId)) {
						Player owner = sysData.getPlayers().get(ownerId);
						isUpdated = sysData.addTrophy(trophy, owner, trophyWinningDate);
					}
					break;
			}
			if (isUpdated) { // if canceling successfully, then true returned,
				// the following message is written to the output file
				writer.write("Successfully added trophy: " + trophy + " to " + ownerId);
			} else {
				writer.write("Failed to add trophy ");
			}
		});
		commands.put("printDatabase", (writer, args) -> {
			writer.write("=============== Printing Stadiums ===============");
			for (Stadium s : sysData.getStadiums().values()) {
				writer.write("" + s);
				writer.write("============ Printing Receptionists ============");
				for (Receptionist r : s.getReceptionists()) {
					if (r != null) {
						writer.write(r.toString());
					}
				}
				writer.write("================ Printing Teams ================");
				for (Team t : s.getTeams()) {
					if (t != null) {
						writer.write(t.toString());
						writer.write("============ Printing Players ============");
						for (Player p : t.getPlayers().keySet()) {
							if (p != null) {
								writer.write(p.toString());
							}
						}
					}
				}
				writer.write("=============== Printing Matches ===============");
				for (Match m : s.getMatches()) {
					if (m != null) {
						writer.write(m.toString());
					}
				}
			}
			writer.write("=============== Printing Customers ===============");
			for (Customer c : sysData.getCustomers().values()) {
				if (c != null) {
					writer.write(c.toString());
					writer.write("============ Printing Subscriptions ============");
					for (Subscription s : c.getSubscriptions()) {
						if (s != null) {
							writer.write(s.toString());
						}
					}
				}
			}
		});
		commands.put("getSuperPlayerMaker", (writer, args) -> {
			int teamId = Integer.parseInt(args[0]);
			Player player = sysData.getSuperPlayerMaker(teamId);
			writer.write("getSuperPlayerMaker returns:");
			if (player != null)
				writer.write("\t" + player);
			else
				writer.write("No \"Super Player Maker\" player was found");
		});
		
		commands.put("getAllSuperPlayerMakers", (writer, args) -> {
			writer.write("getAllSuperPlayerMakers returns:");
			ArrayList<Player> players = sysData.getAllSuperPlayerMakers();
			if (players != null) {
				int i = 1;
				for (Player p : players)
					if (p != null)
						writer.write((i++) + "\t" + p);
			} else
				writer.write("No \"Super Player Maker\" players were found");
		});
		
		commands.put("getTheMostPopularPosition", (writer, args) -> {
			writer.write("getTheMostPopularPosition returns:");
			E_Position position = sysData.getTheMostPopularPosition();
			if (position != null) {
				writer.write("\t" + position);
			} else
				writer.write("No most popular position was found");
		});
		
		commands.put("getTheMostFavoredTeam", (writer, args) -> {
			writer.write("getTheMostFavoredTeam returns:");
			Team team = sysData.getTheMostFavoredTeam();
			if (team != null) {
				writer.write("\t" + team);
			} else
				writer.write("No most favored team was found");
		});
		
		
		commands.put("getTheMostActiveCity", (writer, args) -> {
			writer.write("getTheMostActiveCity returns:");
			E_Cities city = sysData.getTheMostActiveCity(Integer.parseInt(args[0]));
			if (city != null) {
				writer.write("\t" + city);
			} else
				writer.write("No most active city was found");
		});
		
		commands.put("getEntityWithMostTrophies", (writer, args) -> {
			writer.write("getEntityWithMostTrophies returns:");
			Object entity = sysData.getEntityWithMostTrophies();
			if (entity != null) {
				writer.write("\t" + entity);
			} else
				writer.write("No entity with most trophies was found");
		});
		
		commands.put("getTeamWithLargestHomeCrowd", (writer, args) -> {
			writer.write("getTeamWithLargestHomeCrowd returns:");
			Team team = sysData.getTeamWithLargestHomeCrowd();
			if (team != null) {
				writer.write("\t" + team);
			} else
				writer.write("No team with largest home crowd was found");
		});
		
		commands.put("getCustomersStadium1XORStadium2", (writer, args) -> {
			writer.write("getCustomersStadium1XORStadium2 returns:");
			ArrayList<Customer> customers = sysData.getCustomersStadium1XORStadium2(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
			if (customers != null) {
				int i = 1;
				for (Customer c : customers)
					if (c != null)
						writer.write((i++) + "\t" + c);
			} else
				writer.write("No customers of stadium1 XOR Stadium2 were found");
		});
		
		commands.put("getFirstPlayersOfBestHomeTeam", (writer, args) -> {
			writer.write("getFirstPlayersOfBestHomeTeam returns:");
			ArrayList<Player> players = sysData.getFirstPlayersOfBestHomeTeam();
			if (players != null) {
				int i = 1;
				for (Player p : players)
					if (p != null)
						writer.write((i++) + "\t" + p);
			} else
				writer.write("No first players of best home team were found");
		});
	}

	/**
	 * The main method of this system, gets input from input.txt and Writes
	 * output to output.txt file
	 * 
	 * @param args
	 * @throws IOException
	 * @throws ParseException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws IOException, ParseException, ClassNotFoundException {
		// Create Scanner for the text file named "input.txt"
		input = new Scanner(new File("input.txt"));
		// Define Date format
		df = new SimpleDateFormat("dd/MM/yyyy");
		df.setTimeZone(TimeZone.getTimeZone("Asia/Jerusalem"));
		// Define Date & Time format
		dft = new SimpleDateFormat("dd/MM/yyyy;HH:mm");
		dft.setTimeZone(TimeZone.getTimeZone("Asia/Jerusalem"));
		// Writer buffer creation used after update
		MyFileLogWriter.initializeMyFileWriter();
		// if the file has next - not empty
		if (input.hasNext()) {
			sysData = SysData.getInstance();
		}

		while (input.hasNext() && sysData != null) {
			/*
			 * read the command, must be first at line because command value
			 * determine which method will be activated in SysData object.
			 */
			String line = input.nextLine().trim().replaceAll("\t"," ").replaceAll("[ ]{2,}", " ");
			String[] values = line.split(" ");
			command = values[0];
			String[] params = Arrays.copyOfRange(values,1,values.length);
			func(command,params);

		} // ~ end of clause - while input has next

		List<FileAppender> list = new ArrayList<>(fileWriters.values());
		list.sort(FileAppender::compareTo);

		for (FileAppender f : list){
			if (f.isUsed()) {
				MyFileLogWriter.writeToFileInSeparateLine("@BEGIN::COMMAND_" + f.id);
				f.flush();
				MyFileLogWriter.writeToFileInSeparateLine("@END::COMMAND_" + f.id);
			}
		}

		MyFileLogWriter.saveLogFile(); // save the output file
		input.close(); // close connection to input file
		System.out.println("[End Of process]");
		System.out.println("\n NOTICE:\n\t\"End of process\" " + "does NOT mean that all your methods are correct.\n"+ "\n==>\t You NEED to check the \"output.txt\" file");
	}// END OF ~ main

	private static void func(String command,String[] args){
		Command c = commands.get(command);
		if (c != null){
			FileAppender appender = fileWriters.get(command);
			if (appender == null) {
				appender = new FileAppender();
				fileWriters.put(command,appender);
			}
			try {
				c.execute(appender, args);
			}catch (Exception ignored){
				ignored.printStackTrace();
			}
		}
	}

	public static DateFormat getDf() {
		return df;
	}

	public static DateFormat getDft() {
		return dft;
	}
}// ~ END OF Class MainClass
