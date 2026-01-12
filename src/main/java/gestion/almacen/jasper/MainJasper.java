/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestion.almacen.jasper;

import gestion.almacen.ConexionDB;
import java.sql.SQLException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author mario
 */
public class MainJasper {
    public static void main(String[] args) throws JRException, SQLException {
        ConexionDB conn = new ConexionDB();
        
        String informeOrigen = "/Users/mario/Documents/DAM/2/Interfaces/interfaces/src/main/java/gestion/almacen/jasper/ClientesTodos.jasper";
        String informeDestino = "src/main/java/gestion/almacen/jasper/ClientesTodos.pdf";
        
        JasperPrint print = JasperFillManager.fillReport(informeOrigen, null, conn.connect());
        JasperExportManager.exportReportToPdfFile(print, informeDestino);
    }
}
