/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestion.almacen;

import javax.swing.JFrame;

/**
 *
 * @author mario
 */

//Clase de Navegación
public class Navegador {
    
    //Objeto JFrame para generar las ventanas
    private static JFrame ventanaActual;
    
    //Metodo de navegacion
    public static void irA(Vista vista){
        
        //Comprobación si ya hay una ventana abierta
        if (ventanaActual != null) {
            /* Dispose cierra la ventana, ahorrando recursos y evitando tener
            que anular ventanas */
            ventanaActual.dispose();
        }
        
        switch (vista) {
            case MENU:
                ventanaActual = new Main();
                break;
            case CLIENTES:
                ventanaActual = new Clientes();
                break;
            default:
                throw new AssertionError();
        }
        
        //Centra la ventana en la pantalla del usuario
        ventanaActual.setLocationRelativeTo(null);
        
        //Hace visible la pantalla
        ventanaActual.setVisible(true);
    }
    
    public static void salir(){
        //Si hay una ventana abierta la cierra
        if (ventanaActual != null) {
            ventanaActual.dispose();
        }
        
        //Termina la ejecución del programa
        System.exit(0);
    }
}
