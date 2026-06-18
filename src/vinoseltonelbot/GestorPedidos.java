/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vinoseltonelbot;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author faacu
 */
public class GestorPedidos {
    private List<Pedido> pedidos = new ArrayList<>();

    public void registrarPedido(Vino vino, int cantidad, String cliente, String telefono) {
        Pedido pedido = new Pedido(vino, cantidad, cliente, telefono);
        pedidos.add(pedido);

        // Descontar stock del vino
        vino.setStock(vino.getStock() - cantidad);
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }
}
