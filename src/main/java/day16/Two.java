package day16;

import utils.sources.StringFromFileSupplier;

import java.util.List;
import java.util.stream.Collectors;

public class Two
{
    public static void main(String[] args)
    {
        String        input  = StringFromFileSupplier.create("day16.input", false).get().repeat(10000);
        List<Integer> digits = input.chars().mapToObj(c -> Integer.parseInt(Character.toString(c))).collect(Collectors.toList());
        int           offset = Integer.parseInt(input.substring(0, 7));
        
        for (int i = 0; i < 100; i++)
        {
            One.doFFTRound(offset, digits);
        }
        
        String output = digits.stream().skip(offset).limit(8).map(String::valueOf).collect(Collectors.joining());
        System.out.println(output);
    }
}
