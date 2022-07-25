package PaqueteReporteFactoryMethod;

import java.util.List;

public interface Reporte<T>{
    boolean estaReportado(Object o);
    void anyadirReporte(Object o);
    List<T> getLista();
}
