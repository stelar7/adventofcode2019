package day10;

import org.joml.Vector2i;
import utils.*;

import java.util.*;

public class Two
{
    public static void main(String[] args)
    {
        List<Vector2i>         asteroids = One.getAsteroids();
        Map<Vector2i, Integer> scores    = One.generateAsteroidScores(asteroids);
        Vector2i               best      = Utils.getKeyForHighestValue(scores, a -> a);
        
        Map<Float, List<Vector2i>> targets = new HashMap<>();
        for (Vector2i pointB : asteroids)
        {
            if (pointB.equals(best))
            {
                continue;
            }
            
            float xOff   = best.x - pointB.x;
            float yOff   = best.y - pointB.y;
            float angle  = (float) Math.atan2(xOff, yOff);
            float result = (360 - angle) % 360;
            
            Utils.pushToMapList(targets, result, pointB);
        }
        
        List<Float> keys = new ArrayList<>(targets.keySet());
        Collections.sort(keys);
        
        RepeatingListIterator<Float> indecies = new RepeatingListIterator(keys);
        for (List<Vector2i> value : targets.values())
        {
            value.sort(Comparator.comparingDouble(best::distance));
        }
        
        int           remaining = 200;
        List<Integer> outs      = new ArrayList<>();
        while (remaining-- != 0)
        {
            float    val = indecies.next();
            Vector2i hit = targets.get(val).remove(0);
            outs.add(hit.x * 100 + hit.y);
        }
        
        System.out.println(outs.get(199));
    }
}
