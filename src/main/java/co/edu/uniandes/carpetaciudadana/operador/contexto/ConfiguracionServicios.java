package co.edu.uniandes.carpetaciudadana.operador.contexto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ConfiguracionServicios {

    /*@Value("${url.base.bus}")
    private String urlBaseBus;*/

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


}
