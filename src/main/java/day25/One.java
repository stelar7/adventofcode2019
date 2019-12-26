package day25;

import utils.*;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class One
{
    static Map<String, AreaInfo> paths = new HashMap<>();
    
    static class AreaInfo
    {
        String          name;
        List<Direction> options;
        String          item;
        List<Direction> path;
        
        public AreaInfo(String name, List<Direction> options, String item, List<Direction> pathToHere)
        {
            this.name = name;
            this.options = options;
            this.item = item;
            this.path = pathToHere;
        }
    }
    
    public static void main(String[] args)
    {
        explore(new ArrayList<>());
        List<String>   items              = findGoodItems();
        List<AreaInfo> roomsWithGoodItems = findRoomsFromItems(items);
        IntCodeMachine machine            = collectItems(roomsWithGoodItems);
        
        List<Direction> pathToExit = paths.get("Pressure-Sensitive Floor").path;
        Direction       lastStep   = pathToExit.get(pathToExit.size() - 1);
        walkPath(machine, pathToExit);
        
        findCombination(items, machine, lastStep);
    }
    
    public static void findCombination(List<String> items, IntCodeMachine machine, Direction lastStep)
    {
        Set<Set<String>> combinations = Utils.generateGrayCode(items);
        for (Set<String> combination : combinations)
        {
            dropAll(machine, items);
            pickupAll(machine, combination);
            
            machine.input(lastStep.name().toLowerCase());
            machine.toNextPrompt();
            
            String output = Utils.fromASCII(machine.outputList(true));
            if (output.contains("Analysis complete"))
            {
                System.out.println(output);
                break;
            }
        }
    }
    
    private static void pickupAll(IntCodeMachine machine, Collection<String> items)
    {
        for (String item : items)
        {
            machine.input("take " + item);
            machine.toNextPrompt();
        }
        machine.outputList(true);
    }
    
    private static void dropAll(IntCodeMachine machine, Collection<String> items)
    {
        for (String item : items)
        {
            machine.input("drop " + item);
            machine.toNextPrompt();
        }
        machine.outputList(true);
    }
    
    private static void walkPath(IntCodeMachine machine, List<Direction> path)
    {
        for (Direction direction : path)
        {
            machine.outputList(true);
            machine.input(direction.name().toLowerCase());
            machine.toNextPrompt();
        }
    }
    
    private static IntCodeMachine collectItems(List<AreaInfo> roomsWithGoodItems)
    {
        IntCodeMachine machine = new IntCodeMachine("day25.input");
        machine.toNextPrompt();
        
        roomsWithGoodItems.forEach(v -> {
            walkPath(machine, v.path);
            
            machine.input("take " + v.item);
            machine.toNextPrompt();
            
            List<Direction> pathBack = v.path.stream().map(Direction::opposite).collect(Collectors.toList());
            Collections.reverse(pathBack);
            walkPath(machine, pathBack);
        });
        
        return machine;
    }
    
    private static List<AreaInfo> findRoomsFromItems(List<String> items)
    {
        List<AreaInfo> rooms = new ArrayList<>();
        
        for (String item : items)
        {
            for (Entry<String, AreaInfo> entry : paths.entrySet())
            {
                String   k = entry.getKey();
                AreaInfo v = entry.getValue();
                if (v.item.equalsIgnoreCase(item))
                {
                    rooms.add(v);
                    break;
                }
            }
        }
        
        return rooms;
    }
    
    private static List<String> findGoodItems()
    {
        List<String> good = new ArrayList<>();
        
        paths.forEach((k, v) -> {
            IntCodeMachine machine = generateMachineAtRoom(v.path);
            if (v.item.isEmpty())
            {
                return;
            }
            
            machine.outputList(true);
            machine.input("take " + v.item);
            if (!machine.toNextPrompt(10000))
            {
                // if we are unable to get a prompt in 10000 ops, assume we are in an infinite loop
                return;
            }
            
            if (machine.isHalted())
            {
                // if we died, this item is bad
                machine.outputList(true);
                return;
            }
            
            // try walking to the previous room
            machine.input(v.path.get(v.path.size() - 1).opposite().name().toLowerCase());
            machine.toNextPrompt();
            
            String text     = Utils.fromASCII(machine.outputList(true));
            String nextArea = getName(text);
            if (nextArea == null || nextArea.equalsIgnoreCase(k))
            {
                // if we are stuck in the same area, this item is bad
                return;
            }
            
            good.add(v.item);
        });
        
        return good;
    }
    
    private static IntCodeMachine generateMachineAtRoom(List<Direction> path)
    {
        IntCodeMachine machine = new IntCodeMachine("day25.input");
        machine.toNextPrompt();
        walkPath(machine, path);
        return machine;
    }
    
    
    private static void explore(List<Direction> pathToHere)
    {
        IntCodeMachine  machine = generateMachineAtRoom(pathToHere);
        String          output  = Utils.fromASCII(machine.outputList(true));
        String          name    = getName(output);
        List<Direction> options = getDirections(output);
        String          item    = getItem(output);
        
        if (paths.containsKey(name))
        {
            return;
        }
        
        paths.putIfAbsent(name, new AreaInfo(name, options, item, new ArrayList<>(pathToHere)));
        
        for (Direction option : options)
        {
            List<Direction> newOptions = new ArrayList<>(pathToHere);
            newOptions.add(option);
            explore(newOptions);
        }
    }
    
    private static String getName(String output)
    {
        int nameStartIndex = output.indexOf("== ") + 3;
        if (nameStartIndex == 2)
        {
            return null;
        }
        
        return output.substring(nameStartIndex, output.indexOf(" ==", nameStartIndex));
    }
    
    private static String getItem(String output)
    {
        String[] parts = output.split("Items here:");
        if (parts.length != 2)
        {
            return "";
        }
        
        String       line    = parts[1];
        List<String> results = new ArrayList<>();
        String[]     entries = line.split("\n");
        for (String entry : entries)
        {
            if (entry.startsWith("-"))
            {
                return entry.substring(2);
            }
        }
        
        return "";
    }
    
    private static List<Direction> getDirections(String output)
    {
        String line = output;
        if (output.contains("Items here:"))
        {
            line = output.split("Doors here lead:")[1].split("Items here:")[0];
        }
        
        List<String> results = new ArrayList<>();
        String[]     entries = line.split("\n");
        for (String entry : entries)
        {
            if (entry.startsWith("-"))
            {
                results.add(entry.substring(2));
            }
        }
        
        return results.stream().map(String::toUpperCase).map(Direction::valueOf).collect(Collectors.toList());
    }
    
}
