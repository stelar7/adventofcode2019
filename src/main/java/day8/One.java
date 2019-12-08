package day8;

import utils.*;
import utils.sources.StringFromFileSupplier;

import java.util.*;

public class One
{
    public static void main(String[] args)
    {
        String input = StringFromFileSupplier.create("day8.input", false).get();
        
        List<String> layers = Utils.chunk(input, 25 * 6);
        int          index  = Utils.getIndexOfLowestValue(layers, l -> Utils.numberCount(l)[0]);
        
        int[] vals = Utils.numberCount(layers.get(index));
        System.out.println(vals[1] * vals[2]);
        
    }
}
