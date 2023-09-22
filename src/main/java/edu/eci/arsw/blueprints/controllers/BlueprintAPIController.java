/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
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
            //obtener datos que se enviarán a través del API en formato Json
            Gson gson = new Gson();
            String json = gson.toJson(blueprintServices.getFilteredAllBlueprints());
            return new ResponseEntity<>(json, HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Algo salio mal ;-;", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping("/{author}")
    public ResponseEntity<?> getBlueprintByAuthor(@PathVariable String author) {
        try {
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

    @RequestMapping("/{author}/{bpname}")
    public ResponseEntity<?> getBlueprintByAuthor(@PathVariable String author, @PathVariable String bpname) {
        try {
            //obtener datos que se enviarán a través del API en formato Json
            Gson gson = new Gson();
            String json = gson.toJson(blueprintServices.getFilteredBlueprint(author, bpname));
            if (json.equals("[]")) {
                throw new ResourceNotFoundException();
            }
            return new ResponseEntity<>(json, HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addNewBlueprint(@RequestBody Blueprint blueprint){
        try {
            //registrar dato
            blueprintServices.addNewBlueprint(blueprint);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Algo salio mal ;-;",HttpStatus.FORBIDDEN);
        }

    }

    @PutMapping("/{author}/{bpname}")
    public ResponseEntity<?> updateBlueprintAuthorName(@RequestBody Point[] points, @PathVariable("author") String author, @PathVariable("bpname") String name){
        try {
            //registrar dato
            blueprintServices.updateBlueprintAuthorName(points, author, name);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Algo salio mal ;-;",HttpStatus.FORBIDDEN);
        }

    }
}

