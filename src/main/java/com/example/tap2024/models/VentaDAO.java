package com.example.tap2024.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.sql.*;

public class VentaDAO {
    private int idVenta;
    private float precio;
    private int idCliente;
    private String nombreCliente;
    private String fechaVenta;

    public String getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

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
        String query = "INSERT INTO tblVentas (Precio, idClt, FechaVenta) VALUES ("
                + this.precio + ", " + this.idCliente + ", CURDATE())";
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
        SELECT v.ID_Venta, v.Precio, v.idClt, c.nomClt AS Nombre, v.FechaVenta
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
                venta.setFechaVenta(res.getDate("FechaVenta").toString());
                listaVentas.add(venta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaVentas;
    }
    public int obtenerVentasDelMes() {
        int totalVentas = 0;
        String query = """
        SELECT COUNT(*) AS totalVentas
        FROM tblVentas
        WHERE MONTH(FechaVenta) = MONTH(CURDATE()) AND YEAR(FechaVenta) = YEAR(CURDATE())
        """;

        try (Statement stmt = Conexion.conexion.createStatement();
             ResultSet res = stmt.executeQuery(query)) {

            if (res.next()) {
                totalVentas = res.getInt("totalVentas");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalVentas;
    }

    public static ObservableList<PieChart.Data> obtenerArtistasConMasVentas() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        // Consulta que relaciona ventas con artistas
        String query = """
        SELECT a.nomArt AS artista, COUNT(vc.idVenta) AS ventas
        FROM Ventas_Cancion vc
        JOIN Cancion_Artista ca ON vc.idCancion = ca.idCancion
        JOIN tblArtista a ON ca.idArtista = a.idArt
        GROUP BY a.nomArt
    """;

        try (Statement stmt = Conexion.conexion.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String artista = rs.getString("artista");
                int ventas = rs.getInt("ventas");
                pieChartData.add(new PieChart.Data(artista, ventas));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pieChartData;
    }

    public static ObservableList<XYChart.Data<String, Number>> obtenerCancionesMasVendidas() {
        ObservableList<XYChart.Data<String, Number>> barChartData = FXCollections.observableArrayList();

        String query = """
        SELECT c.NomCancion AS cancion, COUNT(vc.idVenta) AS ventas
        FROM Ventas_Cancion vc
        INNER JOIN tblCancion c ON vc.idCancion = ID_Cancion
        GROUP BY c.NomCancion
        ORDER BY ventas DESC
        """;

        try (Statement stmt = Conexion.conexion.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String cancion = rs.getString("cancion");
                int ventas = rs.getInt("ventas");
                barChartData.add(new XYChart.Data<>(cancion, ventas));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return barChartData;
    }


    public int UPDATE() {
        String query = "UPDATE tblVentas SET Precio = ?, idClt = ? WHERE ID_Venta = ?";
        try (PreparedStatement stmt = Conexion.conexion.prepareStatement(query)) {
            stmt.setFloat(1, this.precio);
            stmt.setInt(2, this.idCliente);
            stmt.setInt(3, this.idVenta);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Return 0 si falla
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