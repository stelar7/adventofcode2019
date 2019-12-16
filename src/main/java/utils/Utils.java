package utils;

import org.joml.*;
import utils.pathfinding.*;

import java.io.*;
import java.lang.Math;
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
    
    public static Rectanglef findBoundingBox(Collection<Vector2i> keys)
    {
        int minx = keys.stream().min(Comparator.comparingInt(v -> v.x)).get().x;
        int maxx = keys.stream().max(Comparator.comparingInt(v -> v.x)).get().x;
        
        int miny = keys.stream().min(Comparator.comparingInt(v -> v.y)).get().y;
        int maxy = keys.stream().max(Comparator.comparingInt(v -> v.y)).get().y;
        
        return new Rectanglef(minx, miny, maxx, maxy);
    }
    
    public static String getBlockCharIfTrue(boolean predicate)
    {
        return predicate ? "█" : " ";
    }
    
    public static long gcd(long a, long b)
    {
        if (b == 0)
        {
            return a;
        }
        return gcd(b, a % b);
    }
    
    public static <T> List<List<T>> generateUniquePairs(List<T> input)
    {
        List<List<T>> output = new ArrayList<>();
        
        for (int i = 0; i < input.size(); i++)
        {
            for (int j = i + 1; j < input.size(); j++)
            {
                output.add(List.of(input.get(i), input.get(j)));
            }
        }
        
        return output;
    }
    
    public static long lcm(long a, long b)
    {
        return a / gcd(a, b) * b;
    }
    
    
    public static Map<Long, String> defaultTileSet()
    {
        Map<Long, String> tiles = new HashMap<>();
        tiles.put(0L, "?");
        tiles.put(1L, "█");
        tiles.put(2L, " ");
        tiles.put(3L, "X");
        tiles.put(4L, "*");
        return tiles;
    }
    
    public static void drawGrid(Map<Vector2i, Long> tiles, Map<Long, String> images)
    {
        Rectanglef rect = Utils.findBoundingBox(tiles.keySet());
        for (int i = (int) rect.minY; i <= rect.maxY; i++)
        {
            for (int j = (int) rect.minX; j <= rect.maxX; j++)
            {
                long   color   = tiles.getOrDefault(new Vector2i(j, i), 0L);
                String locChar = images.get(color);
                
                System.out.print(locChar);
            }
            System.out.println();
        }
    }
    
    public static void drawGrid(Map<Vector2i, Long> tiles)
    {
        drawGrid(tiles, Utils.defaultTileSet());
    }
    
    public static String vector2iToString(Vector2i in)
    {
        return in.x + ", " + in.y;
    }
    
    /**
     * anything other than id 0 and id 1 are considered walkable
     */
    public static AStarRouteFinder<VectorNode> generatePathfindingGraph(Map<Vector2i, Long> map)
    {
        Set<VectorNode>          points      = new HashSet<>();
        Map<String, Set<String>> connections = new HashMap<>();
        List<Long>               unwalkable  = List.of(0L, 1L);
        
        
        map.forEach((k, v) -> points.add(new VectorNode(k)));
        map.forEach((k, v) -> {
            if (v != 0L && v != 1L)
            {
                Set<String> nearby = new HashSet<>();
                Vector2i    test   = k.add(1, 0, new Vector2i());
                if (map.containsKey(test) && !unwalkable.contains(map.get(test)))
                {
                    nearby.add(Utils.vector2iToString(test));
                }
                
                test = k.add(-1, 0, new Vector2i());
                if (map.containsKey(test) && !unwalkable.contains(map.get(test)))
                {
                    nearby.add(Utils.vector2iToString(test));
                }
                
                test = k.add(0, 1, new Vector2i());
                if (map.containsKey(test) && !unwalkable.contains(map.get(test)))
                {
                    nearby.add(Utils.vector2iToString(test));
                }
                
                test = k.add(0, -1, new Vector2i());
                if (map.containsKey(test) && !unwalkable.contains(map.get(test)))
                {
                    nearby.add(Utils.vector2iToString(test));
                }
                
                connections.put(Utils.vector2iToString(k), nearby);
            }
        });
        
        Graph<VectorNode> grid = new Graph<>(points, connections);
        return new AStarRouteFinder<>(grid, (a, b) -> 1, (a, b) -> Utils.manhattanDistance(a.getPos(), b.getPos()));
    }
    
    public static List<VectorNode> findLongestPath(Map<Vector2i, Long> map, Vector2i start)
    {
        AStarRouteFinder<VectorNode> finder     = Utils.generatePathfindingGraph(map);
        List<Long>                   unwalkable = List.of(0L, 1L);
        
        List<VectorNode>       longest     = null;
        int                    max         = Integer.MIN_VALUE;
        Map<Vector2i, Integer> distanceMap = new HashMap<>();
        Rectanglef             boundingBox = Utils.findBoundingBox(map.keySet());
        for (int i = (int) boundingBox.minY; i < boundingBox.maxY; i++)
        {
            for (int j = (int) boundingBox.minX; j < boundingBox.maxX; j++)
            {
                Vector2i check = new Vector2i(j, i);
                long     type  = map.getOrDefault(check, 0L);
                if (unwalkable.contains(type))
                {
                    continue;
                }
                
                if (distanceMap.containsKey(check))
                {
                    continue;
                }
                
                List<VectorNode> path = finder.findRoute(new VectorNode(start), new VectorNode(check));
                for (int distance = 0; distance < path.size(); distance++)
                {
                    VectorNode node = path.get(distance);
                    distanceMap.put(node.getPos(), distance + 1);
                }
                
                if (path.size() > max)
                {
                    max = path.size();
                    longest = path;
                }
            }
        }
        
        return longest;
    }
    
    public static int realMod(int a, int b)
    {
        return (((a % b) + b) % b);
    }
}
