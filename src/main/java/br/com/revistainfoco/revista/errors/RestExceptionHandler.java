package br.com.revistainfoco.revista.errors;

import br.com.revistainfoco.revista.errors.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(EstadoJaCadastradoException.class)
    public ResponseEntity<?> handleEstadoCadastradoException(EstadoJaCadastradoException estadoJaCadastradoException) {
        ErrorDetail errorDetail = ErrorDetail.builder().mensagem(estadoJaCadastradoException.getMessage()).build();
        return new ResponseEntity<>(errorDetail, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EstadoNaoEncontradoException.class)
    public ResponseEntity<?> handleEstadoNaoEncontradoException(EstadoNaoEncontradoException estadoNaoEncontradoException) {
        ErrorDetail errorDetail = ErrorDetail.builder().mensagem(estadoNaoEncontradoException.getMessage()).build();
        return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        ErrorDetail errorDetail = ErrorDetail.builder().mensagem(methodArgumentNotValidException.getBindingResult().getFieldError().getDefaultMessage()).build();
        return new ResponseEntity<>(errorDetail, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(CidadeJaCadastradaException.class)
    public ResponseEntity<?> handleCidadeCadastradoException(CidadeJaCadastradaException cidadeJaCadastradoException) {
        ErrorDetail errorDetail = ErrorDetail.builder().mensagem(cidadeJaCadastradoException.getMessage()).build();
        return new ResponseEntity<>(errorDetail, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleCidadeNaoEncontradoException(CidadeNaoEncontradaException cidadeNaoEncontradoException) {
        ErrorDetail errorDetail = ErrorDetail.builder().mensagem(cidadeNaoEncontradoException.getMessage()).build();
        return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AnuncianteJaCadastradoException.class)
    public ResponseEntity<?> handleAnuncianteJaCadastradoException(AnuncianteJaCadastradoException anuncianteJaCadastradoException) {
        ErrorDetail errorDetail = ErrorDetail.builder().mensagem(anuncianteJaCadastradoException.getMessage()).build();
        return new ResponseEntity<>(errorDetail, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ContatoNaoEncontradoException.class)
    public ResponseEntity<?> handleContatoNaoEncontradoException(ContatoNaoEncontradoException contatoNaoEncontradoException) {
        ErrorDetail errorDetail = ErrorDetail.builder().mensagem(contatoNaoEncontradoException.getMessage()).build();
        return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException dataIntegrityViolationException) {
        String message = dataIntegrityViolationException.getMostSpecificCause().getMessage().substring(dataIntegrityViolationException.getMostSpecificCause().getMessage().indexOf("Detalhe: "));
        ErrorDetail errorDetail = ErrorDetail.builder().mensagem("Falha ao realizar uma operação com o banco de dados. " + message).build();
        log.error(dataIntegrityViolationException.getMostSpecificCause().getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.CONFLICT);
    }

}
