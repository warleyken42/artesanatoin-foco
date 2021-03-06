package br.com.revistainfoco.revista.resources;

import br.com.revistainfoco.revista.domain.dto.request.EnderecoRequestDTO;
import br.com.revistainfoco.revista.domain.dto.request.EnderecoUpdateRequestDTO;
import br.com.revistainfoco.revista.domain.dto.response.EnderecoResponseDTO;
import br.com.revistainfoco.revista.domain.entity.Endereco;
import br.com.revistainfoco.revista.errors.ErrorDetail;
import br.com.revistainfoco.revista.services.EnderecoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/enderecos")
public class EnderecoResource {

    private final EnderecoService service;

    @Autowired
    public EnderecoResource(EnderecoService service) {
        this.service = service;
    }

    @Operation(summary = "Busca um endereço pelo cep", description = "Busca um endereço pelo cep", tags = {"enderecos"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço encontrado"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado", content = @Content(schema = @Schema(implementation = ErrorDetail.class))),
            @ApiResponse(responseCode = "415", description = "Media não suportada", content = @Content(schema = @Schema(implementation = ErrorDetail.class))),
    })
    @GetMapping(value = "/findByCep/{cep}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EnderecoResponseDTO> findByCep(@PathVariable("cep") String cep) {
        Endereco endereco = service.findByCep(cep);
        return new ResponseEntity<>(service.toResponse(endereco), HttpStatus.OK);
    }


    @Operation(summary = "Cadastra um Endereço", description = "Cadastra um novo endereço na base de dados", tags = {"enderecos"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Endereço cadastrado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Endereço já cadastrado", content = @Content(schema = @Schema(implementation = ErrorDetail.class))),
            @ApiResponse(responseCode = "415", description = "Media não suportada", content = @Content(schema = @Schema(implementation = ErrorDetail.class))),
            @ApiResponse(responseCode = "422", description = "Erro de validação", content = @Content(schema = @Schema(implementation = ErrorDetail.class)))
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EnderecoResponseDTO> create(@RequestBody @Valid EnderecoRequestDTO enderecoRequestDTO) {
        Endereco enderecoCadastrado = service.create(service.toEntity(enderecoRequestDTO));
        return new ResponseEntity<>(service.toResponse(enderecoCadastrado), HttpStatus.CREATED);
    }

    @Operation(summary = "Retorna todos os endereços cadastradas", description = "Retorna todos os endereços cadastrados", tags = {"enderecos"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereços encontrados"),
            @ApiResponse(responseCode = "204", description = "Não há endereços cadastradas", content = @Content(schema = @Schema()))
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll() {
        List<EnderecoResponseDTO> enderecosCadastrados = new ArrayList<>();
        service.findAll().forEach(endereco -> {
            enderecosCadastrados.add(service.toResponse(endereco));
        });
        if (enderecosCadastrados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(enderecosCadastrados, HttpStatus.OK);
    }

    @Operation(summary = "Busca um endereço cadastrada pelo seu id", description = "Busca um endereço cadastrado pelo seu id", tags = {"enderecos"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço encontrado"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado", content = @Content(schema = @Schema(implementation = ErrorDetail.class)))
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EnderecoResponseDTO> readById(@PathVariable("id") Long id) {
        Endereco enderecoCadastrado = service.findById(id);
        return new ResponseEntity<>(service.toResponse(enderecoCadastrado), HttpStatus.OK);
    }


    @Operation(summary = "Atualiza os dados de um endereço cadastrado", description = "Atualiza os dados de um endereço cadastrado", tags = {"enderecos"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado", content = @Content(schema = @Schema(implementation = ErrorDetail.class))),
            @ApiResponse(responseCode = "415", description = "Media não suportada", content = @Content(schema = @Schema(implementation = ErrorDetail.class))),
            @ApiResponse(responseCode = "422", description = "Erro de validação", content = @Content(schema = @Schema(implementation = ErrorDetail.class)))
    })
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EnderecoResponseDTO> update(@PathVariable("id") Long id, @RequestBody @Valid EnderecoUpdateRequestDTO enderecoUpdateRequestDTO) {
        Endereco enderecoAtualizado = service.update(id, service.toEntity(enderecoUpdateRequestDTO));
        return new ResponseEntity<>(service.toResponse(enderecoAtualizado), HttpStatus.OK);
    }

    @Operation(summary = "Exclui um endereço permanentemente", description = "Exclui um endereço permanentemente", tags = {"enderecos"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Endereço excluido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado", content = @Content(schema = @Schema(implementation = ErrorDetail.class))),
            @ApiResponse(responseCode = "415", description = "Media não suportada", content = @Content(schema = @Schema(implementation = ErrorDetail.class)))
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
