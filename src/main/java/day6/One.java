package day6;

import utils.sources.StringFromFileSupplier;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class One
{
    public static void main(String[] args)
    {
        List<String> inputs = StringFromFileSupplier.create("day6.input", false).getDataSource();
        
        Map<String, String> orbits = new HashMap<>();
        
        for (String input : inputs)
        {
            String base  = input.split("\\)")[0];
            String outer = input.split("\\)")[1];
            orbits.put(outer, base);
        }
        
        AtomicInteger orbitCount = new AtomicInteger();
        orbits.forEach((k, v) -> orbitCount.addAndGet(getOrbits(orbits, k)));
        
        // remove references to the "sun"
        orbitCount.addAndGet(-orbits.size());
        
        System.out.println(orbitCount.get());
    }
    
    private static int getOrbits(Map<String, String> map, String base)
    {
        return 1 + (map.containsKey(base) ? getOrbits(map, map.get(base)) : 0);
    }
}
