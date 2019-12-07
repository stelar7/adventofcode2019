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
        
        public void input(int prev)
        {
            machine.input(prev);
        }
        
        public int output()
        {
            return machine.output();
        }
    }
    
    public static int runAmp(int setting, int prev)
    {
        Amp A = new Amp(setting);
        A.machine.runToInput();
        A.input(prev);
        A.machine.runToOutput();
        return A.output();
    }
    
    public static void main(String[] args)
    {
        IntCodeMachine machine = new IntCodeMachine("day7.input");
        
        int                 best         = 0;
        List<List<Integer>> permutations = Utils.permutations(new ArrayList<>(List.of(0, 1, 2, 3, 4)));
        for (List<Integer> permutation : permutations)
        {
            int A = runAmp(permutation.get(0), 0);
            int B = runAmp(permutation.get(1), A);
            int C = runAmp(permutation.get(2), B);
            int D = runAmp(permutation.get(3), C);
            int E = runAmp(permutation.get(4), D);
            
            if (E > best)
            {
                best = E;
            }
        }
        
        int A = runAmp(4, 0);
        int B = runAmp(3, A);
        int C = runAmp(2, B);
        int D = runAmp(1, C);
        int E = runAmp(0, D);
        
        System.out.println("Best signal = " + best);
    }
}
