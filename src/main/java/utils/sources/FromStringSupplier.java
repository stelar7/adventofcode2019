package utils.sources;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class FromStringSupplier<T> implements Supplier<T>, SourceSupplier<T>
{
    private List<T> data;
    private int     index = 0;
    private boolean repeating;
    
    public FromStringSupplier(List<T> values, boolean repeating)
    {
        this.repeating = repeating;
        this.data = values;
    }
    
    public FromStringSupplier(String inputString, Function<String, T> mapping, boolean repeating)
    {
        this.repeating = repeating;
        this.data = Stream.of(inputString)
                          .map(mapping)
                          .collect(Collectors.toList());
    }
    
    public FromStringSupplier(String inputString, String separator, Function<String, T> mapping, boolean repeating)
    {
        this.repeating = repeating;
        this.data = Arrays.stream(inputString.split(separator))
                          .map(mapping)
                          .collect(Collectors.toList());
    }
    
    public static <T> FromStringSupplier createFromArray(String inputString, Function<String, List<T>> mapping, boolean repeating)
    {
        return new FromStringSupplier(
                Arrays.stream(inputString.split("\n"))
                      .flatMap(a -> Stream.of(mapping.apply(a)))
                      .flatMap(Collection::stream)
                      .collect(Collectors.toList()),
                repeating);
    }
    
    public static <T> FromStringSupplier create(String inputFile, String separator, Function<String, T> mapping, boolean infinite)
    {
        return new FromStringSupplier(inputFile, separator, mapping, infinite);
    }
    
    public static <T> FromStringSupplier create(String inputFile, Function<String, T> mapping, boolean infinite)
    {
        return new FromStringSupplier(inputFile, mapping, infinite);
    }
    
    @Override
    public List<T> getDataSource()
    {
        return data;
    }
    
    public String asString()
    {
        return data.stream().map(Object::toString).collect(Collectors.joining());
    }
    
    @Override
    public T get()
    {
        if (repeating)
        {
            return data.get(index++ % data.size());
        }
        
        return index > data.size() - 1 ? null : data.get(index++);
    }
}
