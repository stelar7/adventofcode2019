package day13;

import org.joml.*;
import utils.*;

import java.util.*;

public class One
{
    public static void main(String[] args)
    {
        IntCodeMachine machine = new IntCodeMachine("day13.input");
        machine.runToEnd();
        
        Map<Vector2i, Long> tiles = new HashMap<>();
        while (machine.peekOutput() != null)
        {
            long x  = machine.output();
            long y  = machine.output();
            long id = machine.output();
            
            tiles.put(new Vector2i((int) x, (int) y), id);
        }
        
        System.out.println(tiles.values().stream().filter(a -> a == 2L).mapToLong(a -> a).count());
    }
}
