package day11;

import org.joml.Vector2i;
import utils.*;

import java.util.*;

public class One
{
    
    public static void main(String[] args)
    {
        IntCodeMachine      machine = new IntCodeMachine("day11.input");
        Map<Vector2i, Long> colors  = new HashMap<>();
        paint(machine, colors);
        
        System.out.println(colors.size());
    }
    
    public static void paint(IntCodeMachine brain, Map<Vector2i, Long> canvas)
    {
        Droid turtle = new Droid();
        while (brain.isRunning())
        {
            long color = canvas.getOrDefault(turtle.position(), 0L);
            brain.input(color);
            
            brain.queueOutput(2);
            if (!brain.isRunning())
            {
                break;
            }
            
            long newColor = brain.output();
            canvas.put(turtle.position(), newColor);
            
            long turn = brain.output();
            turtle.turn(turn);
            turtle.step();
        }
    }
}
