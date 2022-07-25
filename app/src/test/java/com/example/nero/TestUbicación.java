package com.example.nero;

import org.junit.*;
import org.mockito.*;

import PaqueteClasesPrincipales.Ubicacion;
import PaqueteClasesPrincipales.Usuario;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestUbicación {
    Ubicacion ubicacion;

    @Before
    public void init(){
        ubicacion = new Ubicacion(1.1,2.2);
    }
    @After
    public void terminate(){
        ubicacion = null;
    }


    @Test
    public void comprobarUbicacion(){
        Ubicacion ubicacion2 = mock(Ubicacion.class);
        when(ubicacion2.getLatitud()).thenReturn(1.1);
        when(ubicacion2.getLongitud()).thenReturn(2.2);

        assertEquals(true, ubicacion.equals(ubicacion2));
    }

    @Test
    public void comprobarEqualsHashCode(){
        Ubicacion ubi2 = ubicacion;
        Ubicacion ubi3 = mock(Ubicacion.class);
        when(ubi3.getLatitud()).thenReturn(1.1);
        when(ubi3.getLongitud()).thenReturn(2.2);

        assertEquals(ubicacion.hashCode(), ubi2.hashCode(), 0);
        assertNotEquals(ubicacion.hashCode(), ubi3.hashCode(), 0);
    }

    @Test
    public void comprobarToString(){
        String str = ("Ubicacion (latitud: 1.1, longitud: 2.2)");
        assertEquals(str,ubicacion.toString());
    }
}
