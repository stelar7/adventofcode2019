package day16;

import utils.sources.StringFromFileSupplier;

import java.util.List;

public class One
{
    public static void main(String[] args)
    {
        //IntCodeMachine machine = new IntCodeMachine("day16.input");
        List<String> input = StringFromFileSupplier.create("day16.input", false).getDataSource();
    }
}
