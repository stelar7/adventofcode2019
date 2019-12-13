package day13;

import org.joml.Vector2i;
import utils.IntCodeMachine;

public class Two
{
    
    
    public static void main(String[] args)
    {
        final Vector2i bp    = new Vector2i(0);
        long           score = 0;
        
        IntCodeMachine machine = new IntCodeMachine("day13.input");
        machine.setInputFunction(() -> (long) Long.compare(bp.x, bp.y));
        machine.setMemory(0L, 2L, true);
        while (machine.isRunning())
        {
            machine.queueOutput(3);
            if (!machine.isRunning())
            {
                break;
            }
            
            long x    = machine.output();
            long y    = machine.output();
            long code = machine.output();
            if (x == -1 && y == 0)
            {
                score = code;
            }
            
            if (code == 3)
            {
                bp.y = (int) x;
            }
            if (code == 4)
            {
                bp.x = (int) x;
            }
        }
        
        System.out.println(score);
    }
}
