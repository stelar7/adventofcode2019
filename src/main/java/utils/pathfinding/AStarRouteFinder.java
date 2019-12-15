package utils.pathfinding;

import java.util.*;

public class AStarRouteFinder<T extends GraphNode>
{
    private final Graph<T>  graph;
    private final Scorer<T> nextNodeScorer;
    private final Scorer<T> targetScorer;
    
    public AStarRouteFinder(Graph<T> graph, Scorer<T> nextNodeScorer, Scorer<T> targetScorer)
    {
        this.graph = graph;
        this.nextNodeScorer = nextNodeScorer;
        this.targetScorer = targetScorer;
    }
    
    
    public List<T> findRoute(T from, T to)
    {
        Map<T, RouteNode<T>> exploredNodes = new HashMap<>();
        Queue<RouteNode>     openSet       = new PriorityQueue<>();
        
        RouteNode<T> start = new RouteNode<>(from, null, 0d, targetScorer.computeCost(from, to));
        exploredNodes.put(from, start);
        openSet.add(start);
        
        while (!openSet.isEmpty())
        {
            RouteNode<T> next = openSet.poll();
            if (next.getCurrent().equals(to))
            {
                List<T>      route   = new ArrayList<>();
                RouteNode<T> current = next;
                do
                {
                    route.add(0, current.getCurrent());
                    current = exploredNodes.get(current.getPrevious());
                } while (current != null);
                
                return route;
            }
            
            graph.getConnections(next.getCurrent()).forEach(connection -> {
                double       newScore = next.getRouteScore() + nextNodeScorer.computeCost(next.getCurrent(), connection);
                RouteNode<T> nextNode = exploredNodes.getOrDefault(connection, new RouteNode<>(connection));
                exploredNodes.put(connection, nextNode);
                
                if (nextNode.getRouteScore() > newScore)
                {
                    nextNode.setPrevious(next.getCurrent());
                    nextNode.setRouteScore(newScore);
                    nextNode.setEstimatedScore(newScore + targetScorer.computeCost(connection, to));
                    openSet.add(nextNode);
                }
            });
        }
        
        return List.of();
    }
    
}