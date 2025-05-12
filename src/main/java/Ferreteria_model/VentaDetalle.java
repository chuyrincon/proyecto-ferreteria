package Ferreteria_model;

import Ferreteria.db.ConnectionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VentaDetalle {
    private int id_detalle;
    private int id_venta;
    private int id_producto;
    private String producto;
    private int cantidad;
    private int subtotal;

    public VentaDetalle() {}

    public VentaDetalle(int id_detalle, int id_venta, int id_producto, int cantidad, int subtotal) {
        this.id_detalle = id_detalle;
        this.id_venta = id_venta;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    // Getters y setters...

    public int getId_detalle() {
        return id_detalle;
    }

    public void setId_detalle(int id_detalle) {
        this.id_detalle = id_detalle;
    }

    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
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

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }

   public static List<VentaDetalle> obtener(String filtro) {
    List<VentaDetalle> detalles = new ArrayList<>();
    try {
        Connection conexion = ConnectionDB.conectar();
        String sql;
        
        // Si el filtro está vacío o es nulo, no aplicamos el WHERE
        if (filtro == null || filtro.isEmpty()) {
            sql = "SELECT d.id_detalle, d.id_venta, d.id_producto, p.nombre AS producto, d.cantidad, d.subtotal "
                + "FROM detalle_venta d "
                + "JOIN productos p ON d.id_producto = p.id_producto";
        } else {
            // Si el filtro está presente, lo usamos para filtrar por id_venta
            sql = "SELECT d.id_detalle, d.id_venta, d.id_producto, p.nombre AS producto, d.cantidad, d.subtotal "
                + "FROM detalle_venta d "
                + "JOIN productos p ON d.id_producto = p.id_producto "
                + "WHERE d.id_venta = ?";
        }
        
        PreparedStatement statement = conexion.prepareStatement(sql);
        
        // Si hay filtro, lo aplicamos
        if (filtro != null && !filtro.isEmpty()) {
            statement.setInt(1, Integer.parseInt(filtro));  // El filtro se asume como el id_venta
        }
        
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            VentaDetalle detalle = new VentaDetalle();
            detalle.setId_detalle(resultSet.getInt("id_detalle"));
            detalle.setId_venta(resultSet.getInt("id_venta"));
            detalle.setId_producto(resultSet.getInt("id_producto"));
            detalle.setProducto(resultSet.getString("producto"));
            detalle.setCantidad(resultSet.getInt("cantidad"));
            detalle.setSubtotal(resultSet.getInt("subtotal"));

            detalles.add(detalle);
        }
        conexion.close();
    } catch (Exception ex) {
        System.err.println("Error al obtener detalles de venta: " + ex.getMessage());
    }
    return detalles;
}




    // Método para guardar un detalle
    public static boolean guardar(int id_venta, int id_producto, int cantidad, int subtotal) {
    boolean resultado = false;
    try {
        Connection conexion = ConnectionDB.conectar();
        
        // Consulta SQL para insertar un nuevo detalle de venta
        String consulta = "INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = conexion.prepareStatement(consulta);
        
        // Establecer los valores en la consulta
        statement.setInt(1, id_venta);    // ID de la venta
        statement.setInt(2, id_producto); // ID del producto
        statement.setInt(3, cantidad);   // Cantidad de productos
        statement.setInt(4, subtotal);   // Subtotal

        // Ejecutar la consulta
        statement.execute();

        // Verificar si la operación fue exitosa
        resultado = statement.getUpdateCount() == 1;

    } catch (Exception ex) {
        System.err.println("Ocurrió un error al guardar el detalle de la venta: " + ex.getMessage());
    }
    return resultado;
}
    
    public static boolean editar(int id_detalle, int id_venta, int id_producto, int cantidad, int subtotal) {
    boolean resultado = false;
    try {
        Connection conexion = ConnectionDB.conectar();
        
        // Consulta SQL para actualizar un detalle de venta
        String query = "UPDATE detalle_venta SET id_venta = ?, id_producto = ?, cantidad = ?, subtotal = ? WHERE id_detalle_venta = ?";
        PreparedStatement statement = conexion.prepareStatement(query);
        
        // Establecer los valores en la consulta
        statement.setInt(1, id_venta);      // ID de la venta
        statement.setInt(2, id_producto);   // ID del producto
        statement.setInt(3, cantidad);     // Cantidad de productos
        statement.setInt(4, subtotal);     // Subtotal
        statement.setInt(5, id_detalle); // ID del detalle de venta a editar

        // Ejecutar la consulta
        statement.execute();

        // Verificar si la operación fue exitosa
        resultado = statement.getUpdateCount() == 1;

        conexion.close();
    } catch (Exception ex) {
        System.err.println("Ocurrió un error al editar el detalle de la venta: " + ex.getMessage());
    }
    return resultado;
}

}
