package repaso;

import ventapcv2.*;

/**
 *
 * @author mario
 */
public class Venta2Examen{
    private String nombre;
    private String localidad;
    private String procesaOpcion;
    private String memoriaOpcion;
    private String monitorOpcion;
    private String discoDuroOpcion;
    private boolean grabadoraDVD;
    private boolean wifi;
    private boolean sintonizadorTV;
    private boolean backUp;

    public Venta2Examen(String nombre, String localidad, String procesaOpcion, String memoriaOpcion, String monitorOpcion, String discoDuroOpcion, boolean grabadoraDVD, boolean wifi, boolean sintonizadorTV, boolean backUp) {
        this.nombre = nombre;
        this.localidad = localidad;
        this.procesaOpcion = procesaOpcion;
        this.memoriaOpcion = memoriaOpcion;
        this.monitorOpcion = monitorOpcion;
        this.discoDuroOpcion = discoDuroOpcion;
        this.grabadoraDVD = grabadoraDVD;
        this.wifi = wifi;
        this.sintonizadorTV = sintonizadorTV;
        this.backUp = backUp;
    }
    //Constructor vacío
    public Venta2Examen (){
        
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getProcesaOpcion() {
        return procesaOpcion;
    }

    public void setProcesaOpcion(String procesaOpcion) {
        this.procesaOpcion = procesaOpcion;
    }

    public String getMemoriaOpcion() {
        return memoriaOpcion;
    }

    public void setMemoriaOpcion(String memoriaOpcion) {
        this.memoriaOpcion = memoriaOpcion;
    }

    public String getMonitorOpcion() {
        return monitorOpcion;
    }

    public void setMonitorOpcion(String monitorOpcion) {
        this.monitorOpcion = monitorOpcion;
    }

    public String getDiscoDuroOpcion() {
        return discoDuroOpcion;
    }

    public void setDiscoDuroOpcion(String discoDuroOpcion) {
        this.discoDuroOpcion = discoDuroOpcion;
    }

    public boolean isGrabadoraDVD() {
        return grabadoraDVD;
    }

    public void setGrabadoraDVD(boolean grabadoraDVD) {
        this.grabadoraDVD = grabadoraDVD;
    }

    public boolean isWifi() {
        return wifi;
    }

    public void setWifi(boolean wifi) {
        this.wifi = wifi;
    }

    public boolean isSintonizadorTV() {
        return sintonizadorTV;
    }

    public void setSintonizadorTV(boolean sintonizadorTV) {
        this.sintonizadorTV = sintonizadorTV;
    }

    public boolean isBackUp() {
        return backUp;
    }

    public void setBackUp(boolean backUp) {
        this.backUp = backUp;
    }

    @Override
    public String toString() {
        return "Nombre = " + nombre + "\n" +
                "Localidad = " + localidad + "\n" +
                "Procesador = " + procesaOpcion + "\n" +
                "Memoria = " + memoriaOpcion + "\n" +
                "Monitor = " + monitorOpcion + "\n" +
                "Disco Duro = " + discoDuroOpcion + "\n" +
                "Grabadora DVD = " + grabadoraDVD + "\n" +
                "Wifi = " + wifi + "\n" +
                "SintonizadorTV = " + sintonizadorTV + "\n" +
                "BackUp = " + backUp + "\n" +
                "--------------";
    }
    
    public String toCSV(){
        return nombre + "," + 
           localidad + "," + 
           procesaOpcion + "," + 
           memoriaOpcion + "," + 
           monitorOpcion + "," + 
           discoDuroOpcion + "," + 
           grabadoraDVD + "," + 
           wifi + "," + 
           sintonizadorTV + "," + 
           backUp;
    } 
}