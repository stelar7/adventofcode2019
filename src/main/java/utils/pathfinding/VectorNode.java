package utils.pathfinding;

import org.joml.Vector2i;
import utils.Utils;

import java.util.Objects;

public class VectorNode implements GraphNode
{
    Vector2i pos;
    
    public VectorNode(Vector2i pos)
    {
        this.pos = pos;
    }
    
    @Override
    public String getId()
    {
        return Utils.vector2iToString(pos);
    }
    
    @Override
    public String toString()
    {
        return "(" + getId() + ")";
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
        VectorNode that = (VectorNode) o;
        return Objects.equals(pos, that.pos);
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hash(pos);
    }
    
    public Vector2i getPos()
    {
        return pos;
    }
}
