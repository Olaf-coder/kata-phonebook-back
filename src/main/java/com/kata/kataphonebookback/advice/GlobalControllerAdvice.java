package com.kata.kataphonebookback.advice;

import com.kata.kataphonebookback.exceptions.InvalidDataException;
import com.kata.kataphonebookback.exceptions.RessourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {

//    public String
    @ExceptionHandler({RessourceNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(RessourceNotFoundException e) {
        return "NOPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP !!!";
    }

    @ExceptionHandler({InvalidDataException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public  String handleInvalidDataException(InvalidDataException e) {
        return "WIZZZZZZZZZZZZZZ !!!!!!";
    }


}
