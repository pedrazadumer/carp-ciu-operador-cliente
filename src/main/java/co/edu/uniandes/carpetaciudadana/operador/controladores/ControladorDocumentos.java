package co.edu.uniandes.carpetaciudadana.operador.controladores;

import co.edu.uniandes.carpetaciudadana.operador.dto.ConsultaDocumentosXClienteResult;
import co.edu.uniandes.carpetaciudadana.operador.dto.DocumentoDto;
import co.edu.uniandes.carpetaciudadana.operador.dto.PeticionEnviarPaquete;
import co.edu.uniandes.carpetaciudadana.operador.servicios.ServicioArchivos;
import co.edu.uniandes.carpetaciudadana.operador.servicios.ServicioDocumentosCliente;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class ControladorDocumentos {

    private static final String SEPARADOR =
            "\n===============================================================\n";
    private static final String CODIGO_SUB_CARPETA = "CPT-0025";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ServicioArchivos servicioArchivos;

    @Autowired
    private ServicioDocumentosCliente servicioDocumentosCliente;

    @GetMapping("operador/documentos/{cedula}")
    public ConsultaDocumentosXClienteResult obtenerListaDocumentos(@PathVariable String cedula) {
        ConsultaDocumentosXClienteResult respuesta = new ConsultaDocumentosXClienteResult();
        List<DocumentoDto> listadoDocumentos = this.servicioDocumentosCliente.obtenerDocumentosCliente(cedula);
        respuesta.setListadoDocumentos(listadoDocumentos);
        imprimirLogsConsultaDocumentosCliente(cedula, listadoDocumentos);
        return respuesta;
    }

    @PostMapping("operador/paquetes/{cedula}")
    public void enviarPaqueteDocumentos(@PathVariable String cedula, @RequestBody PeticionEnviarPaquete peticion) {
        imprimirLogsEnvioPaqueteDocumentos(cedula, peticion);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(crearCuerpoPeticionEnviarPaquete(peticion.getArchivos()), headers);

        ResponseEntity<String> response = restTemplate
                .postForEntity(peticion.getUrlEntidad() + "/archivos/" + peticion.getNit(), requestEntity, String.class);
    }

    private MultiValueMap crearCuerpoPeticionEnviarPaquete(List<String> archivos) {
        List<String> documentos = this.servicioDocumentosCliente.transformarArchivosEnDocumentos(archivos);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        body.set("codigoSubCarpeta", CODIGO_SUB_CARPETA);
        archivos.forEach(archivo -> body.add("archivos",
                this.servicioArchivos.obtenerArchivoComoRecurso(archivo + ".txt")));
        documentos.forEach(documento -> body.add("mapaArchivosDocumentos", documento));

        return body;
    }

    private void imprimirLogsEnvioPaqueteDocumentos(String cedula, PeticionEnviarPaquete peticion) {
        List<String> documentos = this.servicioDocumentosCliente.transformarArchivosEnDocumentos(peticion.getArchivos());
        System.out.printf("%sOPERACION: Enviar Paquete Documentos a Entidad.%s \nCedula: [%s]\nNit: [%s]\nUrl Operador Entidad Publica: [%s]",
                SEPARADOR, SEPARADOR, cedula, peticion.getNit(), peticion.getUrlEntidad());

        System.out.println("\nDocumentos:");
        System.out.print(peticion.getArchivos().stream().map(archivo -> String.format("- %s.txt", archivo))
                .collect(Collectors.joining("\n")));

        System.out.printf("\n\nSub-Carpeta Seleccionada: [%s]\n", CODIGO_SUB_CARPETA);
        System.out.println("Cruce Archivos vs Documentos Enviados:");
        System.out.print(documentos.stream().map(documento -> String.format("\t- [%s]", documento))
                .collect(Collectors.joining("\n")));

        System.out.println(SEPARADOR);
    }

    private void imprimirLogsConsultaDocumentosCliente(String cedula, List<DocumentoDto> listadoDocumentos) {
        System.out.printf("%sOPERACION: Consultar Documentos Cliente.%s \nCedula: [%s]", SEPARADOR, SEPARADOR, cedula);
        System.out.println("\nDocumentos Cliente:");
        System.out.print(listadoDocumentos.stream().map(documento -> String.format("\t- %s.txt", documento.getNombreDocumento()))
                .collect(Collectors.joining("\n")));
        System.out.println(SEPARADOR);
    }
}
