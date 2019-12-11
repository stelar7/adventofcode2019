package day7;

import utils.*;

import java.util.*;

public class One
{
    static class Amp
    {
        IntCodeMachine machine = new IntCodeMachine("day7.input");
        
        public Amp(Integer setting)
        {
            input(setting);
            next();
        }
        
        public void next()
        {
            machine.next();
        }
        
        public void input(long prev)
        {
            machine.input(prev);
        }
        
        public long output()
        {
            return machine.output();
        }
    }
    
    public static long runAmp(int setting, long prev)
    {
        Amp A = new Amp(setting);
        A.input(prev);
        A.machine.runToEnd();
        return A.output();
    }
    
    public static void main(String[] args)
    {
        IntCodeMachine machine = new IntCodeMachine("day7.input");
        
        long                best         = 0;
        List<List<Integer>> permutations = Utils.permutations(new ArrayList<>(List.of(0, 1, 2, 3, 4)));
        for (List<Integer> permutation : permutations)
        {
            long A = runAmp(permutation.get(0), 0);
            long B = runAmp(permutation.get(1), A);
            long C = runAmp(permutation.get(2), B);
            long D = runAmp(permutation.get(3), C);
            long E = runAmp(permutation.get(4), D);
            
            if (E > best)
            {
                best = E;
            }
        }
        
        System.out.println("Best signal = " + best);
    }
}
