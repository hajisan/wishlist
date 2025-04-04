package com.example.wishlist.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ViewExceptionHandler {


    //Fejlbesked med 404-respons med vores egen 'not found exception'
    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleNotFound(Model model, ResourceNotFoundException e) {

        model.addAttribute("message", e.getMessage());
        return "error/404"; // thymeleaf skabelon i templates/error/404.html
    }


    // ------------ Buffer exceptions fra CHATTEN --------------------

    // Fejlbesked om foreign key violations (eller anden database-relateret fejl)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleSQLConstraint(Model model, DataIntegrityViolationException e) {
        model.addAttribute("message", "Databasefejl: Constraint violation - tjek relationer eller input-data.");
        return "error/database"; // thymeleaf skabelon i templates/error/database.html
    }

    // Håndterer generelle uforudsete fejl
    @ExceptionHandler(Exception.class)
    public String handleUnexpected(Model model, Exception e) {
        e.printStackTrace(); // til logning under udvikling
        model.addAttribute("message", "Noget gik galt. Prøv igen senere.");
        return "error/general"; // thymeleaf skabelon i templates/error/general.html
    }
}


