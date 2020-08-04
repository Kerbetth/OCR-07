package com.nnk.springboot.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * These controllers get access to the homepage
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class IdAlreadyExistException extends RuntimeException {

    public IdAlreadyExistException(Integer id) {
        super("The specified Id: "+id+" already exist");
    }
}
