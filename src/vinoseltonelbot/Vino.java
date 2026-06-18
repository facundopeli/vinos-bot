/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vinoseltonelbot;

/**
 *
 * @author faacu
 */
public class Vino {
    private int id;
    private String nombre;
    private String bodega;
    private double precio;
    private int stock;

    public Vino(int id, String nombre, String bodega, double precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.bodega = bodega;
        this.precio = precio;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getBodega() {
        return bodega;
    }

    public void setBodega(String bodega) {
        this.bodega = bodega;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Vino{" +
                "ID: " + id +
                " - NOMBRE:'" + nombre + '\'' +
                " - BODEGA:'" + bodega + '\'' +
                " - PRECIO: $" + precio +
                " - STOCK: " + stock +
                '}';
    }
}
