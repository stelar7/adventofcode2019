package day6;

import utils.sources.StringFromFileSupplier;

import java.util.*;

public class Two
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
        
        List<String> us   = new ArrayList<>();
        String       test = "YOU";
        while (orbits.containsKey(test))
        {
            us.add(test);
            test = orbits.get(test);
        }
        
        List<String> santa = new ArrayList<>();
        test = "SAN";
        while (orbits.containsKey(test))
        {
            santa.add(test);
            test = orbits.get(test);
        }
        
        for (int i = 0; i < us.size(); i++)
        {
            String parent = us.get(i);
            if (santa.contains(parent))
            {
                System.out.println(i + santa.indexOf(parent) - 2);
                break;
            }
        }
    }
}
