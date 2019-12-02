package day2;

import utils.sources.*;

import java.util.*;
import java.util.function.BiFunction;

public class One
{
    static Map<Integer, BiFunction<int[], Integer, Void>> inst_action = new HashMap<>()
    {{
        put(99, (a, b) -> null);
        
        put(1, (tape, index) -> {
            int paramA = tape[index + 1];
            int paramB = tape[index + 2];
            int paramC = tape[index + 3];
            
            int valueA = tape[paramA];
            int valueB = tape[paramB];
            
            int result = valueA + valueB;
            tape[paramC] = result;
            return null;
        });
        
        put(2, (tape, index) -> {
            int paramA = tape[index + 1];
            int paramB = tape[index + 2];
            int paramC = tape[index + 3];
            
            int valueA = tape[paramA];
            int valueB = tape[paramB];
            
            int result = valueA * valueB;
            tape[paramC] = result;
            return null;
        });
    }};
    
    public static void main(String[] args)
    {
        int[] tape = getTape();
        tape[1] = 12;
        tape[2] = 2;
        
        for (int i = 0; i < tape.length; i += 4)
        {
            int op = tape[i];
            if (op == 99)
            {
                break;
            }
            
            inst_action.get(op).apply(tape, i);
        }
        
        System.out.println(Arrays.toString(tape));
        System.out.println(tape[0]);
    }
    
    public static int[] getTape()
    {
        int[]         tape  = new int[256];
        List<Integer> input = IntFromFileSupplier.createFromCommaFile("day2.input", false).getDataSource();
        for (int i = 0; i < input.size(); i++)
        {
            tape[i] = input.get(i);
        }
        
        return tape;
    }
}
