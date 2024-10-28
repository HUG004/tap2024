package com.example.tap2024.models;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.*;

public class AlbumDAO {
    private int idAlbum;
    private String banda;
    private String nombre;
    private String añoSalida;
    private byte[] imagen;

    @Override
    public String toString() {
        return this.getNombre();
    }

    public int getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(int idAlbum) {
        this.idAlbum = idAlbum;
    }

    public String getBanda() {
        return banda;
    }

    public void setBanda(String banda) {
        this.banda = banda;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAñoSalida() {
        return añoSalida;
    }

    public void setAñoSalida(String añoSalida) {
        this.añoSalida = añoSalida;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public boolean esNuevo() {
        return idAlbum <= 0;
    }

    public int INSERT() {
        int rowCount;
        String query = "INSERT INTO tblAlbum(Banda, Nombre, Año_salida, imagen) VALUES(?, ?, ?, ?)";
        try {
            PreparedStatement stmt = Conexion.conexion.prepareStatement(query);
            stmt.setString(1, this.banda);
            stmt.setString(2, this.nombre);
            stmt.setString(3, this.añoSalida);
            stmt.setBytes(4, this.imagen);
            rowCount = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            rowCount = 0;
        }
        return rowCount;
    }

    public void UPDATE() {
        String query = "UPDATE tblAlbum SET Banda = ?, Nombre = ?, Año_salida = ?, imagen = ? WHERE idalbum = ?";
        try {
            PreparedStatement stmt = Conexion.conexion.prepareStatement(query);
            stmt.setString(1, this.banda);
            stmt.setString(2, this.nombre);
            stmt.setString(3, this.añoSalida);
            stmt.setBytes(4, this.imagen);
            stmt.setInt(5, this.idAlbum);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void DELETE() {
        String query = "DELETE FROM tblAlbum WHERE idalbum = ?";
        try {
            PreparedStatement stmt = Conexion.conexion.prepareStatement(query);
            stmt.setInt(1, this.idAlbum);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<AlbumDAO> SELECTALL() {
        ObservableList<AlbumDAO> listaAlbum = FXCollections.observableArrayList();
        String query = "SELECT * FROM tblAlbum";
        try {
            Statement stmt = Conexion.conexion.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                AlbumDAO album = new AlbumDAO();
                album.setIdAlbum(rs.getInt("idalbum"));
                album.setBanda(rs.getString("Banda"));
                album.setNombre(rs.getString("Nombre"));
                album.setAñoSalida(rs.getString("Año_salida"));
                album.setImagen(rs.getBytes("imagen"));
                listaAlbum.add(album);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaAlbum;
    }

}
