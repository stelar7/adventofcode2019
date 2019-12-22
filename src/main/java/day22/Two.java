package day22;

import day22.One.Action;
import utils.Pair;
import utils.sources.StringFromFileSupplier;

import java.math.BigInteger;
import java.util.List;

public class Two
{
    public static void main(String[] args)
    {
        List<String>                input = StringFromFileSupplier.create("day22.input", false).getDataSource();
        List<Pair<Action, Integer>> moves = One.generateMoveList(input);
        System.out.println(solve(moves, 2020, 119315717514047L, -101741582076661L));
    }
    
    public static long solve(List<Pair<Action, Integer>> moves, long target, long cardCount, long iterations)
    {
        BigInteger a = BigInteger.ONE;
        BigInteger b = BigInteger.ZERO;
        BigInteger m = BigInteger.valueOf(cardCount);
        
        for (Pair<Action, Integer> move : moves)
        {
            if (move.getA() == Action.REVERSE)
            {
                a = a.negate().mod(m);
                b = b.not().mod(m);
            }
            
            if (move.getA() == Action.CUT)
            {
                BigInteger count = BigInteger.valueOf(move.getB());
                b = b.subtract(count).mod(m);
            }
            
            if (move.getA() == Action.DEAL)
            {
                BigInteger count = BigInteger.valueOf(move.getB());
                a = a.multiply(count).mod(m);
                b = b.multiply(count).mod(m);
            }
        }
        
        if (iterations < 0)
        {
            a = a.modInverse(m);
            b = b.negate().multiply(a).mod(m);
        }
        
        BigInteger c = BigInteger.ONE;
        BigInteger d = BigInteger.ZERO;
        long       e = Math.abs(iterations);
        
        while (e > 0)
        {
            if ((e & 1) == 1L)
            {
                c = a.multiply(c).mod(m);
                d = a.multiply(d).add(b).mod(m);
            }
            
            e = e >>> 1;
            b = a.multiply(b).add(b).mod(m);
            a = a.multiply(a).mod(m);
        }
        
        return BigInteger.valueOf(target).multiply(c).add(d).mod(m).longValue();
    }
}