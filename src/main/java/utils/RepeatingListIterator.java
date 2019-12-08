package utils;

import java.util.List;

public class RepeatingListIterator<T>
{
    List<T> list;
    int     index;
    
    public RepeatingListIterator(List<T> list)
    {
        this.list = list;
    }
    
    public T next()
    {
        return list.get(index = (++index % list.size()));
    }
    
    public T get(int i)
    {
        return list.get(i);
    }
}
