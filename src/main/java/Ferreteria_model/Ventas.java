/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ferreteria_model;

/**
 *
 * @author Dell
 */
public class Ventas {
    private int id_cliente;
    private boolean fecha;
    private int total;

    public Ventas(int id_cliente, boolean fecha, int total) {
        this.id_cliente = id_cliente;
        this.fecha = fecha;
        this.total = total;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public boolean isFecha() {
        return fecha;
    }

    public void setFecha(boolean fecha) {
        this.fecha = fecha;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    
}
