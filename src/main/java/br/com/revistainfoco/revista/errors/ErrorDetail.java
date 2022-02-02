package br.com.revistainfoco.revista.errors;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDetail {
    private String mensagem;
}
