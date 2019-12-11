package day5;

import utils.IntCodeMachine;

public class Two
{
    public static void main(String[] args)
    {
        IntCodeMachine intcode = new IntCodeMachine("day5.input");
        intcode.input(5);
        intcode.runToEnd();
        System.out.println(intcode.lastOutput());
    }
}
