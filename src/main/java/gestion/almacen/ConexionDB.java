/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestion.almacen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author mario
 */
public class ConexionDB {
    
    private static final String URL = "jdbc:postgresql://localhost:5432/aprendizaje?currentSchema=almacen";
    private static final String USER = "alumno";
    private static final String PASS = "alumno";
    
    public Connection connect() throws SQLException{
        return DriverManager.getConnection(URL,USER,PASS);
    }
}
