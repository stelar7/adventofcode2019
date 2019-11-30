package utils.sources;

public class IntFromFileSupplier extends FromFileSupplier<Integer>
{
    public IntFromFileSupplier(String inputFile, boolean repeating)
    {
        super(inputFile, Integer::parseInt, repeating);
    }
    
    public IntFromFileSupplier(String inputFile, String separator, boolean repeating)
    {
        super(inputFile, separator, Integer::parseInt, repeating);
    }
    
    public static IntFromFileSupplier create(String inputFile, String separator, boolean infinite)
    {
        return new IntFromFileSupplier(inputFile, separator, infinite);
    }
    
    public static IntFromFileSupplier create(String inputFile, boolean infinite)
    {
        return new IntFromFileSupplier(inputFile, infinite);
    }
}
