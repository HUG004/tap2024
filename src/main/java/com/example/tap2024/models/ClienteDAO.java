package com.example.tap2024.models;

import java.sql.SQLException;
import java.sql.Statement;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ClienteDAO {
    private int idClt;
    private String nomClt;
    private String telClt;
    private String emailClt;

    public int getIdClt() {
        return idClt;
    }
    public void setIdClt(int idClt) {
        this.idClt = idClt;
    }

    public String getNomClt() {
        return nomClt;
    }
    public void setNomClt(String nomClt) {
        this.nomClt = nomClt;
    }

    public String getTelClt() {
        return telClt;
    }
    public void setTelClt(String telClt) {
        this.telClt = telClt;
    }

    public String getEmailClt() {
        return emailClt;
    }
    public void setEmailClt(String emailClt) {
        this.emailClt = emailClt;
    }

    public int INSERT(){
        int rowCount;
        String query ="INSERT INTO tblcliente(nomClt, telClt, emailClt)" +
                " VALUES('"+this.nomClt+"','"+this.telClt+"','"+this.emailClt+"')";
        try {
            Statement stmt = Conexion.conexion.createStatement();
            rowCount = stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            rowCount = 0;
        }
        return rowCount;
    }

    public void UPDATE(){

        String query="UPDATE tblcliente SET nomClt = '"+this.nomClt+"', telClt = '"+this.telClt+"', " +
                "emailClt = '"+this.emailClt+"' WHERE idClt = "+this.idClt;
        try {
            Statement stmt = Conexion.conexion.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void DELETE(){
        String query="DELETE FROM tblcliente WHERE idClt = " + this.idClt;
        try {
            Statement stmt = Conexion.conexion.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<ClienteDAO> SELECTALL(){
        ClienteDAO objCte;
        String query = "SELECT * FROM tblCliente";
        ObservableList<ClienteDAO> listaC = FXCollections.observableArrayList();
        try{
            Statement stmt = Conexion.conexion.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while( res.next() ){
                objCte = new ClienteDAO();
                objCte.idClt = res.getInt(1);
                objCte.nomClt = res.getString(2);
                objCte.telClt = res.getString(3);
                objCte.emailClt = res.getString(4);
                listaC.add(objCte);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return listaC;
    }


}