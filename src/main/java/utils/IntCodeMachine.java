package utils;

import utils.sources.IntFromFileSupplier;

import java.util.*;
import java.util.function.Supplier;

public class IntCodeMachine
{
    public enum OPCode
    {
        HALT(99), ADD(1), MUL(2), INPUT(3), OUTPUT(4), JUMP_TRUE(5), JUMP_FALSE(6), LESS(7), EQUAL(8), REL_ADJUST(9);
        
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
    
    static class OpcodeStatus
    {
        int  mode;
        long param;
        long val;
        
        public OpcodeStatus(int mode, long param, long val)
        {
            this.mode = mode;
            this.param = param;
            this.val = val;
        }
    }
    
    private void printOpcode(String code, long rel, List<OpcodeStatus> stats)
    {
        System.out.print(code + " ");
        for (OpcodeStatus stat : stats)
        {
            if (stat.mode == 0)
            {
                //System.out.printf("%%%d ", stat.param);
                System.out.printf("%%%d(%d) ", stat.param, stat.val);
            }
            if (stat.mode == 1)
            {
                System.out.printf("%d ", stat.param);
            }
            if (stat.mode == 2)
            {
                //System.out.printf("%%%d+%d ", stat.param, rel);
                System.out.printf("%%%d(%d)+%d ", stat.param, stat.val, rel);
            }
        }
    }
    
    Map<OPCode, QuadFunction<Map<Long, Long>, Long[], Long[], OpCodeParameters, Void>> ops = new HashMap<>()
    {{
        
        put(OPCode.HALT, (tape, index, relbase, op) -> {
            System.out.println("HALT");
            return null;
        });
        
        put(OPCode.ADD, (tape, index, relbase, op) -> {
            long valueA = getValueFromParamMode(tape, relbase, op.param1Mode, index[0] + 1L, false);
            long valueB = getValueFromParamMode(tape, relbase, op.param2Mode, index[0] + 2L, false);
            long valueC = getValueFromParamMode(tape, relbase, op.param3Mode, index[0] + 3L, true);
            
            long result = valueA + valueB;
            
            tape.put(valueC, result);
            index[0] += 4;
            
            if (debugging)
            {
                List<OpcodeStatus> status = Arrays.asList(new OpcodeStatus(op.param1Mode, memory.getOrDefault(index[0] + 1L, 0L), valueA),
                                                          new OpcodeStatus(op.param2Mode, memory.getOrDefault(index[0] + 2L, 0L), valueB),
                                                          new OpcodeStatus(op.param3Mode, memory.getOrDefault(index[0] + 3L, 0L), valueC));
                printOpcode("ADD", relbase[0], status);
                System.out.print("= " + result);
                System.out.println();
            }
            
            return null;
        });
        
        put(OPCode.MUL, (tape, index, relbase, op) -> {
            long valueA = getValueFromParamMode(tape, relbase, op.param1Mode, index[0] + 1L, false);
            long valueB = getValueFromParamMode(tape, relbase, op.param2Mode, index[0] + 2L, false);
            long valueC = getValueFromParamMode(tape, relbase, op.param3Mode, index[0] + 3L, true);
            
            long result = valueA * valueB;
            tape.put(valueC, result);
            index[0] += 4;
            
            
            if (debugging)
            {
                List<OpcodeStatus> status = Arrays.asList(new OpcodeStatus(op.param1Mode, memory.getOrDefault(index[0] + 1L, 0L), valueA),
                                                          new OpcodeStatus(op.param2Mode, memory.getOrDefault(index[0] + 2L, 0L), valueB),
                                                          new OpcodeStatus(op.param3Mode, memory.getOrDefault(index[0] + 3L, 0L), valueC));
                printOpcode("MUL", relbase[0], status);
                System.out.print("= " + result);
                System.out.println();
            }
            
            return null;
        });
        
        
        put(OPCode.INPUT, (tape, index, relbase, op) -> {
            long valueA = getValueFromParamMode(tape, relbase, op.param1Mode, index[0] + 1L, true);
            tape.put(valueA, inputSupplier == null ? inputs.poll() : inputSupplier.get());
            index[0] += 2;
            
            if (debugging)
            {
                List<OpcodeStatus> status = Collections.singletonList(new OpcodeStatus(op.param1Mode, memory.getOrDefault(index[0] + 1L, 0L), valueA));
                printOpcode("INN", relbase[0], status);
                System.out.print("= " + tape.get(valueA));
                System.out.println();
            }
            
            return null;
        });
        
        
        put(OPCode.OUTPUT, (tape, index, relbase, op) -> {
            long valueA = getValueFromParamMode(tape, relbase, op.param1Mode, index[0] + 1L, false);
            index[0] += 2;
            outputs.add(valueA);
            
            if (debugging)
            {
                List<OpcodeStatus> status = Collections.singletonList(new OpcodeStatus(op.param1Mode, memory.getOrDefault(index[0] + 1L, 0L), valueA));
                printOpcode("OUT", relbase[0], status);
                System.out.print("= " + valueA);
                System.out.println();
            }
            
            return null;
        });
        
        put(OPCode.JUMP_TRUE, (tape, index, relbase, op) -> {
            long valueA = getValueFromParamMode(tape, relbase, op.param1Mode, index[0] + 1L, false);
            long valueB = getValueFromParamMode(tape, relbase, op.param2Mode, index[0] + 2L, false);
            index[0] += 3;
            
            if (valueA != 0)
            {
                index[0] = valueB;
            }
            
            
            if (debugging)
            {
                List<OpcodeStatus> status = Arrays.asList(new OpcodeStatus(op.param1Mode, memory.getOrDefault(index[0] + 1L, 0L), valueA),
                                                          new OpcodeStatus(op.param2Mode, memory.getOrDefault(index[0] + 2L, 0L), valueB));
                printOpcode("JIT", relbase[0], status);
                System.out.print("= " + (valueA != 0));
                System.out.println();
            }
            
            return null;
        });
        
        put(OPCode.JUMP_FALSE, (tape, index, relbase, op) -> {
            long valueA = getValueFromParamMode(tape, relbase, op.param1Mode, index[0] + 1L, false);
            long valueB = getValueFromParamMode(tape, relbase, op.param2Mode, index[0] + 2L, false);
            index[0] += 3;
            
            if (valueA == 0)
            {
                index[0] = valueB;
            }
            
            if (debugging)
            {
                List<OpcodeStatus> status = Arrays.asList(new OpcodeStatus(op.param1Mode, memory.getOrDefault(index[0] + 1L, 0L), valueA),
                                                          new OpcodeStatus(op.param2Mode, memory.getOrDefault(index[0] + 2L, 0L), valueB));
                printOpcode("JIF", relbase[0], status);
                System.out.print("= " + (valueA == 0));
                System.out.println();
            }
            
            return null;
        });
        
        put(OPCode.LESS, (tape, index, relbase, op) -> {
            long valueA = getValueFromParamMode(tape, relbase, op.param1Mode, index[0] + 1L, false);
            long valueB = getValueFromParamMode(tape, relbase, op.param2Mode, index[0] + 2L, false);
            long valueC = getValueFromParamMode(tape, relbase, op.param3Mode, index[0] + 3L, true);
            tape.compute(valueC, (k, v) -> valueA < valueB ? 1L : 0L);
            index[0] += 4;
            
            
            if (debugging)
            {
                List<OpcodeStatus> status = Arrays.asList(new OpcodeStatus(op.param1Mode, memory.getOrDefault(index[0] + 1L, 0L), valueA),
                                                          new OpcodeStatus(op.param2Mode, memory.getOrDefault(index[0] + 2L, 0L), valueB),
                                                          new OpcodeStatus(op.param3Mode, memory.getOrDefault(index[0] + 3L, 0L), valueC));
                printOpcode("LES", relbase[0], status);
                System.out.print("= " + tape.get(valueC));
                System.out.println();
            }
            
            return null;
        });
        
        put(OPCode.EQUAL, (tape, index, relbase, op) -> {
            long valueA = getValueFromParamMode(tape, relbase, op.param1Mode, index[0] + 1L, false);
            long valueB = getValueFromParamMode(tape, relbase, op.param2Mode, index[0] + 2L, false);
            long valueC = getValueFromParamMode(tape, relbase, op.param3Mode, index[0] + 3L, true);
            tape.compute(valueC, (k, v) -> valueA == valueB ? 1L : 0L);
            index[0] += 4;
            
            
            if (debugging)
            {
                List<OpcodeStatus> status = Arrays.asList(new OpcodeStatus(op.param1Mode, memory.getOrDefault(index[0] + 1L, 0L), valueA),
                                                          new OpcodeStatus(op.param2Mode, memory.getOrDefault(index[0] + 2L, 0L), valueB),
                                                          new OpcodeStatus(op.param3Mode, memory.getOrDefault(index[0] + 3L, 0L), valueC));
                printOpcode("EQL", relbase[0], status);
                System.out.print("= " + tape.get(valueC));
                System.out.println();
            }
            
            return null;
        });
        
        put(OPCode.REL_ADJUST, (tape, index, relbase, op) -> {
            long valueA     = getValueFromParamMode(tape, relbase, op.param1Mode, index[0] + 1L, false);
            long oldRelBase = relbase[0];
            relbase[0] += valueA;
            index[0] += 2;
            
            
            if (debugging)
            {
                List<OpcodeStatus> status = Collections.singletonList(new OpcodeStatus(op.param1Mode, memory.getOrDefault(index[0] + 1L, 0L), valueA));
                printOpcode("ADJ", oldRelBase, status);
                System.out.print("-> " + relbase[0]);
                System.out.println();
            }
            
            return null;
        });
    }};
    
    
    Deque<Long> inputs  = new ArrayDeque<>();
    Deque<Long> outputs = new ArrayDeque<>();
    
    private Long[]          index        = new Long[]{0L};
    private Long[]          relativeBase = new Long[]{0L};
    private Map<Long, Long> memory       = new HashMap<>();
    private Map<Long, Long> memory_b     = new HashMap<>();
    
    public OpCodeParameters currentOp = null;
    public OpCodeParameters prevOp    = null;
    
    private boolean running   = true;
    public  boolean debugging = false;
    
    public Supplier<Long> inputSupplier;
    
    public IntCodeMachine(Map<Long, Long> tape)
    {
        this.memory.putAll(tape);
        this.memory_b.putAll(tape);
        currentOp = new OpCodeParameters(Math.toIntExact(memory.getOrDefault(index[0], 99L)));
    }
    
    public IntCodeMachine(String filename)
    {
        this(getTape(filename));
    }
    
    public void setInputFunction(Supplier<Long> prod)
    {
        inputSupplier = prod;
    }
    
    public boolean isRunning()
    {
        return running;
    }
    
    public void printAllOutput()
    {
        while (peekOutput() != null)
        {
            System.out.println(output());
        }
    }
    
    public void clearOutputs()
    {
        outputs.clear();
    }
    
    public void clearInputs()
    {
        inputs.clear();
    }
    
    
    public List<Long> getInputList()
    {
        return new ArrayList<>(inputs);
    }
    
    /**
     * retains current memory, but resets other variables
     */
    public void restart()
    {
        index[0] = 0L;
        relativeBase[0] = 0L;
        prevOp = null;
        running = true;
        inputs.clear();
        outputs.clear();
        currentOp = new OpCodeParameters(Math.toIntExact(memory.getOrDefault(index[0], 99L)));
    }
    
    public void reset()
    {
        memory.clear();
        memory.putAll(memory_b);
        restart();
    }
    
    public void next()
    {
        if (currentOp.opCode == OPCode.HALT)
        {
            running = false;
            return;
        }
        
        /*
        if (debugging)
        {
            System.out.format("%d %d %d %d %d%n", currentOp.rawOp, currentOp.opCode.code, currentOp.param1Mode, currentOp.param2Mode, currentOp.param3Mode);
        }
         */
        
        ops.get(currentOp.opCode).apply(memory, index, relativeBase, currentOp);
        OpCodeParameters next = new OpCodeParameters(Math.toIntExact(memory.get(index[0])));
        prevOp = currentOp;
        currentOp = next;
    }
    
    /**
     * runs untill the current op is an input op, or the machine halts
     */
    public void queueInput()
    {
        while (currentOp.opCode != OPCode.INPUT && currentOp.opCode != OPCode.HALT)
        {
            next();
        }
        
        if (currentOp.opCode == OPCode.HALT)
        {
            System.out.println("Machine halted, input ignored");
        }
    }
    
    /**
     * runs untill the previous op is an input op, or the machine halts
     */
    public void queueOutput()
    {
        while (currentOp.opCode != OPCode.OUTPUT && currentOp.opCode != OPCode.HALT)
        {
            next();
        }
        next();
        
        if (currentOp.opCode == OPCode.HALT)
        {
            System.out.println("Machine halted, check if running before consuming output!");
        }
    }
    
    public void queueOutput(int times)
    {
        for (int i = 0; i < times; i++)
        {
            queueOutput();
        }
    }
    
    
    public void runToEnd()
    {
        while (currentOp.opCode != OPCode.HALT)
        {
            next();
        }
    }
    
    public void input(long in)
    {
        inputs.addLast(in);
    }
    
    
    public void input(List<Long> in)
    {
        inputs.addAll(in);
    }
    
    public long output()
    {
        return outputs.pollFirst();
    }
    
    public Long peekOutput()
    {
        return outputs.peekFirst();
    }
    
    public long lastOutput()
    {
        return outputs.pollLast();
    }
    
    public void setMemory(long address, long value)
    {
        memory.put(address, value);
        if (address == 0L)
        {
            System.out.println("Setting address 0 will not work unless the program loops, did you mean to add \"false\" as the 3rd parameter?");
        }
    }
    
    public void setMemory(long address, long value, boolean restart)
    {
        memory.put(address, value);
        
        if (restart)
        {
            restart();
        }
    }
    
    public long getMemory(long address)
    {
        return memory.get(address);
    }
    
    public void dumpMemory()
    {
        long max = memory.keySet().stream().mapToLong(a -> a).max().getAsLong();
        long min = memory.keySet().stream().mapToLong(a -> a).min().getAsLong();
        for (long i = min; i <= max; i++)
        {
            System.out.print(memory.getOrDefault(i, 0L) + " ");
        }
        System.out.println();
    }
    
    public static Map<Long, Long> getTape(String filename)
    {
        List<Long>      input  = IntFromFileSupplier.longCreateFromCommaFile(filename, false).getDataSource();
        Map<Long, Long> memory = new HashMap<>();
        for (int i = 0; i < input.size(); i++)
        {
            memory.put((long) i, input.get(i));
        }
        
        return memory;
    }
    
    public static long getValueFromParamMode(Map<Long, Long> memory, Long[] relbase, int mode, Long index, boolean isOutput)
    {
        long value = memory.getOrDefault(index, 0L);
        if (mode == 1)
        {
            return value;
        }
        
        if (mode == 2)
        {
            value += relbase[0];
        }
        return isOutput ? value : memory.getOrDefault(value, 0L);
    }
    
    public static class OpCodeParameters
    {
        public int    rawOp;
        public int    param3Mode;
        public int    param2Mode;
        public int    param1Mode;
        public OPCode opCode;
        
        public OpCodeParameters(int opRaw)
        {
            rawOp = opRaw;
            opCode = OPCode.from(opRaw % 100);
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
        
        public OPCode getOpCode()
        {
            return opCode;
        }
        
        public int getRawOp()
        {
            return rawOp;
        }
        
        @Override
        public String toString()
        {
            return opCode.name();
        }
    }
}
