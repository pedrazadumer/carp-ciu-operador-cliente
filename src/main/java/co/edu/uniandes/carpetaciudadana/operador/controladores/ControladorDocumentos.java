package co.edu.uniandes.carpetaciudadana.operador.controladores;

import co.edu.uniandes.carpetaciudadana.operador.dto.ConsultaDocumentosXClienteResult;
import co.edu.uniandes.carpetaciudadana.operador.dto.DocumentoDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class ControladorDocumentos {

    @GetMapping("operador/documentos/{cedula}")
    public ConsultaDocumentosXClienteResult obtenerListaDocumentos(@PathVariable String cedula) {
        ConsultaDocumentosXClienteResult respuesta = new ConsultaDocumentosXClienteResult();
        List<DocumentoDto> listadoDocumentos = new ArrayList<>();
        if ("123456".equals(cedula)) {
            listadoDocumentos.add(generarDocumentoPrueba("Cedula de Ciudadania", "Cedula"));
            listadoDocumentos.add(generarDocumentoPrueba("Diploma de PreGrado", "Diploma"));
            listadoDocumentos.add(generarDocumentoPrueba("Diploma de PostGrado", "Diploma"));
            listadoDocumentos.add(generarDocumentoPrueba("Diploma de Doctorado", "Diploma"));
            listadoDocumentos.add(generarDocumentoPrueba("Referencia Personal", "Diploma"));
            listadoDocumentos.add(generarDocumentoPrueba("Referencia Laboral", "Diploma"));
            listadoDocumentos.add(generarDocumentoPrueba("Referencia Laboral", "Diploma"));
        } else {
            respuesta.setEstadoRespuesta(true);
            listadoDocumentos.add(generarDocumentoPrueba("Cedula de Ciudadania", "Cedula"));
            listadoDocumentos.add(generarDocumentoPrueba("Diploma de PreGrado", "Diploma"));
            listadoDocumentos.add(generarDocumentoPrueba("Diploma de PostGrado", "Diploma"));
            listadoDocumentos.add(generarDocumentoPrueba("Diploma de Maestria", "Diploma"));
        }
        respuesta.setListadoDocumentos(listadoDocumentos);
        return respuesta;
    }

    private static DocumentoDto generarDocumentoPrueba(String nombreDocumento, String tipoDocumento) {
        DocumentoDto documento = new DocumentoDto();
        documento.setNombreDocumento(nombreDocumento);
        documento.setTipoDocumento(tipoDocumento);
        documento.setIdentificadorDocumento(UUID.randomUUID().toString());
        return documento;
    }
}
