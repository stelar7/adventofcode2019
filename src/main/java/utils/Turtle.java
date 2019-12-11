package utils;

import org.joml.Vector2i;

public class Turtle
{
    public int       x         = 0;
    public int       y         = 0;
    public Direction direction = Direction.NORTH;
    
    public void turn(Direction dir)
    {
        this.direction = dir;
    }
    
    public void turn(long turn)
    {
        if (turn == 0L)
        {
            turnLeft();
        } else
        {
            turnRight();
        }
    }
    
    public void turnLeft()
    {
        this.direction = direction.left();
    }
    
    public void turnRight()
    {
        this.direction = direction.right();
    }
    
    public void step()
    {
        switch (direction)
        {
            case NORTH:
                y--;
                break;
            case SOUTH:
                y++;
                break;
            case WEST:
                x--;
                break;
            case EAST:
                x++;
                break;
        }
    }
    
    public Vector2i position()
    {
        return new Vector2i(x, y);
    }
    
    @Override
    public String toString()
    {
        return "Turtle{" +
               "x=" + x +
               ", y=" + y +
               ", direction=" + direction +
               '}';
    }
}
