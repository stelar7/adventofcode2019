package day5;

import utils.IntCodeMachine;

public class One
{
    public static void main(String[] args)
    {
        IntCodeMachine intcode = new IntCodeMachine("day5.input");
        intcode.input(1);
        intcode.runToEnd();
        System.out.println(intcode.lastOutput());
    }
}
