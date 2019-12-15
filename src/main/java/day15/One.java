package day15;

import org.joml.Vector2i;
import utils.*;
import utils.pathfinding.*;

import java.util.*;

public class One
{
    public static void main(String[] args)
    {
        IntCodeMachine machine = new IntCodeMachine("day15.input");
        Vector2i       start   = new Droid().position();
        
        Map<Vector2i, Long> map  = new HashMap<>();
        Vector2i            goal = doWalkDroid(machine, map);
        
        RouteFinder<VectorNode> finder = Utils.generatePathfindingGraph(map);
        List<VectorNode>        path   = finder.findRoute(new VectorNode(start), new VectorNode(goal));
        
        System.out.println("Route length: " + path.size());
        System.out.println("Route: " + path);
    }
    
    public static Vector2i doWalkDroid(IntCodeMachine machine, Map<Vector2i, Long> map)
    {
        machine.reset();
        
        long             dir    = 1L;
        Droid            droid  = new Droid();
        Vector2i         start  = droid.position();
        Vector2i         goal;
        java.util.Random random = new java.util.Random();
        while (true)
        {
            dir = random.nextInt(4) + 1;
            machine.input(dir);
            machine.queueOutput(1);
            long status = machine.output();
            
            if (status == 0L)
            {
                Vector2i other = droid.position();
                if (dir == 1L)
                {
                    other.add(0, -1);
                }
                if (dir == 2L)
                {
                    other.add(0, 1);
                }
                if (dir == 3L)
                {
                    other.add(-1, 0);
                }
                if (dir == 4L)
                {
                    other.add(1, 0);
                }
                
                map.put(other, 1L);
            }
            
            if (status == 1L)
            {
                droid.move(dir);
                map.put(droid.position(), 2L);
                if (droid.position().equals(start))
                {
                    map.put(start, 4L);
                }
            }
            
            if (status == 2L)
            {
                goal = droid.position();
                map.put(goal, 3L);
                break;
            }
        }
        return goal;
    }
    
}
