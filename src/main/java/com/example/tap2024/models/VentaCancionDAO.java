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

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdCancion() {
        return idCancion;
    }

    public void setIdCancion(int idCancion) {
        this.idCancion = idCancion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int INSERT() {
        int rowCount = 0;
        // Verificar si ya existe la relación antes de insertar
        if (!exists(idCancion, idVenta)) {
            String query = "INSERT INTO Ventas_Cancion(idCancion, idVenta, Detalle) " +
                    "VALUES (" + this.idCancion + ", " + this.idVenta + ", '" + this.descripcion + "')";
            try {
                Statement stmt = Conexion.conexion.createStatement();
                rowCount = stmt.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Error: Ya existe una entrada para idCancion " + idCancion + " y idVenta " + idVenta);
        }
        return rowCount;
    }

    // Método para verificar si la relación ya existe
    private boolean exists(int idCancion, int idVenta) {
        String query = "SELECT COUNT(*) FROM Ventas_Cancion WHERE idCancion = " + idCancion + " AND idVenta = " + idVenta;
        try {
            Statement stmt = Conexion.conexion.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                return rs.getInt(1) > 0; // Retorna true si existe al menos una fila
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Retorna false si ocurre un error o no existe
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