package com.example.tap2024.models;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AlbumDAO {
    private int idAlbum;
    private String banda;
    private String nombre; // New attribute for Album Name
    private String añoSalida;
    private String foto;

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

    public boolean esNuevo() {
        return idAlbum <= 0;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int INSERT() {
        int rowCount;
        String query = "INSERT INTO tblAlbum(Banda, Nombre, Año_salida, Foto) VALUES('" + this.banda + "','" + this.nombre + "','" + this.añoSalida + "','" + this.foto + "')";
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
        String query = "UPDATE tblAlbum SET Banda = '" + this.banda + "', Nombre = '" + this.nombre + "', Año_salida = '" + this.añoSalida + "', Foto = '" + this.foto + "' WHERE idalbum = " + this.idAlbum;
        try {
            Statement stmt = Conexion.conexion.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void DELETE() {
        String query = "DELETE FROM tblAlbum WHERE idalbum = " + this.idAlbum;
        try {
            Statement stmt = Conexion.conexion.createStatement();
            stmt.executeUpdate(query);
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
                album.setFoto(rs.getString("Foto"));
                listaAlbum.add(album);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaAlbum;
    }

}
