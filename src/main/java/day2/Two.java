package day2;

import java.util.Arrays;

public class Two
{
    public static void main(String[] args)
    {
        for (int noun = 0; noun < 100; noun++)
        {
            for (int verb = 0; verb < 100; verb++)
            {
                int[] tape = getTape();
                computeTape(tape, noun, verb);
                
                if (tape[0] == 19690720)
                {
                    System.out.println(Arrays.toString(tape));
                    System.out.println(100 * noun + verb);
                    break;
                }
            }
        }
    }
    
    private static void computeTape(int[] tape, int noun, int verb)
    {
        tape[1] = noun;
        tape[2] = verb;
        for (int i = 0; i < tape.length; i += 4)
        {
            int op = tape[i];
            if (op == 99)
            {
                break;
            }
            
            One.inst_action
                    .get(op)
                    .apply(tape, i);
        }
    }
    
    static int[] tape_cache;
    
    private static int[] getTape()
    {
        if (tape_cache != null)
        {
            int[] inner = new int[256];
            System.arraycopy(tape_cache, 0, inner, 0, tape_cache.length);
            return inner;
        }
    
        tape_cache = One.getTape();
        return getTape();
    }
}
