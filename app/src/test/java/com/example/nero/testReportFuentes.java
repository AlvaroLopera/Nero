package com.example.nero;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import PaqueteClasesPrincipales.Comentario;
import PaqueteReporteFactoryMethod.ReporteException;
import PaqueteReporteFactoryMethod.ReporteFuente;




import PaqueteClasesPrincipales.Fuente;
import PaqueteClasesPrincipales.Ubicacion;
import PaqueteClasesPrincipales.Usuario;
import PaqueteReporteFactoryMethod.ReporteFactoryMethod;
import PaqueteReporteFactoryMethod.ReporteFuente;
import PaqueteReporteFactoryMethod.ReporteFuente;

import static org.junit.Assert.assertEquals;

public class testReportFuentes {

    ReporteFuente rf;

    @Before
    public void init() {
        rf = new ReporteFuente();
    }
    @After
    public void terminate() {
        rf = null;
    }

    @Test
    public void comprobarSiListaVacia(){
        Fuente fuente = mock(Fuente.class);
        assertEquals(0,rf.getLista().size());
    }

    @Test(expected = ReporteException.class)
    public void anyadirObjetoDeOtroTipoNoValido(){
        //Probamos con la clase Usuario pero podria probarse tambien con Comentario
        Usuario invalido = mock(Usuario.class);
        rf.anyadirReporte(invalido);
    }

    @Test
    public void anyadirObjetoDeEsteTipoValido(){
        Fuente fuente = mock(Fuente.class);
        try{
            rf.anyadirReporte(fuente);
        }catch(ReporteException ex){
            fail("No deberia lanzar excepcion");
        }
    }

    @Test
    public void comprobarSiEstaReportadoUnReporteValido(){
        Fuente fuente = mock(Fuente.class);
        when(fuente.getId()).thenReturn(200);

        assertEquals(false, rf.estaReportado(fuente));
        rf.anyadirReporte(fuente);
        assertEquals(true, rf.estaReportado(fuente));
    }

    @Test(expected = ReporteException.class)
    public void comprobarSiEstaReportadoUnReporteNoValido(){
        Usuario usuario = mock(Usuario.class);
        rf.anyadirReporte(usuario);
    }

}
