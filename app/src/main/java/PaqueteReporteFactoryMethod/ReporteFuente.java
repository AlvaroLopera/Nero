package PaqueteReporteFactoryMethod;

import PaqueteClasesPrincipales.Fuente;

import java.util.ArrayList;
import java.util.List;

public class ReporteFuente implements Reporte<Fuente> {
    private List<Fuente> fuentesReportadas;

    public ReporteFuente(){
        fuentesReportadas = new ArrayList<>();
    }

    @Override
    public boolean estaReportado(Object o){
        boolean encontrado = false;

        if(o instanceof Fuente){

            Fuente obj = (Fuente) o;

            int i=0;
            while(!encontrado && i<fuentesReportadas.size()){
                if(fuentesReportadas.get(i).equals(obj))
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
        if(o instanceof Fuente){
            Fuente obj = (Fuente) o;
            fuentesReportadas.add(obj);
        } else {
            throw new ReporteException("Objeto no valido");
        }
    }

    @Override
    public List<Fuente> getLista(){
        return fuentesReportadas;
    }
}
