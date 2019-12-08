package day7;

import day7.One.Amp;
import utils.RepeatingListIterator;
import utils.Utils;

import java.util.*;

public class Two
{
    public static void main(String[] args)
    {
        int best = 0;
        
        List<List<Integer>> permutations2 = Utils.permutations(new ArrayList<>(List.of(5, 6, 7, 8, 9)));
        for (List<Integer> permutation : permutations2)
        {
            Amp                        previusAmp = new Amp(0);
            Amp                        A          = new Amp(permutation.get(0));
            Amp                        B          = new Amp(permutation.get(1));
            Amp                        C          = new Amp(permutation.get(2));
            Amp                        D          = new Amp(permutation.get(3));
            Amp                        E          = new Amp(permutation.get(4));
            RepeatingListIterator<Amp> li         = new RepeatingListIterator(List.of(A, B, C, D, E));
            
            Amp currentAmp = li.get(0);
            System.out.println("init done");
            
            
            while (currentAmp.machine.running)
            {
                currentAmp.machine.runToInput();
                currentAmp.input(previusAmp.output());
                currentAmp.machine.runToOutput();
                
                previusAmp = currentAmp;
                currentAmp = li.next();
                
                int test = previusAmp.output();
                if (test > best)
                {
                    best = test;
                }
            }
        }
        
        System.out.println("best signal = " + best);
    }
}
