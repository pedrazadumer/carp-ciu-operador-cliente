package co.edu.uniandes.carpetaciudadana.operador.servicios.impl;

import co.edu.uniandes.carpetaciudadana.operador.dto.DocumentoDto;
import co.edu.uniandes.carpetaciudadana.operador.servicios.ServicioDocumentosCliente;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class ServicioDocumentosClienteEnMemoria implements ServicioDocumentosCliente {

    private static final String CEDULA_CLIENTE_SEIS_DOCUMENTOS = "123456";
    private static final String DOC_CEDULA_DE_CIUDADANIA = "Cedula de Ciudadania";
    private static final String DOC_DIPLOMA_DE_PRE_GRADO = "Diploma de PreGrado";
    private static final String DOC_DIPLOMA_DE_POST_GRADO = "Diploma de PostGrado";
    private static final String DOC_DIPLOMA_DE_DOCTORADO = "Diploma de Doctorado";
    private static final String DOC_REFERENCIA_PERSONAL = "Referencia Personal";
    private static final String DOC_REFERENCIA_LABORAL = "Referencia Laboral";
    private static final String DOC_DIPLOMA_DE_MAESTRIA = "Diploma de Maestria";

    private Map<String, List<DocumentoDto>> documentosPorCliente;
    private Map<String, String> cruceArchivosDocumentos;
    private final DocumentoDto documentoIdentidad;
    private final DocumentoDto tituloProfesional;
    private final DocumentoDto tituloPostGrado;
    private final DocumentoDto tituloDoctorado;
    private final DocumentoDto referenciaPersonal;
    private final DocumentoDto referenciaLaboral;
    private final DocumentoDto tituloMaestria;

    public ServicioDocumentosClienteEnMemoria() {
        this.documentoIdentidad = new DocumentoDto(DOC_CEDULA_DE_CIUDADANIA, "Cedula", UUID.randomUUID().toString());
        this.tituloProfesional = new DocumentoDto(DOC_DIPLOMA_DE_PRE_GRADO, "Diploma", UUID.randomUUID().toString());
        this.tituloPostGrado = new DocumentoDto(DOC_DIPLOMA_DE_POST_GRADO, "Diploma", UUID.randomUUID().toString());
        this.tituloDoctorado = new DocumentoDto(DOC_DIPLOMA_DE_DOCTORADO, "Diploma", UUID.randomUUID().toString());
        this.referenciaPersonal = new DocumentoDto(DOC_REFERENCIA_PERSONAL, "Diploma", UUID.randomUUID().toString());
        this.referenciaLaboral = new DocumentoDto(DOC_REFERENCIA_LABORAL, "Diploma", UUID.randomUUID().toString());
        this.tituloMaestria = new DocumentoDto(DOC_DIPLOMA_DE_MAESTRIA, "Diploma", UUID.randomUUID().toString());

        this.documentosPorCliente = new HashMap<>();
        this.documentosPorCliente.put(CEDULA_CLIENTE_SEIS_DOCUMENTOS,
                Arrays.asList(this.documentoIdentidad, this.tituloProfesional, this.tituloPostGrado,
                        this.tituloDoctorado, this.referenciaPersonal, this.referenciaLaboral));

        this.cruceArchivosDocumentos = new HashMap<>();
        this.cruceArchivosDocumentos.put(DOC_CEDULA_DE_CIUDADANIA, String.format("DOC-001=%s.txt", DOC_CEDULA_DE_CIUDADANIA));
        this.cruceArchivosDocumentos.put(DOC_DIPLOMA_DE_PRE_GRADO, String.format("DOC-002=%s.txt", DOC_DIPLOMA_DE_PRE_GRADO));
        this.cruceArchivosDocumentos.put(DOC_DIPLOMA_DE_POST_GRADO, String.format("DOC-003=%s.txt", DOC_DIPLOMA_DE_POST_GRADO));
        this.cruceArchivosDocumentos.put(DOC_DIPLOMA_DE_DOCTORADO, String.format("DOC-004=%s.txt", DOC_DIPLOMA_DE_DOCTORADO));
        this.cruceArchivosDocumentos.put(DOC_REFERENCIA_LABORAL, String.format("DOC-005=%s.txt", DOC_REFERENCIA_LABORAL));
        this.cruceArchivosDocumentos.put(DOC_REFERENCIA_PERSONAL, String.format("DOC-006=%s.txt", DOC_REFERENCIA_PERSONAL));
        this.cruceArchivosDocumentos.put(DOC_DIPLOMA_DE_MAESTRIA, String.format("DOC-007=%s.txt", DOC_DIPLOMA_DE_MAESTRIA));
    }


    @Override
    public List<DocumentoDto> obtenerDocumentosCliente(String cedula) {
        return this.documentosPorCliente.getOrDefault(cedula,
                Arrays.asList(this.documentoIdentidad, this.tituloProfesional, this.tituloPostGrado,
                        this.tituloMaestria));
    }

    @Override
    public List<String> transformarArchivosEnDocumentos(List<String> archivos) {
        return archivos.stream()
                .filter(a -> this.cruceArchivosDocumentos.containsKey(a))
                .map(a -> this.cruceArchivosDocumentos.get(a))
                .collect(Collectors.toList());
    }
}
