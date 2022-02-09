package br.com.revistainfoco.revista.domain.dto.response;

import br.com.revistainfoco.revista.domain.entity.Cidade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CidadeResponseDTO {
    private Long id;
    private String nome;
}
