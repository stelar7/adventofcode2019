package day23;

import utils.*;

import java.util.*;

public class Two
{
    public static void main(String[] args)
    {
        List<Triplet<Long, Long, Long>> packets  = new ArrayList<>();
        List<IntCodeMachine>            machines = new ArrayList<>();
        List<Triplet<Long, Long, Long>> NAT      = new ArrayList<>();
        List<Triplet<Long, Long, Long>> sent     = new ArrayList<>();
        
        for (int i = 0; i < 50; i++)
        {
            IntCodeMachine machine = One.createMachine(packets, i);
            machines.add(machine);
        }
        
        // if no packets have been sent in 2000 instructions, consider the network idle
        int idleLimit = 2000 * machines.size();
        int counter   = 0;
        while (true)
        {
            for (IntCodeMachine machine : machines)
            {
                machine.next();
                counter++;
                
                if (machine.outputSize() >= 3)
                {
                    counter = 0;
                    
                    long address = machine.output();
                    long xval    = machine.output();
                    long yval    = machine.output();
                    
                    
                    Triplet<Long, Long, Long> packet = new Triplet<>(address, xval, yval);
                    packets.add(packet);
                    
                    if (address == 255)
                    {
                        NAT.add(packet);
                    }
                }
            }
            
            if (counter > idleLimit)
            {
                counter = 0;
                
                IntCodeMachine            first  = machines.get(0);
                Triplet<Long, Long, Long> packet = NAT.get(NAT.size() - 1);
                first.input(packet.getB());
                first.input(packet.getC());
                sent.add(packet);
                
                if (Utils.containsEqualElementsInOrder(sent))
                {
                    System.out.println(packet.getC());
                    break;
                }
            }
        }
    }
}