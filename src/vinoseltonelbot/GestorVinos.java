/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vinoseltonelbot;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author faacu
 */
public class GestorVinos {
    private List<Vino> listaVinos = new ArrayList<>();

    public void cargarVinosDesdeExcel(String rutaArchivo) {
        listaVinos.clear();
        try (FileInputStream fis = new FileInputStream(rutaArchivo);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            Sheet hoja = workbook.getSheetAt(0);

            for (int i = 1; i <= hoja.getLastRowNum(); i++) {
                Row fila = hoja.getRow(i);
                if (fila == null) continue;

                int id = (int) fila.getCell(0).getNumericCellValue();
                String nombre = fila.getCell(1).getStringCellValue();
                String bodega = fila.getCell(2).getStringCellValue();
                double precio = fila.getCell(3).getNumericCellValue();
                int stock = (int) fila.getCell(4).getNumericCellValue();

                Vino vino = new Vino(id, nombre, bodega, precio, stock);
                listaVinos.add(vino);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Vino> getListaVinos() {
        return listaVinos;
    }
    
    public Vino buscarPorNombre(String nombre) {
        for (Vino v : listaVinos) {
            if (v.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                return v;
            }
        }
        return null; // si no lo encuentra
    }
    
    public List<Vino> buscarPorBodega(String bodega) {
        List<Vino> resultados = new ArrayList<>();
        for (Vino v : listaVinos) {
            if (v.getBodega().toLowerCase().contains(bodega.toLowerCase())) {
                resultados.add(v);
            }
        }
        return resultados;
    }
    public List<Vino> buscarPorStockDisponible() {
        List<Vino> disponibles = new ArrayList<>();
        for (Vino v : listaVinos) {
            if (v.getStock() > 0) {
                disponibles.add(v);
            }
        }
        return disponibles;
    }
    
    public List<Vino> buscarPorStockAgotado() {
        List<Vino> resultados = new ArrayList<>();
        for (Vino v : listaVinos) {
            if (v.getStock() == 0) {
                resultados.add(v);
            }
        }
        return resultados;
    }
}
