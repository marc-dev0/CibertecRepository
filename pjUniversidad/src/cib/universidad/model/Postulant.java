package cib.universidad.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/* Java Bean
* Clase: Postulant  */
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Postulant extends Person{
	private IntegerProperty idPostulante;
	private StringProperty nroDocumento;
	private StringProperty estate;
	private StringProperty adress;
	private Date birthDate;
	private StringProperty phoneNumber;
	private StringProperty email;
	private IntegerProperty idPersona;
	private IntegerProperty idDocumentType;
	private IntegerProperty idDistrict;
	private int estateTrx;

	public Postulant(Integer idPostulante, String nroDocumento, String estate, String adress, Date birthDate, String phoneNumber, String email, Integer idPersona, Integer idDocumentType, Integer idDistrict){

		this.idPostulante = new SimpleIntegerProperty(idPostulante);
		this.nroDocumento = new SimpleStringProperty(nroDocumento);
		this.estate = new SimpleStringProperty(estate);
		this.adress = new SimpleStringProperty(adress);
		this.birthDate = birthDate;
		this.phoneNumber = new SimpleStringProperty(phoneNumber);
		this.email = new SimpleStringProperty(email);
		this.idPersona = new SimpleIntegerProperty(idPersona);
		this.idDocumentType = new SimpleIntegerProperty(idDocumentType);
		this.idDistrict = new SimpleIntegerProperty(idDistrict);
	}

	public Postulant(){
		this(0, null, null, null, null, null, null, 0, 0, 0);
	}

	public Integer getIdPostulante(){
		return idPostulante.get();
	}

	public void setIdPostulante(Integer idPostulante){
		this.idPostulante.set(idPostulante);
	}

	public String getNroDocumento(){
		return nroDocumento.get();
	}

	public void setNroDocumento(String nroDocumento){
		this.nroDocumento.set(nroDocumento);
	}

	public String getEstate(){
		return estate.get();
	}

	public void setEstate(String estate){
		this.estate.set(estate);
	}

	public String getAdress(){
		return adress.get();
	}

	public void setAdress(String adress){
		this.adress.set(adress);
	}

	public Date getBirthDate(){
		return birthDate;
	}

	public void setBirthDate(Date birthDate){
		this.birthDate = birthDate;
	}

	public String getPhoneNumber(){
		return phoneNumber.get();
	}

	public void setPhoneNumber(String phoneNumber){
		this.phoneNumber.set(phoneNumber);
	}

	public String getEmail(){
		return email.get();
	}

	public void setEmail(String email){
		this.email.set(email);
	}

	public Integer getIdPersona(){
		return idPersona.get();
	}

	public void setIdPersona(Integer idPersona){
		this.idPersona.set(idPersona);
	}

	public Integer getIdDocumentType(){
		return idDocumentType.get();
	}

	public void setIdDocumentType(Integer idDocumentType){
		this.idDocumentType.set(idDocumentType);
	}

	public Integer getIdDistrict(){
		return idDistrict.get();
	}

	public void setIdDistrict(Integer idDistrict){
		this.idDistrict.set(idDistrict);
	}

	public IntegerProperty idPostulanteProperty(){
		return idPostulante;
	}

	public StringProperty nroDocumentoProperty(){
		return nroDocumento;
	}

	public StringProperty estateProperty(){
		return estate;
	}

	public StringProperty adressProperty(){
		return adress;
	}

	public StringProperty phoneNumberProperty(){
		return phoneNumber;
	}

	public StringProperty emailProperty(){
		return email;
	}

	public IntegerProperty idPersonaProperty(){
		return idPersona;
	}

	public IntegerProperty idDocumentTypeProperty(){
		return idDocumentType;
	}

	public IntegerProperty idDistrictProperty(){
		return idDistrict;
	}

	public static void getListPostulant(Connection connection, ObservableList<Postulant> lista){
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select p.idPostulante, pe.nombres, pe.apellidoPaterno, pe.apellidoMaterno, " +
													"p.nroDocumento, p.telefonoFijo, pe.telefonoCelular, p.fechaNacimiento, " +
													"p.correo ,p.direccion, p.estado from postulante p " +
												    "inner join Persona pe on pe.idPersona = p.idPersona;");
			while(rs.next()){
				Postulant postulant = new Postulant();
				postulant.setIdPostulante(rs.getInt("idPostulante"));
				postulant.setName(rs.getString("nombres"));
				postulant.setLastName(rs.getString("apellidoPaterno"));
				postulant.setLastNameMother(rs.getString("apellidoMaterno"));
				postulant.setEmail(rs.getString("correo"));
				postulant.setNroDocumento(rs.getString("nroDocumento"));
				postulant.setPhoneNumber(rs.getString("telefonoFijo"));
				postulant.setCellNumber(rs.getString("telefonoCelular"));
				postulant.setBirthDate(rs.getDate("fechaNacimiento"));
				postulant.setAdress(rs.getString("direccion"));
				postulant.setEstate(rs.getString("estado"));
				lista.add(postulant);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int savePostulant(Connection connection){
		int estate =0;
		try {
			PreparedStatement pstm = connection.prepareStatement(
					"INSERT POSTULANTE(nroDocumento, estado, direccion, fechaNacimiento, "
					+ "telefonoFijo, correo, idPersona, idTipoDocumento,idDistrito)"
					+ "VALUES(?,?,?,?,?,?,?,?,?)");

			pstm.setString(1, this.nroDocumento.get());
			pstm.setString(2, this.estate.get());
			pstm.setString(3, this.adress.get());
			pstm.setDate(4, this.birthDate);
			pstm.setString(5, this.phoneNumber.get());
			pstm.setString(6, this.email.get());
			pstm.setInt(7, this.idPersona.get());
			pstm.setInt(8, this.idDocumentType.get());
			pstm.setInt(9, this.idDistrict.get());

			estate = pstm.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return estate;
	}

	public int deletePostulant(Connection connection){

		try {
			PreparedStatement pstm = connection.prepareStatement("delete from postulante where idPostulante = ?");

			pstm.setInt(1, this.idPostulante.get());

			estateTrx = pstm.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return estateTrx;
	}


	public static int getPostulantID(Connection connection){
		int postulantID = 0;
		try {
			PreparedStatement pstm = connection.prepareStatement("select max(idPostulante) 'idPostulante' FROM postulante");

			ResultSet rs;
			rs = pstm.executeQuery();

			while(rs.next()){
				postulantID = rs.getInt("idPostulante");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return postulantID;
	}

	public ObservableList<Postulant> getListPostulantByID(Connection connection){
		ObservableList<Postulant> aux = FXCollections.observableArrayList();
		try {
			PreparedStatement pstm = connection.prepareStatement("select p.idPostulante, pe.nombres, concat_ws" +
					 "	(' ',pe.apellidoPaterno,pe.apellidoMaterno) 'Apellidos', p.estado from postulante p" +
					 "inner join Persona pe on pe.idPersona = p.idPersona" +
					 "inner join ficha_inscripcion fi on fi.idPostulante = p.idPostulante" +
					 "where fi.idPostulante = ?;");

			pstm.setInt(1, this.idPostulante.get());

			ResultSet rs = pstm.executeQuery();

			while(rs.next()){
				Postulant postulant = new Postulant();
				postulant.setIdPostulante(rs.getInt("idPostulante"));
				postulant.setName("nombres");
				postulant.setLastName("Apellidos");
				postulant.setEstate("estado");
				aux.add(postulant);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return aux;
	}
}