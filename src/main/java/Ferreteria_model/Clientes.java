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
public class Clientes {
    private int id_cliente;
    private String nombre;
    private String correo;
    private String telefono;

    public Clientes(int id_cliente, String nombre, String correo, String telefono) {
        this.id_cliente = id_cliente;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
    }
public Clientes() {
    // No hace nada, pero permite crear el objeto vacío
}
    
    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
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
    
     public static List<Clientes> obtener(String filtro) {
        List<Clientes> clientes = new ArrayList<>();
        try {
            Connection conexion = ConnectionDB.conectar();
            PreparedStatement statement = conexion.prepareStatement("SELECT id_cliente, nombre, correo, telefono FROM clientes WHERE nombre LIKE ?");
            statement.setString(1, "%" + filtro + "%");
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                Clientes c = new Clientes();
                c.setId_cliente(resultSet.getInt(1));
                c.setNombre(resultSet.getString(2));
                c.setCorreo(resultSet.getString(3));
                c.setTelefono(resultSet.getString(4));

                clientes.add(c);
            }
        } catch (Exception ex) {
            System.err.println("Ocurrió un error: " + ex.getMessage());
        }
        return clientes;
    }

  public static boolean guardar(String nombre, String correo, String telefono) {
        boolean resultado = false;
        try {
            Connection conexion = ConnectionDB.conectar();
            String consulta = "INSERT INTO clientes (nombre, correo, telefono) VALUES (?, ?, ?)";
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
   public static boolean editar (int id_cliente, String nombre, String correo, String telefono){
        boolean resultado = false;
        try{
        Connection conexion = ConnectionDB.conectar();
        String query = "UPDATE clientes SET nombre=?, correo=?, telefono=? WHERE id_cliente= ?" ;
        PreparedStatement statement = conexion.prepareStatement(query);
        statement.setString(1, nombre);
        statement.setString(2, correo);
        statement.setString(3, telefono);
        statement.setInt(4, id_cliente);
        statement.execute();
        resultado = statement.getUpdateCount() == 1;
        conexion.close();
        }catch(Exception ex){
        System.err.println("Ocurrio un error:" + ex.getMessage());
    }
    return resultado;
    }
    public static boolean eliminar(int id_cliente){
     boolean resultado = true;
        try{
        Connection conexion = ConnectionDB.conectar();
        String consulta = "DELETE from clientes WHERE id_cliente = ?" ;
        PreparedStatement statement = conexion.prepareStatement(consulta);
        statement.setInt(1,id_cliente);
        statement.execute();
        resultado = statement.getUpdateCount() == 1;
        
        conexion.close();
        }catch(Exception ex){
        System.err.println("Ocurrio un error:" + ex.getMessage());
    }
    return resultado;
    }
}
