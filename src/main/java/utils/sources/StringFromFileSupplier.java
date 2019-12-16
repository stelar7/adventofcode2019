package utils.sources;

public class StringFromFileSupplier extends FromFileSupplier<String>
{
    public StringFromFileSupplier(String inputFile, boolean repeating)
    {
        super(inputFile, a -> a, repeating);
    }
    
    public StringFromFileSupplier(String inputFile, String separator, boolean repeating)
    {
        super(inputFile, separator, a -> a, repeating);
    }
    
    public static StringFromFileSupplier create(String inputFile, String separator, boolean infinite)
    {
        return new StringFromFileSupplier(inputFile, separator, infinite);
    }
    
    public static StringFromFileSupplier create(String inputFile, boolean infinite)
    {
        return new StringFromFileSupplier(inputFile, infinite);
    }
    
}
