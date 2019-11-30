package utils;

import java.util.Objects;

public class Pair<A, B>
{
    private A a;
    private B b;
    
    public Pair(A a, B b)
    {
        this.a = a;
        this.b = b;
    }
    
    public A getA()
    {
        return a;
    }
    
    public B getB()
    {
        return b;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(a, pair.a) &&
               Objects.equals(b, pair.b);
    }
    
    public int compareTo(Pair<Integer, Integer> other)
    {
        if (a instanceof Integer && b instanceof Integer)
        {
            if ((Integer) a > other.a)
            {
                return (Integer) a;
            }
            if (other.a > (Integer) a)
            {
                return other.a;
            }
            if (a.equals(other.a))
            {
                if ((Integer) b > other.b)
                {
                    return (Integer) b;
                }
                if (other.b > (Integer) b)
                {
                    return other.b;
                }
            }
        }
        return 0;
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hash(a, b);
    }
    
    @Override
    public String toString()
    {
        return "Pair{" +
               "a=" + a +
               ", b=" + b +
               '}';
    }
}
