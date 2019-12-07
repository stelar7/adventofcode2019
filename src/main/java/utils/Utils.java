package utils;

import org.joml.Vector2i;

import java.io.*;
import java.lang.reflect.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
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
    
    public static int getIndexOfHighestValue(int[] array)
    {
        int largest = 0;
        for (int i = 1; i < array.length; i++)
        {
            if (array[i] > array[largest])
            {
                largest = i;
            }
        }
        return largest;
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
    
    
    public static String getKeyForHighestValue(Map<String, Integer> data)
    {
        final AtomicReference<String> maxKey   = new AtomicReference<>("");
        int[]                         maxValue = {-1};
        data.forEach((k, v) -> {
            maxValue[0] = maxValue[0] < v ? v : maxValue[0];
            if (maxValue[0] == v)
            {
                maxKey.set(k);
            }
        });
        return maxKey.get();
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
            result.add(new ArrayList<T>());
            return result;
        }
        
        T firstElement = original.remove(0);
        
        List<List<T>> returnValue  = new ArrayList<>();
        List<List<T>> permutations = permutations(original);
        
        for (List<T> smallerPermutated : permutations)
        {
            for (int index = 0; index <= smallerPermutated.size(); index++)
            {
                List<T> temp = new ArrayList<T>(smallerPermutated);
                temp.add(index, firstElement);
                returnValue.add(temp);
            }
        }
        
        return returnValue;
    }
}
