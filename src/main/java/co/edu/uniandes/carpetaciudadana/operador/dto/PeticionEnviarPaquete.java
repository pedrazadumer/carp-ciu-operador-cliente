package co.edu.uniandes.carpetaciudadana.operador.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class PeticionEnviarPaquete {
    private String nit;
    private String urlEntidad;
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private String[] archivos;
}
