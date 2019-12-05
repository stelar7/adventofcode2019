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
            int    opInt  = tape[i[0]];
            OpCode opCode = new OpCode(opInt);
            
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
        
        public OpCode(int opRaw)
        {
            opCode = opRaw % 100;
            param1Mode = opRaw / 100 % 10;
            param2Mode = opRaw / 1000 % 10;
            param3Mode = opRaw / 10000 % 10;
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
