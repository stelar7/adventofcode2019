package day1;

import utils.sources.IntFromFileSupplier;

import java.util.function.*;

public class One
{
    public static void main(String[] args)
    {
        IntUnaryOperator toFuelValue = i -> Math.floorDiv(i, 3) - 2;
        
        int freq = IntFromFileSupplier.create("day1.input", false)
                                      .getDataSource()
                                      .stream()
                                      .mapToInt(Integer::valueOf)
                                      .map(toFuelValue)
                                      .sum();
        
        System.out.println(freq);
    }
}
