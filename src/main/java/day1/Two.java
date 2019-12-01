package day1;

import utils.sources.IntFromFileSupplier;

import java.util.*;
import java.util.function.*;

public class Two
{
    public static void main(String[] args)
    {
        IntUnaryOperator toFuelValue = i -> Math.floorDiv(i, 3) - 2;
        Queue<Integer> values = new ArrayDeque<>(IntFromFileSupplier.create("day1.input", false)
                                                                    .getDataSource());
        
        long total = 0;
        while (values.peek() != null)
        {
            int val  = values.poll();
            int real = toFuelValue.applyAsInt(val);
            if (real > 0)
            {
                total += real;
                values.add(real);
            }
        }
        
        System.out.println(total);
    }
}
