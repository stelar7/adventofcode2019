package day10;

import org.joml.Vector2i;
import utils.sources.StringFromFileSupplier;

import java.util.*;

public class One
{
    public static void main(String[] args)
    {
        List<Vector2i>         asteroids = getAsteroids();
        Map<Vector2i, Integer> scores    = generateAsteroidScores(asteroids);
        
        System.out.println(scores.values().stream().mapToInt(a -> a).max().getAsInt());
    }
    
    public static Map<Vector2i, Integer> generateAsteroidScores(List<Vector2i> asteroids)
    {
        Map<Vector2i, Integer> scores = new HashMap<>();
        for (Vector2i pointA : asteroids)
        {
            
            Set<Float> seen = new HashSet<>();
            for (Vector2i pointB : asteroids)
            {
                if (pointA.x == pointB.x && pointA.y == pointB.y)
                {
                    continue;
                }
                
                float angle = (float) Math.atan2(pointA.x - pointB.x, pointA.y - pointB.y);
                seen.add(angle);
            }
            
            scores.put(pointA, seen.size());
        }
        return scores;
    }
    
    public static List<Vector2i> getAsteroids()
    {
        List<String>   input     = StringFromFileSupplier.create("day10.input", false).getDataSource();
        List<Vector2i> asteroids = new ArrayList<>();
        for (int y = 0; y < input.size(); y++)
        {
            String line = input.get(y);
            for (int x = 0; x < line.toCharArray().length; x++)
            {
                if (line.toCharArray()[x] == '#')
                {
                    asteroids.add(new Vector2i(x, y));
                }
            }
        }
        return asteroids;
    }
}
