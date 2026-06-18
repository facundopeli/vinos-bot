/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vinoseltonelbot;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
/**
 *
 * @author faacu
 */
public class VinosBot extends TelegramLongPollingBot{

    private Map<Long, String> estadoUsuario = new HashMap<>();
    private Map<Long, Vino> vinoSeleccionado = new HashMap<>();
    private Map<Long, Integer> cantidadSeleccionada = new HashMap<>();
    private GestorPedidos gestorPedidos = new GestorPedidos();

    @Override
    public String getBotUsername() {
        return "VinosElTonelBot";
    }

    @Override
    public String getBotToken() {
        return "8818227715:AAH_tvCSUaSZGqh3isJVJgH19wf0OYJbrao";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String mensaje = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            GestorVinos gestor = new GestorVinos();
            gestor.cargarVinosDesdeExcel("datos/vinos.xlsx");

            // Estado inicial
            if (!estadoUsuario.containsKey(chatId)) {
                estadoUsuario.put(chatId, "esperandoVino");
                enviarMensaje(chatId, "Bienvenido 🍷\nEscribí el nombre del vino que querés.");
                return;
            }

            String estado = estadoUsuario.get(chatId);

            switch (estado) {
                case "esperandoVino":
                    Vino vino = gestor.buscarPorNombre(mensaje);
                    if (vino != null && vino.getStock() > 0) {
                        vinoSeleccionado.put(chatId, vino);
                        estadoUsuario.put(chatId, "esperandoCantidad");
                        enviarMensaje(chatId, "Tenemos disponible: " + vino.getNombre() +
                                "\nBodega: " + vino.getBodega() +
                                "\nPrecio: $" + vino.getPrecio() +
                                "\nStock: " + vino.getStock() +
                                "\n\n¿Cuántas botellas querés?");
                    } else {
                        enviarMensaje(chatId, "No hay stock de ese vino. Probá con otro nombre.");
                    }
                    break;

                case "esperandoCantidad":
                    try {
                        int cantidad = Integer.parseInt(mensaje);
                        Vino seleccionado = vinoSeleccionado.get(chatId);
                        if (cantidad <= seleccionado.getStock() && cantidad > 0) {
                            cantidadSeleccionada.put(chatId, cantidad);
                            estadoUsuario.put(chatId, "esperandoDatos");
                            enviarMensaje(chatId, "Perfecto. Ahora necesito tus datos:\nNombre y teléfono.");
                        } else {
                            enviarMensaje(chatId, "Cantidad inválida. Máximo disponible: " + seleccionado.getStock());
                        }
                    } catch (NumberFormatException e) {
                        enviarMensaje(chatId, "Por favor, ingresá un número válido de botellas.");
                    }
                    break;

                case "esperandoDatos":
                    Vino vinoFinal = vinoSeleccionado.get(chatId);
                    int cantFinal = cantidadSeleccionada.get(chatId);

                    // Supongamos que el usuario escribe "Facundo - 2211234567"
                    String[] partes = mensaje.split("-");
                    if (partes.length == 2) {
                        String cliente = partes[0].trim();
                        String telefono = partes[1].trim();

                        gestorPedidos.registrarPedido(vinoFinal, cantFinal, cliente, telefono);

                        enviarMensaje(chatId, "Pedido registrado ✅\n" +
                                "Cliente: " + cliente + "\nTel: " + telefono +
                                "\n" + cantFinal + " x " + vinoFinal.getNombre() +
                                "\nGracias por tu compra 🍷");

                        // Reiniciar estado
                        estadoUsuario.remove(chatId);
                        vinoSeleccionado.remove(chatId);
                        cantidadSeleccionada.remove(chatId);
                    } else {
                        enviarMensaje(chatId, "Formato inválido. Escribí: Nombre - Teléfono");
                    }
                    break;
            }
        }
    }

    private void enviarMensaje(Long chatId, String texto) {
        SendMessage mensaje = new SendMessage();
        mensaje.setChatId(chatId.toString());
        mensaje.setText(texto);
        try {
            execute(mensaje);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
