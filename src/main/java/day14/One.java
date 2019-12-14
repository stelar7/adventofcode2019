package day14;

import utils.Pair;
import utils.sources.StringFromFileSupplier;

import java.util.*;

public class One
{
    
    static class ItemStack
    {
        public String name;
        public long   count;
        
        public ItemStack(String stackString)
        {
            String[] cnt = stackString.split(" ");
            name = cnt[1];
            count = Integer.parseInt(cnt[0]);
        }
        
        public ItemStack(String name, long amount)
        {
            this.name = name;
            this.count = amount;
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
            ItemStack itemStack = (ItemStack) o;
            return Objects.equals(name, itemStack.name) &&
                   Objects.equals(count, itemStack.count);
        }
        
        @Override
        public int hashCode()
        {
            return Objects.hash(name, count);
        }
        
        @Override
        public String toString()
        {
            return "ItemStack{" +
                   "name='" + name + '\'' +
                   ", count=" + count +
                   '}';
        }
    }
    
    static Map<String, Pair<Long, List<ItemStack>>> outins = new HashMap<>();
    
    public static void main(String[] args)
    {
        List<String> input = StringFromFileSupplier.create("day14.input", false).getDataSource();
        buildRecipies(input);
        
        long out = calculateOre(1);
        System.out.println(out);
    }
    
    public static long calculateOre(long i)
    {
        return calculateOre(new ItemStack(i + " FUEL"), new HashMap<>());
    }
    
    private static long calculateOre(ItemStack target, HashMap<String, Long> extra)
    {
        if (target.name.equalsIgnoreCase("ORE"))
        {
            return target.count;
        }
        
        if (target.count < extra.getOrDefault(target.name, 0L))
        {
            extra.put(target.name, extra.get(target.name) - target.count);
            return 0;
        }
        
        target.count -= extra.getOrDefault(target.name, 0L);
        Pair<Long, List<ItemStack>> oi        = outins.get(target.name);
        long                        ore       = 0;
        long                        outputAmt = oi.getA();
        List<ItemStack>             inputs    = oi.getB();
        long                        copies    = (int) Math.ceil((float) target.count / (float) outputAmt);
        for (ItemStack input : inputs)
        {
            long amount = input.count * copies;
            ore += calculateOre(new ItemStack(input.name, amount), extra);
        }
        
        extra.put(target.name, outputAmt * copies - target.count);
        return ore;
    }
    
    public static void buildRecipies(List<String> recipies)
    {
        for (String recipy : recipies)
        {
            ItemStack output = new ItemStack(recipy.split(" => ")[1]);
            
            List<ItemStack> inputs  = new ArrayList<>();
            String          content = recipy.split(" => ")[0];
            if (content.contains(", "))
            {
                String[] parts = content.split(", ");
                for (String part : parts)
                {
                    inputs.add(new ItemStack(part));
                }
            } else
            {
                inputs.add(new ItemStack(content));
            }
            outins.put(output.name, new Pair<>(output.count, inputs));
        }
    }
}
