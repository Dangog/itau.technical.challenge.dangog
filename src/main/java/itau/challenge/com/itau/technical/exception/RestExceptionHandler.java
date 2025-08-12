package itau.challenge.com.itau.technical.exception; // (crie este pacote se não existir)

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class RestExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Void> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        logger.warn("Malformed JSON request: {}", ex.getMessage()); // Loga a causa do erro
        return ResponseEntity.badRequest().build(); // Retorna 400 com corpo vazio para os erros de
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Void> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String error = String.format("The parameter '%s' with value '%s' is not a valid '%s'",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());
        logger.warn("Argument type mismatch: {}", error);
        return ResponseEntity.badRequest().build(); // Retorna 400 com corpo vazio
    }

}