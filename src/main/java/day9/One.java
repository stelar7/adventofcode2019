package day9;

import utils.IntCodeMachine;

public class One
{
    public static void main(String[] args)
    {
        IntCodeMachine machine = new IntCodeMachine("day9.input");
        machine.runToInput();
        machine.input(1);
        while (machine.running)
        {
            machine.runToOutput();
        }
    }
}
