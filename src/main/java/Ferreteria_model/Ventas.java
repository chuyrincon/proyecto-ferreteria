package Ferreteria_model;

import Ferreteria.db.ConnectionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Ventas {
    private int id_venta;
    private int id_cliente;
    private String cliente;
    private int id_producto;
    private String producto;
    private double stock;  // Cambiado a double
    private double precio; // Cambiado a double

    public Ventas(int id_venta, int id_cliente, String cliente, int id_producto, String producto, double stock, double precio) {
        this.id_venta = id_venta;
        this.id_cliente = id_cliente;
        this.cliente = cliente;
        this.id_producto = id_producto;
        this.producto = producto;
        this.stock = stock;
        this.precio = precio;
    }

    // Getters y Setters
    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    // Método para obtener ventas, si es necesario
    public static List<Ventas> obtenerVentas(String filtro) {
        List<Ventas> ventas = new ArrayList<>();
        try {
            Connection conexion = ConnectionDB.conectar();
            String query = "SELECT v.id_venta, v.id_cliente, c.nombre AS cliente, v.id_producto, p.nombre AS producto, "
                         + "v.stock, v.precio "
                         + "FROM ventas v "
                         + "JOIN clientes c ON v.id_cliente = c.id_cliente "
                         + "JOIN productos p ON v.id_producto = p.id_producto "
                         + "WHERE c.nombre LIKE ? OR p.nombre LIKE ?";
                         
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setString(1, "%" + filtro + "%");
            statement.setString(2, "%" + filtro + "%");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Ventas venta = new Ventas(
                    resultSet.getInt("id_venta"),
                    resultSet.getInt("id_cliente"),
                    resultSet.getString("cliente"),
                    resultSet.getInt("id_producto"),
                    resultSet.getString("producto"),
                    resultSet.getDouble("stock"),   // Obtenemos como double
                    resultSet.getDouble("precio")   // Obtenemos como double
                );

                ventas.add(venta);
            }
        } catch (Exception ex) {
            System.err.println("Ocurrió un error: " + ex.getMessage());
        }
        return ventas;
    }
}
