package br.com.tjrj.biblioteca.exception.handler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import br.com.tjrj.biblioteca.exception.base.ApiError;
import br.com.tjrj.biblioteca.exception.base.ApiException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiError> handleApiException(ApiException ex, HttpServletRequest req, Locale locale) {
        log.error("Erro tratado: {}", ex.getMessage(), ex);
        String mensagemTraduzida = messageSource.getMessage(ex.getMessageKey(), ex.getArgs(), locale);
        ApiError erro = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(ex.getStatus().value())
                .mensagem(mensagemTraduzida)
                .caminho(req.getRequestURI())
                .build();
        return ResponseEntity.status(ex.getStatus()).body(erro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        log.error("Erro de validação: {}", ex.getMessage(), ex);
        Map<String, String> campos = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err ->
                campos.put(err.getField(), err.getDefaultMessage())
        );

        ApiError erro = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .mensagem("Erro de validação: Um ou mais campos estão inválidos.")
                .caminho(req.getRequestURI())
                .build();

        return ResponseEntity.badRequest()
                .body(Map.of("erro", erro, "campos", campos));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpServletRequest req) {
        log.error("Erro de leitura do corpo da requisição: {}", ex.getMessage(), ex);
        return responder(HttpStatus.BAD_REQUEST, "O corpo da requisição está malformado ou inválido.", req);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiError> handleNoHandlerFound(NoHandlerFoundException ex, HttpServletRequest req) {
        log.error("Endpoint não encontrado: {}", ex.getMessage(), ex);
        return responder(HttpStatus.NOT_FOUND, "O endpoint solicitado não foi encontrado.", req);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest req) {
        log.error("Erro de validação de parâmetros: {}", ex.getMessage(), ex);
        String mensagem = ex.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining(", "));
        return responder(HttpStatus.BAD_REQUEST, mensagem, req);
    }

    @ExceptionHandler({
            IllegalArgumentException.class,
            MethodArgumentTypeMismatchException.class
    })
    public ResponseEntity<ApiError> handleBadRequest(Exception ex, HttpServletRequest req) {
        log.error("Erro de requisição inválida: {}", ex.getMessage(), ex);
        return responder(HttpStatus.BAD_REQUEST, ex.getMessage(), req);
    }

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDenied(Exception ex, HttpServletRequest req) {
        log.error("Acesso negado: {}", ex.getMessage(), ex);
        return responder(HttpStatus.FORBIDDEN, "Acesso negado", req);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex, HttpServletRequest req) {
        log.error("Erro interno inesperado: {}", ex.getMessage(), ex);
        return responder(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno inesperado", req);
    }

    private ResponseEntity<ApiError> responder(HttpStatus status, String mensagem, HttpServletRequest req) {
        ApiError erro = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .mensagem(mensagem)
                .caminho(req.getRequestURI())
                .build();
        return ResponseEntity.status(status).body(erro);
    }
}