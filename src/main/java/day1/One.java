package day1;

import utils.sources.IntFromFileSupplier;

public class One
{
    public static void main(String[] args)
    {
        int freq = IntFromFileSupplier.create("day1.input", false)
                                      .getDataSource()
                                      .stream()
                                      .mapToInt(Integer::valueOf)
                                      .sum();
        
        System.out.println(freq);
    }
}
