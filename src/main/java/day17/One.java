package day17;

import org.joml.Vector2i;
import utils.*;

import java.util.*;

public class One
{
    public static void main(String[] args)
    {
        IntCodeMachine      machine = new IntCodeMachine("day17.input");
        Map<Vector2i, Long> grid    = generateGrid(machine);
        
        List<Vector2i> intersections = new ArrayList<>();
        grid.forEach((k, v) -> {
            if (v == 35L)
            {
                Set<Vector2i> nearby = Utils.findNeighbourNodes4(grid, k, 35L);
                if (nearby.size() >= 3)
                {
                    intersections.add(k);
                    grid.put(k, (long) 'O');
                }
            }
        });
        
        System.out.println(intersections.stream().mapToInt(a -> a.x * a.y).sum());
    }
    
    public static Map<Vector2i, Long> generateGrid(IntCodeMachine machine)
    {
        machine.runToEnd();
        int                 x    = 0;
        int                 y    = 0;
        Map<Vector2i, Long> grid = new HashMap<>();
        while (machine.peekOutput() != null)
        {
            long output = machine.output();
            grid.put(new Vector2i(x, y), output);
            
            x++;
            if (output == 10)
            {
                y++;
                x = 0;
            }
        }
        machine.reset();
        return grid;
    }
}
