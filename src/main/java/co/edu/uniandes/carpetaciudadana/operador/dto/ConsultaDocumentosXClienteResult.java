package co.edu.uniandes.carpetaciudadana.operador.dto;

import lombok.Data;

import java.util.List;

@Data
public class ConsultaDocumentosXClienteResult {
    private boolean estadoRespuesta;
    private List<DocumentoDto> listadoDocumentos;
}
