package PaqueteClasesPrincipales;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Fuente implements Serializable {
    private int id;
    //private Image foto;
    private String disponibilidad;
    private Usuario creador;
    private Boolean inhabilitado;
    private Ubicacion ubicacion;
    private String nombre;
    private List<Comentario> comentarios;

    //------------CONSTRUCTORES
    public Fuente(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
        comentarios = new ArrayList<>();
    }

    public Fuente(int id, Ubicacion ubicacion){
        this.id = id;
        this.ubicacion = ubicacion;
        comentarios = new ArrayList<>();
    }




    //------------GETTERS
    public Ubicacion getUbicacion() {
        return ubicacion;
    }


    /*public Image getFoto() {
        return foto;
    }*/

    public int getId(){
        return id;
    }

    public String getDisponibilidad() {
        return disponibilidad;
    }


    public Usuario getCreador() {
        return creador;
    }


    public Boolean getInhabilitado() {
        return inhabilitado;
    }



    //------------SETTERS
    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }


    /*public void setFoto(Image foto) {
        this.foto = foto;
    }*/


    public void setDisponibilidad(String disponibilidad) {
        this.disponibilidad = disponibilidad;
    }


    public void setCreador(Usuario creador) {
        this.creador = creador;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getNombre(){
        return this.nombre;
    }

    public void setInhabilitado(Boolean inhabilitado) {
        this.inhabilitado = inhabilitado;
    }



    //-------------OVERRIDE
    @Override
    public boolean equals (Object o) {
        boolean t = false;
        if (o instanceof Fuente) {
            Fuente Fuente2 = (Fuente) o;
            if (this.id == Fuente2.getId()) {
                t = true;
            }
        }

        return t;
    }



    //Metodo que muestra por pantalla los datos de una fuente
    @Override
    public String toString() {
        String cadena;
        cadena = "Fuente en " + ubicacion.toString() + ", con disponibilidad descrita tal que: "
                + disponibilidad + ", creada por el usuario: " + creador;
        if (inhabilitado) {
            cadena += " y que se encuentra inhabilitada actualmente.";
        } else {
            cadena += " y que se encuentra habilitada actualmente.";
        }
        return cadena;
    }


    //-------------GETTERS/SETTERS COMENTARIOS
    public void anyadirComentario(Comentario c){
        comentarios.add(c);
    }

    public List<Comentario> getComentarios(){
        return comentarios;
    }

}
