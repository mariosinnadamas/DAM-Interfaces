package ventapcv2;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import ventapc.*;

/**
 *
 * @author mario
 */
public class Venta2 implements Externalizable{
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

    public Venta2(String nombre, String localidad, String procesaOpcion, String memoriaOpcion, String monitorOpcion, String discoDuroOpcion, boolean grabadoraDVD, boolean wifi, boolean sintonizadorTV, boolean backUp) {
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
    public Venta2 (){
        
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
                "BackUp = " + backUp;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(nombre);
        out.writeUTF(localidad);
        out.writeUTF(procesaOpcion);
        out.writeUTF(memoriaOpcion);
        out.writeUTF(monitorOpcion);
        out.writeUTF(discoDuroOpcion);
        out.writeBoolean(grabadoraDVD);
        out.writeBoolean(wifi);
        out.writeBoolean(sintonizadorTV);
        out.writeBoolean(backUp);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        nombre = in.readUTF();
        localidad = in.readUTF();
        procesaOpcion = in.readUTF();
        memoriaOpcion = in.readUTF();
        monitorOpcion = in.readUTF();
        discoDuroOpcion = in.readUTF();
        grabadoraDVD = in.readBoolean();
        wifi = in.readBoolean();
        sintonizadorTV = in.readBoolean();
        backUp = in.readBoolean();
    }
}