
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ferreteria_model;

import Ferreteria.db.ConnectionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dell
 */
public class Usuario {

    public Usuario() {
        
    }

    /**
     * @return the activo
     */
    public int getActivo() {
        return activo;
    }

    /**
     * @param activo the activo to set
     */
    public void setActivo(int activo) {
        this.activo = activo;
    }
      public Usuario(int id_usuario1, String nombre1, String correo1, String contraseña1, String rol1) {
    }
    private int id_usuario;
    private String nombre;
    private String correo;
    private String password;
    private String rol;
    private int activo;


    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void add(Usuario usuarios) {
        
    }
    
    public static List<Usuario> obtener(String filtro) {
        List<Usuario> usuarios = new ArrayList<>();
        try {
            Connection conexion = ConnectionDB.conectar();
            PreparedStatement statement = conexion.prepareStatement("SELECT id_usuario, nombre, correo, password, rol, activo FROM usuarios WHERE nombre LIKE ?");
            statement.setString(1, "%" + filtro + "%");
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                Usuario u = new Usuario();
                u.setId_usuario(resultSet.getInt(1));
                u.setNombre(resultSet.getString(2));
                u.setCorreo(resultSet.getString(3));
                u.setPassword(resultSet.getString(4));
                u.setRol(resultSet.getString(5));
                u.setActivo(resultSet.getInt(6));

                usuarios.add(u);
            }
        } catch (Exception ex) {
            System.err.println("Ocurrió un error: " + ex.getMessage());
        }
        return usuarios;
    }
    
    public static boolean guardar(String nombre, String correo, String password, String rol, int activo) {
        boolean resultado = false;
        try {
            Connection conexion = ConnectionDB.conectar();
            String consulta = "INSERT INTO usuarios (nombre, correo, password, rol, activo) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setString(1, nombre);
            statement.setString(2, correo);
            statement.setString(3, password);
            statement.setString(4, rol);
            statement.setInt(5, activo);
            statement.execute();
            
            resultado = statement.getUpdateCount() == 1;
            
            
        } catch (Exception ex) {
            System.err.println("Ocurrió un error: " + ex.getMessage());
        }
        return resultado;
    }
     public static boolean editar (int id_usuario, String nombre, String correo, String password, String rol, int activo){
        boolean resultado = false;
        try{
        Connection conexion = ConnectionDB.conectar();
        String query = "UPDATE usuarios SET nombre=?, correo=?, password=?, rol=?, activo=? WHERE id_usuario= ?" ;
        PreparedStatement statement = conexion.prepareStatement(query);
        statement.setString(1, nombre);
        statement.setString(2, correo);
        statement.setString(3, password);
        statement.setString(4, rol);
        statement.setInt(5, activo);
        statement.setInt(6, id_usuario);
        statement.execute();
        resultado = statement.getUpdateCount() == 1;
        conexion.close();
        }catch(Exception ex){
        System.err.println("Ocurrio un error:" + ex.getMessage());
    }
    return resultado;
    }
     public static boolean eliminar(int id_usuario){
     boolean resultado = true;
        try{
        Connection conexion = ConnectionDB.conectar();
        String consulta = "DELETE from usuarios WHERE id_usuario= ?" ;
        PreparedStatement statement = conexion.prepareStatement(consulta);
        statement.setInt(1,id_usuario);
        statement.execute();
        resultado = statement.getUpdateCount() == 1;
        
        conexion.close();
        }catch(Exception ex){
        System.err.println("Ocurrio un error:" + ex.getMessage());
    }
    return resultado;
    }
}
