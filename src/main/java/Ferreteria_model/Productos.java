/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ferreteria_model;

import Ferreteria.db.ConnectionDB;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author Dell
 */
public class Productos {
   private int id_producto;
   private String nombre;
   private String descripcion;
   private int precio;
   private int stock;
   private int id_proveedor;
   private String proveedor; 
   
    public Productos(int id_producto, String nombre, String descripcion, int precio, int stock, int id_proveedor) {
        this.id_producto = id_producto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.id_proveedor = id_proveedor;
    }

    public Productos() {
      
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }
    
    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }
    
    
    public static List<Productos> obtener(String filtro) {
    List<Productos> productos = new ArrayList<>();
    try {
        Connection conexion = ConnectionDB.conectar();
        
        // Consulta SQL para obtener productos con el nombre del proveedor
        String query = "SELECT p.id_producto, p.nombre, p.descripcion, p.precio, p.stock, pr.nombre AS proveedor "
                     + "FROM productos p "
                     + "JOIN provedores pr ON p.id_proveedor = pr.id_proveedor "
                     + "WHERE p.nombre LIKE ? OR p.descripcion LIKE ?";
        
        PreparedStatement statement = conexion.prepareStatement(query);
        statement.setString(1, "%" + filtro + "%");
        statement.setString(2, "%" + filtro + "%");

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Productos p = new Productos();
            p.setId_producto(resultSet.getInt("id_producto"));
            p.setNombre(resultSet.getString("nombre"));
            p.setDescripcion(resultSet.getString("descripcion"));
            p.setPrecio(resultSet.getInt("precio"));
            p.setStock(resultSet.getInt("stock"));
            // El nombre del proveedor se obtiene con alias "proveedor"
            p.setProveedor(resultSet.getString("proveedor")); 

            productos.add(p);
        }
    } catch (Exception ex) {
        System.err.println("Ocurrió un error: " + ex.getMessage());
    }
    return productos;
}

    
    public static boolean guardar(String nombre, String descripcion, int precio, int stock, int id_proveedor) {
    boolean resultado = false;
    try {
        Connection conexion = ConnectionDB.conectar();
        String consulta = "INSERT INTO productos (nombre, descripcion, precio, stock, id_proveedor) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = conexion.prepareStatement(consulta);
        statement.setString(1, nombre);
        statement.setString(2, descripcion);
        statement.setInt(3, precio);
        statement.setInt(4, stock);
        statement.setInt(5, id_proveedor);
        statement.execute();
        
        resultado = statement.getUpdateCount() == 1;
    } catch (Exception ex) {
        System.err.println("Ocurrió un error: " + ex.getMessage());
    }
    return resultado;
}

   public static boolean editar(int id_producto, String nombre, String descripcion, int precio, int stock, int id_proveedor) {
    boolean resultado = false;
    try {
        Connection conexion = ConnectionDB.conectar();
        String query = "UPDATE productos SET nombre=?, descripcion=?, precio=?, stock=?, id_proveedor=? WHERE id_producto=?";
        PreparedStatement statement = conexion.prepareStatement(query);
        statement.setString(1, nombre);
        statement.setString(2, descripcion);
        statement.setInt(3, precio);
        statement.setInt(4, stock);
        statement.setInt(5, id_proveedor);
        statement.setInt(6, id_producto);
        statement.execute();
        
        resultado = statement.getUpdateCount() == 1;
    } catch (Exception ex) {
        System.err.println("Ocurrió un error: " + ex.getMessage());
    }
    return resultado;
}

    public static boolean eliminar(int id_producto){
     boolean resultado = true;
        try{
        Connection conexion = ConnectionDB.conectar();
        String consulta = "DELETE from Productos WHERE id_producto = ?" ;
        PreparedStatement statement = conexion.prepareStatement(consulta);
        statement.setInt(1,id_producto);
        statement.execute();
        resultado = statement.getUpdateCount() == 1;
        
        conexion.close();
        }catch(Exception ex){
        System.err.println("Ocurrio un error:" + ex.getMessage());
    }
    return resultado;
    }
    
    
    public static boolean editarStock(int idProducto, int nuevoStock) {
    boolean resultado = false;
    try {
        Connection conexion = ConnectionDB.conectar();
        String query = "UPDATE productos SET stock=? WHERE id_producto=?";
        PreparedStatement statement = conexion.prepareStatement(query);
        statement.setInt(1, nuevoStock);
        statement.setInt(2, idProducto);
        statement.execute();

        resultado = statement.getUpdateCount() == 1;  // Si se actualizó correctamente
    } catch (Exception ex) {
        System.err.println("Ocurrió un error al actualizar el stock: " + ex.getMessage());
    }
    return resultado;
}

     public JasperPrint reporteTodoProducto() {
         Connection conexion = ConnectionDB.conectar(); // tu conexión

    try {
        // Recurso del reporte corregido
        InputStream is = getClass().getResourceAsStream("/reporte/Blank_A4.jasper");

        if (is == null) {
            JOptionPane.showMessageDialog(null, "No se encontró el archivo del reporte.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        JasperReport reporte = (JasperReport) JRLoader.loadObject(is);
        JasperPrint print = JasperFillManager.fillReport(reporte, null, conexion);
        return print;

    } catch (JRException ex) {
        Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, null, ex);
        return null;
    }
     }

}

