package day12;

import org.joml.Vector3i;
import utils.Utils;
import utils.sources.StringFromFileSupplier;

import java.util.*;
import java.util.stream.Collectors;

public class One
{
    
    static class Planet
    {
        Vector3i pos;
        Vector3i vel = new Vector3i(0);
        
        public Planet(Vector3i pos)
        {
            this.pos = pos;
        }
        
        public int potential()
        {
            return Math.abs(pos.x) + Math.abs(pos.y) + Math.abs(pos.z);
        }
        
        public int kinetic()
        {
            return Math.abs(vel.x) + Math.abs(vel.y) + Math.abs(vel.z);
        }
        
        public int total()
        {
            return potential() * kinetic();
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
            Planet planet = (Planet) o;
            return Objects.equals(pos, planet.pos) &&
                   Objects.equals(vel, planet.vel);
        }
        
        @Override
        public int hashCode()
        {
            return Objects.hash(pos, vel);
        }
        
        @Override
        public String toString()
        {
            return "Planet{" +
                   "pos=" + pos.x + "," + pos.y + "," + pos.z +
                   ", vel=" + vel.x + "," + vel.y + "," + vel.z +
                   '}';
        }
    }
    
    public static void step(List<List<Planet>> planets, List<Planet> moons)
    {
        gravity(planets);
        for (Planet moon : moons)
        {
            moon.pos.add(moon.vel);
        }
    }
    
    public static void gravity(List<List<Planet>> planets)
    {
        for (List<Planet> planetPair : planets)
        {
            Planet planet = planetPair.get(0);
            Planet other  = planetPair.get(1);
            
            if (planet.pos.x > other.pos.x)
            {
                planet.vel.add(-1, 0, 0);
                other.vel.add(1, 0, 0);
            } else if (planet.pos.x < other.pos.x)
            {
                planet.vel.add(1, 0, 0);
                other.vel.add(-1, 0, 0);
            }
            
            if (planet.pos.y > other.pos.y)
            {
                planet.vel.add(0, -1, 0);
                other.vel.add(0, 1, 0);
            } else if (planet.pos.y < other.pos.y)
            {
                planet.vel.add(0, 1, 0);
                other.vel.add(0, -1, 0);
            }
            
            if (planet.pos.z > other.pos.z)
            {
                planet.vel.add(0, 0, -1);
                other.vel.add(0, 0, 1);
            } else if (planet.pos.z < other.pos.z)
            {
                planet.vel.add(0, 0, 1);
                other.vel.add(0, 0, -1);
            }
        }
    }
    
    public static void main(String[] args)
    {
        String       regex = "<x=(?<x>-?\\d+), y=(?<y>-?\\d+), z=(?<z>-?\\d+)>";
        List<String> input = StringFromFileSupplier.create("day12.input", false).getDataSource();
        List<Planet> moons = input.stream()
                                  .map(s -> Utils.extractRegex(s, regex, "x", "y", "z"))
                                  .map(m -> new Vector3i(Integer.parseInt(m.get("x")), Integer.parseInt(m.get("y")), Integer.parseInt(m.get("z"))))
                                  .map(Planet::new)
                                  .collect(Collectors.toList());
        
        List<List<Planet>> planets = Utils.generateUniquePairs(moons);
        for (int i = 0; i < 1000; i++)
        {
            step(planets, moons);
        }
        
        int total = moons.stream().mapToInt(Planet::total).sum();
        System.out.println(total);
    }
    
}
