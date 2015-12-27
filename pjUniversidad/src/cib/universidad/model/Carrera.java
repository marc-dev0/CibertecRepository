package cib.universidad.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cib.universidad.connection.MySqlConnection;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class Carrera {

	private final IntegerProperty codigoCarrera;
	private final StringProperty descripcion;
	private final BooleanProperty estado;
	private int estateTrx;
	public Carrera(){
		this(0, null,true);
	}

	public Carrera(int codigoCarrera, String descripcion, boolean estado){
		this.codigoCarrera = new SimpleIntegerProperty(codigoCarrera);
		this.descripcion = new SimpleStringProperty(descripcion);
		this.estado = new SimpleBooleanProperty(estado);

	}

	public int getCodigoCarrera(){
		return codigoCarrera.get();
	}

	public void setCodigoCarrera(int codigoCarrera){
		this.codigoCarrera.set(codigoCarrera);
	}

	public IntegerProperty codigoCarreraProperty(){
		return codigoCarrera;
	}

	public String getDescripcion(){
		return descripcion.get();
	}

	public void setDescripcion(String descripcion){
		this.descripcion.set(descripcion);
	}

	public StringProperty descripcionProperty(){
		return descripcion;
	}

	public boolean getEstado(){
		return estado.get();
	}

	public void setEstado(boolean estado){
		this.estado.set(estado);
	}

	public BooleanProperty estadoProperty(){
		return estado;
	}

	public static void listarCarrera(Connection connection, ObservableList<Carrera> lista){
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT idCarrera, descripcion, estado FROM carrera");
			while(rs.next()){
				lista.add(new Carrera(rs.getInt("idCarrera"), rs.getString("descripcion"), rs.getBoolean("estado")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int guardarRegistro(Connection connection){

		try {

			PreparedStatement pstm = connection.prepareStatement("INSERT carrera (descripcion, estado) values (?,?)");

			//que datos se guardaran en los parametros de la consulta
			pstm.setString(1, this.descripcion.get());
			pstm.setBoolean(2, this.estado.get());

			estateTrx = pstm.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return estateTrx;
	}


	public int actualizarCarrera(Connection connection){

		try {
			PreparedStatement pstm = connection.prepareStatement("update carrera set descripcion = ?, estado = ? "
					+ "where idCarrera = ?");

			pstm.setString(1, this.descripcion.get());
			pstm.setBoolean(2, this.estado.get());
			pstm.setInt(3, this.codigoCarrera.get());

			estateTrx = pstm.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return estateTrx;
	}

	public int eliminarCarrera(Connection connection){

		try {
			PreparedStatement pstm = connection.prepareStatement("delete from carrera where idCarrera = ?");

			pstm.setInt(1, this.getCodigoCarrera());

			estateTrx = pstm.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return estateTrx;
	}

	@Override
	public String toString(){
		return this.descripcion.get();
	}

}
