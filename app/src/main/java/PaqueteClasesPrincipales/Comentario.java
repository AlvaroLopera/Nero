package PaqueteClasesPrincipales;

import java.io.Serializable;

import PaqueteClasesPrincipales.Usuario;

public class Comentario implements Serializable {
    private int id;
    private Usuario creador;
    private String texto;
    private String fecha;
    private Fuente suFuente;

    public Comentario(int id, Usuario creator){
        this.id = id;
        creador = creator;
        suFuente = null;
        texto = null;
    }

    public Usuario getCreador() {
        return creador;
    }

    public String getTexto() {
        return texto;
    }


    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public Fuente getSuFuente(){
        return  suFuente;
    }

    public void setSuFuente(Fuente o){
        suFuente = o;
    }

    @Override
    public boolean equals(Object o) {
        boolean t = false;
        if (o instanceof Comentario) {
            Comentario otroComentario = (Comentario) o;
            if (this.id == otroComentario.getId()) {
                t = true;
            }
        }
        return t;
    }
}
