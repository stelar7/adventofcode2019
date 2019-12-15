package day15;

import org.joml.Vector2i;
import utils.*;
import utils.pathfinding.*;

import java.util.*;

public class One
{
    public static void main(String[] args)
    {
        IntCodeMachine machine  = new IntCodeMachine("day15.input");
        Vector2i       startPos = new Droid().position();
        
        Map<Vector2i, Long> map  = new HashMap<>();
        Vector2i            goal = doWalkDroidLeftWall(machine, map);
        
        AStarRouteFinder<VectorNode> finder = Utils.generatePathfindingGraph(map);
        List<VectorNode>             path   = finder.findRoute(new VectorNode(startPos), new VectorNode(goal));
        
        System.out.println("Commands needed: " + (path.size() - 1));
        System.out.println("Route length: " + path.size());
        System.out.println("Route: " + path);
    }
    
    static long unknownMark = 0;
    static long wallMark    = 1;
    static long airMark     = 2;
    static long goalMark    = 3;
    static long startMark   = 4;
    
    public static Vector2i doWalkDroidLeftWall(IntCodeMachine machine, Map<Vector2i, Long> map)
    {
        machine.reset();
        Droid     droid = new Droid();
        Vector2i  start = droid.position();
        Vector2i  goal  = null;
        Direction dir   = Direction.NORTH;
        
        Map<Vector2i, Integer> visited = new HashMap<>();
        map.put(start, startMark);
        
        while (true)
        {
            machine.input(dir.val);
            machine.queueOutput(1);
            long status = machine.output();
            
            if (status == 0L)
            {
                droid.move(dir.val);
                map.putIfAbsent(droid.position(), wallMark);
                droid.move(dir.opposite().val);
                
                dir = dir.left();
                continue;
            }
            
            if (status == 1L)
            {
                droid.move(dir.val);
                map.putIfAbsent(droid.position(), airMark);
                
                visited.putIfAbsent(droid.position(), 1);
                visited.computeIfPresent(droid.position(), (k, v) -> v + 1);
                
                if (goal != null && visited.getOrDefault(droid.position(), 0) > 1)
                {
                    break;
                }
            }
            
            if (status == 2L)
            {
                droid.move(dir.val);
                goal = droid.position();
                map.putIfAbsent(goal, goalMark);
            }
            
            dir = dir.right();
        }
        
        return goal;
    }
}
