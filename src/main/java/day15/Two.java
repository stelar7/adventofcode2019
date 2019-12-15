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
        
        Map<Vector2i, Long> map  = new HashMap<>();
        Vector2i            goal = One.doWalkDroidLeftWall(machine, map);
        List<VectorNode>    path = Utils.findLongestPath(map, goal);
        
        System.out.println("Fill time: " + (path.size() - 1));
        System.out.println("Longest route length: " + path.size());
        System.out.println("Route : " + path.size());
    }
}
