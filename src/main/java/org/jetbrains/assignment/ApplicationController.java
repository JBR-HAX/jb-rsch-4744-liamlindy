package org.jetbrains.assignment;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Controller
public class ApplicationController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

//    @GetMapping("/hello-world")
//    @ResponseBody
//    public DirectionList sayHello(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) {
//        return new DirectionList(counter.incrementAndGet(), String.format(template, name));
//    }
    @PostMapping("/locations")
    public ResponseEntity<LinkedList> getLocations(@RequestBody List<Locations> directions) {
        LinkedList output = new LinkedList();
        HashMap<String, Integer> current_location = new HashMap<>();
        current_location.put("x", 0);
        current_location.put("y", 0);
        for (int i = 0; i < directions.size(); i++){
            int x = current_location.get("x");
            int y = current_location.get("y");
            if( directions.get(i).direction().equals("EAST")) {
                current_location.put("x",x + directions.get(i).steps());
            }else if( directions.get(i).direction().equals("WEST")) {
                current_location.put("x",x -  directions.get(i).steps());
            }else if( directions.get(i).direction().equals("NORTH")) {
                current_location.put("y",y + directions.get(i).steps());
            }else if( directions.get(i).direction().equals("SOUTH")) {
                current_location.put("y",y - directions.get(i).steps());
            }
            output.add(current_location.clone());
        }
        return ResponseEntity.ok(output);
    }
    @PostMapping("/moves")
    public ResponseEntity<LinkedList> getMoves(@RequestBody List<Moves> moves) {
        LinkedList output = new LinkedList();
        HashMap<String, Integer> current_location = new HashMap<>();
        current_location.put("x", moves.get(0).x());
        current_location.put("y", moves.get(0).x());
        for (int i = 1; i < moves.size(); i++){
            HashMap<String, Object> directions = new HashMap<>();
            String direction = "";
            int steps = 0;

            int x = current_location.get("x");
            int y = current_location.get("y");
            int x_d = moves.get(i).x();
            int y_d = moves.get(i).y();
            int x_math = x - x_d;
            int y_math = y - y_d;

            if ( x > x_d && y == y_d){
                direction = "WEST";
                steps = Math.abs(x_math);
            }
            if ( x < x_d && y == y_d){
                direction = "EAST";
                steps = Math.abs(x_math);
            }
            if ( x == x_d && y > y_d){
                direction = "SOUTH";
                steps = Math.abs(y_math);
            }
            if ( x == x_d && y < y_d){
                direction = "NORTH";
                steps = Math.abs(y_math);
            }
            current_location.put("x",x_d);
            current_location.put("y",y_d);
            directions.put("direction",direction);
            directions.put("steps",steps);
            output.add(directions.clone());


            System.out.print(" X " + x);
            System.out.print(" Y " + y);
            System.out.print(" NEW_X " + x_d);
            System.out.print(" NEW_Y " + y_d);
            System.out.print(" MATH_X " + x_math);
            System.out.print(" MATH_Y " + y_math);
            System.out.println(" OUTPUT " + output);

        }
        return ResponseEntity.ok(output);
    }

}