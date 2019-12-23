package day23;

import utils.*;

import java.util.*;

public class One
{
    public static void main(String[] args)
    {
        List<Triplet<Long, Long, Long>> packets  = new ArrayList<>();
        List<IntCodeMachine>            machines = new ArrayList<>();
        
        for (int i = 0; i < 50; i++)
        {
            IntCodeMachine machine = createMachine(packets, i);
            machines.add(machine);
        }
        
        outer:
        while (true)
        {
            for (IntCodeMachine machine : machines)
            {
                machine.next();
                
                if (machine.outputSize() >= 3)
                {
                    long address = machine.output();
                    long xval    = machine.output();
                    long yval    = machine.output();
                    
                    if (address == 255)
                    {
                        System.out.println(yval);
                        break outer;
                    }
                    
                    
                    Triplet<Long, Long, Long> packet = new Triplet<>(address, xval, yval);
                    packets.add(packet);
                }
            }
        }
    }
    
    public static IntCodeMachine createMachine(List<Triplet<Long, Long, Long>> packets, int i)
    {
        IntCodeMachine machine = new IntCodeMachine("day23.input");
        machine.input(i);
        machine.setInputFunction(() -> {
            for (Triplet<Long, Long, Long> packet : new ArrayList<>(packets))
            {
                if (packet.getA() == i)
                {
                    packets.remove(packet);
                    machine.input(packet.getC());
                    return packet.getB();
                }
            }
            
            return -1L;
        });
        return machine;
    }
}
