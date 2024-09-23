package com.example.note.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {



    // Genel bir Exception yakalama yöntemi
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {

        return new ResponseEntity<>("Beklenmeyen bir hata oluştu.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Not bulunamadığında fırlatılan özel bir hata için yakalama
    @ExceptionHandler(NoteNotFoundException.class)
    public ResponseEntity<Object> handleNoteNotFoundException(NoteNotFoundException ex, WebRequest request) {

        return new ResponseEntity<>("Not bulunamadı.", HttpStatus.NOT_FOUND);
    }

    // Kullanıcı yetkilendirilmediğinde fırlatılan bir hata
    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<Object> handleUnauthorizedAccessException(UnauthorizedAccessException ex, WebRequest request) {

        return new ResponseEntity<>("Yetkisiz erişim.", HttpStatus.UNAUTHORIZED);
    }

}
