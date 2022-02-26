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
@Tag(name = "enderecos", description = "Cadastro de Endereços")
public class EnderecoRequestDTO implements Serializable {

    @Schema(description = "Logradouro do endereço", example = "Rua Cidade Lion")
    private String logradouro;

    @Schema(description = "Cidade do endereço")
    private CidadeRequestDTO cidade;

    @Schema(description = "CEP do endereço", example = "7095190")
    private String cep;

    @Schema(description = "Número do endereço", example = "184")
    private String numero;

    @Schema(description = "Complemento do endereço", example = "Apto 32")
    private String complemento;

    @Schema(description = "Bairro onde está localizado o endereço", example = "Jardim Anny")
    private String bairro;

    @Schema(description = "Unidade federativa onde está localizado o endereço", example = "CE")
    private String uf;
}
