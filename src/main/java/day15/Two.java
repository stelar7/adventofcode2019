package day15;

import org.joml.*;
import utils.*;
import utils.pathfinding.*;

import java.util.*;

public class Two
{
    public static void main(String[] args)
    {
        IntCodeMachine machine = new IntCodeMachine("day15.input");
        
        // walk a few times to ensure a complete map
        Map<Vector2i, Long> map = new HashMap<>();
        for (int i = 0; i < 5; i++)
        {
            One.doWalkDroid(machine, map);
        }
        
        
        Vector2i                goal   = One.doWalkDroid(machine, map);
        RouteFinder<VectorNode> finder = Utils.generatePathfindingGraph(map);
        
        int        max         = Integer.MIN_VALUE;
        Rectanglef boundingBox = Utils.findBoundingBox(map.keySet());
        for (int i = (int) boundingBox.minY; i < boundingBox.maxY; i++)
        {
            for (int j = (int) boundingBox.minX; j < boundingBox.maxX; j++)
            {
                if (map.getOrDefault(new Vector2i(j, i), 0L) == 1L)
                {
                    continue;
                }
                
                List<VectorNode> path = finder.findRoute(new VectorNode(goal), new VectorNode(new Vector2i(j, i)));
                if (path.size() > max)
                {
                    max = path.size();
                }
            }
        }
        
        
        System.out.println("Route length: " + max);
    }
}
