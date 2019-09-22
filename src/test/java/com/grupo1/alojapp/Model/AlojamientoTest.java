package com.grupo1.alojapp.Model;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class AlojamientoTest {

    private Alojamiento alojamiento;

    @Before
    public void setup(){
        alojamiento = new Alojamiento();
    }

    @Test
    public void constructorWithoutParamsTest(){
        assertFalse(this.alojamiento.isEliminado());
    }

    @Test
    public void constructorWithParamsTest(){
        String nombre = "Alojamiento prueba";
        String descripcion = "Este es un alojamiento para tests";
        this.alojamiento = new Alojamiento(nombre, descripcion);
        assertEquals(this.alojamiento.getNombre(), nombre);
        assertEquals(this.alojamiento.getDescripcion(), descripcion);
        assertFalse(this.alojamiento.isEliminado());
    }

    @Test
    public void getPensionesWithoutPensionesTestCase(){
        assertNotNull(this.alojamiento.getPensiones());
        assertEquals(this.alojamiento.getPensiones(), new ArrayList<>());
    }


}
