/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gestion.almacen;

import java.awt.Color;
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

/**
 *
 * @author mario
 */

public class Clientes extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Clientes.class.getName());
    
    private enum Modo {ALTA,BAJA,MODIFICACIONES,CONSULTAPORCODIGO};
    private Modo modo;
    private List <String> errores = new ArrayList<>();
    private String consultaClientes = "SELECT codigo, nif,nombre,apellidos,domicilio,codigo_postal,localidad,telefono,movil,fax,email,total_ventas FROM clientes WHERE codigo = ?";

    
    private ConexionDB conn = new ConexionDB();
    
    //Variables booleanas para comprobar todo
    boolean Codigocomprobado = false;
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
    
    public Clientes() {
        initComponents();
        textoNif2.setEnabled(false);
        textoTotal.setEnabled(false);
        desactivarTodo();
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
    }
    
    public boolean comprobarFormulario(){
        // Hay que hacer que este metodo saque la ventana de errores llamando a otro metodo
        errores.clear();
        
        if (!Codigocomprobado) errores.add("Codigo");
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
        
        if (!errores.isEmpty()) {
            mostrarErrores(errores);
            return false;
        }
        return true;
    }
    
    public void mostrarErrores(List <String> errores){
        
        JOptionPane.showMessageDialog(
        this,
        "Corrige los siguientes campos:\n " + String.join("\n- ", errores),
        "ERROR",
        JOptionPane.ERROR_MESSAGE
    );
    }
    
    public void marcarError(JTextField campo){
        campo.setBackground(Color.red);
        campo.requestFocus();
    }
    
    public void marcarCorrecto (JTextField campo){
        campo.setBackground(Color.white);
    }
    
    private void marcarFormularioComoValido(){
        Codigocomprobado = true;
        nifComprobado = true;
        nif2Comprobado = true;
        nombreComprobado = true;
        apellidosComprobado = true;
        domicilioComprobado = true;
        cpComprobado = true;
        localidadComprobado = true;
        telefonoComprobado = true;
        movilComprobado = true;
        faxComprobado = true;
        mailComprobado = true;
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

        total.setText("Total");

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

        textoNif2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textoNif2ActionPerformed(evt);
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

        textoTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textoTotalActionPerformed(evt);
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
        listados.add(porCodigos);

        entreCodigos.setText("Entre códigos");
        listados.add(entreCodigos);

        graficos.setText("Gráficos");
        listados.add(graficos);

        consultas.add(listados);

        jMenuBar1.add(consultas);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(domicilio)
                                .addGap(48, 48, 48))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(codigo)
                                    .addComponent(textoCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nif)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(textoNif, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textoNif2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nombre)))
                    .addComponent(textoCp, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(telefono)
                            .addComponent(textoTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(42, 42, 42)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(movil)
                            .addComponent(textoMovil, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fax)
                            .addComponent(textoFax, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cp)
                        .addGap(119, 119, 119)
                        .addComponent(localidad))
                    .addComponent(apellidos)
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
                            .addComponent(textoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGap(140, 140, 140)
                            .addComponent(textoLocalidad, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE))
                        .addComponent(textoDomicilio)
                        .addComponent(textoApellidos))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(botonAceptar)
                        .addGap(23, 23, 23)
                        .addComponent(botonCancelar)
                        .addGap(18, 18, 18)
                        .addComponent(botonSalir)
                        .addGap(2, 2, 2)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void textoCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoCodigoActionPerformed
        
        String texto = textoCodigo.getText();
        if (!texto.matches("[0-9]{5}")){
            marcarError(textoCodigo);
            JOptionPane.showMessageDialog(null, "Debe ser una cadena de 5 dígitos","Error",JOptionPane.ERROR_MESSAGE);
        } else {
            marcarCorrecto(textoCodigo);
            switch (modo) {
                //Realiza una consulta antes de agregar para comprobar que puedas dar de alta
                case ALTA:
                
                    try (Connection conexion = conn.connect();
                            PreparedStatement stm = conexion.prepareStatement(consultaClientes)) {
                        stm.setString(1, textoCodigo.getText());
                        try (ResultSet rs = stm.executeQuery()){
                            if (!rs.next()) {
                               activarTodo();
                               textoCodigo.setEnabled(false);
                               textoCodigo.addActionListener(e -> textoNif.requestFocus());
                               Codigocomprobado = true;
                            } else{
                                JOptionPane.showMessageDialog(null, 
                                        "Ya existe un usuario con ese código, no es posible agregarlo", 
                                        "Error", 
                                        JOptionPane.ERROR_MESSAGE);
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
                            PreparedStatement stm = conexion.prepareStatement(consultaClientes)) {
                        stm.setString(1, textoCodigo.getText());
                        try (ResultSet rs = stm.executeQuery()){
                            if (rs.next()) {
                               botonAceptar.requestFocus();
                            } else{
                                JOptionPane.showMessageDialog(null, 
                                        "No se ha encontrado ningun cliente asociado a ese código", 
                                        "Error", 
                                        JOptionPane.ERROR_MESSAGE);
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
                            PreparedStatement stm = conexion.prepareStatement(consultaClientes)) {
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
                                textoTotal.setText(rs.getString("total_ventas"));
                                textoNif.addActionListener(e -> textoNif.requestFocus());
                                textoCodigo.setEnabled(false);
                                Codigocomprobado = true;
                            } else{
                                JOptionPane.showMessageDialog(null, 
                                        "No existe ningún usuario con ese código", 
                                        "Error", 
                                        JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(null, "Debe ser una cadena de 8 dígitos","Error",JOptionPane.ERROR_MESSAGE);
        } else {
            marcarCorrecto(textoNif);
            int dniNumber = Integer.parseInt(textoNif.getText());
            int remainder = dniNumber % 23;
            char letter = DNI_LETTERS[remainder];
            textoNif2.setText(String.valueOf(letter));
            textoNif.addActionListener(e -> textoNombre.requestFocus());
            nifComprobado = true;
            nif2Comprobado = true;
        }
    }//GEN-LAST:event_textoNifActionPerformed

    private void textoNif2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoNif2ActionPerformed
       
    }//GEN-LAST:event_textoNif2ActionPerformed

    private void textoNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoNombreActionPerformed
        
        String texto = textoNombre.getText();
        if (texto.isEmpty()) {
            marcarError(textoNombre);
            JOptionPane.showMessageDialog(null, "No puedes dejar este campo vacío","Error",JOptionPane.ERROR_MESSAGE);
        } else if (!texto.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]+$")){
            marcarError(textoNombre);
            JOptionPane.showMessageDialog(null, "El nombre debe contener solo letras","Error",JOptionPane.ERROR_MESSAGE);
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
        } else if (!texto.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]+$")){
            marcarError(textoApellidos);
            JOptionPane.showMessageDialog(null, "Los apellidos deben contener solo letras","Error",JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(null, "No puedes dejar este campo vacío","Error",JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(null, "Debe ser una cadena de 5 dígitos","Error",JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(null, "No puedes dejar este campo vacío","Error",JOptionPane.ERROR_MESSAGE);
        } else if (!texto.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]+$")){
            marcarError(textoLocalidad);
            JOptionPane.showMessageDialog(null, "La localidad debe contener solo letras","Error",JOptionPane.ERROR_MESSAGE);
        } else{
            marcarCorrecto(textoLocalidad);
            textoLocalidad.addActionListener(e -> textoTelefono.requestFocus());
            localidadComprobado = true;
        }
    }//GEN-LAST:event_textoLocalidadActionPerformed

    private void textoTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoTelefonoActionPerformed
        
        String texto = textoTelefono.getText();
        if (!texto.matches("[0-9]{9}")) {
            marcarError(textoTelefono);
            JOptionPane.showMessageDialog(null, "Debe ser una cadena de 9 dígitos","Error",JOptionPane.ERROR_MESSAGE);
        } else{
            marcarCorrecto(textoTelefono);
            textoTelefono.addActionListener(e -> textoMovil.requestFocus());
            telefonoComprobado = true;
        }
    }//GEN-LAST:event_textoTelefonoActionPerformed

    private void textoMovilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoMovilActionPerformed
        
        String texto = textoMovil.getText();
        if (!texto.matches("[0-9]{9}")) {
            marcarError(textoMovil);
            JOptionPane.showMessageDialog(null, "Debe ser una cadena de 9 dígitos","Error",JOptionPane.ERROR_MESSAGE);
        } else{
            marcarCorrecto(textoMovil);
            textoMovil.addActionListener(e -> textoFax.requestFocus());
            movilComprobado = true;
        }
    }//GEN-LAST:event_textoMovilActionPerformed

    private void textoFaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoFaxActionPerformed
        
        String texto = textoFax.getText();
        if (!texto.matches("[0-9]{9}")) {
            marcarError(textoFax);
            JOptionPane.showMessageDialog(null, "Debe ser una cadena de 9 dígitos","Error",JOptionPane.ERROR_MESSAGE);
        } else{
            marcarCorrecto(textoFax);
            textoFax.addActionListener(e -> textoMail.requestFocus());
            faxComprobado = true;
        }
    }//GEN-LAST:event_textoFaxActionPerformed

    private void textoMailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoMailActionPerformed
        
        String texto = textoMail.getText();
        if (texto.isEmpty()) {
            marcarError(textoMail);
            JOptionPane.showMessageDialog(null, "No puedes dejar este campo vacío","Error",JOptionPane.ERROR_MESSAGE);
        } else{
            marcarCorrecto(textoMail);
            textoMail.addActionListener(e -> botonAceptar.requestFocus());
            textoTotal.setText("0");
            mailComprobado = true;
        }
    }//GEN-LAST:event_textoMailActionPerformed

    private void textoTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoTotalActionPerformed
        
    }//GEN-LAST:event_textoTotalActionPerformed

    private void botonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelarActionPerformed
        resetFormulario();
    }//GEN-LAST:event_botonCancelarActionPerformed

    private void botonAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAceptarActionPerformed
        //Icono personalizado
        Path p = Path.of("src", "main", "java", "formulario", "IconoVerde.jpg");
        ImageIcon imagen = new ImageIcon(p.toString());
        imagen = new ImageIcon(imagen.getImage().getScaledInstance(70, 70, 0));
        
        switch (modo) {
            case ALTA:
                
                if (comprobarFormulario()) {
                    String query = "INSERT INTO clientes (codigo,nif,apellidos,nombre,"
                            + "domicilio,codigo_postal,localidad,telefono,movil,"
                            + "fax,email,total_ventas) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
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
                            imagen);

                } else{
                    JOptionPane.showMessageDialog(null, 
                            "Los siguientes campos están mal: " + errores.toString() 
                                    + "\n" + "Una vez corregidos los campos pulse Enter en cada campo que este mal", 
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                }
                break;
            case BAJA:
                
                String sql = "DELETE FROM clientes WHERE codigo = ?";
                
                    try (Connection conexion = conn.connect();
                            PreparedStatement stm = conexion.prepareStatement(sql)) {
                        stm.setString(1, textoCodigo.getText());
                        
                        stm.executeUpdate();
                        
                    } catch (Exception e) {
                        //Error en la conexión
                        e.printStackTrace();
                    }
                break;
                
            case MODIFICACIONES:
                if (!comprobarFormulario()) {
                    break;  
                } 
                
                String query = "UPDATE clientes SET nif = ?,"
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
                        "Cliente modificado con éxito", 
                        "¡Enhorabuena!",
                        JOptionPane.INFORMATION_MESSAGE,
                        imagen);
                    } else {
                        JOptionPane.showMessageDialog(
                            this,
                            "No se ha podido modificar el cliente",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
                
            case CONSULTAPORCODIGO:
                
                try (Connection conexion = conn.connect();
                        PreparedStatement stm = conexion.prepareStatement(consultaClientes)) {
                    
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
                            textoTotal.setText(rs.getString("total_ventas"));
                            
                        } else{
                            JOptionPane.showMessageDialog(null, 
                                    "No existe ningún usuario con ese código", 
                                    "Error", 
                                    JOptionPane.ERROR_MESSAGE);
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
        resetFormulario();
    }//GEN-LAST:event_botonSalirActionPerformed

    private void altasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_altasActionPerformed
        modoAbcm();
        modo = Modo.ALTA;
    }//GEN-LAST:event_altasActionPerformed

    private void volverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_volverActionPerformed
        Navegador.irA(Vista.MENU);
    }//GEN-LAST:event_volverActionPerformed

    private void bajasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bajasActionPerformed
        modo = Modo.BAJA;
        modoAbcm();
        
    }//GEN-LAST:event_bajasActionPerformed

    private void modificacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modificacionesActionPerformed
        modo = Modo.MODIFICACIONES;
        modoAbcm();
    }//GEN-LAST:event_modificacionesActionPerformed

    private void porCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_porCodigoActionPerformed
        modo = Modo.CONSULTAPORCODIGO;
        modoAbcm();
    }//GEN-LAST:event_porCodigoActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new Clientes().setVisible(true));
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
