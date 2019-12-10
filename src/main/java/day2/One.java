package day2;

import utils.IntCodeMachine;

public class One
{
    public static void main(String[] args)
    {
        IntCodeMachine machine = new IntCodeMachine("day2.input");
        machine.setMemory(1, 12);
        machine.setMemory(2, 2);
        
        machine.runToEnd();
        System.out.println(machine.getMemory(0));
    }
}
