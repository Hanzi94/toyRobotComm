package com.challenge.robotcomm.service;

import com.challenge.robotcomm.model.Direction;
import com.challenge.robotcomm.model.TurnDirection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RobotServiceTest {
    @Autowired
    RobotService robotService;
    @Test
    void testMovement() {
        int x1;
        int y1;
        // test all movement cases
        for(int x = RobotService.MIN_VALUE; x <= RobotService.MAX_VALUE; x++){
            for(int y = RobotService.MIN_VALUE; y <= RobotService.MAX_VALUE; y++){
                for (Direction dir : Direction.values()) {
                    x1 = x;
                    y1 = y;
                    robotService.place(x, y, dir.toString());

                    switch (dir){
                        case NORTH -> {
                            if(y < RobotService.MAX_VALUE){
                                y1 ++;
                                robotService.move();
                                Assertions.assertEquals(x + "," + y1 + "," + dir, robotService.report());
                            }
                            else{
                                Assertions.assertThrowsExactly(IndexOutOfBoundsException.class, ()->robotService.move());
                            }
                        }
                        case SOUTH -> {
                            if(y > RobotService.MIN_VALUE){
                                y1--;
                                robotService.move();
                                Assertions.assertEquals(x + "," + y1 + "," + dir, robotService.report());
                            }
                            else{
                                Assertions.assertThrowsExactly(IndexOutOfBoundsException.class, ()->robotService.move());
                            }
                        }
                        case EAST -> {
                            if(x < RobotService.MAX_VALUE){
                                x1++;
                                robotService.move();
                                Assertions.assertEquals(x1 + "," + y + "," + dir, robotService.report());
                            }
                            else{
                                Assertions.assertThrowsExactly(IndexOutOfBoundsException.class, ()->robotService.move());
                            }
                        }
                        case WEST -> {
                            if(x > RobotService.MIN_VALUE){
                                x1--;
                                robotService.move();
                                Assertions.assertEquals(x1 + "," + y + "," + dir, robotService.report());
                            }
                            else{
                                Assertions.assertThrowsExactly(IndexOutOfBoundsException.class, ()->robotService.move());
                            }
                        }
                    }
                }
            }
        }
    }
    @Test
    void testTurn() {
        // test all turning cases
        for (Direction dir : Direction.values()) {
            switch (dir) {
                case NORTH -> {
                    robotService.place(1, 1, dir.toString());
                    robotService.turn(TurnDirection.LEFT);
                    Assertions.assertEquals("1,1" + "," + Direction.WEST, robotService.report());
                    robotService.place(1, 1, dir.toString());
                    robotService.turn(TurnDirection.RIGHT);
                    Assertions.assertEquals("1,1" + "," + Direction.EAST, robotService.report());
                }
                case SOUTH -> {
                    robotService.place(1, 1, dir.toString());
                    robotService.turn(TurnDirection.LEFT);
                    Assertions.assertEquals("1,1" + "," + Direction.EAST, robotService.report());
                    robotService.place(1, 1, dir.toString());
                    robotService.turn(TurnDirection.RIGHT);
                    Assertions.assertEquals("1,1" + "," + Direction.WEST, robotService.report());
                }
                case EAST -> {
                    robotService.place(1, 1, dir.toString());
                    robotService.turn(TurnDirection.LEFT);
                    Assertions.assertEquals("1,1" + "," + Direction.NORTH, robotService.report());
                    robotService.place(1, 1, dir.toString());
                    robotService.turn(TurnDirection.RIGHT);
                    Assertions.assertEquals("1,1" + "," + Direction.SOUTH, robotService.report());
                }
                case WEST -> {
                    robotService.place(1, 1, dir.toString());
                    robotService.turn(TurnDirection.LEFT);
                    Assertions.assertEquals("1,1" + "," + Direction.SOUTH, robotService.report());
                    robotService.place(1, 1, dir.toString());
                    robotService.turn(TurnDirection.RIGHT);
                    Assertions.assertEquals("1,1" + "," + Direction.NORTH, robotService.report());
                }
            }
        }
    }

    @Test
    void testMissRobot(){
        // test no place robot report once
        robotService.initialRobot();
        Assertions.assertThrowsExactly(IllegalArgumentException.class, ()->robotService.move());
        Assertions.assertThrowsExactly(IllegalArgumentException.class, ()->robotService.turn(TurnDirection.LEFT));
        Assertions.assertThrowsExactly(IllegalArgumentException.class, ()->robotService.turn(TurnDirection.LEFT));
    }


    @Test
    void testErrorPlace(){
        // test place face error
        IllegalArgumentException exp = Assertions.assertThrowsExactly(IllegalArgumentException.class,
                ()->robotService.place(1, 1, "xrest"));
        Assertions.assertEquals("Illegal face: xrest", exp.getMessage());

        // test x < minimum
        exp = Assertions.assertThrowsExactly(IllegalArgumentException.class, ()->robotService.place(-1,4,"east"));
        Assertions.assertEquals("Place at (-1,4) will cause falling off", exp.getMessage());
        // test x > maximum
        exp = Assertions.assertThrowsExactly(IllegalArgumentException.class, ()->robotService.place(5,1, "east"));
        Assertions.assertEquals("Place at (5,1) will cause falling off", exp.getMessage());
        // test y < minimum
        exp = Assertions.assertThrowsExactly(IllegalArgumentException.class, ()->robotService.place(1,-1, "East"));
        Assertions.assertEquals("Place at (1,-1) will cause falling off", exp.getMessage());
        // test y > maximum
        exp = Assertions.assertThrowsExactly(IllegalArgumentException.class, ()->robotService.place(1,5, "EASt"));
        Assertions.assertEquals("Place at (1,5) will cause falling off", exp.getMessage());
        // test y > maximum and face error
        exp = Assertions.assertThrowsExactly(IllegalArgumentException.class, ()->robotService.place(1,5, "EASt2"));
        Assertions.assertEquals("Place at (1,5) will cause falling off\n" +
                "Illegal face: EASt2", exp.getMessage());
    }
}
