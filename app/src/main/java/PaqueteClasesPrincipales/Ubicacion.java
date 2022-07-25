package PaqueteClasesPrincipales;

import java.io.Serializable;

public class Ubicacion implements Serializable {

    //Variables de instancia
    private Double latitud;
    private Double longitud;



    public Ubicacion (Double latitud, Double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }



    //Getters para capturar la informacion desde otras clases
    public Double getLatitud() {
        return latitud;
    }

    public Double getLongitud() {
        return longitud;
    }



    //Setters para modificar la informacion desde otras clases
    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }



    public boolean equals (Object o) {
        boolean t = false;
        if (o instanceof Ubicacion) {
            Ubicacion Ubicacion2 = (Ubicacion) o;
            if (latitud.equals(Ubicacion2.getLatitud()) && (longitud.equals(Ubicacion2.getLongitud()))) {
                t = true;
            }
        }

        return t;
    }


    @Override
    public int hashCode() {
        int suma;
        suma = latitud.hashCode() + longitud.hashCode();
        return suma;
    }


    @Override
    public String toString() {
        String cadena = "";
        cadena= "Ubicacion (latitud: " + latitud + ", longitud: " + longitud + ")";
        return cadena;
    }


}

