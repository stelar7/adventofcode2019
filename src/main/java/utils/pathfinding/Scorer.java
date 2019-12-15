package utils.pathfinding;

@FunctionalInterface
public interface Scorer<T extends GraphNode>
{
    double computeCost(T from, T to);
}