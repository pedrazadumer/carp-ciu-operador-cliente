package co.edu.uniandes.carpetaciudadana.operador.controladores;

import co.edu.uniandes.carpetaciudadana.operador.dto.RespuestaEntidadPublicaDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ControladorEntidadesPublicas {

    @GetMapping("entidades-publicas/{nit}")
    public RespuestaEntidadPublicaDto registrarUsuarioPrueba(@PathVariable String nit) {
        RespuestaEntidadPublicaDto respuesta = new RespuestaEntidadPublicaDto();
        respuesta.setCorreoEntidad("fabiola-posada@gmail.com");
        return respuesta;
    }
}
