package cib.universidad.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/* Java Bean
* Clase: Person  */
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Person{
	private IntegerProperty idPerson;
	private StringProperty name;
	private StringProperty lastName;
	private StringProperty lastNameMother;
	private StringProperty cellNumber;
	private StringProperty sex;

	public Person(Integer idPerson, String name, String lastName, String lastNameMother, String cellNumber, String sex){
		this.idPerson = new SimpleIntegerProperty(idPerson);
		this.name  =new SimpleStringProperty(name);
		this.lastName = new SimpleStringProperty(lastName);
		this.lastNameMother = new SimpleStringProperty(lastNameMother);
		this.cellNumber = new SimpleStringProperty(cellNumber);
		this.sex = new SimpleStringProperty(sex);
	}

	public Person(){
		this(0, null, null, null, null, null);
	}

	public Integer getIdPerson(){
		return idPerson.get();
	}

	public void setIdPerson(Integer idPerson){
		this.idPerson.set(idPerson);
	}

	public String getName(){
		return name.get();
	}

	public void setName(String name){
		this.name.set(name);
	}

	public String getLastName(){
		return lastName.get();
	}

	public void setLastName(String lastName){
		this.lastName.set(lastName);
	}

	public String getLastNameMother(){
		return lastNameMother.get();
	}

	public void setLastNameMother(String lastNameMother){
		this.lastNameMother.set(lastNameMother);
	}

	public String getCellNumber(){
		return cellNumber.get();
	}

	public void setCellNumber(String cellNumber){
		this.cellNumber.set(cellNumber);
	}

	public String getSex(){
		return this.sex.get();
	}

	public void setSex(String sex){
		this.sex.set(sex);
	}

	public IntegerProperty idPersonProperty(){
		return idPerson;
	}

	public StringProperty nameProperty(){
		return name;
	}

	public StringProperty lastNameProperty(){
		return lastName;
	}

	public StringProperty lastNameMotherProperty(){
		return lastNameMother;
	}

	public StringProperty cellNumberProperty(){
		return cellNumber;
	}

	public int savePerson(Connection connection){
		int estate =0;
		try {
			PreparedStatement pstm = connection.prepareStatement("INSERT persona "
					+ "(nombres, apellidoPaterno, apellidomaterno, telefonoCelular, sexo)"
					+ " VALUES(?,?,?,?,?)");

			pstm.setString(1, this.name.get());
			pstm.setString(2, this.lastName.get());
			pstm.setString(3, this.lastNameMother.get());
			pstm.setString(4, this.cellNumber.get());
			pstm.setString(5, this.sex.get());

			estate = pstm.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return estate;
	}

	public int getPersonID(Connection connection){

		int personID = 0;
		try {
			PreparedStatement pstm = connection.prepareStatement("select max(idPersona) 'idPersona' from persona");

			ResultSet rs;

			rs = pstm.executeQuery();

			while(rs.next()){
				personID = rs.getInt("idPersona");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return personID;
	}
}
