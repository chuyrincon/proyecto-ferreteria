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
public class Provedores {
    private int id_proveedor;
    private String nombre;
    private String correo;
    private String telefono;

    public Provedores(int id_proveedor, String nombre, String correo, String telefono) {
        this.id_proveedor = id_proveedor;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
    }

    public Provedores() {
        
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
     public static List<Provedores> obtener(String filtro) {
        List<Provedores> provedores = new ArrayList<>();
        try {
            Connection conexion = ConnectionDB.conectar();
            PreparedStatement statement = conexion.prepareStatement("SELECT id_proveedor, nombre, correo, telefono FROM provedores WHERE nombre LIKE ?");
            statement.setString(1, "%" + filtro + "%");
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                Provedores p = new Provedores();
                p.setId_proveedor(resultSet.getInt(1));
                p.setNombre(resultSet.getString(2));
                p.setCorreo(resultSet.getString(3));
                p.setTelefono(resultSet.getString(4));

                provedores.add(p);
            }
        } catch (Exception ex) {
            System.err.println("Ocurrió un error: " + ex.getMessage());
        }
        return provedores;
    }
     public static boolean guardar(String nombre, String correo, String telefono) {
        boolean resultado = false;
        try {
            Connection conexion = ConnectionDB.conectar();
            String consulta = "INSERT INTO provedores (nombre, correo, telefono) VALUES (?, ?, ?)";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setString(1, nombre);
            statement.setString(2, correo);
            statement.setString(3, telefono);
            statement.execute();
            
            resultado = statement.getUpdateCount() == 1;
            
            
        } catch (Exception ex) {
            System.err.println("Ocurrió un error: " + ex.getMessage());
        }
        return resultado;
    }
     public static boolean editar (int id_proveedor, String nombre, String correo, String telefono){
        boolean resultado = false;
        try{
        Connection conexion = ConnectionDB.conectar();
        String query = "UPDATE provedores SET nombre=?, correo=?, telefono=? WHERE id_proveedor= ?" ;
        PreparedStatement statement = conexion.prepareStatement(query);
        statement.setString(1, nombre);
        statement.setString(2, correo);
        statement.setString(3, telefono);
        statement.setInt(4, id_proveedor);
        statement.execute();
        resultado = statement.getUpdateCount() == 1;
        conexion.close();
        }catch(Exception ex){
        System.err.println("Ocurrio un error:" + ex.getMessage());
    }
    return resultado;
    }
     public static boolean eliminar(int id_proveedor){
     boolean resultado = true;
        try{
        Connection conexion = ConnectionDB.conectar();
        String consulta = "DELETE from provedores WHERE id_proveedor = ?" ;
        PreparedStatement statement = conexion.prepareStatement(consulta);
        statement.setInt(1,id_proveedor);
        statement.execute();
        resultado = statement.getUpdateCount() == 1;
        
        conexion.close();
        }catch(Exception ex){
        System.err.println("Ocurrio un error:" + ex.getMessage());
    }
    return resultado;
    }

    
}
