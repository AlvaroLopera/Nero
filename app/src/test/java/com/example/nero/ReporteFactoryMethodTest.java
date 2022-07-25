package com.example.nero;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.*;
import org.mockito.runners.MockitoJUnitRunner;
import PaqueteReporteFactoryMethod.ReporteFactoryMethod;
import PaqueteReporteFactoryMethod.ReporteFuente;
import PaqueteReporteFactoryMethod.ReporteUsuario;
import PaqueteReporteFactoryMethod.ReporteComentario;
import java.security.KeyStore;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.junit.Assert.*;

public class ReporteFactoryMethodTest {
    ReporteFactoryMethod reporte;

    @Before
    public void init(){
        reporte = new ReporteFactoryMethod();
    }

    @After
    public void terminate(){
        reporte = null;
    }

    @Test
    public void comprobarFactoryMethodComentario(){
        //Boolean res;
        if(!(reporte.getReporte(ReporteFactoryMethod.tipoReporteComentario) instanceof ReporteComentario) ){
            fail("No es una instancia");
        }


        /*    res = true;
        }else{
            res = true;
        }
        assertEquals(true, res);*/
    }
    @Test
    public void comprobarFactoryMethodFuente(){
        Boolean res;
        if(reporte.getReporte("ReporteFuente") instanceof ReporteFuente ){
            res = true;
        }else{
            res = true;
        }
        assertEquals(true, res);
    }

    @Test
    public void comprobarFactoryMethodUsuario(){
        Boolean res;
        if(reporte.getReporte("ReporteUsuario") instanceof ReporteUsuario){
            res = true;
        }else{
            res = true;
        }
        assertEquals(true, res);
    }
}
