package ventapcv2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author mario
 */
public class VentaPCMenu2 extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VentaPCMenu2.class.getName());
    
    //Arraylist en la que almacenaré las ventas
    ArrayList<Venta2>listaVentas = new ArrayList();
    
    //Modelo de lista ventas para poder manipular datos en el JList
    DefaultListModel<String> modeloListaVentas = new DefaultListModel<>();
    
    public VentaPCMenu2() {
        initComponents();
        //Evito que puedan modificar el tamaño de la ventana
        this.setResizable(false);
        
        //Desactivar todos los botones, asigno el foco y establezco las opciones predeterminadas
        resetFormulario();
        
        //Asignar ActionCommand a cada boton
        ProcesaOpcionA.setActionCommand("P4 3.0 Gb");
        ProcesaOpcionB.setActionCommand("P4 3.2 Gb");
        ProcesaOpcionC.setActionCommand("P4 Celeron");
        ProcesaOpcionD.setActionCommand("AMD 650");
        
        MemoriaOpcionA.setActionCommand("128 Mb");
        MemoriaOpcionB.setActionCommand("256 Mb");
        MemoriaOpcionC.setActionCommand("512 Mb");
        MemoriaOpcionD.setActionCommand("1024 Mb");
        
        MonitorOpcionA.setActionCommand("15''");
        MonitorOpcionB.setActionCommand("17''");
        MonitorOpcionC.setActionCommand("TFT 15''");
        MonitorOpcionD.setActionCommand("TFT 17''");
        
        DiscoDuroOpcionA.setActionCommand("60 Gb");
        DiscoDuroOpcionB.setActionCommand("80 Gb");
        DiscoDuroOpcionC.setActionCommand("120 Gb");
        DiscoDuroOpcionD.setActionCommand("200 Gb");
        
        //Añado la lista como modelo para poder agregar elementos después
        listaClientes.setModel(modeloListaVentas);
        
        //Evito que se pueda cerrar el formulario con la X
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
    }
    
    //Metodo para modificar los botones de Radio
    private void estadoRadioButtonGroup(boolean activar){
        //Array con los grupos
        ButtonGroup[] grupos = {GrupoProcesador,GrupoMemoria, GrupoDiscoDuro,GrupoMonitor};
        //Recorro el array
        for (ButtonGroup grupo: grupos){
            //Creo una lista de botones del grupo seleccionado
            Enumeration<AbstractButton> botones = grupo.getElements();
            while(botones.hasMoreElements()){
                //Creo un abstractButton y lo activo
                AbstractButton b = botones.nextElement();
                b.setEnabled(activar);
            }
        }
    }
    
    //Metodo para activar todos los botones del formulario menos eliminar
    private void activarTodo(){
        comboLocalidad.setEnabled(true);
        BotonAniadir.setEnabled(true);
        BotonBuscar.setEnabled(true);
        CheckWifi.setEnabled(true);
        CheckBackUp.setEnabled(true);
        CheckGrabadora.setEnabled(true);
        CheckSinto.setEnabled(true);
        estadoRadioButtonGroup(true);
    }
    
    //Metodo para establecer las opciones predeterminadas
    private void opcionesPredeterminadas(){
        ProcesaOpcionA.setSelected(true);
        MemoriaOpcionD.setSelected(true);
        MonitorOpcionD.setSelected(true);
        DiscoDuroOpcionD.setSelected(true);
        comboLocalidad.setSelectedIndex(0);
        CheckGrabadora.setSelected(true);
        CheckWifi.setSelected(true);
        CheckSinto.setSelected(false);
        CheckBackUp.setSelected(false);
    }
    
    //Metodo para resetear el formulario
    private void resetFormulario(){
        textoNombre.setText("");
        textoNombre.setEnabled(true);
        BotonAniadir.setEnabled(false);
        BotonBuscar.setEnabled(false);
        BotonEliminar.setEnabled(false);
        CheckWifi.setEnabled(false);
        CheckBackUp.setEnabled(false);
        CheckGrabadora.setEnabled(false);
        CheckSinto.setEnabled(false);
        comboLocalidad.setEnabled(false);
        estadoRadioButtonGroup(false);
        opcionesPredeterminadas();
        textoNombre.requestFocus();
    }
    
    //Metodo para seleccionar los RadioButons de un cliente seleccionado
    private void seleccionarRadioButtons(ButtonGroup grupo, String actionCommand){
        //Hago una lista de botones del grupo pasado por parámetro
        Enumeration<AbstractButton> botones = grupo.getElements();
        while (botones.hasMoreElements()) {
            //Creo un boton
            AbstractButton b = botones.nextElement();
            //Si coincide el action comand del boton con el pasado por parámetro selecciono el botón
            if (b.getActionCommand().equals(actionCommand)) {
                b.setSelected(true);
            }
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GrupoProcesador = new javax.swing.ButtonGroup();
        GrupoMemoria = new javax.swing.ButtonGroup();
        GrupoMonitor = new javax.swing.ButtonGroup();
        GrupoDiscoDuro = new javax.swing.ButtonGroup();
        EtiquetaClientes = new javax.swing.JLabel();
        textoNombre = new javax.swing.JTextField();
        EtiquetaNombre = new javax.swing.JLabel();
        EtiquetaLocalidad = new javax.swing.JLabel();
        comboLocalidad = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        listaClientes = new javax.swing.JList<>();
        EtiquetaProcesador = new javax.swing.JLabel();
        EtiquetaMemoria = new javax.swing.JLabel();
        EtiquetaMonitor = new javax.swing.JLabel();
        EtiquetaDiscoDuro = new javax.swing.JLabel();
        EtiquetaDiscoDuro1 = new javax.swing.JLabel();
        ProcesaOpcionA = new javax.swing.JRadioButton();
        ProcesaOpcionB = new javax.swing.JRadioButton();
        ProcesaOpcionC = new javax.swing.JRadioButton();
        ProcesaOpcionD = new javax.swing.JRadioButton();
        MemoriaOpcionA = new javax.swing.JRadioButton();
        MemoriaOpcionB = new javax.swing.JRadioButton();
        MemoriaOpcionC = new javax.swing.JRadioButton();
        MemoriaOpcionD = new javax.swing.JRadioButton();
        MonitorOpcionD = new javax.swing.JRadioButton();
        MonitorOpcionA = new javax.swing.JRadioButton();
        MonitorOpcionB = new javax.swing.JRadioButton();
        MonitorOpcionC = new javax.swing.JRadioButton();
        DiscoDuroOpcionD = new javax.swing.JRadioButton();
        DiscoDuroOpcionA = new javax.swing.JRadioButton();
        DiscoDuroOpcionB = new javax.swing.JRadioButton();
        DiscoDuroOpcionC = new javax.swing.JRadioButton();
        CheckGrabadora = new javax.swing.JCheckBox();
        CheckWifi = new javax.swing.JCheckBox();
        CheckSinto = new javax.swing.JCheckBox();
        CheckBackUp = new javax.swing.JCheckBox();
        BotonAniadir = new javax.swing.JButton();
        BotonBuscar = new javax.swing.JButton();
        BotonEliminar = new javax.swing.JButton();
        BotonCancelar = new javax.swing.JButton();
        BotonSalir = new javax.swing.JButton();
        BotonMostrarVentas = new javax.swing.JButton();
        BotonGuardarVentas = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Venta de ordenadores");
        setName("Venta de ordenadores"); // NOI18N

        EtiquetaClientes.setText("Lista de clientes");

        textoNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textoNombreActionPerformed(evt);
            }
        });

        EtiquetaNombre.setText("Nombre del cliente");

        EtiquetaLocalidad.setText("Localidad");

        comboLocalidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Villalba", "Alpedrete", "Galapagar", "Guadarrama", "Moralzarzal" }));

        listaClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                listaClientesMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(listaClientes);

        EtiquetaProcesador.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        EtiquetaProcesador.setText("Procesador");

        EtiquetaMemoria.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        EtiquetaMemoria.setText("Memoria");

        EtiquetaMonitor.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        EtiquetaMonitor.setText("Monitor");

        EtiquetaDiscoDuro.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        EtiquetaDiscoDuro.setText("DiscoDuro");

        EtiquetaDiscoDuro1.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        EtiquetaDiscoDuro1.setText("Opciones");

        GrupoProcesador.add(ProcesaOpcionA);
        ProcesaOpcionA.setText("P4 3.0 Gb");

        GrupoProcesador.add(ProcesaOpcionB);
        ProcesaOpcionB.setText("P4 3.2 Gb");

        GrupoProcesador.add(ProcesaOpcionC);
        ProcesaOpcionC.setText("P4 Celeron");

        GrupoProcesador.add(ProcesaOpcionD);
        ProcesaOpcionD.setText("AMD 650");

        GrupoMemoria.add(MemoriaOpcionA);
        MemoriaOpcionA.setText("128 Mb");

        GrupoMemoria.add(MemoriaOpcionB);
        MemoriaOpcionB.setText("256 Mb");

        GrupoMemoria.add(MemoriaOpcionC);
        MemoriaOpcionC.setText("512 Mb");

        GrupoMemoria.add(MemoriaOpcionD);
        MemoriaOpcionD.setText("1024 Mb");

        GrupoMonitor.add(MonitorOpcionD);
        MonitorOpcionD.setText("TFT 17''");

        GrupoMonitor.add(MonitorOpcionA);
        MonitorOpcionA.setText("15''");

        GrupoMonitor.add(MonitorOpcionB);
        MonitorOpcionB.setText("17''");

        GrupoMonitor.add(MonitorOpcionC);
        MonitorOpcionC.setText("TFT 15''");

        GrupoDiscoDuro.add(DiscoDuroOpcionD);
        DiscoDuroOpcionD.setText("200 Gb");

        GrupoDiscoDuro.add(DiscoDuroOpcionA);
        DiscoDuroOpcionA.setText("60 Gb");

        GrupoDiscoDuro.add(DiscoDuroOpcionB);
        DiscoDuroOpcionB.setText("80 Gb");

        GrupoDiscoDuro.add(DiscoDuroOpcionC);
        DiscoDuroOpcionC.setText("120 Gb");

        CheckGrabadora.setSelected(true);
        CheckGrabadora.setText("Grabadora DVD");

        CheckWifi.setSelected(true);
        CheckWifi.setText("Wifi");

        CheckSinto.setText("Sintonizador TV");

        CheckBackUp.setText("Backup/Restore");

        BotonAniadir.setText("Añadir");
        BotonAniadir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonAniadirActionPerformed(evt);
            }
        });

        BotonBuscar.setText("Buscar");
        BotonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonBuscarActionPerformed(evt);
            }
        });

        BotonEliminar.setText("Eliminar");
        BotonEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonEliminarActionPerformed(evt);
            }
        });

        BotonCancelar.setText("Cancelar");
        BotonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonCancelarActionPerformed(evt);
            }
        });

        BotonSalir.setText("Salir");
        BotonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonSalirActionPerformed(evt);
            }
        });

        BotonMostrarVentas.setText("Mostrar Ventas");
        BotonMostrarVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonMostrarVentasActionPerformed(evt);
            }
        });

        BotonGuardarVentas.setText("Guardar Ventas");
        BotonGuardarVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonGuardarVentasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(BotonAniadir, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BotonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BotonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(BotonMostrarVentas)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BotonGuardarVentas)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(BotonCancelar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(BotonSalir, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ProcesaOpcionC)
                                    .addComponent(EtiquetaProcesador)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(128, 128, 128)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(EtiquetaMemoria)
                                            .addComponent(MemoriaOpcionA)
                                            .addComponent(MemoriaOpcionB)
                                            .addComponent(MemoriaOpcionC)
                                            .addComponent(MemoriaOpcionD))
                                        .addGap(39, 39, 39)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(EtiquetaMonitor)
                                            .addComponent(MonitorOpcionD)
                                            .addComponent(MonitorOpcionA)
                                            .addComponent(MonitorOpcionB)
                                            .addComponent(MonitorOpcionC))
                                        .addGap(40, 40, 40)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(EtiquetaDiscoDuro)
                                            .addComponent(DiscoDuroOpcionA)
                                            .addComponent(DiscoDuroOpcionB)
                                            .addComponent(DiscoDuroOpcionC)
                                            .addComponent(DiscoDuroOpcionD)))
                                    .addComponent(ProcesaOpcionA)
                                    .addComponent(ProcesaOpcionB)
                                    .addComponent(ProcesaOpcionD))))
                        .addContainerGap(15, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(CheckSinto)
                                .addComponent(CheckWifi)
                                .addComponent(CheckGrabadora)
                                .addComponent(EtiquetaDiscoDuro1)
                                .addComponent(CheckBackUp))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(EtiquetaNombre)
                                    .addComponent(EtiquetaLocalidad))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(textoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(88, 88, 88)
                                        .addComponent(EtiquetaClientes))
                                    .addComponent(comboLocalidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(EtiquetaClientes)
                            .addComponent(textoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(EtiquetaNombre))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(EtiquetaLocalidad)
                            .addComponent(comboLocalidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(EtiquetaProcesador)
                            .addComponent(EtiquetaMemoria)
                            .addComponent(EtiquetaMonitor)
                            .addComponent(EtiquetaDiscoDuro))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(ProcesaOpcionA)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ProcesaOpcionB)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ProcesaOpcionC)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ProcesaOpcionD))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(MemoriaOpcionA)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(MemoriaOpcionB)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(MemoriaOpcionC)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(MemoriaOpcionD))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(MonitorOpcionA)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(MonitorOpcionB)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(MonitorOpcionC)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(MonitorOpcionD))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(DiscoDuroOpcionA)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(DiscoDuroOpcionB)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(DiscoDuroOpcionC)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(DiscoDuroOpcionD))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(EtiquetaDiscoDuro1)
                        .addGap(18, 18, 18)
                        .addComponent(CheckGrabadora)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CheckWifi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CheckSinto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CheckBackUp)))
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BotonAniadir, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BotonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BotonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BotonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BotonMostrarVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BotonGuardarVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BotonSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void textoNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoNombreActionPerformed
        String texto = textoNombre.getText();
        //Me aseguro de que no tenga simbología y tenga de 1 a 15 carácteres
        if (!texto.matches("^[a-zA-ZÁÉÍÓÚáéíóúÑñ ]{1,15}$")){
            JOptionPane.showMessageDialog(this, "El nombre no es válido. \n" + 
                    "Debe contener solo letras y un máximo de 15 carácteres", "Error de validación", JOptionPane.ERROR_MESSAGE);
            textoNombre.setText("");
            resetFormulario(); //Por si ha introducido un nombre válido y luego uno mal
        } else{
            opcionesPredeterminadas();
            activarTodo();
            comboLocalidad.requestFocus();
            textoNombre.setEnabled(false);
        }
    }//GEN-LAST:event_textoNombreActionPerformed
    
    private void BotonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonEliminarActionPerformed
        //Elimino por índice porque si lo hago por nombre puedo eliminar varias ventas del mismo cliente sin querer
        int indice = listaClientes.getSelectedIndex();
        if (indice == -1) {
            JOptionPane.showMessageDialog(this, "No hay ningún cliente seleccionado", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int dec = JOptionPane.showConfirmDialog(this, 
                "¿Seguro que quieres eliminar la venta seleccionada?", 
                "Confirmar eliminar venta", 
                JOptionPane.YES_NO_OPTION);
        
        if (dec == JOptionPane.YES_OPTION) {
            //Elimino de ambas listas
            listaVentas.remove(indice);
            modeloListaVentas.remove(indice);
            
            //Reseteo el formulario
            resetFormulario();
        }
    }//GEN-LAST:event_BotonEliminarActionPerformed

    private void BotonAniadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonAniadirActionPerformed
        //Creo variables y guardo los valores dentro
        String nombre = textoNombre.getText();
        String localidad = comboLocalidad.getSelectedItem().toString();
        String procesador = GrupoProcesador.getSelection().getActionCommand();
        String memoria = GrupoMemoria.getSelection().getActionCommand();
        String monitor = GrupoMonitor.getSelection().getActionCommand();
        String hdd = GrupoDiscoDuro.getSelection().getActionCommand();
        boolean grabadora=CheckGrabadora.isSelected();
        boolean wifi = CheckWifi.isSelected();
        boolean sintonizador = CheckSinto.isSelected();
        boolean backup = CheckBackUp.isSelected();
        
        //Creo el objeto
        Venta2 venta = new Venta2(nombre,localidad,procesador,memoria,monitor,hdd,grabadora,wifi,sintonizador,backup);
        
        //Lo añado a la lista
        listaVentas.add(venta);
        
        //Añado el elemento al modelo para que se vea en el JList
        modeloListaVentas.addElement(nombre);
        
        //Reseteo el formulario
        resetFormulario();
    }//GEN-LAST:event_BotonAniadirActionPerformed

    private void BotonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonBuscarActionPerformed
        String nombre = textoNombre.getText();
        
        //Creo una nueva lista por si hay varias ventas de un mismo cliente
        ArrayList<Venta2> listaClientesRepe = new ArrayList();
        
        //Añado los clientes que coincidan con el nombre del JText a la nueva lista
        for (Venta2 temp : listaVentas) {
            if (nombre.matches(temp.getNombre())) {
                listaClientesRepe.add(temp);
            }
        }
        
        //Si la lista nueva esta vacía significa que no hay nadie
        if (listaClientesRepe.isEmpty()){
            JOptionPane.showMessageDialog(this, "No se ha encontrado ninguna compra a nombre de " + nombre);
        }
        
        //Recorro la lista nueva mostrando una ventana en cada caso que coincida la venta
        int contador = 0;
        boolean seguirBuscando = true;
        
        //Desactivo y activo todo lo necesario
        BotonAniadir.setEnabled(false);
        estadoRadioButtonGroup(false);
        CheckWifi.setEnabled(false);
        CheckBackUp.setEnabled(false);
        CheckGrabadora.setEnabled(false);
        CheckSinto.setEnabled(false);
        comboLocalidad.setEnabled(false);
        textoNombre.setEnabled(true);
        
        while (seguirBuscando && contador < listaClientesRepe.size()) {
            Venta2 v = listaClientesRepe.get(contador);
            
            //Selecciono los elementos
            comboLocalidad.setSelectedItem(v.getLocalidad());
            seleccionarRadioButtons(GrupoProcesador,v.getProcesaOpcion());
            seleccionarRadioButtons(GrupoMemoria,v.getMemoriaOpcion());
            seleccionarRadioButtons(GrupoMonitor,v.getMonitorOpcion());
            seleccionarRadioButtons(GrupoDiscoDuro,v.getDiscoDuroOpcion());
            CheckGrabadora.setSelected(v.isGrabadoraDVD());
            CheckWifi.setSelected(v.isWifi());
            CheckSinto.setSelected(v.isSintonizadorTV());
            CheckBackUp.setSelected(v.isBackUp());
            
            contador++;

            // Si todavía quedan más ventas, pregunto si quiere seguir
            if (contador < listaClientesRepe.size()) {
                int respuesta = JOptionPane.showConfirmDialog(
                    this,
                    "¿Desea ver la siguiente venta del cliente?",
                    "Continuar búsqueda" + " (" + contador + "/"+listaClientesRepe.size() + ")",
                    JOptionPane.YES_NO_OPTION
                );

                if (respuesta != JOptionPane.YES_OPTION) {
                    seguirBuscando = false; // sale del bucle
                }
            } else {
                JOptionPane.showMessageDialog(this, "No hay más ventas de este cliente.");
            }
        }
    }//GEN-LAST:event_BotonBuscarActionPerformed

    private void listaClientesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaClientesMousePressed
        //Activo boton eliminar cuando selecciona un cliente y desactivo añadir y buscar
        BotonEliminar.setEnabled(true);
        BotonAniadir.setEnabled(false);
        BotonBuscar.setEnabled(false);
        
        //Almaceno el índice del JList seleccionado
        int indice = listaClientes.getSelectedIndex();
        if(indice == -1)return;
        
        //Como mi JList y mi ArrayList estan parejos puedo simplemente seleccionar el índice
        Venta2 temp = listaVentas.get(indice);
        
        //Introduzco el nombre del cliente de la venta seleccionada en el JTextField
        textoNombre.setText(listaClientes.getSelectedValue());
        
        //Selecciono las opciones de la venta
        comboLocalidad.setSelectedItem(temp.getLocalidad());
        seleccionarRadioButtons(GrupoProcesador,temp.getProcesaOpcion());
        seleccionarRadioButtons(GrupoMemoria,temp.getMemoriaOpcion());
        seleccionarRadioButtons(GrupoMonitor,temp.getMonitorOpcion());
        seleccionarRadioButtons(GrupoDiscoDuro,temp.getDiscoDuroOpcion());
        CheckGrabadora.setSelected(temp.isGrabadoraDVD());
        CheckWifi.setSelected(temp.isWifi());
        CheckSinto.setSelected(temp.isSintonizadorTV());
        CheckBackUp.setSelected(temp.isBackUp());
        
        //Desactivo los radioButtons y checkbox para que no puedan ser modificados
        estadoRadioButtonGroup(false);
        CheckWifi.setEnabled(false);
        CheckBackUp.setEnabled(false);
        CheckGrabadora.setEnabled(false);
        CheckSinto.setEnabled(false);
        comboLocalidad.setEnabled(false);
        
    }//GEN-LAST:event_listaClientesMousePressed

    private void BotonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonCancelarActionPerformed
        resetFormulario();
    }//GEN-LAST:event_BotonCancelarActionPerformed

    private void BotonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonSalirActionPerformed
        int respuesta = JOptionPane.showConfirmDialog(this, 
                "¿Desea salir del formulario?", 
                "Confirmar salida", 
                JOptionPane.YES_NO_OPTION);
        
        if (respuesta == JOptionPane.YES_OPTION) {
            System.exit(0); //Cierra todo
        }
    }//GEN-LAST:event_BotonSalirActionPerformed

    private void BotonMostrarVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonMostrarVentasActionPerformed
        
        File fichero = new File ("recursos/ventas.csv");
        
        if (!fichero.exists()) {
            JOptionPane.showMessageDialog(this, "Error, el fichero no existe", "Documento no encontrado", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        //Limpio las listas antes de cargar los datos
        listaVentas.clear();
        modeloListaVentas.clear();
        
        String [] datos;
        String linea = "";
        boolean primeraLinea = true;
        
        try (BufferedReader br = new BufferedReader(new FileReader(fichero))){
            while ((linea=br.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }
                if (linea.trim().isEmpty()) {
                    continue;
                }
                datos = linea.split(",");
                if (datos.length >=10) {
                    String nombre = datos[0].trim();
                    String localidad = datos[1].trim();
                    String procesaOpcion = datos[2].trim();
                    String memoriaOpcion = datos[3].trim();
                    String monitorOpcion = datos[4].trim();
                    String discoDuroOpcion = datos[5].trim();
                    boolean grabadoraDVD = Boolean.parseBoolean(datos[6].trim());
                    boolean wifi = Boolean.parseBoolean(datos[7].trim());
                    boolean sintonizadorTV = Boolean.parseBoolean(datos[8].trim());
                    boolean backUp = Boolean.parseBoolean(datos[9].trim());
                    Venta2 v = new Venta2(nombre, localidad, procesaOpcion, memoriaOpcion, monitorOpcion, discoDuroOpcion, grabadoraDVD, wifi, sintonizadorTV, backUp);
                    System.out.println(v);
                    listaVentas.add(v);
                    modeloListaVentas.addElement(v.getNombre());
                } else{
                    JOptionPane.showMessageDialog(this, "Ha habido un error, no coinciden la cantidad de campos", "Error en la lectura del CSV", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_BotonMostrarVentasActionPerformed

    private void BotonGuardarVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonGuardarVentasActionPerformed
        
        File directorio = new File("recursos");

        if (listaVentas == null || listaVentas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Error, no hay nada que guardar, la lista está vacía", "Lista Vacía", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!directorio.exists()) {
            directorio.mkdirs();
        }

        File fichero = new File("recursos/ventas.csv");

        ArrayList<Venta2> ventasTotales = new ArrayList<>();

        // Leo las ventas que ya están en el CSV (si existe)
        if (fichero.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(fichero))) {

                String linea;
                boolean primeraLinea = true;

                while ((linea = br.readLine()) != null) {
                    if (primeraLinea) { 
                        primeraLinea = false;
                        continue;
                    }
                    if (linea.trim().isEmpty()) continue;

                    String[] datos = linea.split(",");
                    if (datos.length >= 10) {
                        ventasTotales.add(new Venta2(
                            datos[0].trim(), datos[1].trim(), datos[2].trim(), datos[3].trim(),
                            datos[4].trim(), datos[5].trim(),
                            Boolean.parseBoolean(datos[6].trim()),
                            Boolean.parseBoolean(datos[7].trim()),
                            Boolean.parseBoolean(datos[8].trim()),
                            Boolean.parseBoolean(datos[9].trim())
                        ));
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Añado solo las ventas nuevas que NO estaban antes
        for (Venta2 nueva : listaVentas) {
            boolean existe = false;

            for (Venta2 v : ventasTotales) {
                if (v.toCSV().equals(nueva.toCSV())) {
                    existe = true;
                    break;
                }
            }

            if (!existe) {
                ventasTotales.add(nueva);
            }
        }

        //Escribo todo el contenido
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fichero))) {
            
            bw.write("Nombre,Localidad,Procesador,Memoria,Monitor,HDD,GrabadoraDVD,WIFI,SintonizadorTV,BackUp");
            bw.newLine();
            
            for (Venta2 v : ventasTotales) {
                bw.write(v.toCSV());
                bw.newLine();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        listaVentas.clear();
        modeloListaVentas.clear();
       
    }//GEN-LAST:event_BotonGuardarVentasActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new VentaPCMenu2().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BotonAniadir;
    private javax.swing.JButton BotonBuscar;
    private javax.swing.JButton BotonCancelar;
    private javax.swing.JButton BotonEliminar;
    private javax.swing.JButton BotonGuardarVentas;
    private javax.swing.JButton BotonMostrarVentas;
    private javax.swing.JButton BotonSalir;
    private javax.swing.JCheckBox CheckBackUp;
    private javax.swing.JCheckBox CheckGrabadora;
    private javax.swing.JCheckBox CheckSinto;
    private javax.swing.JCheckBox CheckWifi;
    private javax.swing.JRadioButton DiscoDuroOpcionA;
    private javax.swing.JRadioButton DiscoDuroOpcionB;
    private javax.swing.JRadioButton DiscoDuroOpcionC;
    private javax.swing.JRadioButton DiscoDuroOpcionD;
    private javax.swing.JLabel EtiquetaClientes;
    private javax.swing.JLabel EtiquetaDiscoDuro;
    private javax.swing.JLabel EtiquetaDiscoDuro1;
    private javax.swing.JLabel EtiquetaLocalidad;
    private javax.swing.JLabel EtiquetaMemoria;
    private javax.swing.JLabel EtiquetaMonitor;
    private javax.swing.JLabel EtiquetaNombre;
    private javax.swing.JLabel EtiquetaProcesador;
    private javax.swing.ButtonGroup GrupoDiscoDuro;
    private javax.swing.ButtonGroup GrupoMemoria;
    private javax.swing.ButtonGroup GrupoMonitor;
    private javax.swing.ButtonGroup GrupoProcesador;
    private javax.swing.JRadioButton MemoriaOpcionA;
    private javax.swing.JRadioButton MemoriaOpcionB;
    private javax.swing.JRadioButton MemoriaOpcionC;
    private javax.swing.JRadioButton MemoriaOpcionD;
    private javax.swing.JRadioButton MonitorOpcionA;
    private javax.swing.JRadioButton MonitorOpcionB;
    private javax.swing.JRadioButton MonitorOpcionC;
    private javax.swing.JRadioButton MonitorOpcionD;
    private javax.swing.JRadioButton ProcesaOpcionA;
    private javax.swing.JRadioButton ProcesaOpcionB;
    private javax.swing.JRadioButton ProcesaOpcionC;
    private javax.swing.JRadioButton ProcesaOpcionD;
    private javax.swing.JComboBox<String> comboLocalidad;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> listaClientes;
    private javax.swing.JTextField textoNombre;
    // End of variables declaration//GEN-END:variables
}
