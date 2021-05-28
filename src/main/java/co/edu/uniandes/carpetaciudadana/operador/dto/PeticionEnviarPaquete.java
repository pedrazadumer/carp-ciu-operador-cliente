package co.edu.uniandes.carpetaciudadana.operador.dto;

import lombok.Data;

@Data
public class PeticionEnviarPaquete {
    private String nit;
    private String urlEntidad;
    private String[] archivos;
}
