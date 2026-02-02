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

public class Clientes extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Clientes.class.getName());
    
    private enum Modo {ALTA,BAJA,MODIFICACIONES,CONSULTAPORCODIGO};
    private Modo modo;
    
    //Array para almacenar los errores
    private List <String> errores = new ArrayList<>();
    
    //Consulta reutilizada
    private String consultaClientes = "SELECT codigo, nif,nombre,apellidos,domicilio,codigo_postal,localidad,telefono,movil,fax,email,total_ventas FROM clientes WHERE codigo = ?";
    
    //Directorios para el jasper
    private String informeOrigenTodos = "src/main/java/gestion/almacen/jasper/clientes/ClientesTodos.jasper";
    private String informeDestinoTodos = "src/main/java/gestion/almacen/jasper/clientes/ClientesTodos.pdf";
    private String informeOrigenGraficos = "src/main/java/gestion/almacen/jasper/clientes/Graficos.jasper";
    private String informeDestinoGraficos = "src/main/java/gestion/almacen/jasper/clientes/Graficos.pdf";
    
    private ConexionDB conn = new ConexionDB();
    
    //Variables
    private String codigo;
    private String nif;
    private String nombre;
    private String apellidos;
    private String domicilio;
    private String cp;
    private String localidad;
    private String telefono;
    private String movil;
    private String fax;
    private String mail;
    
    public Clientes() {
        initComponents();
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        textoNif2.setEnabled(false);
        textoTotal.setEnabled(false);
        desactivarTodo();
        setTitle("Clientes");
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
    }
    
    private boolean validarCodigo(boolean mostrarError){
        codigo = textoCodigo.getText().trim();
        
        if (!codigo.matches("[0-9]{5}")) {
            marcarError(textoCodigo);
            if (mostrarError) errores.add("Código vacío");
            return false;
        }

        marcarCorrecto(textoCodigo);
        return true;
    }
    
    private boolean validarDNI(boolean mostrarError){
        
        nif = textoNif.getText().trim().toUpperCase();

        final char[] DNI_LETTERS = {
            'T','R','W','A','G','M','Y','F','P','D',
            'X','B','N','J','Z','S','Q','V','H','L',
            'C','K','E'
        };

        if (!nif.matches("[0-9]{8}")) {
            marcarError(textoNif);
            if (mostrarError) errores.add("NIF debe tener 8 dígitos");
            return false;
        }

        int dniNumber = Integer.parseInt(nif);
        char letra = DNI_LETTERS[dniNumber % 23];

        textoNif2.setText(String.valueOf(letra));
        marcarCorrecto(textoNif);

        return true;
    }
    
    private boolean validarNombre(boolean mostrarError) {
        
        nombre = textoNombre.getText().trim();

        if (nombre.isEmpty()) {
            marcarError(textoNombre);
            if (mostrarError) errores.add("Nombre vacío");
            return false;
        }

        if (!nombre.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]{1,50}$")) {
            marcarError(textoNombre);
            if (mostrarError) errores.add("Nombre con formato erróneo o supera los 50 caracteres");
            return false;
        }

        marcarCorrecto(textoNombre);
        return true;
    }
    
    private boolean validarApellidos(boolean mostrarError) {
        
        apellidos = textoApellidos.getText().trim();

        if (apellidos.isEmpty()) {
            marcarError(textoApellidos);
            if (mostrarError) errores.add("Apellidos vacío");
            return false;
        }

        if (!apellidos.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]{1,50}$")) {
            marcarError(textoApellidos);
            if (mostrarError) errores.add("Apellidos debe contener "
                    + "solo letras y un máximo de 50 carácteres");
            return false;
        }

        marcarCorrecto(textoApellidos);
        return true;
    }
    
    private boolean validarDomicilio(boolean mostrarError) {
        
        domicilio = textoDomicilio.getText().trim();
        if (domicilio.isEmpty()) {
            marcarError(textoDomicilio);
            if (mostrarError) errores.add("Dirección no puede estar vacío");
            return false;
        }
        
        if (!domicilio.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ0-9\\s.,\\-ºª#\\/]{1,50}$")) {
            marcarError(textoDomicilio);
            if (mostrarError) errores.add("Dirección tiene un formato érroneo o es demasiado larga");
            return false;
        }

        marcarCorrecto(textoDomicilio);
        return true;
    }
    
    private boolean validarCP(boolean mostrarError) {
        
        cp = textoCp.getText().trim();
        if (!cp.matches("[0-9]{5}")) {
            marcarError(textoCp);
            if (mostrarError) errores.add("Codigo postal deben ser un máximo de 5 dígitos");
            return false;
        }

        marcarCorrecto(textoCp);
        return true;
    }

    private boolean validarLocalidad(boolean mostrarError) {
        
        localidad = textoLocalidad.getText().trim();
        if (localidad.isEmpty()) {
            marcarError(textoLocalidad);
            if (mostrarError) errores.add("Localidad no puede estar vacío");
            return false;
        }
        
        if (!localidad.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]{1,50}$")) {
            marcarError(textoLocalidad);
            if (mostrarError) errores.add("Localidad solo puede contener letras y un máximo 50 carácteres");
            return false;
        }

        marcarCorrecto(textoLocalidad);
        return true;
    }
    
    private boolean validarTelefono(boolean mostrarError){
        
        telefono = textoTelefono.getText().trim();
        if (!telefono.matches("^([0-9]{9})?$")) {
            marcarError(textoTelefono);
            if (mostrarError) errores.add("Telefono es un máximo de 9 dígitos");
            return false;
        }

        marcarCorrecto(textoTelefono);
        return true;
    }
    
    private boolean validarMovil(boolean mostrarError){
        
        movil = textoMovil.getText().trim();
        if (!movil.matches("^([0-9]{9})?$")) {
            marcarError(textoMovil);
            if (mostrarError) errores.add("Movil es un máximo de 9 dígitos");
            return false;
        }

        marcarCorrecto(textoMovil);
        return true;
    }
    
    private boolean validarFax(boolean mostrarError){
        
        fax = textoFax.getText().trim();
        if (!fax.matches("^([0-9]{9})?$")) {
            marcarError(textoFax);
            if (mostrarError) errores.add("Fax es un máximo de 9 dígitos");
            return false;
        }

        marcarCorrecto(textoFax);
        return true;
    }
    
    private boolean validarEmail(boolean mostrarError){
        
        mail = textoMail.getText().trim();
        if (!mail.matches("^(?=.{0,50}$)([a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,})?$")) {
            marcarError(textoMail);
            if (mostrarError) errores.add("Mail no cumple con los requisitos necesarios");
            return false;
        }

        marcarCorrecto(textoMail);
        return true;
    }
    
    public boolean comprobarFormulario(){
        
        errores.clear();
        boolean ok = true;

        ok &= validarCodigo(true);
        ok &= validarDNI(true);
        ok &= validarNombre(true);
        ok &= validarApellidos(true);
        ok &= validarDomicilio(true);
        ok &= validarCP(true);
        ok &= validarLocalidad(true);
        ok &= validarTelefono(true);
        ok &= validarMovil(true);
        ok &= validarFax(true);
        ok &= validarEmail(true);
        
        return ok;
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
        etiquetaNif = new javax.swing.JLabel();
        etiquetaNombre = new javax.swing.JLabel();
        etiquetaApellidos = new javax.swing.JLabel();
        etiquetaDomicilio = new javax.swing.JLabel();
        etiquetaCp = new javax.swing.JLabel();
        etiquetaLocalidad = new javax.swing.JLabel();
        etiquetaTelefono = new javax.swing.JLabel();
        etiquetaMovil = new javax.swing.JLabel();
        etiquetaFax = new javax.swing.JLabel();
        etiquetaMail = new javax.swing.JLabel();
        etiquetaTotalVentas = new javax.swing.JLabel();
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

        etiquetaCodigo.setText("Código");

        etiquetaNif.setText("N.I.F");

        etiquetaNombre.setText("Nombre");

        etiquetaApellidos.setText("Apellidos");

        etiquetaDomicilio.setText("Domicilio");

        etiquetaCp.setText("C.P");

        etiquetaLocalidad.setText("Localidad");

        etiquetaTelefono.setText("Telefono");

        etiquetaMovil.setText("Movil");

        etiquetaFax.setText("Fax");

        etiquetaMail.setText("e-mail");

        etiquetaTotalVentas.setText("Total Ventas");

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
                                        .addComponent(etiquetaMail)
                                        .addGap(271, 271, 271)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(etiquetaTotalVentas)
                                    .addComponent(textoTotal)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(etiquetaTelefono)
                                    .addComponent(textoTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(42, 42, 42)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(etiquetaMovil)
                                    .addComponent(textoMovil, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(42, 42, 42)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(etiquetaFax)
                                    .addComponent(textoFax)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(textoCp, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(54, 54, 54)
                                .addComponent(textoLocalidad))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(etiquetaDomicilio)
                                .addGap(56, 345, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(etiquetaModoModificar)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(etiquetaCodigo)
                                            .addComponent(textoCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(etiquetaNif)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(textoNif, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(textoNif2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addGap(39, 39, 39)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(textoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(etiquetaNombre)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(etiquetaCp)
                                        .addGap(119, 119, 119)
                                        .addComponent(etiquetaLocalidad))
                                    .addComponent(etiquetaApellidos))
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
                    .addComponent(etiquetaCodigo)
                    .addComponent(etiquetaNif)
                    .addComponent(etiquetaNombre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textoCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textoNif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textoNif2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(etiquetaApellidos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textoApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(etiquetaDomicilio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textoDomicilio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etiquetaCp)
                    .addComponent(etiquetaLocalidad))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textoCp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textoLocalidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etiquetaTelefono)
                    .addComponent(etiquetaMovil)
                    .addComponent(etiquetaFax))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textoTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textoMovil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textoFax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etiquetaMail)
                    .addComponent(etiquetaTotalVentas))
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
        
        if (!validarCodigo(false)) {
            return;
        }
        
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
                           textoNif.requestFocus();
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
                        PreparedStatement stm = conexion.prepareStatement(consultaClientes)) {
                    stm.setString(1, textoCodigo.getText());
                    try (ResultSet rs = stm.executeQuery()){
                        if (rs.next()) {
                           botonAceptar.requestFocus();
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
                            textoNif.requestFocus();
                            textoCodigo.setEnabled(false);
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
    }//GEN-LAST:event_textoCodigoActionPerformed
    
    private void textoNifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoNifActionPerformed
        
        validarDNI(false);
        textoNombre.requestFocus();
    }//GEN-LAST:event_textoNifActionPerformed

    private void textoNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoNombreActionPerformed
        
        validarNombre(false);
        textoApellidos.requestFocus();
    }//GEN-LAST:event_textoNombreActionPerformed

    private void textoApellidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoApellidosActionPerformed
        
        validarApellidos(false);
        textoDomicilio.requestFocus();
    }//GEN-LAST:event_textoApellidosActionPerformed

    private void textoDomicilioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoDomicilioActionPerformed
        validarDomicilio(false);
        textoCp.requestFocus();
    }//GEN-LAST:event_textoDomicilioActionPerformed

    private void textoCpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoCpActionPerformed
        validarCP(false);
        textoLocalidad.requestFocus();
    }//GEN-LAST:event_textoCpActionPerformed

    private void textoLocalidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoLocalidadActionPerformed
        validarLocalidad(false);
        textoTelefono.requestFocus();
    }//GEN-LAST:event_textoLocalidadActionPerformed

    private void textoTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoTelefonoActionPerformed
        validarTelefono(false);
        textoMovil.requestFocus();
    }//GEN-LAST:event_textoTelefonoActionPerformed

    private void textoMovilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoMovilActionPerformed
        validarMovil(false);
        textoFax.requestFocus();
    }//GEN-LAST:event_textoMovilActionPerformed

    private void textoFaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoFaxActionPerformed
        validarFax(false);
        textoMail.requestFocus();
    }//GEN-LAST:event_textoFaxActionPerformed

    private void textoMailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoMailActionPerformed
        validarEmail(false);
        textoTotal.setText("0");
        botonAceptar.requestFocus();
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
                if (textoCodigo.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(
                        null,
                        "Introduce un código",
                        "Errores en el formulario",
                        JOptionPane.ERROR_MESSAGE,
                        imagenPerso(false)
                    );
                    return;
                }
                
                if (!comprobarFormulario()) {
                    JOptionPane.showMessageDialog(
                        null,
                        String.join("\n", errores),
                        "Errores en el formulario",
                        JOptionPane.ERROR_MESSAGE,
                        imagenPerso(false)
                    );
                    return;
                }
                
                String query = "INSERT INTO clientes (codigo,nif,apellidos,nombre,"
                        + "domicilio,codigo_postal,localidad,telefono,movil,"
                        + "fax,email,total_ventas) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
                String dni = textoNif.getText().trim() + textoNif2.getText().trim();
                try (Connection conexion = conn.connect();
                        PreparedStatement stm = conexion.prepareStatement(query)){

                        stm.setString(1, codigo);
                        stm.setString(2, dni);
                        stm.setString(3, apellidos);
                        stm.setString(4, nombre);
                        stm.setString(5, domicilio);
                        stm.setString(6, cp);
                        stm.setString(7, localidad);
                        stm.setString(8, telefono);
                        stm.setString(9, movil);
                        stm.setString(10, fax);
                        stm.setString(11, mail);
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
                
                break;
            case BAJA:
                
                String sql = "DELETE FROM clientes WHERE codigo = ?";
                    if (validarCodigo(false)) {
                        try (Connection conexion = conn.connect();
                                PreparedStatement stm = conexion.prepareStatement(sql)) {
                            stm.setString(1, textoCodigo.getText());

                            stm.executeUpdate();

                            JOptionPane.showMessageDialog(null,
                                    "El cliente ha sido borrado con éxito",
                                    "Operación realizada", 
                                    JOptionPane.INFORMATION_MESSAGE,imagenPerso(true));
                            resetFormulario();
                            desactivarTodo();
                            modoAbcm();
                        
                        } catch (Exception e) {
                            //Error en la conexión
                            e.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "No se puede eliminar al cliente. "
                                        + "Pulse enter cuando rellene el campo Código",
                                "ERROR",
                                JOptionPane.ERROR_MESSAGE,imagenPerso(false));
                    }
                break;
                
            case MODIFICACIONES:
                
                if (!comprobarFormulario()) {
                    JOptionPane.showMessageDialog(
                    null,
                    String.join("\n", errores),
                    "Errores en el formulario",
                    JOptionPane.ERROR_MESSAGE,
                    imagenPerso(false)
                    );
                    return;
                }
                
                String update = "UPDATE clientes SET nif = ?,"
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
                        PreparedStatement stm = conexion.prepareStatement(update)){

                    dni = textoNif.getText().trim() + textoNif2.getText().trim();
                    stm.setString(1, dni);
                    stm.setString(2, apellidos);
                    stm.setString(3, nombre);
                    stm.setString(4, domicilio);
                    stm.setString(5, cp);
                    stm.setString(6, localidad);
                    stm.setString(7, telefono);
                    stm.setString(8, movil);
                    stm.setString(9, fax);
                    stm.setString(10, mail);
                    stm.setString(11, codigo);

                    int filas = stm.executeUpdate();
                    
                    if (filas == 1) {
                        JOptionPane.showMessageDialog(null, 
                        "Cliente modificado con éxito", 
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
                        PreparedStatement stm = conexion.prepareStatement(consultaClientes)) {
                    
                    stm.setString(1, textoCodigo.getText());
                    
                    try (ResultSet rs = stm.executeQuery()){
                        if (rs.next()) {
                            textoCodigo.setEnabled(false);
                            dni = rs.getString("nif");
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
            System.getLogger(Clientes.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (JRException ex) {
            System.getLogger(Clientes.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
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
            System.getLogger(Clientes.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (JRException ex) {
            System.getLogger(Clientes.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
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
        java.awt.EventQueue.invokeLater(() -> new Clientes().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem altas;
    private javax.swing.JMenuItem bajas;
    private javax.swing.JButton botonAceptar;
    private javax.swing.JButton botonCancelar;
    private javax.swing.JButton botonSalir;
    private javax.swing.JMenu consultas;
    private javax.swing.JMenuItem entreCodigos;
    private javax.swing.JLabel etiquetaApellidos;
    private javax.swing.JLabel etiquetaCodigo;
    private javax.swing.JLabel etiquetaCp;
    private javax.swing.JLabel etiquetaDomicilio;
    private javax.swing.JLabel etiquetaFax;
    private javax.swing.JLabel etiquetaLocalidad;
    private javax.swing.JLabel etiquetaMail;
    private javax.swing.JLabel etiquetaModoModificar;
    private javax.swing.JLabel etiquetaMovil;
    private javax.swing.JLabel etiquetaNif;
    private javax.swing.JLabel etiquetaNombre;
    private javax.swing.JLabel etiquetaTelefono;
    private javax.swing.JLabel etiquetaTotalVentas;
    private javax.swing.JMenuItem graficos;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JMenu listados;
    private javax.swing.JMenu mantenimiento;
    private javax.swing.JMenuItem modificaciones;
    private javax.swing.JMenuItem porCodigo;
    private javax.swing.JMenuItem porCodigos;
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
    private javax.swing.JMenuItem volver;
    // End of variables declaration//GEN-END:variables
}
