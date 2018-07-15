package Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Class Employee ~ represent a single Employee of the company
 * 
 * @author Java Course Team 2018 - Shai Gutman
 * @author University Of Haifa - Israel
 */
public abstract class Employee implements Comparable<Employee> {
	// -------------------------------Class  Members------------------------------
	private int id;
	private String firstName;
	private String lastName;
	private Date birthdate;
	private Date startWorkingDate;
	private Address address;

	// -------------------------------Constructors------------------------------
	public Employee(int id, String firstName, String lastName, Date birthdate, Date startWorkingDate, Address address) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
		this.startWorkingDate = startWorkingDate;
		this.address = address;
	}

	public Employee(int empNum) {
		this.id = empNum;
	}

	// -------------------------------Getters And Setters------------------------------
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public Date getStartWorkingDate() {
		return startWorkingDate;
	}

	public void setStartWorkingDate(Date startWorkingDate) {
		this.startWorkingDate = startWorkingDate;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	// -------------------------------More Methods------------------------------
	/**
	 * This method calculate this employee seniority (in years). 
	 * if the employee started to work in this year, than its seniority is 0 years.
	 * 
	 * @return employee seniority
	 */
	public int getEmployeeSeniority() {
		
		Calendar cal=Calendar.getInstance();//sets calendar cal to calculate years differences
		cal.setTime(new Date());
		int currentYear=cal.get(Calendar.YEAR);
		cal.setTime(startWorkingDate);
		int employeeYear=cal.get(Calendar.YEAR);
		return currentYear-employeeYear;//returns differences between years of two dates
		
	}
	
	/**
	 * Emploee objects must be ordered by employee's seniority
	 */
	public int compareTo(Employee o2) {
		if(this.getEmployeeSeniority()>o2.getEmployeeSeniority()) {//compareTo by employees seniority,returns 1 if first is bigger, 0 if equal and -1 otherwise
			return 1;
		}
		else if(this.getEmployeeSeniority()==o2.getEmployeeSeniority()) {
			return 0;
		}
		else
			return -1;
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
		Employee other = (Employee) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		DateFormat dft = new SimpleDateFormat("dd/MM/yyyy;HH:mm");
		return "Employee | id: " + id + ", name: " + firstName + " " + lastName + ", birthdate: " + dft.format(birthdate)
				+ ", swd: " + dft.format(startWorkingDate) + ", address: " + address;
	}

}
