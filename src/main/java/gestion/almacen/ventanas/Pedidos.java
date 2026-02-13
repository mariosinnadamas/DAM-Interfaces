/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gestion.almacen.ventanas;

import gestion.almacen.ConexionDB;
import java.awt.Color;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author mario
 */
public class Pedidos extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Pedidos.class.getName());
    private enum Modo {CLIENTES,PROVEEDORES};
    private Modo modo;
    
    //Array para almacenar los errores
    private List <String> errores = new ArrayList<>();
    
    //Consulta reutilizada
    private String consultaClientes = "SELECT codigo, nif, nombre, apellidos, domicilio, codigo_postal, localidad, total_ventas FROM clientes WHERE codigo = ?";
    private String consultaProveedores = "SELECT codigo, nif,nombre,apellidos,domicilio,codigo_postal,localidad,total_compras FROM proveedores WHERE codigo = ?";
    private String consultaArticulos = "SELECT codigo, descripcion, stock, stock_minimo, precio_de_compra,precio_de_venta FROM articulos WHERE codigo = ?";


    
    private ConexionDB conn = new ConexionDB();
    
    //Variables
    private String codigo;
    private String dni;
    private String nombre;
    private String apellidos;
    private String domicilio;
    private String cp;
    private String localidad;
    private String total;
    
    private String descripción;
    private String unidadesString;
    private String stock;
    private String precio;
    private String importe;
    
    private BigDecimal stockBD;
    private BigDecimal precioCompraBD;
    
    public void modoDefault(){
        textoCodigo.setEnabled(true);
        botonSalir.setEnabled(true);
        textoCodigo.requestFocus();
    }
    
    //Metodo para desactivar todo
    public void desactivarTodo(){
        textoCodigo.setEnabled(false);
        textoNif.setEnabled(false);
        textoNif2.setEnabled(false);
        textoNombre.setEnabled(false);
        textoApellidos.setEnabled(false);
        textoDomicilio.setEnabled(false);
        textoCp.setEnabled(false);
        textoLocalidad.setEnabled(false);
        textoTotal.setEnabled(false);
        textoArticulo.setEnabled(false);
        textoDescripcion.setEnabled(false);
        textoUnidades.setEnabled(false);
        textoStock.setEnabled(false);
        textoPrecio.setEnabled(false);
        textoImporte.setEnabled(false);
        botonFactura.setEnabled(false);
        botonAceptar.setEnabled(false);
        botonSalir.setEnabled(false);
        botonCancelarPedido.setEnabled(false);
        botonCancelarTodo.setEnabled(false);
    }
    
    //Metodo para desactivar todo
    public void activarTodo(){
        textoCodigo.setEnabled(true);
        textoNif.setEnabled(true);
        textoNombre.setEnabled(true);
        textoApellidos.setEnabled(true);
        textoDomicilio.setEnabled(true);
        textoCp.setEnabled(true);
        textoLocalidad.setEnabled(true);
        textoTotal.setEnabled(true);
        textoArticulo.setEnabled(true);
        textoDescripcion.setEnabled(true);
        textoUnidades.setEnabled(true);
        textoStock.setEnabled(true);
        textoPrecio.setEnabled(true);
        textoImporte.setEnabled(true);
        botonFactura.setEnabled(true);
        botonAceptar.setEnabled(true);
        botonSalir.setEnabled(true);
        botonCancelarPedido.setEnabled(true);
        botonCancelarTodo.setEnabled(true);
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
        textoTotal.setText("");
        textoArticulo.setText("");
        textoDescripcion.setText("");
        textoUnidades.setText("");
        textoStock.setText("");
        textoPrecio.setText("");
        textoImporte.setText("");
        marcarCorrecto(textoCodigo);
        marcarCorrecto(textoArticulo);
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
    
    //Metodo para marcar un campo como erroneo
    public void marcarError(JTextField campo){
        campo.setBackground(Color.red);
        campo.requestFocus();
    }
    
    //Metodo para marcar un campo como correcto
    public void marcarCorrecto (JTextField campo){
        campo.setBackground(Color.white);
    }
    
    public Pedidos() {
        initComponents();
        desactivarTodo();
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
        textoCodigo = new javax.swing.JTextField();
        etiquetaNif = new javax.swing.JLabel();
        textoNif = new javax.swing.JTextField();
        textoNif2 = new javax.swing.JTextField();
        textoNombre = new javax.swing.JTextField();
        etiquetaNombre = new javax.swing.JLabel();
        textoApellidos = new javax.swing.JTextField();
        etiquetaApellidos = new javax.swing.JLabel();
        etiquetaDomicilio = new javax.swing.JLabel();
        textoDomicilio = new javax.swing.JTextField();
        etiquetaCp = new javax.swing.JLabel();
        textoCp = new javax.swing.JTextField();
        textoLocalidad = new javax.swing.JTextField();
        etiquetaLocalidad = new javax.swing.JLabel();
        textoTotal = new javax.swing.JTextField();
        etiquetaTotalVentas = new javax.swing.JLabel();
        textoArticulo = new javax.swing.JTextField();
        textoDescripcion = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        etiquetaArticulo = new javax.swing.JLabel();
        etiquetaArticulo1 = new javax.swing.JLabel();
        etiquetaArticulo2 = new javax.swing.JLabel();
        etiquetaArticulo3 = new javax.swing.JLabel();
        etiquetaArticulo4 = new javax.swing.JLabel();
        textoUnidades = new javax.swing.JTextField();
        textoStock = new javax.swing.JTextField();
        textoPrecio = new javax.swing.JTextField();
        etiquetaArticulo5 = new javax.swing.JLabel();
        textoImporte = new javax.swing.JTextField();
        botonAceptar = new javax.swing.JButton();
        botonSalir = new javax.swing.JButton();
        botonFactura = new javax.swing.JButton();
        botonCancelarPedido = new javax.swing.JButton();
        botonCancelarTodo = new javax.swing.JButton();
        etiquetaModoModificar = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        clienteItem = new javax.swing.JMenuItem();
        proveedorItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        volverItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        etiquetaCodigo.setText("Código");

        textoCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textoCodigoActionPerformed(evt);
            }
        });

        etiquetaNif.setText("N.I.F");

        etiquetaNombre.setText("Nombre");

        etiquetaApellidos.setText("Apellidos");

        etiquetaDomicilio.setText("Domicilio");

        etiquetaCp.setText("C.P");

        etiquetaLocalidad.setText("Localidad");

        etiquetaTotalVentas.setText("Total");

        textoArticulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textoArticuloActionPerformed(evt);
            }
        });

        etiquetaArticulo.setText("Artículo");

        etiquetaArticulo1.setText("Descripción");

        etiquetaArticulo2.setText("Unidades");

        etiquetaArticulo3.setText("Stock");

        etiquetaArticulo4.setText("Precio");

        textoUnidades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textoUnidadesActionPerformed(evt);
            }
        });

        etiquetaArticulo5.setText("Importe");

        botonAceptar.setText("Aceptar");

        botonSalir.setText("Salir");
        botonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSalirActionPerformed(evt);
            }
        });

        botonFactura.setText("Factura");

        botonCancelarPedido.setText("Cancelar Pedido");

        botonCancelarTodo.setText("Cancelar Todo");

        etiquetaModoModificar.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        etiquetaModoModificar.setText("Seleccione un modo");

        jMenu2.setText("Menú");

        clienteItem.setText("Cliente");
        clienteItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clienteItemActionPerformed(evt);
            }
        });
        jMenu2.add(clienteItem);

        proveedorItem.setText("Proveedor");
        proveedorItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                proveedorItemActionPerformed(evt);
            }
        });
        jMenu2.add(proveedorItem);
        jMenu2.add(jSeparator2);

        volverItem.setText("Volver");
        jMenu2.add(volverItem);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(etiquetaDomicilio)
                            .addComponent(etiquetaApellidos)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(textoCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(etiquetaCodigo))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(etiquetaNif)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(textoNif, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(textoNif2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(etiquetaNombre)
                                    .addComponent(textoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(textoDomicilio, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jSeparator1)
                    .addComponent(textoApellidos)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textoCp, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(etiquetaCp))
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textoLocalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(etiquetaLocalidad))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(etiquetaTotalVentas)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(textoTotal)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(etiquetaArticulo)
                            .addComponent(textoArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(etiquetaArticulo2)
                            .addComponent(textoUnidades, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(etiquetaArticulo1)
                                    .addComponent(textoDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(34, 34, 34))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(etiquetaArticulo3)
                                    .addComponent(textoStock, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(etiquetaArticulo4)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(textoPrecio, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(etiquetaArticulo5)
                                        .addGap(63, 63, 63))
                                    .addComponent(textoImporte)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(botonFactura)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(botonAceptar)
                            .addGap(14, 14, 14)
                            .addComponent(botonSalir))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(botonCancelarPedido)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(botonCancelarTodo))))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(157, 157, 157)
                .addComponent(etiquetaModoModificar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(etiquetaModoModificar)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(etiquetaCodigo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textoCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(etiquetaNif)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textoNif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textoNif2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(etiquetaNombre)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(etiquetaApellidos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textoDomicilio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(etiquetaDomicilio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textoApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etiquetaCp)
                    .addComponent(etiquetaLocalidad)
                    .addComponent(etiquetaTotalVentas))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textoLocalidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textoCp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etiquetaArticulo)
                    .addComponent(etiquetaArticulo1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textoArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textoDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etiquetaArticulo2)
                    .addComponent(etiquetaArticulo3)
                    .addComponent(etiquetaArticulo4)
                    .addComponent(etiquetaArticulo5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textoUnidades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textoStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textoPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textoImporte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonAceptar)
                    .addComponent(botonSalir)
                    .addComponent(botonFactura))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonCancelarPedido)
                    .addComponent(botonCancelarTodo))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void textoCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoCodigoActionPerformed

        if (!validarCodigo(false)) {
            return;
        }

        switch (modo) {
           
            case CLIENTES:

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
                            textoTotal.setText(rs.getString("total_ventas"));
                            textoArticulo.setEnabled(true);
                            textoArticulo.requestFocus();
                        } else{
                            JOptionPane.showMessageDialog(null, 
                                    "No existe ningún usuario con ese código", 
                                    "Error", 
                                    JOptionPane.ERROR_MESSAGE,null);
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
    }//GEN-LAST:event_textoCodigoActionPerformed

    private void botonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSalirActionPerformed
        desactivarTodo();
        etiquetaModoModificar.setText("Seleccione un modo");
        resetFormulario();
    }//GEN-LAST:event_botonSalirActionPerformed

    private void clienteItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clienteItemActionPerformed
        
        etiquetaModoModificar.setText("Modo: Clientes");
        modo = Modo.CLIENTES;
        modoDefault();
    }//GEN-LAST:event_clienteItemActionPerformed

    private void proveedorItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_proveedorItemActionPerformed
        
        etiquetaModoModificar.setText("Modo: Proveedores");
        modo = Modo.PROVEEDORES;
    }//GEN-LAST:event_proveedorItemActionPerformed

    private void textoArticuloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoArticuloActionPerformed
        // TODO add your handling code here:
        switch (modo) {
           
            case CLIENTES:

            try (Connection conexion = conn.connect();
                        PreparedStatement stm = conexion.prepareStatement(consultaArticulos)) {
                    
                    stm.setString(1, textoArticulo.getText());
                    
                    try (ResultSet rs = stm.executeQuery()){
                        if (rs.next()) {
                            textoArticulo.setEnabled(false);
                            textoDescripcion.setText(rs.getString("descripcion"));
                            textoStock.setText(rs.getString("stock"));
                            stockBD = rs.getBigDecimal("stock");
                            textoPrecio.setText(rs.getString("precio_de_compra"));
                            precioCompraBD = rs.getBigDecimal("precio_de_compra");
                            textoUnidades.setEnabled(true);
                            botonCancelarPedido.setEnabled(true);
                            textoUnidades.requestFocus();
                        } else{
                            JOptionPane.showMessageDialog(null, 
                                    "No existe ningún articulo con ese código", 
                                    "Error", 
                                    JOptionPane.ERROR_MESSAGE,null);
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
    }//GEN-LAST:event_textoArticuloActionPerformed

    private void textoUnidadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoUnidadesActionPerformed
        
       
        
        switch (modo) {
            case CLIENTES:
                
                try {
                    BigDecimal unidadesBD = new BigDecimal(textoUnidades.getText());
                    
                    if (unidadesBD.compareTo(stockBD) >= 0) {
                        BigDecimal total = unidadesBD.multiply(precioCompraBD);
                        textoImporte.setText(String.valueOf(total));
                        textoUnidades.setEnabled(false);
                        botonAceptar.setEnabled(true);
                        botonAceptar.requestFocus();
                    
                } else {
                    JOptionPane.showMessageDialog(null, 
                                            "No tenemos suficiente stock, stock disponible: " + stockBD, 
                                            "Error",
                                            JOptionPane.ERROR_MESSAGE,null);
                }
                    break;
                    
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, 
                                            "No se ha introducido un número válido", 
                                            "Error", 
                                            JOptionPane.ERROR_MESSAGE,null);
                    textoUnidades.setText("");
                    textoUnidades.requestFocus();
                    return;
                }

                
                    
            case PROVEEDORES:
                
                
                
                break;
            default:
                break;
        }
        
        
    }//GEN-LAST:event_textoUnidadesActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new Pedidos().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonAceptar;
    private javax.swing.JButton botonCancelarPedido;
    private javax.swing.JButton botonCancelarTodo;
    private javax.swing.JButton botonFactura;
    private javax.swing.JButton botonSalir;
    private javax.swing.JMenuItem clienteItem;
    private javax.swing.JLabel etiquetaApellidos;
    private javax.swing.JLabel etiquetaArticulo;
    private javax.swing.JLabel etiquetaArticulo1;
    private javax.swing.JLabel etiquetaArticulo2;
    private javax.swing.JLabel etiquetaArticulo3;
    private javax.swing.JLabel etiquetaArticulo4;
    private javax.swing.JLabel etiquetaArticulo5;
    private javax.swing.JLabel etiquetaCodigo;
    private javax.swing.JLabel etiquetaCp;
    private javax.swing.JLabel etiquetaDomicilio;
    private javax.swing.JLabel etiquetaLocalidad;
    private javax.swing.JLabel etiquetaModoModificar;
    private javax.swing.JLabel etiquetaNif;
    private javax.swing.JLabel etiquetaNombre;
    private javax.swing.JLabel etiquetaTotalVentas;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JMenuItem proveedorItem;
    private javax.swing.JTextField textoApellidos;
    private javax.swing.JTextField textoArticulo;
    private javax.swing.JTextField textoCodigo;
    private javax.swing.JTextField textoCp;
    private javax.swing.JTextField textoDescripcion;
    private javax.swing.JTextField textoDomicilio;
    private javax.swing.JTextField textoImporte;
    private javax.swing.JTextField textoLocalidad;
    private javax.swing.JTextField textoNif;
    private javax.swing.JTextField textoNif2;
    private javax.swing.JTextField textoNombre;
    private javax.swing.JTextField textoPrecio;
    private javax.swing.JTextField textoStock;
    private javax.swing.JTextField textoTotal;
    private javax.swing.JTextField textoUnidades;
    private javax.swing.JMenuItem volverItem;
    // End of variables declaration//GEN-END:variables
}
