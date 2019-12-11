package utils.sources;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

public class IntFromFileSupplier extends FromFileSupplier<Integer>
{
    public IntFromFileSupplier(String inputFile, boolean repeating)
    {
        super(inputFile, Integer::parseInt, repeating);
    }
    
    public IntFromFileSupplier(String inputFile, Function<String, Integer> func, boolean repeating)
    {
        super(inputFile, func, repeating);
    }
    
    public IntFromFileSupplier(String inputFile, String separator, boolean repeating)
    {
        super(inputFile, separator, Integer::parseInt, repeating);
    }
    
    public IntFromFileSupplier(String inputFile, String separator, Function<String, Integer> func, boolean repeating)
    {
        super(inputFile, separator, func, repeating);
    }
    
    public static IntFromFileSupplier create(String inputFile, String separator, boolean infinite)
    {
        return new IntFromFileSupplier(inputFile, separator, infinite);
    }
    
    public static IntFromFileSupplier create(String inputFile, boolean infinite)
    {
        return new IntFromFileSupplier(inputFile, infinite);
    }
    
    public static <T> FromFileSupplier createFromCommaFile(String inputFile, boolean infinite)
    {
        return FromFileSupplier.<T>createFromArray(inputFile, s -> Arrays.stream(s.split(",")).filter(a -> !a.isEmpty()).map(Integer::parseInt).map(a -> (T) a).collect(Collectors.toList()), false);
    }
    
    public static <T> FromFileSupplier longCreateFromCommaFile(String inputFile, boolean infinite)
    {
        return FromFileSupplier.<T>createFromArray(inputFile, s -> Arrays.stream(s.split(",")).filter(a -> !a.isEmpty()).map(Long::parseLong).map(a -> (T) a).collect(Collectors.toList()), false);
    }
}
