package br.edu.imepac.atendimento.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ClinicaMedicaHandleExceptions {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado, tente novamente!"); // Returns a response with status 500.
    }


    @ExceptionHandler(AuthenticationClinicaMedicaException.class)
    public ResponseEntity<String> handleUnauthorized(Exception e) {
        log.error("An error occurred: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Dados de acesso inválido!"); // Returns a response with status 401.
    }
}