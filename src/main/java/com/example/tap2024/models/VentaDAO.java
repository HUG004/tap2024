package com.example.tap2024.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class VentaDAO {
    private int idVenta;
    private float precio;
    private int idCliente;
    private String nombreCliente;

    // Getters y Setters
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

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }


    public int INSERT() {
        int rowCount;
        String query = "INSERT INTO tblVentas (Precio, idClt) VALUES (" + this.precio + ", " + this.idCliente + ")";
        try {
            Statement stmt = Conexion.conexion.createStatement();
            rowCount = stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            rowCount = 0;
        }
        return rowCount;
    }


    public ObservableList<VentaDAO> SELECTALL() {
        ObservableList<VentaDAO> listaVentas = FXCollections.observableArrayList();
        String query = """
        SELECT v.ID_Venta, v.Precio, v.idClt, c.nomClt AS Nombre
        FROM tblVentas v
        JOIN tblcliente c ON v.idClt = c.idClt
    """;

        try {
            Statement stmt = Conexion.conexion.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                VentaDAO venta = new VentaDAO();
                venta.setIdVenta(res.getInt("ID_Venta"));
                venta.setPrecio(res.getFloat("Precio"));
                venta.setIdCliente(res.getInt("idClt"));
                venta.setNombreCliente(res.getString("Nombre"));
                listaVentas.add(venta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaVentas;
    }



    public int UPDATE() {
        int rowCount = 0;
        String query = "UPDATE tblVentas SET Precio = " + this.precio + " WHERE ID_Venta = " + this.idVenta;
        try {
            Statement stmt = Conexion.conexion.createStatement();
            rowCount = stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowCount;
    }

    public ObservableList<CancionDAO> obtenerHistorialCompras(int idCliente) {
        ObservableList<CancionDAO> historialCompras = FXCollections.observableArrayList();
        String query = """
            SELECT tblCancion.ID_Cancion, tblCancion.NomCancion, tblCancion.costoCancion
            FROM tblVentas
            INNER JOIN Ventas_Cancion ON tblVentas.ID_Venta = Ventas_Cancion.idVenta
            INNER JOIN tblCancion ON tblCancion.ID_Cancion = Ventas_Cancion.idCancion
            WHERE tblVentas.idClt = ?
            """;
        try (PreparedStatement stmt = Conexion.conexion.prepareStatement(query)) {
            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                CancionDAO cancion = new CancionDAO();
                cancion.setIdCancion(rs.getInt("ID_Cancion"));
                cancion.setNombre(rs.getString("NomCancion"));
                cancion.setCostoCancion(rs.getFloat("costoCancion"));
                historialCompras.add(cancion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historialCompras;
    }
    @Override
    public String toString() {
        return String.valueOf(idVenta); // Retorna el ID de la venta como texto
    }
}