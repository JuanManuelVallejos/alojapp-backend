package com.grupo1.alojapp.Controller;

import com.grupo1.alojapp.DTOs.CloudFileDTO;
import org.junit.Test;
import java.util.List;
import org.junit.Before;
import java.util.ArrayList;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import com.grupo1.alojapp.Model.Pension;
import com.grupo1.alojapp.DTOs.PensionDTO;
import com.grupo1.alojapp.Model.CloudFile;
import com.grupo1.alojapp.DTOs.AlojamientoDTO;
import org.springframework.http.ResponseEntity;
import com.grupo1.alojapp.Services.PensionService;
import com.grupo1.alojapp.Services.AlojamientoService;
import org.springframework.web.multipart.MultipartFile;
import org.mockito.internal.util.reflection.FieldSetter;
import com.grupo1.alojapp.Controllers.CloudFileController;
import com.grupo1.alojapp.Controllers.AlojamientoController;
import com.grupo1.alojapp.Exceptions.AlojamientoEliminadoException;

public class AlojamientoControllerTest {

    private AlojamientoController alojamientoController;
    private AlojamientoService alojamientoServiceMock;
    private CloudFileController cloudFileControllerMock;
    private PensionService pensionServiceMock;

    @Before
    public void setup(){
        try {
            this.alojamientoController = new AlojamientoController();
            this.alojamientoServiceMock = mock(AlojamientoService.class);
            this.cloudFileControllerMock = mock(CloudFileController.class);
            this.pensionServiceMock = mock(PensionService.class);
            FieldSetter.setField(
                    this.alojamientoController,
                    this.alojamientoController.getClass().getDeclaredField("alojamientoService"),
                    alojamientoServiceMock);
            FieldSetter.setField(
                    this.alojamientoController,
                    this.alojamientoController.getClass().getDeclaredField("cloudFileController"),
                    cloudFileControllerMock);
            FieldSetter.setField(
                    this.alojamientoController,
                    this.alojamientoController.getClass().getDeclaredField("pensionService"),
                    pensionServiceMock);
        } catch (NoSuchFieldException e) {
            fail("Error en inicializacion gral de tests" + e.getMessage());
        }
    }

    @Test
    public void testGetAlojamientosVigentes(){
        AlojamientoDTO anyAlojamiento = new AlojamientoDTO();
        ArrayList<AlojamientoDTO> alojamientosListExpected = new ArrayList<AlojamientoDTO>() {{ add(anyAlojamiento); }};
        ResponseEntity<List<AlojamientoDTO>> alojamientosResponseExpected = ResponseEntity.ok(alojamientosListExpected);

        doReturn(alojamientosListExpected).when(alojamientoServiceMock).getAllVigentes();

        assertEquals(alojamientosResponseExpected,alojamientoController.getAlojamientosVigentes());
        verify(alojamientoServiceMock, times(1)).getAllVigentes();
    }

    @Test
    public void testGetAlojamientosPorValidar(){
        AlojamientoDTO anyAlojamiento = new AlojamientoDTO();
        ArrayList<AlojamientoDTO> alojamientosListExpected = new ArrayList<AlojamientoDTO>() {{ add(anyAlojamiento); }};
        ResponseEntity<List<AlojamientoDTO>> alojamientosResponseExpected = ResponseEntity.ok(alojamientosListExpected);

        doReturn(alojamientosListExpected).when(alojamientoServiceMock).getAllPorValidar();

        assertEquals(alojamientosResponseExpected,alojamientoController.getPorEstados("PORVALIDAR"));
        verify(alojamientoServiceMock, times(1)).getAllPorValidar();
    }

    @Test
    public void testGetAlojamientosValidados(){
        AlojamientoDTO anyAlojamiento = new AlojamientoDTO();
        ArrayList<AlojamientoDTO> alojamientosListExpected = new ArrayList<AlojamientoDTO>() {{ add(anyAlojamiento); }};
        ResponseEntity<List<AlojamientoDTO>> alojamientosResponseExpected = ResponseEntity.ok(alojamientosListExpected);

        doReturn(alojamientosListExpected).when(alojamientoServiceMock).getAllValidados();

        assertEquals(alojamientosResponseExpected,alojamientoController.getPorEstados("VALIDADO"));
        verify(alojamientoServiceMock, times(1)).getAllValidados();
    }

    @Test
    public void testGetAllRechazados(){
        AlojamientoDTO anyAlojamiento = new AlojamientoDTO();
        ArrayList<AlojamientoDTO> alojamientosListExpected = new ArrayList<AlojamientoDTO>() {{ add(anyAlojamiento); }};
        ResponseEntity<List<AlojamientoDTO>> alojamientosResponseExpected = ResponseEntity.ok(alojamientosListExpected);

        doReturn(alojamientosListExpected).when(alojamientoServiceMock).getAllRechazados();

        assertEquals(alojamientosResponseExpected,alojamientoController.getPorEstados("RECHAZADO"));
        verify(alojamientoServiceMock, times(1)).getAllRechazados();
    }

    @Test
    public void testGetAlojamiento() throws AlojamientoEliminadoException {
        AlojamientoDTO alojamientoExpected = new AlojamientoDTO();
        ResponseEntity<AlojamientoDTO> alojamientoResponseExpected = ResponseEntity.ok(alojamientoExpected);

        doReturn(alojamientoExpected).when(alojamientoServiceMock).getById(alojamientoExpected.getId());

        assertEquals(alojamientoResponseExpected,alojamientoController.getAlojamiento(alojamientoExpected.getId()));
        verify(alojamientoServiceMock, times(1)).getById(alojamientoExpected.getId());
    }

    @Test
    public void testSaveOrUpdateAlojamiento() {
        AlojamientoDTO alojamientoPassed = new AlojamientoDTO();
        ResponseEntity<AlojamientoDTO> alojamientoResponseExpected = ResponseEntity.ok(alojamientoPassed);

        doReturn(alojamientoPassed).when(alojamientoServiceMock).saveAlojamientoFromDTO(alojamientoPassed);

        assertEquals(alojamientoResponseExpected, alojamientoController.saveOrUpdateAlojamiento(alojamientoPassed));
        verify(alojamientoServiceMock, times(1)).saveAlojamientoFromDTO(alojamientoPassed);
    }

    @Test
    public void testUpdateAlojamiento() {
        AlojamientoDTO alojamientoPassed = new AlojamientoDTO();
        ResponseEntity<AlojamientoDTO> alojamientoResponseExpected = ResponseEntity.ok(alojamientoPassed);

        doReturn(alojamientoPassed).when(alojamientoServiceMock).saveAlojamientoFromDTO(alojamientoPassed);

        assertEquals(alojamientoResponseExpected, alojamientoController.updateAlojamiento(alojamientoPassed));
        verify(alojamientoServiceMock, times(1)).saveAlojamientoFromDTO(alojamientoPassed);
    }

    @Test
    public void testDeleteAlojamiento() throws AlojamientoEliminadoException{
        long ID_TEST = 1;
        alojamientoController.deleteAlojamiento(ID_TEST);

        verify(alojamientoServiceMock, times(1)).deleteAlojamientoById(ID_TEST);
    }

    @Test
    public void testUploadFile() throws Exception{
        long ID_TEST = 1;
        AlojamientoDTO anyAlojamiento = new AlojamientoDTO();
        MultipartFile file = mock(MultipartFile.class);
        CloudFileDTO cloudFile = mock(CloudFileDTO.class);

        doReturn(cloudFile).when(cloudFileControllerMock).internalUploadCloudFile(file);
        doReturn(anyAlojamiento).when(alojamientoServiceMock).addCloudFileToAlojamiento(cloudFile, ID_TEST);

        assertEquals(ResponseEntity.ok(anyAlojamiento), alojamientoController.uploadFile(file,ID_TEST));
        verify(cloudFileControllerMock, times(1)).internalUploadCloudFile(file);
        verify(alojamientoServiceMock, times(1)).addCloudFileToAlojamiento(cloudFile,ID_TEST);
    }

    @Test
    public void testCheck(){
        long ID_TEST = 1;
        AlojamientoDTO anyAlojamiento = new AlojamientoDTO();
        ResponseEntity<AlojamientoDTO> responseExpected = ResponseEntity.ok(anyAlojamiento);

        doReturn(anyAlojamiento).when(alojamientoServiceMock).checkAlojamiento(anyLong());

        assertEquals(responseExpected, alojamientoController.check(ID_TEST));
        verify(alojamientoServiceMock, times(1)).checkAlojamiento(ID_TEST);
    }

    @Test
    public void testUncheck(){
        long ID_TEST = 1;
        String JUSTIFICACION = "Esto es una justificacion de prueba";
        AlojamientoDTO anyAlojamiento = new AlojamientoDTO();
        ResponseEntity<AlojamientoDTO> responseExpected = ResponseEntity.ok(anyAlojamiento);

        doReturn(anyAlojamiento).when(alojamientoServiceMock).uncheckAlojamiento(anyLong(), anyString());

        assertEquals(responseExpected, alojamientoController.uncheck(ID_TEST, JUSTIFICACION));
        verify(alojamientoServiceMock, times(1)).uncheckAlojamiento(ID_TEST, JUSTIFICACION);
    }

    @Test
    public void testAgregarModificarPensionWithPensionExistente() throws AlojamientoEliminadoException{
        long ID_TEST = 1;
        AlojamientoDTO anyAlojamiento = new AlojamientoDTO();
        ResponseEntity<AlojamientoDTO> alojamientoResponseExpected = ResponseEntity.ok(anyAlojamiento);
        PensionDTO anyPension = new PensionDTO();

        doReturn(anyAlojamiento).when(alojamientoServiceMock).getById(anyLong());
        doReturn(true).when(alojamientoServiceMock).modificarPensionSiExiste(any(AlojamientoDTO.class), any(PensionDTO.class));

        assertEquals(alojamientoResponseExpected, alojamientoController.agregarModificarPension(anyPension));
        verify(alojamientoServiceMock, times(1)).modificarPensionSiExiste(anyAlojamiento, anyPension);
        verify(alojamientoServiceMock, times(2)).getById(anyPension.idalojamiento);
        verify(pensionServiceMock, times(0)).createPension(any(PensionDTO.class));
    }

    @Test
    public void testAgregarModificarPensionWithoutPensionExistente() throws AlojamientoEliminadoException{
        long ID_TEST = 1;
        AlojamientoDTO anyAlojamiento = new AlojamientoDTO();
        ResponseEntity<AlojamientoDTO> alojamientoResponseExpected = ResponseEntity.ok(anyAlojamiento);
        PensionDTO anyPension = new PensionDTO();
        Pension pensionMock = mock(Pension.class);

        doReturn(anyAlojamiento).when(alojamientoServiceMock).getById(anyLong());
        doReturn(false).when(alojamientoServiceMock).modificarPensionSiExiste(any(AlojamientoDTO.class), any(PensionDTO.class));
        doReturn(pensionMock).when(pensionServiceMock).createPension(any(PensionDTO.class));
        doReturn(anyAlojamiento).when(alojamientoServiceMock).agregarPension(anyLong(),any(Pension.class));

        assertEquals(alojamientoResponseExpected, alojamientoController.agregarModificarPension(anyPension));
        verify(alojamientoServiceMock, times(1)).getById(anyPension.idalojamiento);
        verify(alojamientoServiceMock, times(1)).modificarPensionSiExiste(anyAlojamiento, anyPension);
        verify(pensionServiceMock, times(1)).createPension(anyPension);
        verify(alojamientoServiceMock, times(1)).agregarPension(anyPension.idalojamiento, pensionMock);
    }

}
