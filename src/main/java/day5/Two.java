package day5;

import day5.One.OpCode;

public class Two
{
    static
    {
        One.ops.put(5, (tape, index, op) -> {
            int paramA = tape[index[0] + 1];
            int paramB = tape[index[0] + 2];
            int valueA = One.getValueFromParamMode(tape, op.param1Mode, paramA);
            int valueB = One.getValueFromParamMode(tape, op.param2Mode, paramB);
            index[0] += 3;
            
            if (valueA != 0)
            {
                index[0] = valueB;
            }
            return null;
        });
        
        One.ops.put(6, (tape, index, op) -> {
            int paramA = tape[index[0] + 1];
            int paramB = tape[index[0] + 2];
            int valueA = One.getValueFromParamMode(tape, op.param1Mode, paramA);
            int valueB = One.getValueFromParamMode(tape, op.param2Mode, paramB);
            index[0] += 3;
            
            if (valueA == 0)
            {
                index[0] = valueB;
            }
            return null;
        });
        
        One.ops.put(7, (tape, index, op) -> {
            int paramA = tape[index[0] + 1];
            int paramB = tape[index[0] + 2];
            int paramC = tape[index[0] + 3];
            int valueA = One.getValueFromParamMode(tape, op.param1Mode, paramA);
            int valueB = One.getValueFromParamMode(tape, op.param2Mode, paramB);
            int valueC = One.getValueFromParamMode(tape, op.param3Mode, paramC);
            index[0] += 4;
            
            if (valueA < valueB)
            {
                tape[paramC] = 1;
            } else
            {
                tape[paramC] = 0;
            }
            return null;
        });
        
        One.ops.put(8, (tape, index, op) -> {
            int paramA = tape[index[0] + 1];
            int paramB = tape[index[0] + 2];
            int paramC = tape[index[0] + 3];
            int valueA = One.getValueFromParamMode(tape, op.param1Mode, paramA);
            int valueB = One.getValueFromParamMode(tape, op.param2Mode, paramB);
            int valueC = One.getValueFromParamMode(tape, op.param3Mode, paramC);
            index[0] += 4;
            
            if (valueA == valueB)
            {
                tape[paramC] = 1;
            } else
            {
                tape[paramC] = 0;
            }
            return null;
        });
    }
    
    public static void main(String[] args)
    {
        Integer[] tape = One.getTape();
        Integer[] i    = new Integer[]{0};
        
        for (; i[0] < tape.length; )
        {
            OpCode opCode = new OpCode(String.valueOf(tape[i[0]]));
            
            if (opCode.opCode == 99)
            {
                break;
            }
            
            One.ops.get(opCode.opCode).apply(tape, i, opCode);
        }
    }
}
