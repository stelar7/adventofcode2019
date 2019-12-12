package day12;

import day12.One.Planet;
import org.joml.*;
import utils.Utils;
import utils.sources.StringFromFileSupplier;

import java.util.*;
import java.util.stream.Collectors;

public class Two
{
    public static void main(String[] args)
    {
        String       regex = "<x=(?<x>-?\\d+), y=(?<y>-?\\d+), z=(?<z>-?\\d+)>";
        List<String> input = StringFromFileSupplier.create("day12.input", false).getDataSource();
        List<Planet> moons = input.stream()
                                  .map(s -> Utils.extractRegex(s, regex, "x", "y", "z"))
                                  .map(m -> new Vector3i(Integer.parseInt(m.get("x")), Integer.parseInt(m.get("y")), Integer.parseInt(m.get("z"))))
                                  .map(Planet::new)
                                  .collect(Collectors.toList());
        
        List<List<Planet>> planets = Utils.generateUniquePairs(moons);
        
        Set<String> seenX      = new HashSet<>();
        Set<String> seenY      = new HashSet<>();
        Set<String> seenZ      = new HashSet<>();
        int         repeatingX = -1;
        int         repeatingY = -1;
        int         repeatingZ = -1;
        
        for (int i = 0; i < Integer.MAX_VALUE; i++)
        {
            if (repeatingX >= 0 && repeatingY >= 0 && repeatingZ >= 0)
            {
                break;
            }
            
            One.step(planets, moons);
            
            if (checkRepeat(repeatingX, moons, 0, seenX))
            {
                repeatingX = i;
            }
            
            if (checkRepeat(repeatingY, moons, 1, seenY))
            {
                repeatingY = i;
            }
            
            if (checkRepeat(repeatingZ, moons, 2, seenZ))
            {
                repeatingZ = i;
            }
        }
        
        System.out.println(Utils.lcm(repeatingX, Utils.lcm(repeatingY, repeatingZ)));
    }
    
    public static boolean checkRepeat(int oldRepeat, List<Planet> moons, int index, Set<String> seen)
    {
        if (oldRepeat < 0)
        {
            StringBuilder coords = new StringBuilder();
            for (Planet moon : moons)
            {
                coords.append(moon.pos.get(index));
                coords.append(",");
                coords.append(moon.vel.get(index));
                coords.append(",");
            }
            if (seen.contains(coords.toString()))
            {
                return true;
            }
            seen.add(coords.toString());
        }
        
        return false;
    }
}
