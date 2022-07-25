package PaqueteReporteFactoryMethod;

import PaqueteClasesPrincipales.Comentario;

import java.util.ArrayList;
import java.util.List;

public class ReporteComentario implements Reporte<Comentario> {
    private List<Comentario> comentariosReportados;

    public ReporteComentario(){
        comentariosReportados = new ArrayList<>();
    }

    @Override
    public boolean estaReportado(Object o){

        boolean encontrado = false;

        if(o instanceof Comentario){

            Comentario obj = (Comentario) o;

            int i=0;
            while(!encontrado && i<comentariosReportados.size()){
                if(comentariosReportados.get(i).equals(obj))
                    encontrado = true;
                i++;
            }

        } else {
            throw new ReporteException("Objeto no valido");
        }

        return encontrado;
    }

    @Override
    public void anyadirReporte(Object o){
        if(o instanceof Comentario){
            Comentario obj = (Comentario) o;
            comentariosReportados.add(obj);
        } else {
            throw new ReporteException("Objeto no valido");
        }
    }

    @Override
    public List<Comentario> getLista(){
        return comentariosReportados;
    }

}
