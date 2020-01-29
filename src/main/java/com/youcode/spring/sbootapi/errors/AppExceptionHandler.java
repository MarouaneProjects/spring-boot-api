package com.youcode.spring.sbootapi.errors;

import com.youcode.spring.sbootapi.dtos.response.base.AppResponse;
import com.youcode.spring.sbootapi.dtos.response.base.ErrorResponse;
import com.youcode.spring.sbootapi.errors.exceptions.PermissionDeniedException;
import com.youcode.spring.sbootapi.errors.exceptions.ResourceNotFoundException;
import com.youcode.spring.sbootapi.dtos.response.base.AppResponse;
import com.youcode.spring.sbootapi.dtos.response.base.ErrorResponse;
import com.youcode.spring.sbootapi.errors.exceptions.PermissionDeniedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.Arrays;
import java.util.HashMap;

import static java.util.stream.Collectors.toList;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @Value("${app.debug}")
    boolean debug;

    @ExceptionHandler({PermissionDeniedException.class, AccessDeniedException.class})
    public ResponseEntity<AppResponse> permissionDenied(Exception ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<AppResponse> notFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(new ErrorResponse("Resource not found"), HttpStatus.NOT_FOUND);
    }

    /*
   @ExceptionHandler({ResourceNotFoundException.class})
   public RedirectView handleNotFound(RuntimeException ex, HttpServletRequest request) {
       String redirect = "/errors/404";
       RedirectView rw = new RedirectView(redirect);
       rw.setStatusCode(HttpStatus.MOVED_PERMANENTLY); // you might not need this
       FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);
       if (outputFlashMap != null) {
           outputFlashMap.put("message", ex.getLocalizedMessage());
       }
       return rw;
   }*/

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AppResponse> exception(Exception ex) {
        HashMap<String, Object> errors = new HashMap<>(debug ? 3 : 2);
        errors.put("error", ex.getClass().getName());
        errors.put("info", ex.getLocalizedMessage());
        if (debug)
            errors.put("stack_trace", Arrays.stream(ex.getStackTrace()).map(StackTraceElement::toString).collect(toList()));
        return new ResponseEntity<>(new ErrorResponse(errors), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
