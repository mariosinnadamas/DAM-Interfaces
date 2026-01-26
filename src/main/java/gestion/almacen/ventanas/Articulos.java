/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gestion.almacen.ventanas;

import gestion.almacen.ConexionDB;
import gestion.almacen.navegacion.Navegador;
import gestion.almacen.navegacion.Vista;
import java.awt.Color;
import java.awt.Image;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author mario
 */

public class Articulos extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Articulos.class.getName());
    
    private enum Modo {ALTA,BAJA,MODIFICACIONES,CONSULTAPORCODIGO};
    private Modo modo;
    
    //Array para almacenar los errores
    private List <String> errores = new ArrayList<>();
    
    //Consulta reutilizada
    private String consultaArticulos = "SELECT codigo, stock, stock_minimo, precio_de_compra, precio_de_venta,descripcion FROM articulos WHERE codigo = ?";
    
    //Directorios para el jasper
    private String informeOrigenTodos = "/Users/mario/Documents/DAM/2/Interfaces/interfaces/src/main/java/gestion/almacen/jasper/articulos/ArticulosTodos.jasper";
    private String informeDestinoTodos = "src/main/java/gestion/almacen/jasper/articulos/ArticulosTodos.pdf";
    private String informeOrigenGraficos = "/Users/mario/Documents/DAM/2/Interfaces/interfaces/src/main/java/gestion/almacen/jasper/articulos/Graficos.jasper";
    private String informeDestinoGraficos = "src/main/java/gestion/almacen/jasper/articulos/Graficos.pdf";
    
    private ConexionDB conn = new ConexionDB();
    
    //Variables booleanas para comprobar todo
    boolean codigoComprobado = false;
    boolean stockComprobado = false;
    boolean StockMinComprobado = false;
    boolean precioCComprobado = false;
    boolean precioVComprobado = false;
    boolean descripcionComprobado = false;
    
    private BigDecimal stock;
    private BigDecimal stockMinimo;
    private BigDecimal precioCompra;
    private BigDecimal precioVenta;

    
    public Articulos() {
        initComponents();
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        desactivarTodo();
    }
    
    //Icono personalizado
    public ImageIcon imagenPerso(boolean estaBien){
        
        String nombreIcono = estaBien ? "IconoVerde.jpg" : "IconoRojo.png";

        Path ruta = Path.of( "src", "main", "java", "gestion", "almacen", 
                "resources", nombreIcono);

        Image imagen = new ImageIcon(ruta.toString()).getImage()
                .getScaledInstance(70, 70, Image.SCALE_SMOOTH);

        return new ImageIcon(imagen);  
    }
    
    //Metodo para desactivar todo
    private void desactivarTodo(){
        textoCodigo.setEnabled(false);
        textoStock.setEnabled(false);
        textoStockMin.setEnabled(false);
        textoPrecioC.setEnabled(false);
        textoPrecioV.setEnabled(false);
        textoDescripcion.setEnabled(false);
        botonAceptar.setEnabled(false);
        botonCancelar.setEnabled(false);
        botonSalir.setEnabled(false);
    }
    
    //Metodo para activar todo
    public void activarTodo(){
        textoCodigo.setEnabled(true);
        textoStock.setEnabled(true);
        textoStockMin.setEnabled(true);
        textoPrecioC.setEnabled(true);
        textoPrecioV.setEnabled(true);
        textoDescripcion.setEnabled(true);
        botonAceptar.setEnabled(true);
        botonCancelar.setEnabled(true);
        botonSalir.setEnabled(true);
    }

    //Modo Altas-Bajas-Consultas-Modificaciones
    public void modoAbcm(){
        textoCodigo.setEnabled(true);
        botonAceptar.setEnabled(true);
        botonCancelar.setEnabled(true);
        botonSalir.setEnabled(true);
    }
    
    public void resetFormulario(){
        textoCodigo.setText("");
        textoStock.setText("");
        textoStockMin.setText("");
        textoPrecioC.setText("");
        textoPrecioV.setText("");
        textoDescripcion.setText("");
        marcarCorrecto(textoCodigo);
        marcarCorrecto(textoStock);
        marcarCorrecto(textoStockMin);
        marcarCorrecto(textoPrecioC);
        marcarCorrecto(textoPrecioV);
        marcarCorrecto(textoDescripcion);
        codigoComprobado = false;
        stockComprobado = false;
        StockMinComprobado = false;
        precioCComprobado = false;
        precioVComprobado = false;
        descripcionComprobado = false;    
    }
    
    // Método para validar código (5 dígitos)
    private boolean validarCodigo(String codigo) {
        if (codigo == null || !codigo.matches("[0-9]{5}")) {
            marcarError(textoCodigo);
            JOptionPane.showMessageDialog(null, 
                "El código debe ser una cadena de 5 dígitos",
                "Error", JOptionPane.ERROR_MESSAGE, imagenPerso(false));
            return false;
        }
        marcarCorrecto(textoCodigo);
        return true;
    }
    
    // Método para validar cualquier campo numérico (stock, precios)
    private boolean validarCampoNumerico(JTextField campo, String nombreCampo, 
                                         boolean puedeSerCero) {
        String texto = campo.getText().trim();

        if (texto.isEmpty()) {
            marcarError(campo);
            JOptionPane.showMessageDialog(null, 
                "El " + nombreCampo + " no puede estar vacío",
                "Error", JOptionPane.ERROR_MESSAGE, imagenPerso(false));
            return false;
        }

        texto = texto.replace(',', '.');

        BigDecimal valor;
        try {
            valor = new BigDecimal(texto);
        } catch (NumberFormatException e) {
            marcarError(campo);
            JOptionPane.showMessageDialog(null,
                "El " + nombreCampo + " debe ser un número válido",
                "Error", JOptionPane.ERROR_MESSAGE, imagenPerso(false));
            return false;
        }

        // Validar si puede ser cero o negativo
        if (!puedeSerCero && valor.compareTo(BigDecimal.ZERO) <= 0) {
            marcarError(campo);
            JOptionPane.showMessageDialog(null,
                "El " + nombreCampo + " debe ser mayor que 0",
                "Error", JOptionPane.ERROR_MESSAGE, imagenPerso(false));
            return false;
        }

        if (valor.compareTo(BigDecimal.ZERO) < 0) {
            marcarError(campo);
            JOptionPane.showMessageDialog(null,
                "El " + nombreCampo + " no puede ser negativo",
                "Error", JOptionPane.ERROR_MESSAGE, imagenPerso(false));
            return false;
        }

        // Validar decimales (máximo 2)
        if (valor.scale() > 2) {
            marcarError(campo);
            JOptionPane.showMessageDialog(null,
                "El " + nombreCampo + " solo puede tener 2 decimales",
                "Error", JOptionPane.ERROR_MESSAGE, imagenPerso(false));
            return false;
        }

        // Validar máximo (999999.99)
        BigDecimal maximo = new BigDecimal("999999.99");
        if (valor.compareTo(maximo) > 0) {
            marcarError(campo);
            JOptionPane.showMessageDialog(null,
                "El " + nombreCampo + " no puede superar 999999.99",
                "Error", JOptionPane.ERROR_MESSAGE, imagenPerso(false));
            return false;
        }

        marcarCorrecto(campo);
        return true;
    }
    
    // Método específico para validar stock
    private boolean validarStock() {
        if (!validarCampoNumerico(textoStock, "stock", true)) {
            return false;
        }

        // Conversión y almacenamiento en variable
        try {
            stock = new BigDecimal(textoStock.getText().replace(',', '.'));
            stock = stock.setScale(2, RoundingMode.HALF_UP);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }
    
    // Método específico para validar stock mínimo
    private boolean validarStockMinimo() {
        if (!validarCampoNumerico(textoStockMin, "stock mínimo", true)) {
            return false;
        }

        try {
            stockMinimo = new BigDecimal(textoStockMin.getText().replace(',', '.'));
            stockMinimo = stockMinimo.setScale(2, RoundingMode.HALF_UP);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }
    
    // Método para validar descripción
    private boolean validarDescripcion() {
        String texto = textoDescripcion.getText().trim();

        if (texto.isEmpty()) {
            marcarError(textoDescripcion);
            JOptionPane.showMessageDialog(null,
                "La descripción no puede estar vacía",
                "Error", JOptionPane.ERROR_MESSAGE, imagenPerso(false));
            return false;
        }

        if (!texto.matches("^.{1,25}$")) {
            marcarError(textoDescripcion);
            JOptionPane.showMessageDialog(null,
                "La descripción no puede superar 25 caracteres",
                "Error", JOptionPane.ERROR_MESSAGE, imagenPerso(false));
            return false;
        }

        marcarCorrecto(textoDescripcion);
        return true;
    }
    
    // Método para verificar si todos los campos están validados
    private void verificarSiTodoValido() {
        boolean todoValido = false;

        switch (modo) {
            case ALTA:
                todoValido = codigoComprobado && stockComprobado && 
                            StockMinComprobado && precioCComprobado && 
                            precioVComprobado && descripcionComprobado;
                break;
            case MODIFICACIONES:
                todoValido = codigoComprobado;
                break;
            case BAJA:
            case CONSULTAPORCODIGO:
                todoValido = codigoComprobado;
                break;
            default:
                todoValido = false;
        }

        botonAceptar.setEnabled(todoValido);
    }
    
    public boolean comprobarFormulario(){

        errores.clear();

        if (!codigoComprobado) errores.add("Codigo");
        if (!stockComprobado) errores.add("Stock");
        if (!StockMinComprobado) errores.add("Stock mínimo");
        if (!precioCComprobado) errores.add("Precio de compra");
        if (!precioVComprobado) errores.add("Precio de venta");
        if (!descripcionComprobado) errores.add("Descripcion");

        return errores.isEmpty();
    }
    
    //Metodo para mostrar una ventana con los errores
    public void mostrarErrores(List <String> errores){
        
        JOptionPane.showMessageDialog(
        this,
        "Corrige los siguientes campos:\n " + String.join("\n- ", errores),
        "ERROR",
        JOptionPane.ERROR_MESSAGE,imagenPerso(false)
    );
    }
    
    //Metodo para marcar un campo como erroneo
    public void marcarError(JTextField campo){
        campo.setBackground(Color.red);
        campo.requestFocus();
    }
    
    //Metodo para marcar un campo como correcto
    public void marcarCorrecto (JTextField campo){
        campo.setBackground(Color.white);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        etiquetaCodigo = new javax.swing.JLabel();
        nif = new javax.swing.JLabel();
        nombre = new javax.swing.JLabel();
        apellidos = new javax.swing.JLabel();
        domicilio = new javax.swing.JLabel();
        cp = new javax.swing.JLabel();
        textoCodigo = new javax.swing.JTextField();
        textoStock = new javax.swing.JTextField();
        textoStockMin = new javax.swing.JTextField();
        textoPrecioC = new javax.swing.JTextField();
        textoPrecioV = new javax.swing.JTextField();
        textoDescripcion = new javax.swing.JTextField();
        botonAceptar = new javax.swing.JButton();
        botonCancelar = new javax.swing.JButton();
        botonSalir = new javax.swing.JButton();
        etiquetaModoModificar = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        mantenimiento = new javax.swing.JMenu();
        altas = new javax.swing.JMenuItem();
        bajas = new javax.swing.JMenuItem();
        modificaciones = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        volver = new javax.swing.JMenuItem();
        consultas = new javax.swing.JMenu();
        porCodigo = new javax.swing.JMenuItem();
        listados = new javax.swing.JMenu();
        porCodigos = new javax.swing.JMenuItem();
        entreCodigos = new javax.swing.JMenuItem();
        graficos = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        etiquetaCodigo.setText("Código");

        nif.setText("Stock");

        nombre.setText("Stock mínimo");

        apellidos.setText("Precio de compra");

        domicilio.setText("Precio de venta");

        cp.setText("Descripción");

        textoCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textoCodigoActionPerformed(evt);
            }
        });

        textoStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textoStockActionPerformed(evt);
            }
        });

        textoStockMin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textoStockMinActionPerformed(evt);
            }
        });

        textoPrecioC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textoPrecioCActionPerformed(evt);
            }
        });

        textoPrecioV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textoPrecioVActionPerformed(evt);
            }
        });

        textoDescripcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textoDescripcionActionPerformed(evt);
            }
        });

        botonAceptar.setText("Aceptar");
        botonAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAceptarActionPerformed(evt);
            }
        });

        botonCancelar.setText("Cancelar");
        botonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCancelarActionPerformed(evt);
            }
        });

        botonSalir.setText("Salir");
        botonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSalirActionPerformed(evt);
            }
        });

        etiquetaModoModificar.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        etiquetaModoModificar.setText("Seleccione un modo");

        mantenimiento.setText("Mantenimiento");

        altas.setText("Altas");
        altas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                altasActionPerformed(evt);
            }
        });
        mantenimiento.add(altas);

        bajas.setText("Bajas");
        bajas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bajasActionPerformed(evt);
            }
        });
        mantenimiento.add(bajas);

        modificaciones.setText("Modificaciones");
        modificaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modificacionesActionPerformed(evt);
            }
        });
        mantenimiento.add(modificaciones);
        mantenimiento.add(jSeparator1);

        volver.setText("Volver");
        volver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                volverActionPerformed(evt);
            }
        });
        mantenimiento.add(volver);

        jMenuBar1.add(mantenimiento);

        consultas.setText("Consultas");

        porCodigo.setText("Por código");
        porCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                porCodigoActionPerformed(evt);
            }
        });
        consultas.add(porCodigo);

        listados.setText("Listados");

        porCodigos.setText("Por códigos");
        porCodigos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                porCodigosActionPerformed(evt);
            }
        });
        listados.add(porCodigos);

        entreCodigos.setText("Entre códigos");
        entreCodigos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                entreCodigosActionPerformed(evt);
            }
        });
        listados.add(entreCodigos);

        graficos.setText("Gráficos");
        graficos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                graficosActionPerformed(evt);
            }
        });
        listados.add(graficos);

        consultas.add(listados);

        jMenuBar1.add(consultas);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(textoDescripcion)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(etiquetaModoModificar)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(apellidos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(textoPrecioC)
                                    .addComponent(cp))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(domicilio)
                                    .addComponent(textoPrecioV, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(botonAceptar)
                                    .addGap(23, 23, 23)
                                    .addComponent(botonCancelar)
                                    .addGap(18, 18, 18)
                                    .addComponent(botonSalir))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(etiquetaCodigo)
                                        .addComponent(textoCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(nif)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(textoStock, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(textoStockMin, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(nombre)))))))
                        .addGap(1, 1, 1)))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(etiquetaModoModificar)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(etiquetaCodigo)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(textoCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(nif)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(textoStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(apellidos)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textoPrecioC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(nombre)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textoStockMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(domicilio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textoPrecioV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textoDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonAceptar)
                    .addComponent(botonCancelar)
                    .addComponent(botonSalir))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void textoCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoCodigoActionPerformed
        
        String texto = textoCodigo.getText();
        if (!validarCodigo(texto)){
            return;
        }
        
        marcarCorrecto(textoCodigo);
        
        switch (modo) {
            //Realiza una consulta antes de agregar para comprobar que puedas dar de alta
            case ALTA:

                try (Connection conexion = conn.connect();
                        PreparedStatement stm = conexion.prepareStatement(consultaArticulos)) {
                    stm.setString(1, textoCodigo.getText());
                    try (ResultSet rs = stm.executeQuery()){
                        if (!rs.next()) {
                           activarTodo();
                           textoCodigo.setEnabled(false);
                           textoStock.requestFocus();
                           codigoComprobado = true;
                        } else{
                            JOptionPane.showMessageDialog(null, 
                                    "Ya existe un artículo con ese código, no es posible agregarlo", 
                                    "Error", 
                                    JOptionPane.ERROR_MESSAGE, imagenPerso(false));
                            textoCodigo.requestFocus();
                        }
                    } catch (SQLException e) {
                        //No se ha encontrado nadie?
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    //Error en la conexión
                    e.printStackTrace();
                }
                break;
                //Hace una consulta para comprobar que exista, de no ser así no pasa el focus a aceptar
            case BAJA:

                try (Connection conexion = conn.connect();
                        PreparedStatement stm = conexion.prepareStatement(consultaArticulos)) {
                    stm.setString(1, textoCodigo.getText());
                    try (ResultSet rs = stm.executeQuery()){
                        if (rs.next()) {
                           botonAceptar.requestFocus();
                           codigoComprobado = true;
                           textoCodigo.setEnabled(false);
                        } else{
                            JOptionPane.showMessageDialog(null, 
                                    "No se ha encontrado ningun artículo asociado a ese código", 
                                    "Error", 
                                    JOptionPane.ERROR_MESSAGE, imagenPerso(false));
                            textoCodigo.requestFocus();
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case MODIFICACIONES:

                //Hace una consulta para comprobar que pueda modificar
                try (Connection conexion = conn.connect();
                        PreparedStatement stm = conexion.prepareStatement(consultaArticulos)) {
                    stm.setString(1, textoCodigo.getText());

                    try (ResultSet rs = stm.executeQuery()){
                        if (rs.next()) {
                            activarTodo();
                            textoStock.setText(rs.getString("stock"));
                            textoStockMin.setText(rs.getString("stock_minimo"));
                            textoPrecioC.setText(rs.getString("precio_de_compra"));
                            textoPrecioV.setText(rs.getString("precio_de_venta"));
                            textoDescripcion.setText(rs.getString("descripcion"));
                            textoStock.requestFocus();
                            textoCodigo.setEnabled(false);
                            codigoComprobado = true;

                            //Inicializar las variables BigDecimal 
                            try {
                                stock = new BigDecimal(rs.getString("stock"));
                                stockMinimo = new BigDecimal(rs.getString("stock_minimo"));
                                precioCompra = new BigDecimal(rs.getString("precio_de_compra"));
                                precioVenta = new BigDecimal(rs.getString("precio_de_venta"));

                                // Redondear si es necesario
                                stock = stock.setScale(2, RoundingMode.HALF_UP);
                                stockMinimo = stockMinimo.setScale(2, RoundingMode.HALF_UP);
                                precioCompra = precioCompra.setScale(2, RoundingMode.HALF_UP);
                                precioVenta = precioVenta.setScale(2, RoundingMode.HALF_UP);

                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                            
                            stockComprobado = false;
                            StockMinComprobado = false;
                            precioCComprobado = false;
                            precioVComprobado = false;
                            descripcionComprobado = false;
                            
                            // Deshabilitar Aceptar hasta que valide algún campo
                            botonAceptar.setEnabled(false);
                        } else{
                            JOptionPane.showMessageDialog(null, 
                                    "No existe ningún artículo con ese código", 
                                    "Error", 
                                    JOptionPane.ERROR_MESSAGE, imagenPerso(false));
                            textoCodigo.requestFocus();
                        }

                    } catch (SQLException e) {
                        //No se ha encontrado nadie?
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    //Error en la conexión
                    e.printStackTrace();
                }

                break;

            case CONSULTAPORCODIGO:
                botonAceptar.requestFocus();
            default:
                break;
        } 
    }//GEN-LAST:event_textoCodigoActionPerformed
    
    private void textoStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoStockActionPerformed
                
        //Evita que se quede vacío
        if (validarStock()) {
        // Actualizar la variable stock con el valor actual
            try {
                stock = new BigDecimal(textoStock.getText().replace(',', '.'));
                stock = stock.setScale(2, RoundingMode.HALF_UP);
            } catch (NumberFormatException e) {
                marcarError(textoStock);
                return;
            }

            marcarCorrecto(textoStock);
            stockComprobado = true;
            textoStockMin.requestFocus();
            verificarSiTodoValido();
        }
    }//GEN-LAST:event_textoStockActionPerformed

    private void textoStockMinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoStockMinActionPerformed
        
        if (validarStockMinimo()) {
        // Actualizar la variable stockMinimo con el valor actual
            try {
                stockMinimo = new BigDecimal(textoStockMin.getText().replace(',', '.'));
                stockMinimo = stockMinimo.setScale(2, RoundingMode.HALF_UP);
            } catch (NumberFormatException e) {
                marcarError(textoStockMin);
                return;
            }
        
        marcarCorrecto(textoStockMin);
        StockMinComprobado = true;
        textoPrecioC.requestFocus();
        verificarSiTodoValido();
    }
    }//GEN-LAST:event_textoStockMinActionPerformed

    private void textoPrecioCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoPrecioCActionPerformed
        
        if (validarCampoNumerico(textoPrecioC, "Precio de compra", false)) {
            // Actualizar la variable BigDecimal
            try {
                precioCompra = new BigDecimal(textoPrecioC.getText().replace(',', '.'));
                precioCompra = precioCompra.setScale(2, RoundingMode.HALF_UP);
                precioCComprobado = true;
                marcarCorrecto(textoPrecioC);
                textoPrecioV.requestFocus();
                verificarSiTodoValido();
            } catch (NumberFormatException e) {
                marcarError(textoPrecioC);
            }
        }
    }//GEN-LAST:event_textoPrecioCActionPerformed

    private void textoPrecioVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoPrecioVActionPerformed
        
        if (validarCampoNumerico(textoPrecioV, "Pecio de venta", false)) {
            try {
                precioVenta = new BigDecimal(textoPrecioV.getText().replace(',', '.'));
                precioVenta = precioVenta.setScale(2, RoundingMode.HALF_UP);
                precioVComprobado = true;
                marcarCorrecto(textoPrecioV);
                textoDescripcion.requestFocus();
                verificarSiTodoValido();
            } catch (NumberFormatException e) {
                marcarError(textoPrecioV);
            }
        }
    }//GEN-LAST:event_textoPrecioVActionPerformed

    private void textoDescripcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoDescripcionActionPerformed
        
        if (validarDescripcion()) {
            marcarCorrecto(textoDescripcion);
            botonAceptar.requestFocus();
            descripcionComprobado = true;
            verificarSiTodoValido();
        }
    }//GEN-LAST:event_textoDescripcionActionPerformed

    private void botonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelarActionPerformed
        resetFormulario();
        desactivarTodo();
        modoAbcm();
    }//GEN-LAST:event_botonCancelarActionPerformed

    private void botonAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAceptarActionPerformed
        
        //modificar alta, que no saque las ventanas
        switch (modo) {
            case ALTA:
                boolean altaValida = true;
                List<String> erroresAlta = new ArrayList<>();

                // Validar código
                if (!validarCodigo(textoCodigo.getText())) {
                    altaValida = false;
                    erroresAlta.add("Código");
                }

                // Validar stock y actualizar variable
                if (!validarStock()) {
                    altaValida = false;
                    erroresAlta.add("Stock");
                } else {
                    try {
                        stock = new BigDecimal(textoStock.getText().replace(',', '.'));
                        stock = stock.setScale(2, RoundingMode.HALF_UP);
                    } catch (NumberFormatException e) {
                        altaValida = false;
                        erroresAlta.add("Stock (valor inválido)");
                    }
                }

                // Validar stock mínimo y actualizar variable
                if (!validarStockMinimo()) {
                    altaValida = false;
                    erroresAlta.add("Stock mínimo");
                } else {
                    try {
                        stockMinimo = new BigDecimal(textoStockMin.getText().replace(',', '.'));
                        stockMinimo = stockMinimo.setScale(2, RoundingMode.HALF_UP);
                    } catch (NumberFormatException e) {
                        altaValida = false;
                        erroresAlta.add("Stock mínimo (valor inválido)");
                    }
                }

                // Validar precio compra y actualizar variable
                if (!validarCampoNumerico(textoPrecioC, "precio de compra", false)) {
                    altaValida = false;
                    erroresAlta.add("Precio de compra");
                } else {
                    try {
                        precioCompra = new BigDecimal(textoPrecioC.getText().replace(',', '.'));
                        precioCompra = precioCompra.setScale(2, RoundingMode.HALF_UP);
                    } catch (NumberFormatException e) {
                        altaValida = false;
                        erroresAlta.add("Precio de compra (valor inválido)");
                    }
                }

                // Validar precio venta y actualizar variable
                if (!validarCampoNumerico(textoPrecioV, "precio de venta", false)) {
                    altaValida = false;
                    erroresAlta.add("Precio de venta");
                } else {
                    try {
                        precioVenta = new BigDecimal(textoPrecioV.getText().replace(',', '.'));
                        precioVenta = precioVenta.setScale(2, RoundingMode.HALF_UP);
                    } catch (NumberFormatException e) {
                        altaValida = false;
                        erroresAlta.add("Precio de venta (valor inválido)");
                    }
                }

                // Validar descripción
                if (!validarDescripcion()) {
                    altaValida = false;
                    erroresAlta.add("Descripción");
                }

                // Si hay errores, mostrarlos y salir
                if (!altaValida) {
                    mostrarErrores(erroresAlta);
                    break;
                }
                
                String query = "INSERT INTO articulos (codigo,precio_de_compra,"
                            + "precio_de_venta,stock,stock_minimo, descripcion) "
                            + "VALUES (?,?,?,?,?,?)";
                    
                    try (Connection conexion = conn.connect();
                            PreparedStatement stm = conexion.prepareStatement(query)){

                            stm.setString(1, textoCodigo.getText());
                            stm.setBigDecimal(2, precioCompra);
                            stm.setBigDecimal(3, precioVenta);
                            stm.setBigDecimal(4, stock);
                            stm.setBigDecimal(5, stockMinimo);
                            stm.setString(6, textoDescripcion.getText());

                            stm.executeUpdate();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    JOptionPane.showMessageDialog(null, 
                            "Todos los campos estan bien, artículo agregado a la base de datos", 
                            "¡Enhorabuena!",
                            JOptionPane.INFORMATION_MESSAGE,
                            imagenPerso(true));
                    
                    resetFormulario();
                    desactivarTodo();
                    modoAbcm();
                    break;
            case BAJA:
                
                String sql = "DELETE FROM articulos WHERE codigo = ?";
                    if (codigoComprobado) {
                        try (Connection conexion = conn.connect();
                                PreparedStatement stm = conexion.prepareStatement(sql)) {
                            stm.setString(1, textoCodigo.getText());

                            stm.executeUpdate();

                            JOptionPane.showMessageDialog(null,
                                    "El articulo ha sido borrado con éxito",
                                    "Operación realizada", 
                                    JOptionPane.INFORMATION_MESSAGE,imagenPerso(true));
                            codigoComprobado = false;
                            resetFormulario();
                            desactivarTodo();
                            modoAbcm();

                        } catch (Exception e) {
                            //Error en la conexión
                            e.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "No se puede eliminar el articulo. "
                                        + "Pulse enter cuando rellene el campo Código",
                                "ERROR",
                                JOptionPane.ERROR_MESSAGE,imagenPerso(false));
                    }
                break;
                
            case MODIFICACIONES:
                
                //Validar todos los campos antes de aceptar
                boolean formularioValido = true;
                List<String> erroresModificacion = new ArrayList<>();

                // Validar stock (si se modificó)
                if (!textoStock.getText().trim().isEmpty()) {
                    if (!validarStock()) {
                        formularioValido = false;
                        erroresModificacion.add("Stock");
                    }
                }

                // Validar stock mínimo (si se modificó)
                if (!textoStockMin.getText().trim().isEmpty()) {
                    if (!validarStockMinimo()) {
                        formularioValido = false;
                        erroresModificacion.add("Stock mínimo");
                    }
                }

                // Validar precio compra (si se modificó)
                if (!textoPrecioC.getText().trim().isEmpty()) {
                    if (!validarCampoNumerico(textoPrecioC, "precio de compra", false)) {
                        formularioValido = false;
                        erroresModificacion.add("Precio de compra");
                    } else {
                        try {
                            precioCompra = new BigDecimal(textoPrecioC.getText().replace(',', '.'));
                            precioCompra = precioCompra.setScale(2, RoundingMode.HALF_UP);
                        } catch (NumberFormatException e) {
                            formularioValido = false;
                            erroresModificacion.add("Precio de compra (valor inválido)");
                        }
                    }
                }

                // Validar precio venta (si se modificó)
                if (!textoPrecioV.getText().trim().isEmpty()) {
                    if (!validarCampoNumerico(textoPrecioV, "precio de venta", false)) {
                        formularioValido = false;
                        erroresModificacion.add("Precio de venta");
                    } else {
                        try {
                            precioVenta = new BigDecimal(textoPrecioV.getText().replace(',', '.'));
                            precioVenta = precioVenta.setScale(2, RoundingMode.HALF_UP);
                        } catch (NumberFormatException e) {
                            formularioValido = false;
                            erroresModificacion.add("Precio de venta (valor inválido)");
                        }
                    }
                }

                // Validar descripción (si se modificó)
                if (!textoDescripcion.getText().trim().isEmpty()) {
                    if (!validarDescripcion()) {
                        formularioValido = false;
                        erroresModificacion.add("Descripción");
                    }
                }

                if (!formularioValido) {
                    break;
                } 
                
                String update = "UPDATE articulos SET stock = ?,"
                        + "stock_minimo = ?, "
                        + "precio_de_compra = ?, "
                        + "precio_de_venta = ?, "
                        + "descripcion = ? "
                        + "WHERE codigo = ?";
                    
                try (Connection conexion = conn.connect();
                        PreparedStatement stm = conexion.prepareStatement(update)){
                    stm.setBigDecimal(1, stock);
                    stm.setBigDecimal(2, stockMinimo);
                    stm.setBigDecimal(3, precioCompra);
                    stm.setBigDecimal(4, precioVenta);
                    stm.setString(5, textoDescripcion.getText());
                    stm.setString(6, textoCodigo.getText());
                    

                    int filas = stm.executeUpdate();
                    
                    if (filas == 1) {
                        JOptionPane.showMessageDialog(null, 
                        "Artículo modificado con éxito", 
                        "¡Enhorabuena!",
                        JOptionPane.INFORMATION_MESSAGE,
                        imagenPerso(true));
                        
                        resetFormulario();
                        desactivarTodo();
                        modoAbcm();
                        
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
                
            case CONSULTAPORCODIGO:
                
                try (Connection conexion = conn.connect();
                        PreparedStatement stm = conexion.prepareStatement(consultaArticulos)) {
                    
                    stm.setString(1, textoCodigo.getText());
                    
                    try (ResultSet rs = stm.executeQuery()){
                        if (rs.next()) {
                            textoCodigo.setEnabled(false);
                            textoStock.setText(String.valueOf(rs.getBigDecimal("stock")));
                            textoStockMin.setText(String.valueOf(rs.getBigDecimal("stock_minimo")));
                            textoPrecioC.setText(String.valueOf(rs.getBigDecimal("precio_de_compra")));
                            textoPrecioV.setText(String.valueOf(rs.getBigDecimal("precio_de_venta")));
                            textoDescripcion.setText(rs.getString("descripcion"));
                            
                        } else{
                            JOptionPane.showMessageDialog(null, 
                                    "No existe ningún cliente con ese código", 
                                    "Error", 
                                    JOptionPane.ERROR_MESSAGE,imagenPerso(false));
                            resetFormulario();
                        }
                        
                    } catch (SQLException e) {
                        //No se ha encontrado nadie?
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    //Error en la conexión
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }//GEN-LAST:event_botonAceptarActionPerformed

    private void botonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSalirActionPerformed
        desactivarTodo();
        etiquetaModoModificar.setText("Seleccione un modo");
        resetFormulario();
    }//GEN-LAST:event_botonSalirActionPerformed

    private void altasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_altasActionPerformed
        
        desactivarTodo();
        resetFormulario();
        modoAbcm();
        textoCodigo.setText("");
        modo = Modo.ALTA;
        etiquetaModoModificar.setText("Alta de artículo");
    }//GEN-LAST:event_altasActionPerformed

    private void volverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_volverActionPerformed
        
        Navegador.irA(Vista.MENU);
    }//GEN-LAST:event_volverActionPerformed

    private void bajasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bajasActionPerformed
        
        desactivarTodo();
        resetFormulario();
        modoAbcm();
        textoCodigo.setText("");
        modo = Modo.BAJA;
        etiquetaModoModificar.setText("Baja de artículo");
    }//GEN-LAST:event_bajasActionPerformed

    private void modificacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modificacionesActionPerformed
        
        desactivarTodo();
        resetFormulario();
        modoAbcm();
        textoCodigo.setText("");
        modo = Modo.MODIFICACIONES;
        etiquetaModoModificar.setText("Modificación de artículo");
    }//GEN-LAST:event_modificacionesActionPerformed

    private void porCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_porCodigoActionPerformed
        
        desactivarTodo();
        resetFormulario();
        modoAbcm();
        textoCodigo.setText("");
        modo = Modo.CONSULTAPORCODIGO;
        etiquetaModoModificar.setText("Consulta por código");
    }//GEN-LAST:event_porCodigoActionPerformed

    private void porCodigosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_porCodigosActionPerformed
        
        try {
            JasperPrint print = JasperFillManager.fillReport(informeOrigenTodos, null, conn.connect());
            JasperExportManager.exportReportToPdfFile(print, informeDestinoTodos);
        } catch (SQLException ex) {
            System.getLogger(Articulos.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (JRException ex) {
            System.getLogger(Articulos.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
    }//GEN-LAST:event_porCodigosActionPerformed

    private void entreCodigosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_entreCodigosActionPerformed
        
        Navegador.irA(Vista.BUSQUEDAENTRECODIGOS_CLIENTES);
    }//GEN-LAST:event_entreCodigosActionPerformed

    private void graficosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_graficosActionPerformed
        
        try {
            JasperPrint print = JasperFillManager.fillReport(informeOrigenGraficos, null, conn.connect());
            JasperExportManager.exportReportToPdfFile(print, informeDestinoGraficos);
        } catch (SQLException ex) {
            System.getLogger(Articulos.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (JRException ex) {
            System.getLogger(Articulos.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }//GEN-LAST:event_graficosActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Articulos().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem altas;
    private javax.swing.JLabel apellidos;
    private javax.swing.JMenuItem bajas;
    private javax.swing.JButton botonAceptar;
    private javax.swing.JButton botonCancelar;
    private javax.swing.JButton botonSalir;
    private javax.swing.JMenu consultas;
    private javax.swing.JLabel cp;
    private javax.swing.JLabel domicilio;
    private javax.swing.JMenuItem entreCodigos;
    private javax.swing.JLabel etiquetaCodigo;
    private javax.swing.JLabel etiquetaModoModificar;
    private javax.swing.JMenuItem graficos;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JMenu listados;
    private javax.swing.JMenu mantenimiento;
    private javax.swing.JMenuItem modificaciones;
    private javax.swing.JLabel nif;
    private javax.swing.JLabel nombre;
    private javax.swing.JMenuItem porCodigo;
    private javax.swing.JMenuItem porCodigos;
    private javax.swing.JTextField textoCodigo;
    private javax.swing.JTextField textoDescripcion;
    private javax.swing.JTextField textoPrecioC;
    private javax.swing.JTextField textoPrecioV;
    private javax.swing.JTextField textoStock;
    private javax.swing.JTextField textoStockMin;
    private javax.swing.JMenuItem volver;
    // End of variables declaration//GEN-END:variables
}
