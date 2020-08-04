package com.nnk.springboot.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * These controllers get access to the homepage
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class IdDoesntExistException extends RuntimeException {

    public IdDoesntExistException(Integer id) {
        super("The specified Id: "+id+" do not exist");
    }
}
