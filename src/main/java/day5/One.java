package day5;

import utils.IntCodeMachine;

public class One
{
    public static void main(String[] args)
    {
        IntCodeMachine intcode = new IntCodeMachine("day5.input");
        intcode.runToInput();
        intcode.input(1);
        
        while (intcode.running)
        {
            intcode.runToOutput();
        }
        
        System.out.println(intcode.output());
    }
}
