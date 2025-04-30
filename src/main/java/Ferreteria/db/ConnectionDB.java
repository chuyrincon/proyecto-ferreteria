/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ferreteria.db;


import Ferreteria_model.Usuario;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 
Clase para manejar la conexión a una base de datos MariaDB.*/
public class ConnectionDB {
    private static final String URL = "jdbc:mariadb://localhost:3306/ferreteria";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "1234";

    public static Connection conectar() {
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
        }
        return conexion;
    }

    public static void main(String[] args) {
        conectar();
    }

    public static Connection obtener() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
public static boolean autenticarUsuario(String username, String password) {
        String sql = "SELECT * FROM usuarios WHERE username = ? AND password = ?";

        try (Connection conn = obtener(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Se asignan los valores a la consulta SQL
            stmt.setString(1, username);
            stmt.setString(2, password);

            // Se ejecuta la consulta
            ResultSet rs = stmt.executeQuery();

            // Si hay al menos un resultado, significa que las credenciales son correctas
            return rs.next();
        } catch (SQLException e) {
            // Se imprime cualquier error en consola
            e.printStackTrace();
            return false;
        }
    }

public static boolean registrarUsuario(String username, String password) {
        String sql = "INSERT INTO usuarios (nombre, password) VALUES (?, ?)";

        try (Connection conn = obtener(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Se asignan los valores de Usuario y contraseña a la consulta
            stmt.setString(1, username);
            stmt.setString(2, password);

            // Se ejecuta la consulta y se verifica si se insertó al menos una fila
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            // Se imprime cualquier error en consola
            e.printStackTrace();
            return false;
        }
}
    public static List<Usuario> obtenerUsuarios() {
        List<Usuario> usuarios1 = new ArrayList<>();
        String query = "SELECT id_usuario, nombre, correo, password, rol FROM tblUsuarios";
        
        try (Connection conn = conectar(); 
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Iterar a través de los resultados de la consulta
            while (rs.next()) {
                // Crear un objeto Usuario y asignar los valores obtenidos
                Usuario usuarios = new Usuario(
                        rs.getInt("id_usuario"),          
                        rs.getString("nombre"),      
                        rs.getString("correo"),   
                        rs.getString("contraseña"), 
                        rs.getString("rol")       
                );
                usuarios.add(usuarios);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios1;
    }

}