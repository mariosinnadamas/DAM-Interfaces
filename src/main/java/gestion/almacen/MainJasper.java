/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestion.almacen;

import java.sql.SQLException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author mario
 */
public class MainJasper {
    public static void main(String[] args) throws JRException, SQLException {
        ConexionDB conn = new ConexionDB();
        String direccionBuena = "src/main/java/gestion/almacen/";
        
        JasperCompileManager.compileReportToFile("src/main/java/gestion/almacen/prueba.jrxml", direccionBuena + "Prueba.jasper");
	JasperReport report = (JasperReport) JRLoader.loadObjectFromFile(direccionBuena + "Prueba.jasper");
        JasperPrint print = JasperFillManager.fillReport(report, null, conn.connect());
        JasperExportManager.exportReportToPdfFile(print, direccionBuena+"output.pdf");
    }
}
