package co.edu.uniandes.carpetaciudadana.operador.controladores;

import co.edu.uniandes.carpetaciudadana.operador.dto.ConsultaDocumentosXClienteResult;
import co.edu.uniandes.carpetaciudadana.operador.dto.DocumentoDto;
import co.edu.uniandes.carpetaciudadana.operador.dto.PeticionEnviarPaquete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class ControladorDocumentos {

    public static final String SEPARADOR =
            "\n===============================================================\n";

    @Autowired
    private RestTemplate restTemplate;

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

    @PostMapping("operador/paquetes/{cedula}")
    public void test(@PathVariable String cedula, @RequestBody PeticionEnviarPaquete peticion) {

        System.out.printf("%sSe ha recibido una peticion para enviar un paquete de documentos al operador con los siguientes parametros: \nCedula: [%s]\nNit: [%s]\nUrl Operador Entidad Publica: [%s]",
                SEPARADOR, cedula, peticion.getNit(), peticion.getUrlEntidad());

        System.out.println("\nDocumentos:");
        for (int i = 0; i < peticion.getArchivos().length; i++) {
            System.out.printf("- %s.txt", peticion.getArchivos()[i]);
            if (i < peticion.getArchivos().length - 1) System.out.println();
        }
        System.out.print(SEPARADOR);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);


        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(obtenerArchivosAEnviar(peticion.getArchivos()), headers);

        ResponseEntity<String> response = restTemplate
                .postForEntity(peticion.getUrlEntidad() + "/archivos/" + peticion.getNit(), requestEntity, String.class);


        /*ResponseEntity<Object> respuesta = this.restTemplate.postForEntity(
                urlEntidad + "/archivos/" + cedula,
                obtenerArchivosAEnviar(),
                Object.class,
                Collections.singletonMap("Content-Type", "multipart/form-data"));*/
    }

    private static MultiValueMap obtenerArchivosAEnviar(String[] archivos) {
        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();

        for (int i = 0; i < archivos.length; i++) {
            body.add("archivos", obtenerArchivoComoRecurso(archivos[i]));
        }

        /*body.add("archivos", obtenerArchivoComoRecurso("Cedula de Ciudadania"));
        body.add("archivos", obtenerArchivoComoRecurso("Diploma de PreGrado"));*/
        return body;
    }

    private static File obtenerArchivo(String nombreArchivo) {
        try {
            return new ClassPathResource(nombreArchivo + ".txt", ControladorDocumentos.class.getClassLoader()).getFile();
        } catch (IOException e) {
            throw new RuntimeException("Lectura del archivo fallo.");
        }
    }

    private static Resource obtenerArchivoComoRecurso(String nombreArchivo) {
        return new ClassPathResource(nombreArchivo + ".txt", ControladorDocumentos.class.getClassLoader());
    }

    private static DocumentoDto generarDocumentoPrueba(String nombreDocumento, String tipoDocumento) {
        DocumentoDto documento = new DocumentoDto();
        documento.setNombreDocumento(nombreDocumento);
        documento.setTipoDocumento(tipoDocumento);
        documento.setIdentificadorDocumento(UUID.randomUUID().toString());
        return documento;
    }
}
