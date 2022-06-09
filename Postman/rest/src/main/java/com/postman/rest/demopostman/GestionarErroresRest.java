package com.postman.rest.demopostman;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GestionarErroresRest extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = RuntimeException.class)
    protected ResponseEntity<MensajeDeErroresRest> manejadorDeConflictos(RuntimeException e, WebRequest webRequest){

        MensajeDeErroresRest mensajeDeErroresRest = new MensajeDeErroresRest(e.getMessage(), e.getCause().toString());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensajeDeErroresRest);

    }
}
