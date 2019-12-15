package utils.pathfinding;

import java.util.Objects;

public class RouteNode<T extends GraphNode> implements Comparable<RouteNode>
{
    private final T      current;
    private       T      previous;
    private       double routeScore;
    private       double estimatedScore;
    
    RouteNode(T current)
    {
        this(current, null, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    }
    
    RouteNode(T current, T previous, double routeScore, double estimatedScore)
    {
        this.current = current;
        this.previous = previous;
        this.routeScore = routeScore;
        this.estimatedScore = estimatedScore;
    }
    
    @Override
    public int compareTo(RouteNode other)
    {
        return Double.compare(this.estimatedScore, other.estimatedScore);
    }
    
    public void setPrevious(T previous)
    {
        this.previous = previous;
    }
    
    public void setRouteScore(double routeScore)
    {
        this.routeScore = routeScore;
    }
    
    public void setEstimatedScore(double estimatedScore)
    {
        this.estimatedScore = estimatedScore;
    }
    
    public T getCurrent()
    {
        return current;
    }
    
    public T getPrevious()
    {
        return previous;
    }
    
    public double getRouteScore()
    {
        return routeScore;
    }
    
    public double getEstimatedScore()
    {
        return estimatedScore;
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
        RouteNode<?> routeNode = (RouteNode<?>) o;
        return Double.compare(routeNode.routeScore, routeScore) == 0 &&
               Double.compare(routeNode.estimatedScore, estimatedScore) == 0 &&
               Objects.equals(current, routeNode.current) &&
               Objects.equals(previous, routeNode.previous);
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hash(current, previous, routeScore, estimatedScore);
    }
    
    @Override
    public String toString()
    {
        return "RouteNode{" +
               "current=" + current +
               ", previous=" + previous +
               ", routeScore=" + routeScore +
               ", estimatedScore=" + estimatedScore +
               '}';
    }
}