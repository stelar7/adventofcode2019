package day17;

import org.joml.Vector2i;
import utils.*;

import java.util.*;
import java.util.regex.*;

public class Two
{
    public static void main(String[] args)
    {
        IntCodeMachine      machine  = new IntCodeMachine("day17.input");
        Map<Vector2i, Long> grid     = One.generateGrid(machine);
        String              path     = findPath(grid);
        List<String>        shortest = findFullSubsequence(path);
        
        expandAndAddNewline(shortest);
        String     input = String.join("", shortest) + "n\n";
        List<Long> longs = Utils.toASCII(input);
        
        machine.setMemory(0L, 2L, true);
        machine.input(longs);
        machine.runToEnd();
        machine.printAllOutput();
    }
    
    private static void expandAndAddNewline(List<String> shortest)
    {
        for (int i = 0; i < shortest.size(); i++)
        {
            String       line    = shortest.get(i);
            String[]     split   = line.split(",");
            List<String> newline = new ArrayList<>();
            for (String part : split)
            {
                if (part.length() > 1)
                {
                    newline.add(part.substring(0, 1));
                    newline.add(part.substring(1));
                } else
                {
                    newline.add(line);
                    break;
                }
            }
            
            String realLine = String.join(",", newline) + "\n";
            shortest.set(i, realLine);
        }
    }
    
    private static List<String> findFullSubsequence(String path)
    {
        List<String> returned = new ArrayList<>();
        
        char         repl    = 'A';
        List<String> ignored = new ArrayList<>();
        for (int i = 0; i < 3; i++)
        {
            ignored.add(Character.toString(repl++));
            String substr = findBestSubsequence(path, ignored);
            path = path.replace(substr, ignored.get(ignored.size() - 1));
            returned.add(substr);
        }
        returned.add(0, path);
        return returned;
    }
    
    // LZ77?
    private static String findBestSubsequence(String path, List<String> offset)
    {
        StringBuilder sb = new StringBuilder(path);
        sb.reverse();
        
        String[]      entries = sb.toString().split(",");
        int           index   = 0;
        StringBuilder first   = new StringBuilder();
        
        int lastCount = 0;
        for (int i = 0; i < entries.length; i++)
        {
            String entry = entries[index++];
            if (first.toString().isEmpty() && offset.contains(entry))
            {
                continue;
            }
            
            first.append(entry);
            Pattern p       = Pattern.compile("(" + first.toString() + ")");
            Matcher matcher = p.matcher(sb.toString());
            int     count   = 0;
            while (matcher.find())
            {
                count++;
            }
            
            if (count == 1)
            {
                // remove last entry...
                first.reverse();
                while (first.charAt(0) != ',')
                {
                    first.deleteCharAt(0);
                }
                first.deleteCharAt(0);
                return first.toString();
            }
            
            first.append(",");
        }
        
        return sb.toString();
    }
    
    private static String findPath(Map<Vector2i, Long> grid)
    {
        Vector2i  start = Utils.getKeyForHighestValue(grid, Long::intValue);
        Vector2i  prev  = null;
        Direction dir   = Direction.NORTH;
        
        List<String> entries = new ArrayList<>();
        
        boolean didNotTurn = true;
        do
        {
            didNotTurn = true;
            for (Direction direction : Direction.values())
            {
                String letter = "";
                if (dir.left() == direction)
                {
                    letter = "L";
                } else if (dir.right() == direction)
                {
                    letter = "R";
                } else
                {
                    continue;
                }
                
                
                Droid droid = new Droid();
                droid.x = start.x;
                droid.y = start.y;
                droid.turn(direction);
                
                do
                {
                    droid.step();
                } while (grid.getOrDefault(droid.position(), -1L) == (long) '#');
                
                droid.turn(droid.direction.opposite());
                droid.step();
                
                if (droid.position().equals(prev))
                {
                    continue;
                }
                
                Vector2i diff   = droid.position().sub(start, new Vector2i());
                int      offset = Math.max(Math.abs(diff.x), Math.abs(diff.y));
                
                if (offset == 0)
                {
                    continue;
                }
                
                entries.add(letter + offset);
                
                dir = direction;
                start = droid.position();
                didNotTurn = false;
            }
        } while (!didNotTurn);
        
        return String.join(",", entries);
    }
}
