package day19;

import utils.IntCodeMachine;

public class Two
{
    public static void main(String[] args)
    {
        IntCodeMachine machine = new IntCodeMachine("day19.input");
        int            x       = 500;
        int            y       = 500;
        
        while (true)
        {
            if (One.testCoordinate(machine, x, y) == 1L)
            {
                if (One.testCoordinate(machine, x - 99, y + 99) == 1L)
                {
                    System.out.println((x - 99) * 10000 + y);
                    break;
                } else
                {
                    x++;
                }
            } else
            {
                y++;
            }
        }
    }
}
