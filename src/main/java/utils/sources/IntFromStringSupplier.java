package utils.sources;

import java.util.Arrays;
import java.util.function.*;
import java.util.stream.Collectors;

public class IntFromStringSupplier extends FromStringSupplier<Integer>
{
    public IntFromStringSupplier(String inputFile, boolean repeating)
    {
        super(inputFile, Integer::parseInt, repeating);
    }
    
    public IntFromStringSupplier(String inputFile, Function<String, Integer> func, boolean repeating)
    {
        super(inputFile, func, repeating);
    }
    
    public IntFromStringSupplier(String inputFile, String separator, boolean repeating)
    {
        super(inputFile, separator, Integer::parseInt, repeating);
    }
    
    public IntFromStringSupplier(String inputFile, String separator, Function<String, Integer> func, boolean repeating)
    {
        super(inputFile, separator, func, repeating);
    }
    
    public static IntFromStringSupplier create(String inputFile, String separator, boolean infinite)
    {
        return new IntFromStringSupplier(inputFile, separator, infinite);
    }
    
    public static IntFromStringSupplier create(String inputFile, boolean infinite)
    {
        return new IntFromStringSupplier(inputFile, infinite);
    }
    
    public static <T> Supplier createFromCommaString(String inputFile, boolean infinite)
    {
        return FromStringSupplier.<T>createFromArray(inputFile, s -> Arrays.stream(s.split(",")).filter(a -> !a.isEmpty()).map(Integer::parseInt).map(a -> (T) a).collect(Collectors.toList()), infinite);
    }
    
    public static <T> Supplier longCreateFromCommaFile(String inputFile, boolean infinite)
    {
        return FromStringSupplier.<T>createFromArray(inputFile, s -> Arrays.stream(s.split(",")).filter(a -> !a.isEmpty()).map(Long::parseLong).map(a -> (T) a).collect(Collectors.toList()), infinite);
    }
}
