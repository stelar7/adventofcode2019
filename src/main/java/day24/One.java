package day24;

import org.joml.Vector2i;
import utils.Utils;
import utils.sources.StringFromFileSupplier;

import java.util.*;

public class One
{
    public static void main(String[] args)
    {
        List<String>             input = StringFromFileSupplier.create("day24.input", false).getDataSource();
        Map<Vector2i, Long>      grid  = Utils.parseGrid(input);
        Set<Map<Vector2i, Long>> seen  = new HashSet<>();
        seen.add(grid);
        
        while (true)
        {
            grid = doIteration(grid);
            if (!seen.add(grid))
            {
                calculateScore(grid);
                break;
            }
        }
    }
    
    private static void calculateScore(Map<Vector2i, Long> grid)
    {
        int  pow   = 0;
        long score = 0;
        for (int y = 0; y < 5; y++)
        {
            for (int x = 0; x < 5; x++)
            {
                Vector2i k = new Vector2i(x, y);
                long     v = grid.get(k);
                
                if (v == '#')
                {
                    score += Math.pow(2, pow);
                }
                
                pow++;
            }
        }
        System.out.println(score);
    }
    
    private static Map<Vector2i, Long> doIteration(Map<Vector2i, Long> grid)
    {
        Map<Vector2i, Long> newGrid = new HashMap<>();
        
        grid.forEach((k, v) -> {
            Set<Vector2i> nearby = Utils.findNeighbourNodes4(grid, k, '#');
            boolean       isBug  = v == '#';
            
            if (isBug && nearby.size() != 1)
            {
                newGrid.put(k, (long) '.');
            }
            
            if (!isBug && (nearby.size() == 1 || nearby.size() == 2))
            {
                newGrid.put(k, (long) '#');
            }
            
            newGrid.putIfAbsent(k, v);
        });
        
        return newGrid;
    }
}
