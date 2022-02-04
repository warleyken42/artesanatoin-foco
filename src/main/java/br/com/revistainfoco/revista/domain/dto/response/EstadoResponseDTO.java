package br.com.revistainfoco.revista.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstadoResponseDTO {
    private BigInteger id;
    private String nome;
    private String uf;
}
