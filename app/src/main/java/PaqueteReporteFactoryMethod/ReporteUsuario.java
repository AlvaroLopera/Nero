package PaqueteReporteFactoryMethod;

import PaqueteClasesPrincipales.Usuario;

import java.util.ArrayList;
import java.util.List;

public class ReporteUsuario implements Reporte<Usuario> {
    private List<Usuario> reportados;

    public ReporteUsuario(){
        reportados = new ArrayList<>();
    }

    @Override
    public boolean estaReportado(Object o){

        boolean encontrado = false;
        if(o instanceof Usuario){

            Usuario obj = (Usuario) o;

            int i=0;
            while(!encontrado && i<reportados.size()){
                if(reportados.get(i).equals(obj))
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
        if(o instanceof Usuario){
            Usuario obj = (Usuario) o;
            reportados.add(obj);
        } else {
            throw new ReporteException("Objeto no valido");
        }
    }

    @Override
    public List<Usuario> getLista(){
        return reportados;
    }
}
