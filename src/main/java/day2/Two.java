package day2;

import utils.sources.StringFromFileSupplier;

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
            
            One.inst_action.get(op).apply(tape, i);
        }
    }
    
    static int[] tape;
    
    private static int[] getTape()
    {
        if (tape != null)
        {
            int[] inner = new int[256];
            System.arraycopy(tape, 0, inner, 0, tape.length);
            return inner;
        }
        
        tape = new int[256];
        String input = StringFromFileSupplier.create("day2.input", false).get();
        for (int i = 0; i < input.split(",").length; i++)
        {
            tape[i] = Integer.parseInt(input.split(",")[i]);
        }
        
        return getTape();
    }
}
