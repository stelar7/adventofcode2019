package day5;

import utils.TriFunction;
import utils.sources.IntFromFileSupplier;

import java.util.*;

public class One
{
    static Scanner scanner = new Scanner(System.in);
    
    static Map<Integer, TriFunction<Integer[], Integer[], OpCode, Void>> ops = new HashMap<>()
    {{
        put(99, (tape, index, op) -> null);
        
        put(1, (tape, index, op) -> {
            int paramA = tape[index[0] + 1];
            int paramB = tape[index[0] + 2];
            int paramC = tape[index[0] + 3];
            
            int valueA = getValueFromParamMode(tape, op.param1Mode, paramA);
            int valueB = getValueFromParamMode(tape, op.param2Mode, paramB);
            
            int result = valueA + valueB;
            
            tape[paramC] = result;
            index[0] += 4;
            return null;
        });
        
        put(2, (tape, index, op) -> {
            int paramA = tape[index[0] + 1];
            int paramB = tape[index[0] + 2];
            int paramC = tape[index[0] + 3];
            
            int valueA = getValueFromParamMode(tape, op.param1Mode, paramA);
            int valueB = getValueFromParamMode(tape, op.param2Mode, paramB);
            
            int result = valueA * valueB;
            tape[paramC] = result;
            index[0] += 4;
            return null;
        });
        
        put(3, (tape, index, op) -> {
            int paramA = tape[index[0] + 1];
            int valueA = getValueFromParamMode(tape, op.param1Mode, paramA);
            System.out.print("Input value: ");
            tape[paramA] = Integer.parseInt(scanner.nextLine());
            System.out.println();
            index[0] += 2;
            return null;
        });
        
        put(4, (tape, index, op) -> {
            int paramA = tape[index[0] + 1];
            int valueA = getValueFromParamMode(tape, op.param1Mode, paramA);
            index[0] += 2;
            System.out.println(valueA + " ");
            return null;
        });
    }};
    
    public static void main(String[] args)
    {
        Integer[] tape = getTape();
        
        Integer[] i = new Integer[]{0};
        for (; i[0] < tape.length; )
        {
            int    opInt    = tape[i[0]];
            String opString = String.valueOf(opInt);
            OpCode opCode   = new OpCode(opString);
            
            if (opCode.opCode == 99)
            {
                break;
            }
            
            ops.get(opCode.opCode).apply(tape, i, opCode);
        }
    }
    
    public static int getValueFromParamMode(Integer[] tape, int mode, int value)
    {
        return mode == 0 ? tape[value] : value;
    }
    
    public static Integer[] getTape()
    {
        List<Integer> input = IntFromFileSupplier.createFromCommaFile("day5.input", false).getDataSource();
        Integer[]     tape  = new Integer[input.size()];
        for (int i = 0; i < input.size(); i++)
        {
            tape[i] = input.get(i);
        }
        
        return tape;
    }
    
    public static class OpCode
    {
        public int param3Mode;
        public int param2Mode;
        public int param1Mode;
        public int opCode;
        
        public OpCode(String opString)
        {
            if (opString.length() == 5)
            {
                param3Mode = Integer.parseInt(opString.substring(0, 1));
                param2Mode = Integer.parseInt(opString.substring(1, 2));
                param1Mode = Integer.parseInt(opString.substring(2, 3));
                
                opCode = Integer.parseInt(opString.substring(3, 5));
            }
            
            if (opString.length() == 4)
            {
                param3Mode = 0;
                param2Mode = Integer.parseInt(opString.substring(0, 1));
                param1Mode = Integer.parseInt(opString.substring(1, 2));
                
                opCode = Integer.parseInt(opString.substring(2, 4));
            }
            
            if (opString.length() == 3)
            {
                param3Mode = 0;
                param2Mode = 0;
                param1Mode = Integer.parseInt(opString.substring(0, 1));
                
                opCode = Integer.parseInt(opString.substring(1, 3));
            }
            
            if (opString.length() == 2)
            {
                param3Mode = 0;
                param2Mode = 0;
                param1Mode = 0;
                
                opCode = Integer.parseInt(opString.substring(0, 2));
            }
            
            if (opString.length() == 1)
            {
                param3Mode = 0;
                param2Mode = 0;
                param1Mode = 0;
                
                opCode = Integer.parseInt(opString.substring(0, 1));
            }
        }
        
        public int getParam3Mode()
        {
            return param3Mode;
        }
        
        public int getParam2Mode()
        {
            return param2Mode;
        }
        
        public int getParam1Mode()
        {
            return param1Mode;
        }
        
        public int getOpCode()
        {
            return opCode;
        }
    }
}
