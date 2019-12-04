package day3;

import org.joml.Vector2i;
import utils.Utils;
import utils.sources.StringFromFileSupplier;

import java.util.*;

public class Two
{
    public static void main(String[] args)
    {
        List<String> wires         = StringFromFileSupplier.create("day3.input", false).getDataSource();
        
        List<Map<Vector2i, Integer>> seens = new ArrayList<>();
        for (String wire : wires)
        {
            String[]               instructions = wire.split(",");
            Vector2i               pos          = new Vector2i(0, 0);
            Map<Vector2i, Integer> seen         = new HashMap<>();
            int                    steps        = 0;
            
            for (String instruction : instructions)
            {
                String   dir      = instruction.substring(0, 1);
                int      distance = Integer.parseInt(instruction.substring(1));
                Vector2i offset   = One.directions.get(dir);
                
                for (int i = 0; i < distance; i++)
                {
                    pos = pos.add(offset, new Vector2i());
                    steps++;
                    
                    if (!seen.containsKey(pos))
                    {
                        seen.put(pos, steps);
                    }
                }
            }
            seens.add(seen);
        }
        
        Set<Vector2i> intersections = Utils.intersection(seens.get(0).keySet(), seens.get(1).keySet());
        System.out.println(intersections.stream().mapToInt(i -> seens.get(0).get(i) + seens.get(1).get(i)).min().getAsInt());
    }
}
