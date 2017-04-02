package model.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="Students")
public class Student 
{
	@Id
	private int studentID;
	@Column(name="FirstName")
	private String fName;
	@Column(name="LastName")
	private String lName;
	private double gradesAverage;
	public Student(){
		
	}
	public Student(int studentID, String fName, String lName, double gradesAverage)
	{
		super();
		this.fName=fName;
		this.lName=lName;
		this.gradesAverage=gradesAverage;
		this.studentID=studentID;
	}
	public int getStudentID() 
	{
		return studentID;
	}

	public void setStudentID(int studentID) 
	{
		this.studentID = studentID;
	}

	public String getfName() 
	{
		return fName;
	}

	public void setfName(String fName) 
	{
		this.fName = fName;
	}

	public String getlName() 
	{
		return lName;
	}

	@Override
	public String toString() {
		return "Student [studentID=" + studentID + ", fName=" + fName + ", lName=" + lName + ", gradesAverage="
				+ gradesAverage + "]";
	}
	public void setlName(String lName) 
	{
		this.lName = lName;
	}

	public double getGradesAverage() 
	{
		return gradesAverage;
	}

	public void setGradesAverage(double gradesAverage)
	{
		this.gradesAverage = gradesAverage;
	}
	
	
}
