/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vinoseltonelbot;

/**
 *
 * @author faacu
 */
public class Pedido {
    private Vino vino;
    private int cantidad;
    private String cliente;
    private String telefono;

    public Pedido(Vino vino, int cantidad, String cliente, String telefono) {
        this.vino = vino;
        this.cantidad = cantidad;
        this.cliente = cliente;
        this.telefono = telefono;
    }

    public Vino getVino() {
        return vino;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getCliente() {
        return cliente;
    }

    public String getTelefono() {
        return telefono;
    }

    @Override
    public String toString() {
        return "Pedido: " + cantidad + " x " + vino.getNombre() +
               " - Cliente: " + cliente + " (" + telefono + ")";
    }
}    
