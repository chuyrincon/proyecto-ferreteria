/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ferreteria_model;

/**
 *
 * @author Dell
 */
public class Clientes {
    private int id_usuario;
    private String nombre;
    private String correo;
    private String telefono;

    public Clientes(int id_usuario, String nombre, String correo, String telefono) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
    }

    
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
}
