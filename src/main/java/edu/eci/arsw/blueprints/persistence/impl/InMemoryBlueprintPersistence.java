/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 *
 * @author hcadavid
 */
@Component
@Qualifier("InMemory")
public class InMemoryBlueprintPersistence implements BlueprintsPersistence{

    private final Map<Tuple<String,String>,Blueprint> blueprints=new HashMap<>();

    public InMemoryBlueprintPersistence() {
        //load stub data
        Point[] pts=new Point[]{new Point(140, 140),new Point(115, 115)};
        Blueprint bp=new Blueprint("_authorname_", "_bpname_ ",pts);
        blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);

        // Tres puntos adicionales
        pts=new Point[]{new Point(432, 157),new Point(492, 647)};
        bp=new Blueprint("Juliana", "Blueprint1",pts);
        blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        pts=new Point[]{new Point(21, 12),new Point(164, 847)};
        bp=new Blueprint("Mariana", "Blueprint1",pts);
        blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        pts=new Point[]{new Point(20, 9),new Point(2002, 147)};
        bp=new Blueprint("Ximena", "Blueprint1",pts);
        blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        // Dos planos del mismo autor
        pts=new Point[]{new Point(10, 12),new Point(2, 259)};
        bp=new Blueprint("Santiago", "Blueprint1",pts);
        blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        pts=new Point[]{new Point(140, 140),new Point(140, 140)};
        bp=new Blueprint("Santiago", "Blueprint2",pts);
        blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);

    }    
    
    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(),bp.getName()))){
            throw new BlueprintPersistenceException("The given blueprint already exists: "+bp);
        }
        else{
            blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        }        
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) {
        return blueprints.get(new Tuple<>(author, bprintname));
    }

    @Override
    public Set<Blueprint> getBlueprintsByAuthor(String author) {
        Set<Tuple<String, String>> autorName = blueprints.keySet();
        Set<Blueprint> responses = new HashSet<>();
        for (Tuple<String, String> autNa : autorName){
            if (autNa.getElem1().equals(author)){
                responses.add(blueprints.get(autNa));
            }
        }
        return responses;
    }

    @Override
    public Set<Blueprint> getAllBlueprints() {
        Set<Blueprint> responses = new HashSet<>();
        responses.addAll(blueprints.values());
        return responses;
    }

    public void updateBlueprint(Point[] points, String author, String name) {
        Blueprint blueprint = getBlueprint(author, name);
        blueprint.setPoints(Arrays.asList(points));
    }

}
