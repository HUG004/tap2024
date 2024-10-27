package com.example.tap2024.models;

import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;

public class VentaCancionDAO {
    private int idCancion;
    private int idVenta;
    private String descripcion;
    private int idCancionOriginal;
    private int idVentaOriginal;

    public void setIdCancionOriginal(int idCancion) {
        this.idCancionOriginal = idCancion;
    }

    public void setIdVentaOriginal(int idVenta) {
        this.idVentaOriginal = idVenta;
    }

    public int getIdCancion() {
        return idCancion;
    }

    public void setIdCancion(int idCancion) {
        this.idCancion = idCancion;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int INSERT() {
        int rowCount;
        String query = "INSERT INTO Ventas_Cancion(idCancion, idVenta, Detalle) " +
                "VALUES (" + this.idCancion + ", " + this.idVenta + ", '" + this.descripcion + "')";
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
        String query = "UPDATE Ventas_Cancion SET idCancion = " + this.idCancion +
                ", idVenta = " + this.idVenta +
                ", Detalle = '" + this.descripcion + "' " +
                "WHERE idCancion = " + this.idCancionOriginal +
                " AND idVenta = " + this.idVentaOriginal;
        try {
            Statement stmt = Conexion.conexion.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void DELETE() {
        String query = "DELETE FROM Ventas_Cancion WHERE idCancion = " + this.idCancion +
                " AND idVenta = " + this.idVenta;
        try {
            Statement stmt = Conexion.conexion.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<VentaCancionDAO> SELECTALL() {
        ObservableList<VentaCancionDAO> listaRelaciones = FXCollections.observableArrayList();
        String query = "SELECT idCancion, idVenta, Detalle FROM Ventas_Cancion";
        try {
            Statement stmt = Conexion.conexion.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                VentaCancionDAO relacion = new VentaCancionDAO();
                relacion.setIdCancion(rs.getInt("idCancion"));
                relacion.setIdVenta(rs.getInt("idVenta"));
                relacion.setDescripcion(rs.getString("Detalle"));
                listaRelaciones.add(relacion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaRelaciones;
    }
}

