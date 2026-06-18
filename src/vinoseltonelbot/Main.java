/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package vinoseltonelbot;

import java.util.List;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 *
 * @author faacu
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new VinosBot());
            System.out.println("Bot iniciado...");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        // --- Lectura del Excel ---
        GestorVinos gestor = new GestorVinos();
        gestor.cargarVinosDesdeExcel("datos/vinos.xlsx");

        // --- Prueba: mostrar todos los vinos ---
        for (Vino v : gestor.getListaVinos()) {
            System.out.println(v);
        }

        // --- Búsqueda por nombre ---
        Vino resultado = gestor.buscarPorNombre("Rutini Malbec");
        if (resultado != null) {
            System.out.println("Encontrado: " + resultado);
        } else {
            System.out.println("No se encontró ese vino.");
        }
        
        // --- Búsqueda por bodega ---
        List<Vino> vinosChandon = gestor.buscarPorBodega("Chandon");
        System.out.println("Vinos de Chandon: " + vinosChandon);
        
        // --- Búsqueda por stock ---
        List<Vino> disponibles = gestor.buscarPorStockDisponible();
        System.out.println("Con stock: " + disponibles);
    }
}
