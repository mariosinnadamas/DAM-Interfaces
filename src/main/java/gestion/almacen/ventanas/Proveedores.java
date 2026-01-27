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

public class Proveedores extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Proveedores.class.getName());
    
    private enum Modo {ALTA,BAJA,MODIFICACIONES,CONSULTAPORCODIGO};
    private Modo modo;
    
    //Array para almacenar los errores
    private List <String> errores = new ArrayList<>();
    
    //Consulta reutilizada
    private String consultaProveedores = "SELECT codigo, nif,nombre,apellidos,domicilio,codigo_postal,localidad,telefono,movil,fax,email,total_compras FROM proveedores WHERE codigo = ?";
    
    //Directorios para el jasper
    private String informeOrigenTodos = "/Users/mario/Documents/DAM/2/Interfaces/interfaces/src/main/java/gestion/almacen/jasper/proveedores/ProveedoresTodos.jasper";
    private String informeDestinoTodos = "src/main/java/gestion/almacen/jasper/proveedores/ProveedoresTodos.pdf";
    private String informeOrigenGraficos = "/Users/mario/Documents/DAM/2/Interfaces/interfaces/src/main/java/gestion/almacen/jasper/proveedores/Graficos.jasper";
    private String informeDestinoGraficos = "src/main/java/gestion/almacen/jasper/proveedores/GraficosProveedores.pdf";
    
    private ConexionDB conn = new ConexionDB();
    
    //Variables booleanas para comprobar todo
    boolean codigoComprobado = false;
    boolean nifComprobado = false;
    boolean nif2Comprobado = false;
    boolean nombreComprobado = false;
    boolean apellidosComprobado = false;
    boolean domicilioComprobado = false;
    boolean cpComprobado = false;
    boolean localidadComprobado = false;
    boolean telefonoComprobado = false;
    boolean movilComprobado = false;
    boolean faxComprobado = false;
    boolean mailComprobado = false;
    
    public Proveedores() {
        initComponents();
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        textoNif2.setEnabled(false);
        textoTotal.setEnabled(false);
        desactivarTodo();
        setTitle("Proveedores");
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
    public void desactivarTodo(){
        textoCodigo.setEnabled(false);
        textoNif.setEnabled(false);
        textoNombre.setEnabled(false);
        textoApellidos.setEnabled(false);
        textoDomicilio.setEnabled(false);
        textoCp.setEnabled(false);
        textoLocalidad.setEnabled(false);
        textoTelefono.setEnabled(false);
        textoMovil.setEnabled(false);
        textoFax.setEnabled(false);
        textoMail.setEnabled(false);
        botonAceptar.setEnabled(false);
        botonCancelar.setEnabled(false);
        botonSalir.setEnabled(false);
    }
    
    //Metodo para activar todo
    public void activarTodo(){
        textoCodigo.setEnabled(true);
        textoNif.setEnabled(true);
        textoNombre.setEnabled(true);
        textoApellidos.setEnabled(true);
        textoDomicilio.setEnabled(true);
        textoCp.setEnabled(true);
        textoLocalidad.setEnabled(true);
        textoTelefono.setEnabled(true);
        textoMovil.setEnabled(true);
        textoFax.setEnabled(true);
        textoMail.setEnabled(true);
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
        textoNif.setText("");
        textoNif2.setText("");
        textoNombre.setText("");
        textoApellidos.setText("");
        textoDomicilio.setText("");
        textoCp.setText("");
        textoLocalidad.setText("");
        textoTelefono.setText("");
        textoMovil.setText("");
        textoFax.setText("");
        textoMail.setText("");
        textoTotal.setText("");
        marcarCorrecto(textoCodigo);
        marcarCorrecto(textoNif);
        marcarCorrecto(textoNombre);
        marcarCorrecto(textoApellidos);
        marcarCorrecto(textoDomicilio);
        marcarCorrecto(textoCp);
        marcarCorrecto(textoLocalidad);
        marcarCorrecto(textoTelefono);
        marcarCorrecto(textoMovil);
        marcarCorrecto(textoFax);
        marcarCorrecto(textoMail);
        marcarCorrecto(textoTotal);
        codigoComprobado = false;
        nifComprobado = false;
        nif2Comprobado = false;
        nombreComprobado = false;
        apellidosComprobado = false;
        domicilioComprobado = false;
        cpComprobado = false;
        localidadComprobado = false;
        telefonoComprobado = false;
        movilComprobado = false;
        faxComprobado = false;
        mailComprobado = false;
    }
    
    public boolean comprobarFormulario(){
        // Hay que hacer que este metodo saque la ventana de errores llamando a otro metodo
        errores.clear();
        
        if (!codigoComprobado) errores.add("Codigo");
        if (!nifComprobado) errores.add("Nif");
        if (!nif2Comprobado) errores.add("Letra Nif");
        if (!nombreComprobado) errores.add("Nombre");
        if (!apellidosComprobado) errores.add("Apellidos");
        if (!domicilioComprobado) errores.add("Domicilio");
        if (!cpComprobado) errores.add("Código postal");
        if (!localidadComprobado) errores.add("Localidad");
        if (!telefonoComprobado) errores.add("Telefono");
        if (!movilComprobado) errores.add("Movil");
        if (!faxComprobado) errores.add("Fax");
        if (!mailComprobado) errores.add("eMail");
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

        codigo = new javax.swing.JLabel();
        nif = new javax.swing.JLabel();
        nombre = new javax.swing.JLabel();
        apellidos = new javax.swing.JLabel();
        domicilio = new javax.swing.JLabel();
        cp = new javax.swing.JLabel();
        localidad = new javax.swing.JLabel();
        telefono = new javax.swing.JLabel();
        movil = new javax.swing.JLabel();
        fax = new javax.swing.JLabel();
        mail = new javax.swing.JLabel();
        total = new javax.swing.JLabel();
        textoCodigo = new javax.swing.JTextField();
        textoNif = new javax.swing.JTextField();
        textoNif2 = new javax.swing.JTextField();
        textoNombre = new javax.swing.JTextField();
        textoApellidos = new javax.swing.JTextField();
        textoDomicilio = new javax.swing.JTextField();
        textoCp = new javax.swing.JTextField();
        textoLocalidad = new javax.swing.JTextField();
        textoTelefono = new javax.swing.JTextField();
        textoMovil = new javax.swing.JTextField();
        textoFax = new javax.swing.JTextField();
        textoMail = new javax.swing.JTextField();
        textoTotal = new javax.swing.JTextField();
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

        codigo.setText("Código");

        nif.setText("N.I.F");

        nombre.setText("Nombre");

        apellidos.setText("Apellidos");

        domicilio.setText("Domicilio");

        cp.setText("C.P");

        localidad.setText("Localidad");

        telefono.setText("Telefono");

        movil.setText("Movil");

        fax.setText("Fax");

        mail.setText("e-mail");

        total.setText("Total Compras");

        textoCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textoCodigoActionPerformed(evt);
            }
        });

        textoNif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textoNifActionPerformed(evt);
            }
        });

        textoNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textoNombreActionPerformed(evt);
            }
        });

        textoApellidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textoApellidosActionPerformed(evt);
            }
        });

        textoDomicilio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textoDomicilioActionPerformed(evt);
            }
        });

        textoCp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textoCpActionPerformed(evt);
            }
        });

        textoLocalidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textoLocalidadActionPerformed(evt);
            }
        });

        textoTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textoTelefonoActionPerformed(evt);
            }
        });

        textoMovil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textoMovilActionPerformed(evt);
            }
        });

        textoFax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textoFaxActionPerformed(evt);
            }
        });

        textoMail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textoMailActionPerformed(evt);
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(botonAceptar)
                        .addGap(23, 23, 23)
                        .addComponent(botonCancelar)
                        .addGap(18, 18, 18)
                        .addComponent(botonSalir))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textoApellidos, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(textoDomicilio, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(textoMail, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(61, 61, 61))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(mail)
                                        .addGap(271, 271, 271)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(total)
                                    .addComponent(textoTotal)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(telefono)
                                    .addComponent(textoTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(42, 42, 42)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(movil)
                                    .addComponent(textoMovil, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(42, 42, 42)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(fax)
                                    .addComponent(textoFax)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(textoCp, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(54, 54, 54)
                                .addComponent(textoLocalidad))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(domicilio)
                                .addGap(56, 345, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(etiquetaModoModificar)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(codigo)
                                            .addComponent(textoCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(nif)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(textoNif, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(textoNif2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addGap(39, 39, 39)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(textoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(nombre)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(cp)
                                        .addGap(119, 119, 119)
                                        .addComponent(localidad))
                                    .addComponent(apellidos))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(etiquetaModoModificar)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(codigo)
                    .addComponent(nif)
                    .addComponent(nombre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textoCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textoNif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textoNif2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(apellidos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textoApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(domicilio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textoDomicilio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cp)
                    .addComponent(localidad))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textoCp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textoLocalidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(telefono)
                    .addComponent(movil)
                    .addComponent(fax))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textoTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textoMovil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textoFax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mail)
                    .addComponent(total))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textoMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonAceptar)
                    .addComponent(botonCancelar)
                    .addComponent(botonSalir))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void textoCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoCodigoActionPerformed
        
        String texto = textoCodigo.getText();
        if (!texto.matches("[0-9]{5}")){
            marcarError(textoCodigo);
            JOptionPane.showMessageDialog(null, "Debe ser una cadena de 5 dígitos"
                    ,"Error",JOptionPane.ERROR_MESSAGE,imagenPerso(false));
        } else {
            marcarCorrecto(textoCodigo);
            switch (modo) {
                //Realiza una consulta antes de agregar para comprobar que puedas dar de alta
                case ALTA:
                    
                    try (Connection conexion = conn.connect();
                            PreparedStatement stm = conexion.prepareStatement(consultaProveedores)) {
                        stm.setString(1, textoCodigo.getText());
                        try (ResultSet rs = stm.executeQuery()){
                            if (!rs.next()) {
                               activarTodo();
                               textoCodigo.setEnabled(false);
                               textoCodigo.addActionListener(e -> textoNif.requestFocus());
                               codigoComprobado = true;
                            } else{
                                JOptionPane.showMessageDialog(null, 
                                        "Ya existe un usuario con ese código, no es posible agregarlo", 
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
                            PreparedStatement stm = conexion.prepareStatement(consultaProveedores)) {
                        stm.setString(1, textoCodigo.getText());
                        try (ResultSet rs = stm.executeQuery()){
                            if (rs.next()) {
                               botonAceptar.requestFocus();
                               codigoComprobado = true;
                               textoCodigo.setEnabled(false);
                            } else{
                                JOptionPane.showMessageDialog(null, 
                                        "No se ha encontrado ningun cliente asociado a ese código", 
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
                            PreparedStatement stm = conexion.prepareStatement(consultaProveedores)) {
                        stm.setString(1, textoCodigo.getText());
                        
                        try (ResultSet rs = stm.executeQuery()){
                            if (rs.next()) {
                                activarTodo();
                                String dni = rs.getString("nif");
                                textoNif.setText(dni.substring(0,8));
                                textoNif2.setText(dni.substring(8));
                                textoNombre.setText(rs.getString("nombre"));
                                textoApellidos.setText(rs.getString("apellidos"));
                                textoDomicilio.setText(rs.getString("domicilio"));
                                textoCp.setText(rs.getString("codigo_postal"));
                                textoLocalidad.setText(rs.getString("localidad"));
                                textoTelefono.setText(rs.getString("telefono"));
                                textoMovil.setText(rs.getString("movil"));
                                textoFax.setText(rs.getString("fax"));
                                textoMail.setText(rs.getString("email"));
                                textoTotal.setText(rs.getString("total_compras"));
                                textoNif.addActionListener(e -> textoNif.requestFocus());
                                textoCodigo.setEnabled(false);
                                codigoComprobado = true;
                            } else{
                                JOptionPane.showMessageDialog(null, 
                                        "No existe ningún usuario con ese código", 
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
        } 
    }//GEN-LAST:event_textoCodigoActionPerformed
    
    private void textoNifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoNifActionPerformed
        
        String texto = textoNif.getText();
        final char[] DNI_LETTERS = {
        'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 
        'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 
        'C', 'K', 'E'};
        
        if (!texto.matches("[0-9]{8}")) {
            marcarError(textoNif);
            JOptionPane.showMessageDialog(null, "Debe ser una cadena de 8 dígitos",
                    "Error",JOptionPane.ERROR_MESSAGE, imagenPerso(false));
        } else {
            marcarCorrecto(textoNif);
            int dniNumber = Integer.parseInt(textoNif.getText());
            int remainder = dniNumber % 23;
            char letra = DNI_LETTERS[remainder];
            textoNif2.setText(String.valueOf(letra));
            textoNif.addActionListener(e -> textoNombre.requestFocus());
            nifComprobado = true;
            nif2Comprobado = true;
        }
    }//GEN-LAST:event_textoNifActionPerformed

    private void textoNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoNombreActionPerformed
        
        String texto = textoNombre.getText();
        if (texto.isEmpty()) {
            marcarError(textoNombre);
            JOptionPane.showMessageDialog(null, "No puedes dejar este campo vacío",
                    "Error",JOptionPane.ERROR_MESSAGE,imagenPerso(false));
        } else if (!texto.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]{1,50}$")){
            marcarError(textoNombre);
            JOptionPane.showMessageDialog(null, "El nombre debe contener solo "
                    + "letras y un máximo de 50 carácteres","Error"
                    ,JOptionPane.ERROR_MESSAGE,imagenPerso(false));
        } else{
            marcarCorrecto(textoNombre);
            textoNombre.addActionListener(e -> textoApellidos.requestFocus());
            nombreComprobado = true;
        }
    }//GEN-LAST:event_textoNombreActionPerformed

    private void textoApellidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoApellidosActionPerformed
        
        String texto = textoApellidos.getText();
        if (texto.isEmpty()) {
            marcarError(textoApellidos);
            JOptionPane.showMessageDialog(null, "No puedes dejar este campo vacío","Error",JOptionPane.ERROR_MESSAGE);
        } else if (!texto.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]{1,50}$")){
            marcarError(textoApellidos);
            JOptionPane.showMessageDialog(null, "Los apellidos deben contener "
                    + "solo letras y un máximo de 50 carácteres"
                    ,"Error",JOptionPane.ERROR_MESSAGE,imagenPerso(false));
        } else{
            marcarCorrecto(textoApellidos);
            textoApellidos.addActionListener(e -> textoDomicilio.requestFocus());
            apellidosComprobado = true;
        }
    }//GEN-LAST:event_textoApellidosActionPerformed

    private void textoDomicilioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoDomicilioActionPerformed
        
        String texto = textoDomicilio.getText();
        if (texto.isEmpty()) {
            marcarError(textoDomicilio);
            JOptionPane.showMessageDialog(null, "No puedes dejar este campo vacío"
                    ,"Error",JOptionPane.ERROR_MESSAGE,imagenPerso(false));
        } else if (!texto.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ0-9\\s.,\\-ºª#\\/]{1,50}$")){
            marcarError(textoDomicilio);
            JOptionPane.showMessageDialog(null, "El domicilio debe contener "
                    + "solo letras y un máximo de 50 carácteres"
                    ,"Error",JOptionPane.ERROR_MESSAGE, imagenPerso(false));
        } else{
            marcarCorrecto(textoDomicilio);
            textoDomicilio.addActionListener(e -> textoCp.requestFocus());
            domicilioComprobado = true;
        }
    }//GEN-LAST:event_textoDomicilioActionPerformed

    private void textoCpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoCpActionPerformed
        
        String cp = textoCp.getText();
        if (!cp.matches("[0-9]{5}")) {
            marcarError(textoCp);
            JOptionPane.showMessageDialog(null, "Debe ser una cadena de "
                    + "5 dígitos","Error",JOptionPane.ERROR_MESSAGE
                    ,imagenPerso(false));
        } else{
            marcarCorrecto(textoCp);
            textoCp.addActionListener(e -> textoLocalidad.requestFocus());
            cpComprobado = true;
        }
    }//GEN-LAST:event_textoCpActionPerformed

    private void textoLocalidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoLocalidadActionPerformed
        
        String texto = textoLocalidad.getText();
        if (texto.isEmpty()) {
            marcarError(textoLocalidad);
            JOptionPane.showMessageDialog(null, "No puedes dejar este "
                    + "campo vacío","Error",JOptionPane.ERROR_MESSAGE
                    ,imagenPerso(false));
        } else if (!texto.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]{1,50}$")){
            marcarError(textoLocalidad);
            JOptionPane.showMessageDialog(null, "La localidad debe contener "
                    + "solo letras y un máximo de 50 carácteres"
                    ,"Error",JOptionPane.ERROR_MESSAGE,imagenPerso(false));
        } else{
            marcarCorrecto(textoLocalidad);
            textoLocalidad.addActionListener(e -> textoTelefono.requestFocus());
            localidadComprobado = true;
        }
    }//GEN-LAST:event_textoLocalidadActionPerformed

    private void textoTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoTelefonoActionPerformed
        
        String texto = textoTelefono.getText();
        if (!texto.matches("^([0-9]{9})?$")) {
            marcarError(textoTelefono);
            JOptionPane.showMessageDialog(null, "Debe ser una cadena de 9 dígitos"
                    ,"Error",JOptionPane.ERROR_MESSAGE,imagenPerso(false));
        } else{
            marcarCorrecto(textoTelefono);
            textoTelefono.addActionListener(e -> textoMovil.requestFocus());
            telefonoComprobado = true;
        }
    }//GEN-LAST:event_textoTelefonoActionPerformed

    private void textoMovilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoMovilActionPerformed
        
        String texto = textoMovil.getText();
        if (!texto.matches("^([0-9]{9})?$")) {
            marcarError(textoMovil);
            JOptionPane.showMessageDialog(null, "Debe ser una cadena de 9 dígitos"
                    ,"Error",JOptionPane.ERROR_MESSAGE,imagenPerso(false));
        } else{
            marcarCorrecto(textoMovil);
            textoMovil.addActionListener(e -> textoFax.requestFocus());
            movilComprobado = true;
        }
    }//GEN-LAST:event_textoMovilActionPerformed

    private void textoFaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoFaxActionPerformed
        
        String texto = textoFax.getText();
        if (!texto.matches("^([0-9]{9})?$")) {
            marcarError(textoFax);
            JOptionPane.showMessageDialog(null, "Debe ser una cadena de 9 dígitos"
                    ,"Error",JOptionPane.ERROR_MESSAGE,imagenPerso(false));
        } else{
            marcarCorrecto(textoFax);
            textoFax.addActionListener(e -> textoMail.requestFocus());
            faxComprobado = true;
        }
    }//GEN-LAST:event_textoFaxActionPerformed

    private void textoMailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoMailActionPerformed
        
        String texto = textoMail.getText();
        if (!texto.matches("^(?=.{0,50}$)([a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,})?$")) {
            marcarError(textoMail);
            JOptionPane.showMessageDialog(null, "El formato del email es inválido"
                    ,"Error",JOptionPane.ERROR_MESSAGE,imagenPerso(false));
        } else{
            marcarCorrecto(textoMail);
            textoMail.addActionListener(e -> botonAceptar.requestFocus());
            textoTotal.setText("0");
            mailComprobado = true;
        }
    }//GEN-LAST:event_textoMailActionPerformed

    private void botonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelarActionPerformed
        resetFormulario();
        desactivarTodo();
        modoAbcm();
    }//GEN-LAST:event_botonCancelarActionPerformed

    private void botonAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAceptarActionPerformed
        
        //modificar alta, que no saque las ventanas
        switch (modo) {
            case ALTA:
                
                if (comprobarFormulario()) {
                    String query = "INSERT INTO proveedores (codigo,nif,apellidos,nombre,"
                            + "domicilio,codigo_postal,localidad,telefono,movil,"
                            + "fax,email,total_compras) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
                    String dni = textoNif.getText().trim() + textoNif2.getText().trim();
                    try (Connection conexion = conn.connect();
                            PreparedStatement stm = conexion.prepareStatement(query)){

                            stm.setString(1, textoCodigo.getText().trim());
                            stm.setString(2, dni);
                            stm.setString(3, textoApellidos.getText().trim());
                            stm.setString(4, textoNombre.getText().trim());
                            stm.setString(5, textoDomicilio.getText().trim());
                            stm.setString(6, textoCp.getText().trim());
                            stm.setString(7, textoLocalidad.getText().trim());
                            stm.setString(8, textoTelefono.getText().trim());
                            stm.setString(9, textoMovil.getText().trim());
                            stm.setString(10, textoFax.getText().trim());
                            stm.setString(11, textoMail.getText().trim());
                            stm.setString(12, textoTotal.getText().trim());

                            stm.executeUpdate();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    JOptionPane.showMessageDialog(null, 
                            "Todos los campos estan bien, cliente agregado a la base de datos", 
                            "¡Enhorabuena!",
                            JOptionPane.INFORMATION_MESSAGE,
                            imagenPerso(true));
                    
                    resetFormulario();
                    desactivarTodo();
                    modoAbcm();
                    
                } else{
                    mostrarErrores(errores);
                }
                
                break;
            case BAJA:
                
                String sql = "DELETE FROM proveedores WHERE codigo = ?";
                    if (codigoComprobado) {
                    try (Connection conexion = conn.connect();
                            PreparedStatement stm = conexion.prepareStatement(sql)) {
                        stm.setString(1, textoCodigo.getText());
                        
                        stm.executeUpdate();
                        
                        JOptionPane.showMessageDialog(null,
                                "El proveedor ha sido borrado con éxito",
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
                                "No se puede eliminar al proveedor. "
                                        + "Pulse enter cuando rellene el campo Código",
                                "ERROR",
                                JOptionPane.ERROR_MESSAGE,imagenPerso(false));
                    }
                break;
                
            case MODIFICACIONES:
                if (!comprobarFormulario()) {
                    mostrarErrores(errores);
                    break;  
                } 
                
                String query = "UPDATE proveedores SET nif = ?,"
                        + "apellidos = ?, "
                        + "nombre = ?, "
                        + "domicilio = ?, "
                        + "codigo_postal = ?, "
                        + "localidad = ?, "
                        + "telefono = ?, "
                        + "movil=?, "
                        + "fax = ?, "
                        + "email = ? "
                        + "WHERE codigo = ?";
                    
                try (Connection conexion = conn.connect();
                        PreparedStatement stm = conexion.prepareStatement(query)){

                    String dni = textoNif.getText().trim() + textoNif2.getText().trim();
                    stm.setString(1, dni);
                    stm.setString(2, textoApellidos.getText().trim());
                    stm.setString(3, textoNombre.getText().trim());
                    stm.setString(4, textoDomicilio.getText().trim());
                    stm.setString(5, textoCp.getText().trim());
                    stm.setString(6, textoLocalidad.getText().trim());
                    stm.setString(7, textoTelefono.getText().trim());
                    stm.setString(8, textoMovil.getText().trim());
                    stm.setString(9, textoFax.getText().trim());
                    stm.setString(10, textoMail.getText().trim());
                    stm.setString(11, textoCodigo.getText().trim());

                    int filas = stm.executeUpdate();
                    
                    if (filas == 1) {
                        JOptionPane.showMessageDialog(null, 
                        "Proveedor modificado con éxito", 
                        "¡Enhorabuena!",
                        JOptionPane.INFORMATION_MESSAGE,
                        imagenPerso(true));
                        
                        resetFormulario();
                        desactivarTodo();
                        modoAbcm();
                        
                    } else {
                        JOptionPane.showMessageDialog(
                            this,
                            "No se ha podido modificar el cliente",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE,imagenPerso(false)
                        );
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
                
            case CONSULTAPORCODIGO:
                
                try (Connection conexion = conn.connect();
                        PreparedStatement stm = conexion.prepareStatement(consultaProveedores)) {
                    
                    stm.setString(1, textoCodigo.getText());
                    
                    try (ResultSet rs = stm.executeQuery()){
                        if (rs.next()) {
                            textoCodigo.setEnabled(false);
                            String dni = rs.getString("nif");
                            textoNif.setText(dni.substring(0,8));
                            textoNif2.setText(dni.substring(8));
                            textoNombre.setText(rs.getString("nombre"));
                            textoApellidos.setText(rs.getString("apellidos"));
                            textoDomicilio.setText(rs.getString("domicilio"));
                            textoCp.setText(rs.getString("codigo_postal"));
                            textoLocalidad.setText(rs.getString("localidad"));
                            textoTelefono.setText(rs.getString("telefono"));
                            textoMovil.setText(rs.getString("movil"));
                            textoFax.setText(rs.getString("fax"));
                            textoMail.setText(rs.getString("email"));
                            textoTotal.setText(rs.getString("total_compras"));
                            
                        } else{
                            JOptionPane.showMessageDialog(null, 
                                    "No existe ningún usuario con ese código", 
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
        etiquetaModoModificar.setText("Alta de usuario");
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
        etiquetaModoModificar.setText("Baja de usuario");
    }//GEN-LAST:event_bajasActionPerformed

    private void modificacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modificacionesActionPerformed
        
        desactivarTodo();
        resetFormulario();
        modoAbcm();
        textoCodigo.setText("");
        modo = Modo.MODIFICACIONES;
        etiquetaModoModificar.setText("Modificación de usuario");
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
            System.getLogger(Proveedores.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (JRException ex) {
            System.getLogger(Proveedores.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
    }//GEN-LAST:event_porCodigosActionPerformed

    private void entreCodigosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_entreCodigosActionPerformed
        
        Navegador.irA(Vista.BUSQUEDAENTRECODIGOS_PROVEEDORES);
    }//GEN-LAST:event_entreCodigosActionPerformed

    private void graficosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_graficosActionPerformed
        
        try {
            JasperPrint print = JasperFillManager.fillReport(informeOrigenGraficos, null, conn.connect());
            JasperExportManager.exportReportToPdfFile(print, informeDestinoGraficos);
        } catch (SQLException ex) {
            System.getLogger(Proveedores.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (JRException ex) {
            System.getLogger(Proveedores.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
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
        java.awt.EventQueue.invokeLater(() -> new Proveedores().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem altas;
    private javax.swing.JLabel apellidos;
    private javax.swing.JMenuItem bajas;
    private javax.swing.JButton botonAceptar;
    private javax.swing.JButton botonCancelar;
    private javax.swing.JButton botonSalir;
    private javax.swing.JLabel codigo;
    private javax.swing.JMenu consultas;
    private javax.swing.JLabel cp;
    private javax.swing.JLabel domicilio;
    private javax.swing.JMenuItem entreCodigos;
    private javax.swing.JLabel etiquetaModoModificar;
    private javax.swing.JLabel fax;
    private javax.swing.JMenuItem graficos;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JMenu listados;
    private javax.swing.JLabel localidad;
    private javax.swing.JLabel mail;
    private javax.swing.JMenu mantenimiento;
    private javax.swing.JMenuItem modificaciones;
    private javax.swing.JLabel movil;
    private javax.swing.JLabel nif;
    private javax.swing.JLabel nombre;
    private javax.swing.JMenuItem porCodigo;
    private javax.swing.JMenuItem porCodigos;
    private javax.swing.JLabel telefono;
    private javax.swing.JTextField textoApellidos;
    private javax.swing.JTextField textoCodigo;
    private javax.swing.JTextField textoCp;
    private javax.swing.JTextField textoDomicilio;
    private javax.swing.JTextField textoFax;
    private javax.swing.JTextField textoLocalidad;
    private javax.swing.JTextField textoMail;
    private javax.swing.JTextField textoMovil;
    private javax.swing.JTextField textoNif;
    private javax.swing.JTextField textoNif2;
    private javax.swing.JTextField textoNombre;
    private javax.swing.JTextField textoTelefono;
    private javax.swing.JTextField textoTotal;
    private javax.swing.JLabel total;
    private javax.swing.JMenuItem volver;
    // End of variables declaration//GEN-END:variables
}
