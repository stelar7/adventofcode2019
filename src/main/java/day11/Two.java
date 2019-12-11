package day11;

import org.joml.*;
import utils.*;

import java.util.*;

public class Two
{
    public static void main(String[] args)
    {
        IntCodeMachine      machine = new IntCodeMachine("day11.input");
        Map<Vector2i, Long> colors  = new HashMap<>();
        colors.put(new Vector2i(0, 0), 1L);
        One.paint(machine, colors);
        
        Rectanglef rect = Utils.findBoundingBox(colors.keySet());
        for (int i = (int) rect.minY; i <= rect.maxY; i++)
        {
            for (int j = (int) rect.minX; j <= rect.maxX; j++)
            {
                long   color   = colors.getOrDefault(new Vector2i(j, i), 0L);
                String locChar = Utils.getBlockCharIfTrue(color == 1L);
                
                System.out.print(locChar);
            }
            System.out.println();
        }
    }
}
