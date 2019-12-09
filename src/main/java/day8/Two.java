package day8;

import utils.*;
import utils.sources.StringFromFileSupplier;

import java.util.*;
import java.util.stream.Collectors;

public class Two
{
    public static void main(String[] args)
    {
        String input = StringFromFileSupplier.create("day8.input", false).get();
        
        List<String> layers = Utils.chunk(input, 25 * 6);
        String       image  = "2".repeat(25 * 6);
        
        for (int i = layers.size() - 1; i > -1; i--)
        {
            image = parseLayer(image, layers.get(i));
        }
        
        System.out.println("█".repeat(25));
        Utils.chunk(image, 25).forEach(line -> System.out.println(line.chars().mapToObj(a -> a == '0' ? "█" : " ").collect(Collectors.joining())));
        System.out.println("█".repeat(25));
    }
    
    private static String parseLayer(String parsedLayer, String s)
    {
        StringBuilder sb = new StringBuilder(parsedLayer);
        for (int i = 0; i < s.length(); i++)
        {
            if (s.charAt(i) != '2')
            {
                sb.setCharAt(i, s.charAt(i));
            }
        }
        
        return sb.toString();
    }
}
