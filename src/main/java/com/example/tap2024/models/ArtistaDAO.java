package com.example.tap2024.models;

import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;

public class ArtistaDAO {
    private int idArt;
    private String nomArt;
    private String cancion;

    @Override
    public String toString() {
        return this.getNomArt();
    }

    public int getIdArt() {
        return idArt;
    }
    public void setIdArt(int idArt) {
        this.idArt = idArt;
    }

    public String getNomArt() {
        return nomArt;
    }
    public void setNomArt(String nomArt) {
        this.nomArt = nomArt;
    }

    public String getCancion() {
        return cancion;
    }
    public void setCancion(String cancion) {
        this.cancion = cancion;
    }

    public int INSERT() {
        int rowCount;
        String query = "INSERT INTO tblArtista(nomArt, cancion) " +
                "VALUES('" + this.nomArt + "','" + this.cancion + "')";
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
        String query = "UPDATE tblArtista SET nomArt = '" + this.nomArt + "', cancion = '" + this.cancion + "' " +
                "WHERE idArt = " + this.idArt;
        try {
            Statement stmt = Conexion.conexion.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void DELETE() {
        String query = "DELETE FROM tblArtista WHERE idArt = " + this.idArt;
        try {
            Statement stmt = Conexion.conexion.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<ArtistaDAO> SELECTALL() {
        ArtistaDAO objArt;
        String query = "SELECT * FROM tblArtista";
        ObservableList<ArtistaDAO> listaArt = FXCollections.observableArrayList();
        try {
            Statement stmt = Conexion.conexion.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                objArt = new ArtistaDAO();
                objArt.idArt = res.getInt(1);
                objArt.nomArt = res.getString(2);
                objArt.cancion = res.getString(3);
                listaArt.add(objArt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaArt;
    }
}