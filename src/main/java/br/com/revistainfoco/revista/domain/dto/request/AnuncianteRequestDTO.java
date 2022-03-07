package br.com.revistainfoco.revista.domain.dto.request;


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
public class AnuncianteRequestDTO implements Serializable {

    @Schema(description = "Cnpj do anunciante", example = "99.172.719/0001-77")
    private String cnpj;

    @Schema(description = "Razao social do anunciante", example = "Revista In Foco Ltda-ME")
    private String razaoSocial;

    @Schema(description = "Nome Fantasia do anunciante", example = "Revista in foco")
    private String nomeFantasia;

    @Schema(description = "Endereço do anunciante")
    private EnderecoRequestDTO endereco;

    @Schema(description = "Email do anunciante", example = "warley-ft@hotmail.com")
    private String email;

    @Schema(description = "Site do anunciante", example = "www.revistainfoco.com")
    private String site;

    @Schema(description = "Contato do anunciante")
    private ContatoRequestDTO contato;
}
