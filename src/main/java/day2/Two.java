package day2;

import utils.IntCodeMachine;

public class Two
{
    public static void main(String[] args)
    {
        IntCodeMachine machine = new IntCodeMachine("day2.input");
        for (int noun = 0; noun < 100; noun++)
        {
            for (int verb = 0; verb < 100; verb++)
            {
                machine.reset();
                machine.setMemory(1, noun);
                machine.setMemory(2, verb);
                machine.runToEnd();
                
                if (machine.getMemory(0) == 19690720)
                {
                    System.out.println(100 * noun + verb);
                    break;
                }
            }
        }
    }
}
