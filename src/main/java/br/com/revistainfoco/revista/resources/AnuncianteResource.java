package br.com.revistainfoco.revista.resources;

import br.com.revistainfoco.revista.domain.dto.request.AnuncianteRequestDTO;
import br.com.revistainfoco.revista.domain.dto.response.AnuncianteResponseDTO;
import br.com.revistainfoco.revista.domain.entity.Anunciante;
import br.com.revistainfoco.revista.errors.ErrorDetail;
import br.com.revistainfoco.revista.services.AnuncianteService;
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
@RequestMapping(value = "/anunciantes")
public class AnuncianteResource {

    private final AnuncianteService service;

    @Autowired
    public AnuncianteResource(AnuncianteService service) {
        this.service = service;
    }

    @Operation(summary = "Cadastra um Anunciante", description = "Cadastra um novo anunciante na base de dados", tags = {"anunciantes"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Anunciante cadastrado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Anunciante já cadastrado", content = @Content(schema = @Schema(implementation = ErrorDetail.class))),
            @ApiResponse(responseCode = "415", description = "Media não suportada", content = @Content(schema = @Schema(implementation = ErrorDetail.class))),
            @ApiResponse(responseCode = "422", description = "Erro de validação", content = @Content(schema = @Schema(implementation = ErrorDetail.class)))
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AnuncianteResponseDTO> create(@RequestBody @Valid AnuncianteRequestDTO anuncianteRequestDTO) {
        Anunciante anuncianteCadastrado = service.create(service.toEntity(anuncianteRequestDTO));
        return new ResponseEntity<>(service.toResponse(anuncianteCadastrado), HttpStatus.CREATED);
    }

    @Operation(summary = "Retorna todos os anunciantes cadastrados", description = "Retorna todos os anunciantes cadastrados", tags = {"anunciantes"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Anunciantes encontrados"),
            @ApiResponse(responseCode = "204", description = "Não há anunciantes cadastrados", content = @Content(schema = @Schema()))
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AnuncianteResponseDTO>> findAll() {
        List<AnuncianteResponseDTO> anunciantesCadastrados = new ArrayList<>();
        service.findAll().forEach(endereco -> anunciantesCadastrados.add(service.toResponse(endereco)));
        if (anunciantesCadastrados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(anunciantesCadastrados, HttpStatus.OK);
    }

    @Operation(summary = "Busca um anunciante cadastrado pelo seu id", description = "Busca um anunciante cadastrado pelo seu id", tags = {"anunciantes"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Anunciante encontrado"),
            @ApiResponse(responseCode = "404", description = "Anunciante não encontrado", content = @Content(schema = @Schema(implementation = ErrorDetail.class)))
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AnuncianteResponseDTO> readById(@PathVariable("id") Long id) {
        Anunciante anuncianteCadastrado = service.findById(id);
        return new ResponseEntity<>(service.toResponse(anuncianteCadastrado), HttpStatus.OK);
    }

    @Operation(summary = "Atualiza os dados de um anunciante cadastrado", description = "Atualiza os dados de um anunciante cadastrado", tags = {"anunciantes"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Anunciante atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Anunciante não encontrado", content = @Content(schema = @Schema(implementation = ErrorDetail.class))),
            @ApiResponse(responseCode = "415", description = "Media não suportada", content = @Content(schema = @Schema(implementation = ErrorDetail.class))),
            @ApiResponse(responseCode = "422", description = "Erro de validação", content = @Content(schema = @Schema(implementation = ErrorDetail.class)))
    })
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AnuncianteResponseDTO> update(@PathVariable("id") Long id, @RequestBody AnuncianteRequestDTO anuncianteUpdateRequestDTO) {
        Anunciante anuncianteAtualizado = service.update(id, service.toEntity(anuncianteUpdateRequestDTO));
        return new ResponseEntity<>(service.toResponse(anuncianteAtualizado), HttpStatus.OK);
    }

    @Operation(summary = "Exclui um anunciante permanentemente", description = "Exclui um anunciante permanentemente", tags = {"anunciantes"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Anunciante excluido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Anunciante não encontrado", content = @Content(schema = @Schema(implementation = ErrorDetail.class))),
            @ApiResponse(responseCode = "415", description = "Media não suportada", content = @Content(schema = @Schema(implementation = ErrorDetail.class)))
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
