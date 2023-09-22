/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/blueprints")
/**
 *
 * @author hcadavid
 */
public class BlueprintAPIController {
    @Autowired
    private BlueprintsServices blueprintServices;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getBlueprint() {
        try {
            System.out.println("metodo 1");
            //obtener datos que se enviarán a través del API en formato Json
            Gson gson = new Gson();
            String json = gson.toJson(blueprintServices.getAllBlueprints());
            return new ResponseEntity<>(json, HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error bla bla bla", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping("/{author}")
    public ResponseEntity<?> getBlueprintByAuthor(@PathVariable String author) {
        try {
            System.out.println("metodo 2");
            //obtener datos que se enviarán a través del API en formato Json
            Gson gson = new Gson();
            String json = gson.toJson(blueprintServices.getFilteredBlueprintsByAuthor(author));
            if (json.equals("[]")) {
                throw new ResourceNotFoundException();
            }
            return new ResponseEntity<>(json, HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}

