package br.com.gumillanf.cooperativa.config.exception.handler;

import br.com.gumillanf.cooperativa.config.exception.ActionNotAllowedException;
import br.com.gumillanf.cooperativa.config.exception.InvalidCpfException;
import br.com.gumillanf.cooperativa.config.exception.ResourceNotFoundException;
import lombok.Getter;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        String userMessage = messageSource.getMessage("invalid.message", null, LocaleContextHolder.getLocale());
        String developerMessage = ex.getCause().toString();
        List<Erro> erros = Collections.singletonList(new Erro(userMessage, developerMessage));
        return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<Erro> erros = createErrorList(ex.getBindingResult());
        return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({ EmptyResultDataAccessException.class })
    public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
        String userMessage = messageSource.getMessage("resource.not-found", null, LocaleContextHolder.getLocale());
        String developerMessage = ex.toString();
        List<Erro> erros = Collections.singletonList(new Erro(userMessage, developerMessage));
        return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({ DataIntegrityViolationException.class } )
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        String userMessage = messageSource.getMessage("resource.operation-not-allowed", null, LocaleContextHolder.getLocale());
        String developerMessage = ExceptionUtils.getRootCauseMessage(ex);
        List<Erro> erros = Collections.singletonList(new Erro(userMessage, developerMessage));
        return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({ ActionNotAllowedException.class } )
    public ResponseEntity<Object> handleActionNotAllowedException(ActionNotAllowedException ex, WebRequest request) {
        String userMessage = ex.getMessage();
        String developerMessage = ex.getMessage();
        List<Erro> erros = Collections.singletonList(new Erro(userMessage, developerMessage));
        return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({ ResourceNotFoundException.class } )
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        String userMessage = ex.getMessage();
        String developerMessage = ex.getMessage();
        List<Erro> erros = Collections.singletonList(new Erro(userMessage, developerMessage));
        return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({ InvalidCpfException.class } )
    public ResponseEntity<Object> handleInvalidCpfException(InvalidCpfException ex, WebRequest request) {
        String userMessage = ex.getMessage();
        String developerMessage = ex.getMessage();
        List<Erro> erros = Collections.singletonList(new Erro(userMessage, developerMessage));
        return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    private List<Erro> createErrorList(BindingResult bindingResult) {
        List<Erro> erros = new ArrayList<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String userMessage = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            String developerMessage = fieldError.toString();
            erros.add(new Erro(userMessage, developerMessage));
        }

        return erros;
    }

    public static class Erro {

        public Erro(String userMessage, String developerMessage) {
            this.userMessage = userMessage;
            this.developerMessage = developerMessage;
        }

        @Getter
        private final String userMessage;

        @Getter
        private final String developerMessage;

    }

}
