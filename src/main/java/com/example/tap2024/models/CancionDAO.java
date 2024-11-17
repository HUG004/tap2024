package com.example.tap2024.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CancionDAO {
    private int idCancion;
    private String nombre;
    private float costoCancion;
    private String tipoGenero;

    public GeneroDAO getGenero() {
        GeneroDAO genero = new GeneroDAO();
        genero.setTipoGenero(this.tipoGenero);
        return genero;
    }
    @Override
    public String toString() {
        return this.getNombre();
    }

    public int getIdCancion() {
        return idCancion;
    }

    public void setIdCancion(int idCancion) {
        this.idCancion = idCancion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getCostoCancion() {
        return costoCancion;
    }

    public void setCostoCancion(float costoCancion) {
        this.costoCancion = costoCancion;
    }

    public String getTipoGenero() {
        return tipoGenero;
    }

    public void setTipoGenero(String tipoGenero) {
        this.tipoGenero = tipoGenero;
    }

    public boolean esNuevo() {
        return idCancion == 0;
    }

    public int INSERT() {
        int rowCount;
        String query = "INSERT INTO tblCancion(NomCancion, costoCancion, tipo_genero) " +
                "VALUES('" + this.nombre + "', " + this.costoCancion + ", '" + this.tipoGenero + "')";
        try {
            Statement stmt = Conexion.conexion.createStatement();
            rowCount = stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            rowCount = 0;
        }
        return rowCount;
    }

    public void UPDATE() {
        String query = "UPDATE tblCancion SET NomCancion = '" + this.nombre + "', costoCancion = " + this.costoCancion +
                ", tipo_genero = '" + this.tipoGenero + "' WHERE ID_Cancion = " + this.idCancion;
        try {
            Statement stmt = Conexion.conexion.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void DELETE() {
        String query = "DELETE FROM tblCancion WHERE ID_Cancion = " + this.idCancion;
        try {
            Statement stmt = Conexion.conexion.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<CancionDAO> SELECTALL() {
        ObservableList<CancionDAO> listaCanciones = FXCollections.observableArrayList();
        String query = "SELECT * FROM tblCancion";
        try {
            Statement stmt = Conexion.conexion.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                CancionDAO cancion = new CancionDAO();
                cancion.setIdCancion(rs.getInt("ID_Cancion"));
                cancion.setNombre(rs.getString("NomCancion"));
                cancion.setCostoCancion(rs.getFloat("costoCancion"));
                cancion.setTipoGenero(rs.getString("tipo_genero"));
                listaCanciones.add(cancion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaCanciones;
    }

}