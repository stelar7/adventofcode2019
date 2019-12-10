package utils;

import org.joml.Vector2i;

import java.io.*;
import java.lang.reflect.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.Collectors;

public class Utils
{
    
    public static Map<String, String> extractRegex(String input, String regex, String... vars)
    {
        Map<String, String> matches = new HashMap<>();
        
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        if (!m.find())
        {
            System.out.println(input);
        }
        
        Arrays.stream(vars).forEach(v -> matches.put(v, m.group(v)));
        
        return matches;
    }
    
    public static LocalDateTime localDateTimeFromRegex(String input, String regex)
    {
        Map<String, String> parts = Utils.extractRegex(input, regex, "year", "month", "day", "hour", "min");
        int                 year  = Integer.parseInt(parts.get("year"));
        int                 month = Integer.parseInt(parts.get("month"));
        int                 day   = Integer.parseInt(parts.get("day"));
        int                 hour  = Integer.parseInt(parts.get("hour"));
        int                 min   = Integer.parseInt(parts.get("min"));
        return LocalDateTime.of(year, month, day, hour, min);
    }
    
    
    public static <T, Y> T getKeyForHighestValue(Map<T, Y> data, ToIntFunction<Y> mapper)
    {
        final AtomicReference<T> maxKey   = new AtomicReference<>();
        int[]                    maxValue = {-1};
        data.forEach((k, v) -> {
            int test = mapper.applyAsInt(v);
            maxValue[0] = Math.max(maxValue[0], test);
            
            // replace key if same value
            if (maxValue[0] == test)
            {
                maxKey.set(k);
            }
        });
        return maxKey.get();
    }
    
    public static <T, Y> void pushToMapList(Map<T, List<Y>> map, T key, Y value)
    {
        map.computeIfPresent(key, (k, v) -> {
            v.add(value);
            return v;
        });
        
        map.computeIfAbsent(key, (k) -> {
            List<Y> points = new ArrayList<>();
            points.add(value);
            return points;
        });
    }
    
    public static <T> Map<String, String> extractRegex(String input, String regex, Class clazz)
    {
        String[] params = Arrays.stream(clazz.getDeclaredFields())
                                .filter(a -> !Modifier.isStatic(a.getModifiers()))
                                .filter(a -> !Modifier.isFinal(a.getModifiers()))
                                .map(Field::getName)
                                .toArray(String[]::new);
        
        return extractRegex(input, regex, params);
    }
    
    public static String readFile(String filename)
    {
        InputStream file = Utils.class.getClassLoader().getResourceAsStream(filename);
        return new BufferedReader(new InputStreamReader(file)).lines().collect(Collectors.joining("\n"));
    }
    
    public static int min(int... numbers)
    {
        return Arrays.stream(numbers)
                     .min()
                     .orElse(Integer.MAX_VALUE);
    }
    
    public static int max(int... numbers)
    {
        return Arrays.stream(numbers)
                     .max()
                     .orElse(Integer.MIN_VALUE);
    }
    
    public static int sum(int... numbers)
    {
        return Arrays.stream(numbers).sum();
    }
    
    
    public static char[] removeIndex(char[] source, int index)
    {
        char[] result = new char[source.length - 1];
        System.arraycopy(source, 0, result, 0, index);
        if (source.length != index)
        {
            System.arraycopy(source, index + 1, result, index, source.length - index - 1);
        }
        return result;
    }
    
    
    public static int[] letterCount(String a)
    {
        int[] letterCount = new int[26];
        a.chars().forEach(c -> letterCount[c - 97]++);
        return letterCount;
    }
    
    public static int[] numberCount(String a)
    {
        int[] letterCount = new int[10];
        a.chars().forEach(c -> letterCount[c - '0']++);
        return letterCount;
    }
    
    
    public static int[] charCount(String a)
    {
        int[] chars = new int[128];
        a.chars().forEach(c -> chars[c]++);
        return chars;
    }
    
    public static int manhattanDistance(Vector2i one, Vector2i two)
    {
        return (Math.abs(two.x - one.x) + Math.abs(two.y - one.y));
    }
    
    public static <T> Set<T> intersection(Collection<T> a, Collection<T> b)
    {
        return a.stream().distinct().filter(b::contains).collect(Collectors.toSet());
    }
    
    public static <T> List<List<T>> permutations(List<T> original)
    {
        if (original.size() == 0)
        {
            List<List<T>> result = new ArrayList<>();
            result.add(new ArrayList<>());
            return result;
        }
        
        T firstElement = original.remove(0);
        
        List<List<T>> returnValue  = new ArrayList<>();
        List<List<T>> permutations = permutations(original);
        
        for (List<T> smallerPermutated : permutations)
        {
            for (int index = 0; index <= smallerPermutated.size(); index++)
            {
                List<T> temp = new ArrayList<>(smallerPermutated);
                temp.add(index, firstElement);
                returnValue.add(temp);
            }
        }
        
        return returnValue;
    }
    
    public static Pair<String, String> split(String input, int index)
    {
        String a = input.substring(0, index);
        String b = input.substring(index);
        return new Pair<>(a, b);
    }
    
    public static List<String> chunk(String input, int size)
    {
        String       current = input;
        List<String> output  = new ArrayList<>();
        
        while (!current.isEmpty())
        {
            Pair<String, String> layer = split(current, size);
            output.add(layer.getA());
            current = layer.getB();
        }
        
        return output;
    }
    
    public static <T, R extends Comparable<R>> int getIndexOfLowestValue(List<T> input, Function<T, R> mapper)
    {
        int fewestIndex = -1;
        R   fewestValue = null;
        
        for (int i = 0; i < input.size(); i++)
        {
            T layer = input.get(i);
            R test  = mapper.apply(layer);
            
            if (test == null)
            {
                continue;
            }
            
            if (fewestValue == null || test.compareTo(fewestValue) < 0)
            {
                fewestValue = test;
                fewestIndex = i;
            }
        }
        
        return fewestIndex;
    }
    
    public static <T, R extends Comparable<R>> int getIndexOfHighestValue(List<T> input, Function<T, R> mapper)
    {
        int highestIndex = -1;
        R   highestValue = null;
        
        for (int i = 0; i < input.size(); i++)
        {
            T layer = input.get(i);
            R test  = mapper.apply(layer);
            
            if (test == null)
            {
                continue;
            }
            
            if (highestValue == null || test.compareTo(highestValue) > 0)
            {
                highestValue = test;
                highestIndex = i;
            }
        }
        
        return highestIndex;
    }
}
