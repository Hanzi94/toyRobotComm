package com.challenge.robotcomm.controller;

import com.challenge.robotcomm.model.TurnDirection;
import com.challenge.robotcomm.service.RobotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The rest api for toy robot with command set string for commands split with white space:
 *      PLACE X,Y,F
 *      MOVE
 *      LEFT
 *      RIGHT
 *      REPORT
 */
@RestController
public class RobotController {
    private final RobotService robotService;
    @Autowired
    public RobotController(RobotService robotService){
        this.robotService = robotService;
    }

    @GetMapping("/report")
    public ResponseEntity<String> report(){
        return ResponseEntity.ok("Output: " + robotService.report());
    }

    @PutMapping("/place")
    public ResponseEntity<String> place(int x, int y, String face){
        try{
            robotService.place(x, y, face);
            return ResponseEntity.ok().body("");
        }
        catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/right")
    public ResponseEntity<String> turnRight(){
        try{
            robotService.turn(TurnDirection.RIGHT);
            return ResponseEntity.ok().body("");
        }
        catch (IllegalArgumentException e){
            return new ResponseEntity<>(RobotService.ROBOT_MISSING, HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/left")
    public ResponseEntity<String> turnLeft(){
        try{
            robotService.turn(TurnDirection.LEFT);
            return ResponseEntity.ok().body("");
        }
        catch (IllegalArgumentException e){
            return new ResponseEntity<>(RobotService.ROBOT_MISSING, HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/move")
    public ResponseEntity<String> move(){
        try{
            robotService.move();
            return ResponseEntity.ok().body("");
        }
        catch (IndexOutOfBoundsException e){
            return new ResponseEntity<>(RobotService.OUT_OF_BOUNDS_CMD, HttpStatus.BAD_REQUEST);
        }
        catch (IllegalArgumentException e){
            return new ResponseEntity<>(RobotService.ROBOT_MISSING, HttpStatus.BAD_REQUEST);
        }
    }

}
