package day24;

import org.joml.Vector2i;
import utils.Utils;
import utils.sources.StringFromFileSupplier;

import java.util.*;

public class Two
{
    public static void main(String[] args)
    {
        List<String>        input = StringFromFileSupplier.create("day24.input", false).getDataSource();
        Map<Vector2i, Long> grid  = Utils.parseGrid(input);
        
        Map<Integer, Map<Vector2i, Long>> levels = new HashMap<>();
        
        for (int i = 0; i < 200; i++)
        {
            grid = doIteration(levels);
        }
    }
    
    private static Map<Vector2i, Long> doIteration(Map<Integer, Map<Vector2i, Long>> levels)
    {
        Map<Vector2i, Long> newGrid = new HashMap<>();
        
        
        return newGrid;
    }
}
