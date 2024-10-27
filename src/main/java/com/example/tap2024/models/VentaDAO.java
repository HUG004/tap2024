package com.example.tap2024.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class VentaDAO {
    private int idVenta;
    private float precio;
    private int idCliente;  // Foreign key from ClienteDAO

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    // Insert a new sale into the database
    public int INSERT() {
        int rowCount;
        String query = "INSERT INTO tblventa (precio, idCliente) VALUES (" + this.precio + "," + this.idCliente + ")";
        try {
            Statement stmt = Conexion.conexion.createStatement();
            rowCount = stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            rowCount = 0;
        }
        return rowCount;
    }

    // Retrieve all sales from the database
    public ObservableList<VentaDAO> SELECTALL() {
        ObservableList<VentaDAO> listaVentas = FXCollections.observableArrayList();
        String query = "SELECT * FROM tblventas";
        try {
            Statement stmt = Conexion.conexion.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                VentaDAO venta = new VentaDAO();
                venta.setIdVenta(res.getInt("idVenta"));
                venta.setPrecio(res.getFloat("precio"));
                venta.setIdCliente(res.getInt("idCliente"));
                listaVentas.add(venta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaVentas;
    }

}
