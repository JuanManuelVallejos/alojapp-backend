package com.grupo1.alojapp.Controller;

import com.grupo1.alojapp.Controllers.AlojamientoController;
import com.grupo1.alojapp.DTOs.AlojamientoDTO;
import com.grupo1.alojapp.Services.AlojamientoService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.FieldSetter;
import java.util.ArrayList;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

public class AlojamientoControllerTest {

    private AlojamientoController alojamientoController;
    private AlojamientoService alojamientoServiceMock;

    @Before
    public void setup(){
        try {
            this.alojamientoController = new AlojamientoController();
            this.alojamientoServiceMock = mock(AlojamientoService.class);
            FieldSetter.setField(
                    this.alojamientoController,
                    this.alojamientoController.getClass().getDeclaredField("alojamientoService"),
                    alojamientoServiceMock);
        } catch (NoSuchFieldException e) {
            fail("Error en inicializacion gral de tests" + e.getMessage());
        }
    }

    @Test
    public void testGetAlojamientosVigentes(){
        AlojamientoDTO anyAlojamiento = new AlojamientoDTO();
        ArrayList<AlojamientoDTO> alojamientosListExpected = new ArrayList<AlojamientoDTO>() {{ add(anyAlojamiento); }};
        doReturn(alojamientosListExpected).when(alojamientoServiceMock).getAllVigentes();
        assertEquals(alojamientosListExpected,alojamientoServiceMock.getAllVigentes());
        verify(alojamientoServiceMock, times(1)).getAllVigentes();
    }

}
