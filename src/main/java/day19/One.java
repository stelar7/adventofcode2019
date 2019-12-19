package day19;

import utils.IntCodeMachine;

public class One
{
    public static void main(String[] args)
    {
        IntCodeMachine machine = new IntCodeMachine("day19.input");
        
        int score = 0;
        for (int y = 0; y < 50; y++)
        {
            for (int x = 0; x < 50; x++)
            {
                score += testCoordinate(machine, x, y);
            }
        }
        
        System.out.println(score);
    }
    
    
    public static long testCoordinate(IntCodeMachine machine, int x, int y)
    {
        machine.reset();
        machine.input(x);
        machine.input(y);
        
        machine.queueOutput();
        return machine.output();
    }
}
