package utils;

import utils.sources.IntFromFileSupplier;

import java.util.*;

public class IntCodeMachine
{
    
    public enum OPCode
    {
        HALT(99), ADD(1), MUL(2), INPUT(3), OUTPUT(4), JUMP_TRUE(5), JUMP_FALSE(6), LESS(7), EQUAL(8);
        
        int code;
        
        OPCode(int code)
        {
            this.code = code;
        }
        
        public static OPCode from(int i)
        {
            return Arrays.stream(values()).filter(a -> a.code == i).findFirst().get();
        }
    }
    
    
    Map<OPCode, TriFunction<Integer[], Integer[], OpCodeParameters, Void>> ops = new HashMap<>()
    {{
        
        put(OPCode.HALT, (tape, index, op) -> null);
        
        put(OPCode.ADD, (tape, index, op) -> {
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
        
        put(OPCode.MUL, (tape, index, op) -> {
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
        
        
        put(OPCode.INPUT, (tape, index, op) -> {
            int paramA = tape[index[0] + 1];
            int valueA = getValueFromParamMode(tape, op.param1Mode, paramA);
            int input  = op.getInput();
            System.out.println("got input " + input);
            tape[paramA] = op.getInput();
            index[0] += 2;
            return null;
        });
        
        
        put(OPCode.OUTPUT, (tape, index, op) -> {
            int paramA = tape[index[0] + 1];
            int valueA = getValueFromParamMode(tape, op.param1Mode, paramA);
            index[0] += 2;
            System.out.println("Send output " + valueA);
            op.output = valueA;
            return null;
        });
        
        put(OPCode.JUMP_TRUE, (tape, index, op) -> {
            int paramA = tape[index[0] + 1];
            int paramB = tape[index[0] + 2];
            int valueA = getValueFromParamMode(tape, op.param1Mode, paramA);
            int valueB = getValueFromParamMode(tape, op.param2Mode, paramB);
            index[0] += 3;
            
            if (valueA != 0)
            {
                index[0] = valueB;
            }
            return null;
        });
        
        put(OPCode.JUMP_FALSE, (tape, index, op) -> {
            int paramA = tape[index[0] + 1];
            int paramB = tape[index[0] + 2];
            int valueA = getValueFromParamMode(tape, op.param1Mode, paramA);
            int valueB = getValueFromParamMode(tape, op.param2Mode, paramB);
            index[0] += 3;
            
            if (valueA == 0)
            {
                index[0] = valueB;
            }
            return null;
        });
        
        put(OPCode.LESS, (tape, index, op) -> {
            int paramA = tape[index[0] + 1];
            int paramB = tape[index[0] + 2];
            int paramC = tape[index[0] + 3];
            int valueA = getValueFromParamMode(tape, op.param1Mode, paramA);
            int valueB = getValueFromParamMode(tape, op.param2Mode, paramB);
            int valueC = getValueFromParamMode(tape, op.param3Mode, paramC);
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
        
        put(OPCode.EQUAL, (tape, index, op) -> {
            int paramA = tape[index[0] + 1];
            int paramB = tape[index[0] + 2];
            int paramC = tape[index[0] + 3];
            int valueA = getValueFromParamMode(tape, op.param1Mode, paramA);
            int valueB = getValueFromParamMode(tape, op.param2Mode, paramB);
            int valueC = getValueFromParamMode(tape, op.param3Mode, paramC);
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
    }};
    
    public Integer[] index = new Integer[]{0};
    Integer[] tape;
    
    public OpCodeParameters currentOp = null;
    public OpCodeParameters prevOp    = null;
    
    public boolean running = true;
    
    public IntCodeMachine(Integer[] tape)
    {
        this.tape = tape;
        currentOp = new OpCodeParameters(tape[index[0]]);
    }
    
    public IntCodeMachine(String filename)
    {
        this.tape = getTape(filename);
        currentOp = new OpCodeParameters(tape[index[0]]);
    }
    
    public void next()
    {
        if (currentOp.opCode == OPCode.HALT)
        {
            running = false;
            return;
        }
        
        ops.get(currentOp.opCode).apply(tape, index, currentOp);
        OpCodeParameters next = new OpCodeParameters(tape[index[0]]);
        prevOp = currentOp;
        currentOp = next;
    }
    
    public void runToInput()
    {
        while (currentOp.opCode != OPCode.INPUT && currentOp.opCode != OPCode.HALT)
        {
            next();
        }
    }
    
    public void runToOutput()
    {
        while (currentOp.opCode != OPCode.OUTPUT && currentOp.opCode != OPCode.HALT)
        {
            next();
        }
        next();
    }
    
    public void input(int prev)
    {
        currentOp.setInput(prev);
    }
    
    public int output()
    {
        return prevOp != null ? prevOp.output : 0;
    }
    
    public static Integer[] getTape(String filename)
    {
        List<Integer> input = IntFromFileSupplier.createFromCommaFile(filename, false).getDataSource();
        Integer[]     tape  = new Integer[input.size()];
        for (int i = 0; i < input.size(); i++)
        {
            tape[i] = input.get(i);
        }
        
        return tape;
    }
    
    public static int getValueFromParamMode(Integer[] tape, int mode, int value)
    {
        return mode == 0 ? tape[value] : value;
    }
    
    public static class OpCodeParameters
    {
        public int    param3Mode;
        public int    param2Mode;
        public int    param1Mode;
        public OPCode opCode;
        public int    input;
        public int    output;
        
        private boolean inputReady;
        
        public OpCodeParameters(int opRaw)
        {
            opCode = OPCode.from(opRaw % 100);
            param1Mode = opRaw / 100 % 10;
            param2Mode = opRaw / 1000 % 10;
            param3Mode = opRaw / 10000 % 10;
        }
        
        
        public int getOutput()
        {
            return output;
        }
        
        public int getInput()
        {
            inputReady = false;
            return input;
        }
        
        public void setInput(int input)
        {
            this.input = input;
            inputReady = true;
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
        
        public OPCode getOpCode()
        {
            return opCode;
        }
        
        public boolean inputReady()
        {
            return inputReady;
        }
        
        @Override
        public String toString()
        {
            return opCode.name() + "(in:" + input + ") (out:" + output + ")";
        }
    }
}
