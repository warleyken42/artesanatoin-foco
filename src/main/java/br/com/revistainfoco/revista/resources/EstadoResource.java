package br.com.revistainfoco.revista.resources;

import br.com.revistainfoco.revista.domain.dto.request.EstadoRequestDTO;
import br.com.revistainfoco.revista.domain.dto.response.EstadoResponseDTO;
import br.com.revistainfoco.revista.errors.ErrorDetail;
import br.com.revistainfoco.revista.services.EstadoService;
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
import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoResource {

    private final EstadoService service;

    @Autowired
    public EstadoResource(EstadoService service) {
        this.service = service;
    }

    @Operation(summary = "Cadastra um estado", description = "Cadastra um novo estado na base de dados", tags = {"estados"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Estado cadastrado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Estado já cadastrado", content = @Content(schema = @Schema(implementation = ErrorDetail.class))),
            @ApiResponse(responseCode = "415", description = "Media não suportada", content = @Content(schema = @Schema(implementation = ErrorDetail.class))),
            @ApiResponse(responseCode = "422", description = "Erro de validação", content = @Content(schema = @Schema(implementation = ErrorDetail.class)))
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EstadoResponseDTO> create(@RequestBody @Valid EstadoRequestDTO estadoRequestDTO) {
        EstadoResponseDTO estadoCriado = service.create(estadoRequestDTO);
        return new ResponseEntity<>(estadoCriado, HttpStatus.CREATED);
    }

    @Operation(summary = "Retorna todos os estados cadastrados", description = "Retorna todos os estados cadastrados", tags = {"estados"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estados encontrados"),
            @ApiResponse(responseCode = "204", description = "Não há estados cadastrados", content = @Content(schema = @Schema()))
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> readAll() {
        List<EstadoResponseDTO> estados = service.readAll();
        if (estados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(estados, HttpStatus.OK);
    }

    @Operation(summary = "Busca um estado cadastrado pelo seu id", description = "Busca um estado cadastrado pelo seu id", tags = {"estados"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado encontrado"),
            @ApiResponse(responseCode = "404", description = "Estado não encontrado", content = @Content(schema = @Schema(implementation = ErrorDetail.class)))
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EstadoResponseDTO> readById(@PathVariable("id") Long id) {
        EstadoResponseDTO estadoResponseDTO = service.readById(id);
        return new ResponseEntity<>(estadoResponseDTO, HttpStatus.OK);
    }

    @Operation(summary = "Atualiza os dados de um estado cadastrado", description = "Atualiza os dados de um estado cadastrado", tags = {"estados"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Estado não encontrado", content = @Content(schema = @Schema(implementation = ErrorDetail.class))),
            @ApiResponse(responseCode = "415", description = "Media não suportada", content = @Content(schema = @Schema(implementation = ErrorDetail.class))),
            @ApiResponse(responseCode = "422", description = "Erro de validação", content = @Content(schema = @Schema(implementation = ErrorDetail.class)))
    })
    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EstadoResponseDTO> update(@PathVariable("id") Long id, @RequestBody @Valid EstadoRequestDTO estadoRequestDTO) {
        EstadoResponseDTO estadoResponseDTO = service.update(id, estadoRequestDTO);
        return new ResponseEntity<>(estadoResponseDTO, HttpStatus.OK);
    }

    @Operation(summary = "Exclui um estado permanentemente", description = "Exclui um estado permanentemente", tags = {"estados"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Estado excluido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Estado não encontrado", content = @Content(schema = @Schema(implementation = ErrorDetail.class))),
            @ApiResponse(responseCode = "415", description = "Media não suportada", content = @Content(schema = @Schema(implementation = ErrorDetail.class)))
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
