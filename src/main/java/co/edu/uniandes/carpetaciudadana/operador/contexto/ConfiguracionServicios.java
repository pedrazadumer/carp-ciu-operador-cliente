package co.edu.uniandes.carpetaciudadana.operador.contexto;

import co.edu.uniandes.carpetaciudadana.operador.servicios.ServicioArchivos;
import co.edu.uniandes.carpetaciudadana.operador.servicios.ServicioDocumentosCliente;
import co.edu.uniandes.carpetaciudadana.operador.servicios.impl.ServicioArchivosArtefactoLocal;
import co.edu.uniandes.carpetaciudadana.operador.servicios.impl.ServicioDocumentosClienteEnMemoria;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ConfiguracionServicios {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ServicioArchivos servicioArchivos() {
        return new ServicioArchivosArtefactoLocal();
    }

    @Bean
    public ServicioDocumentosCliente servicioDocumentosCliente() {
        return new ServicioDocumentosClienteEnMemoria();
    }
}
