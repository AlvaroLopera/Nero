package PaqueteReporteFactoryMethod;

public class ReporteFactoryMethod implements Factory{

    public static final String tipoReporteUsuario = "ReporteUsuario";
    public static final String tipoReporteComentario = "ReporteComentario";
    public static final String tipoReporteFuente = "ReporteFuente";
    public Reporte getReporte(String tipoReporte){

        Reporte devolver = null;

        if(tipoReporte != null){
            if(tipoReporte.equalsIgnoreCase(tipoReporteUsuario)){
                devolver = new ReporteUsuario();
            } else if(tipoReporte.equalsIgnoreCase(tipoReporteComentario)){
                devolver = new ReporteComentario();
            } else if(tipoReporte.equalsIgnoreCase(tipoReporteFuente)){
                devolver = new ReporteFuente();
            }
        }

        return devolver;
    }
}
