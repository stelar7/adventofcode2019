package day14;

import utils.sources.StringFromFileSupplier;

import java.util.*;

public class Two
{
    public static void main(String[] args)
    {
        List<String> input = StringFromFileSupplier.create("day14.input", false).getDataSource();
        One.buildRecipies(input);
        
        long fuel   = 1;
        long target = 1000000000000L;
        
        while (true)
        {
            long nextIter = fuel + 1;
            
            long ore = One.calculateOre(nextIter);
            if (ore > target)
            {
                break;
            }
            
            double targetOverOre = (double) target / (double) ore;
            double bestGuess     = Math.floor(nextIter * targetOverOre);
            
            fuel = (long) Math.max(nextIter, bestGuess);
        }
        
        System.out.println(fuel);
    }
}
