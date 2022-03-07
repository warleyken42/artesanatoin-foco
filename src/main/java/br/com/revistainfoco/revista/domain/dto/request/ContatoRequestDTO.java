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
public class ContatoRequestDTO implements Serializable {

    @Schema(description = "Nome do contato", example = "José Ramos")
    private String nome;

    @Schema(description = "Sobrenome do contato", example = "Farias de Azevedo")
    private String sobrenome;

    @Schema(description = "Telefone celular do contato", example = "11951482367")
    private String celular;

    @Schema(description = "Email do contato", example = "jose.ramos@email.com")
    private String email;
}
