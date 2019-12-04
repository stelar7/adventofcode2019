package day4;

import utils.sources.StringFromFileSupplier;

import java.util.stream.Collectors;

public class Two
{
    public static void main(String[] args)
    {
        String input = StringFromFileSupplier.create("day4.input", false).get();
        
        int min = Integer.parseInt(input.split("-")[0]);
        int max = Integer.parseInt(input.split("-")[1]);
        
        int match = 0;
        for (int i = min; i <= max; i++)
        {
            String number = String.valueOf(i);
            String sorted = number.chars()
                                  .sorted()
                                  .mapToObj(c -> String.valueOf((char) c))
                                  .collect(Collectors.joining(""));
            
            if (!number.equalsIgnoreCase(sorted))
            {
                continue;
            }
            
            long inarow = number.chars()
                                .mapToObj(c -> String.valueOf((char) c))
                                .collect(Collectors.groupingBy(c -> c, Collectors.counting()))
                                .values()
                                .stream()
                                .filter(v -> v == 2)
                                .count();
            if (inarow > 0)
            {
                match++;
            }
        }
        
        System.out.println(match);
    }
}
