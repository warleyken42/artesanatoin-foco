package br.com.revistainfoco.revista.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "anunciantes", description = "Cadastro de Anunciantes")
public class AnuncianteResponseDTO implements Serializable {

    @Schema(description = "ID do anunciante que se deseja atualizar", example = "1", required = true)
    private Long id;

    @Schema(description = "Cnpj do anunciante que se deseja atualizar", example = "99.172.719/0001-77", required = true)
    private String cnpj;

    @Schema(description = "Razão Social do anunciante", example = "Warley Kennedy Figueiredo", required = true)
    private String razaoSocial;

    @Schema(description = "Nome Fantasia do anunciante", example = "REVISTA_IN_FOCO", required = true)
    private String nomeFantasia;

    @Schema(description = "Endereço do anunciante")
    private EnderecoResponseDTO endereco;

    @Schema(description = "Email do anunciante", example = "warley-ft@hotmail.com", required = true)
    private String email;

    @Schema(description = "Site do anunciante", example = "www.revista_in_foco.com")
    private String site;
}
