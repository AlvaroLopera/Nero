package PaqueteClasesPrincipales;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//Se implementa Serializable para que se pueda pasar este objeto entre distintas Activities
public class Usuario implements Serializable {

    private String nombre_usuario;
    private String password;
    private String email;
    private String nombre;
    //private Image foto;
    //private Date fecha;
    private Boolean baneado;
    private List<Comentario> comentarios;
    private List<Fuente> susFuentes;

    //-------------CONSTRUCTORES
    public Usuario(String nombre_usuario, String contra) {
        this.nombre_usuario = nombre_usuario;
        this.password = contra;
        this.email = null;
        this.nombre = null;
        //this.foto = null;
        //this.fecha = null;
        this.baneado = null;
        comentarios = new ArrayList<>();
        susFuentes = new ArrayList<>();
    }

     public Usuario(String nombre_usuario){
        this.nombre_usuario = nombre_usuario;
        comentarios = new ArrayList<>();
        susFuentes = new ArrayList<>();
     }


    //-------------GETTERS

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getNombre() {
        return nombre;
    }

    /*public Image getFoto() {
        return foto;
    }

    public Date getFecha() {
        return fecha;
    }*/

    public Boolean getBaneado() {
        return baneado;
    }


    //-------------SETTERS

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /*public void setFoto(Image foto) {
        this.foto = foto;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }*/

    public void setBaneado(Boolean baneado) {
        this.baneado = baneado;
    }


    //-------------OVERRIDE
    @Override
    public boolean equals(Object o) {
        boolean t = false;
        if (o instanceof Usuario) {
            Usuario otro_usuario = (Usuario) o;
            if (nombre_usuario.equals(otro_usuario.getNombre_usuario())) {
                t = true;
            }
        }
        return t;
    }

    @Override
    public String toString() {
        String cadena = "";
        cadena = ("Usuario con nickname: " + nombre_usuario + ", contrase�a: " + password + ", email: " + email + ", nombre real: " + nombre);
        if (baneado) {
            cadena = cadena + "y baneado";
        } else {
            cadena = cadena + "y no baneado";
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

    //-------------GETTERS/SETTERS FUENTES
    public void anyadirFuente(Fuente f){
        susFuentes.add(f);
    }

    public List<Fuente> getSusFuentes(){
        return susFuentes;
    }
}