package ventapc;

import java.util.List;

/**
 *
 * @author mario
 */
public class Venta {
    private String nombre;
    private String localidad;
    private String procesaOpcion;
    private String memoriaOpcion;
    private String monitorOpcion;
    private String discoDuroOpcion;
    private List<String> opciones;

    public Venta(String nombre, String localidad, String procesaOpcion, String memoriaOpcion, String monitorOpcion, String discoDuroOpcion, List<String> opciones) {
        this.nombre = nombre;
        this.localidad = localidad;
        this.procesaOpcion = procesaOpcion;
        this.memoriaOpcion = memoriaOpcion;
        this.monitorOpcion = monitorOpcion;
        this.discoDuroOpcion = discoDuroOpcion;
        this.opciones = opciones;
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

    public List<String> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<String> opciones) {
        this.opciones = opciones;
    }

    @Override
    public String toString() {
        return "Venta{" + "nombre=" + nombre + 
                ", localidad=" + localidad + 
                ", procesaOpcion=" + procesaOpcion + 
                ", memoriaOpcion=" + memoriaOpcion + 
                ", monitorOpcion=" + monitorOpcion + 
                ", discoDuroOpcion=" + discoDuroOpcion + 
                ", opciones=" + opciones + '}';
    }
}