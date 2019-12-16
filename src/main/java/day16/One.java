package day16;

import utils.Utils;
import utils.sources.*;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.*;

public class One
{
    public static void main(String[] args)
    {
        String        input  = StringFromFileSupplier.create("day16.input", false).get();
        List<Integer> digits = input.chars().mapToObj(c -> Integer.parseInt(Character.toString(c))).collect(Collectors.toList());
        
        for (int fftRound = 0; fftRound < 100; fftRound++)
        {
            doFFTRound(0, digits);
        }
        
        String output = digits.stream().limit(8).map(String::valueOf).collect(Collectors.joining());
        System.out.println(output);
    }
    
    public static void doFFTRound(int offset, List<Integer> digits)
    {
        if (offset > (digits.size() / 2))
        {
            doFFTRoundPartialSum(offset, digits);
        } else
        {
            doFFTRoundPattern(digits);
        }
    }
    
    private static void doFFTRoundPartialSum(int offset, List<Integer> digits)
    {
        final int[] partial = new int[]{IntStream.range(offset, digits.size()).map(digits::get).sum()};
        
        for (int index = offset; index < digits.size(); index++)
        {
            int modMe = partial[0];
            partial[0] -= digits.get(index);
            
            digits.set(index, Utils.realMod(modMe, 10));
        }
    }
    
    private static void doFFTRoundPattern(List<Integer> digits)
    {
        List<Integer> next = new ArrayList<>();
        for (int i = 0; i < digits.size(); i++)
        {
            Supplier<Integer> supplier = generatePattern(i + 1);
            
            int value = digits.stream().mapToInt(a -> a * supplier.get()).sum();
            next.add(Math.abs(value % 10));
        }
        
        digits.clear();
        digits.addAll(next);
    }
    
    private static Supplier<Integer> generatePattern(int index)
    {
        String            pattern  = "0,".repeat(index) + "1,".repeat(index) + "0,".repeat(index) + "-1,".repeat(index);
        Supplier<Integer> supplier = IntFromStringSupplier.createFromCommaString(pattern, true);
        supplier.get();
        
        return supplier;
    }
}
