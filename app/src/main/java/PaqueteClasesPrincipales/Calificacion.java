package PaqueteClasesPrincipales;

public class Calificacion {
    private Usuario thisUser;
    private Fuente thisFuente;
    private int nota;

    public Calificacion(Usuario esteUsuario, Fuente estaFuente){
        thisFuente = estaFuente;
        thisUser = esteUsuario;

    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public int getNota() {
        return nota;
    }
}
