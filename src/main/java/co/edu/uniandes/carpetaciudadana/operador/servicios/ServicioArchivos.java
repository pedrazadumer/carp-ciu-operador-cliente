package co.edu.uniandes.carpetaciudadana.operador.servicios;

import org.springframework.core.io.Resource;

public interface ServicioArchivos {

    Resource obtenerArchivoComoRecurso(String nombreArchivo);

}
