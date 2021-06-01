package co.edu.uniandes.carpetaciudadana.operador.servicios.impl;

import co.edu.uniandes.carpetaciudadana.operador.controladores.ControladorDocumentos;
import co.edu.uniandes.carpetaciudadana.operador.servicios.ServicioArchivos;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class ServicioArchivosArtefactoLocal implements ServicioArchivos {

    @Override
    public Resource obtenerArchivoComoRecurso(String nombreArchivo) {
        return new ClassPathResource(nombreArchivo, ControladorDocumentos.class.getClassLoader());
    }
}
