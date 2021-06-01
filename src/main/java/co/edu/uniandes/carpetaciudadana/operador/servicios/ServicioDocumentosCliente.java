package co.edu.uniandes.carpetaciudadana.operador.servicios;

import co.edu.uniandes.carpetaciudadana.operador.dto.DocumentoDto;

import java.util.List;

public interface ServicioDocumentosCliente {

    List<DocumentoDto> obtenerDocumentosCliente(String cedula);

    List<String> transformarArchivosEnDocumentos(List<String> archivos);

}
