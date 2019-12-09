package day9;

import utils.IntCodeMachine;

public class Two
{
    public static void main(String[] args)
    {
        IntCodeMachine machine = new IntCodeMachine("day9.input");
        machine.runToInput();
        machine.input(2);
        while (machine.running)
        {
            machine.runToOutput();
        }
    }
}
