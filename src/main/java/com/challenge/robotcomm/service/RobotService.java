package com.challenge.robotcomm.service;

import com.challenge.robotcomm.model.Direction;
import com.challenge.robotcomm.model.Robot;
import com.challenge.robotcomm.model.TurnDirection;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RobotService {
    public static final int MAX_VALUE = 4;
    public static final int MIN_VALUE = 0;
    public static final String OUT_OF_BOUNDS_CMD = "Move action will cause falling off, ignored";
    public static final String ROBOT_MISSING = "Robot Missing";
    private static final List<Direction>DIRECTIONS
            = List.of(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST);

    private final Robot robot;

    public RobotService(){
        robot = new Robot();
    }



    /**
     * Place the toy robot on the table in position X,Y and facing NORTH, SOUTH, EAST or WEST.
     * @param x the x coordinate of the place on the table
     * @param y the y coordinate of the place on the table
     * @param faceStr robot's face should be one of NORTH, SOUTH, EAST or WEST
     */
    public synchronized void place(int x, int y, String faceStr) throws IllegalArgumentException{
        List<String>error = new ArrayList<>();
        Direction face = null;
        if(x < MIN_VALUE || x > MAX_VALUE || y < MIN_VALUE || y > MAX_VALUE){
            error.add("Place at (" + x + "," + y + ") will cause falling off");
        }
        try{
            face = Direction.valueOf(faceStr.trim().toUpperCase());
        }
        catch (IllegalArgumentException e){
            error.add("Illegal face: " + faceStr);
        }
        if(!error.isEmpty()){
            throw new IllegalArgumentException(String.join("\n", error));
        }
        robot.place(x, y, face);
    }

    /**
     * announce the X,Y and F of the robot
     * @return The string contains the X,Y and F of the robot
     */
    public synchronized String report(){
        if(!robot.isPlaced()){
            return "ROBOT MISSING";
        }
        return robot.toString();
    }

    /**
     * rotate the robot 90 degrees to LEFT or RIGHT in the specified direction without changing the position of the robot
     * @param turnDir The turning direction
     */
    public synchronized void turn(TurnDirection turnDir) throws IllegalArgumentException{
        if(robot.isPlaced()){
            int index = turnDir == TurnDirection.LEFT
                    ? DIRECTIONS.indexOf(robot.getFace()) - 1
                    : DIRECTIONS.indexOf(robot.getFace()) + 1;
            if(index < 0){
                index = DIRECTIONS.size() - 1;
            }
            else if(index > DIRECTIONS.size() - 1){
                index = 0;
            }
            robot.setFace(DIRECTIONS.get(index));
        }
        else{
            throw new IllegalArgumentException();
        }
    }

    /**
     * Move the toy robot one unit forward in the direction it is currently facing
     */
    public synchronized void move() throws IndexOutOfBoundsException, IllegalArgumentException{
        if(robot.isPlaced()){
            int x = robot.getX();
            int y = robot.getY();
            switch(robot.getFace()){
                case NORTH -> y ++;
                case SOUTH -> y --;
                case EAST -> x ++;
                case WEST -> x --;
            }
            if(x >= MIN_VALUE && x <= MAX_VALUE){
                robot.setX(x);
            }
            else{
                throw new IndexOutOfBoundsException();
            }
            if(y >= MIN_VALUE && y <= MAX_VALUE){
                robot.setY(y);
            }
            else{
                throw new IndexOutOfBoundsException();
            }
        }
        else{
            throw new IllegalArgumentException();
        }
    }
    /**
     * Initialise robot for unit test
     */
    public void initialRobot(){
        robot.setX(0);
        robot.setY(0);
        robot.setFace(null);
        robot.setPlaced(false);
    }
}
