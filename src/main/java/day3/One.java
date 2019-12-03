package day3;

import org.joml.Vector2i;
import utils.Utils;
import utils.sources.StringFromFileSupplier;

import java.util.*;

public class One
{
    static Map<String, Vector2i> directions = new HashMap<>()
    {{
        put("U", new Vector2i(-1, 0));
        put("D", new Vector2i(1, 0));
        put("L", new Vector2i(0, -1));
        put("R", new Vector2i(0, 1));
    }};
    
    public static void main(String[] args)
    {
        List<String> wires = StringFromFileSupplier.create("day3.input", false).getDataSource();
        
        List<Set<Vector2i>> seens = new ArrayList<>();
        for (String wire : wires)
        {
            Vector2i      pos          = new Vector2i(0, 0);
            Set<Vector2i> seen         = new HashSet<>();
            String[]      instructions = wire.split(",");
            
            for (String instruction : instructions)
            {
                String   dir      = instruction.substring(0, 1);
                int      distance = Integer.parseInt(instruction.substring(1));
                Vector2i offset   = directions.get(dir);
                
                for (int i = 0; i < distance; i++)
                {
                    pos = pos.add(offset, new Vector2i());
                    seen.add(pos);
                }
            }
            seens.add(seen);
        }
        
        Set<Vector2i> intersections = Utils.intersection(seens.get(0), seens.get(1));
        System.out.println(intersections.stream().mapToInt(a -> Math.abs(a.x) + Math.abs(a.y)).min().getAsInt());
    }
}
