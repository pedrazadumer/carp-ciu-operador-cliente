package co.edu.uniandes.carpetaciudadana.operador.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentoDto {
    private String nombreDocumento;
    private String tipoDocumento;
    private String identificadorDocumento;
}
