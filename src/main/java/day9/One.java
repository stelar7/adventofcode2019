package day9;

import utils.IntCodeMachine;

public class One
{
    public static void main(String[] args)
    {
        IntCodeMachine machine = new IntCodeMachine("day9.input");
        machine.input(1);
        machine.runToEnd();
        System.out.println(machine.output());
    }
}
