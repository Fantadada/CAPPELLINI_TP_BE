package org.insa.graphs.algorithm.utils;

import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.*;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Random;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class test_perf {

    enum ModeParcours {
        Dijkstra, AStar
    }

    public DijkstraAlgorithm initAlgorithm (ModeParcours mode, ShortestPathData data) {
        if (mode== ModeParcours.Dijkstra) {
            return new DijkstraAlgorithm(data);
        } else {
            return new AStarAlgorithm(data);
        }
    }

    @Test
    // Verifie que la solution donné par Dijkstra a ete trouvé plus rapidement que celle de Bellman sur un grand chemin
    public void testVitesse() throws IOException {
        final String map = "C:\\Users\\damie\\Downloads\\map\\toulouse.mapgr";
        final GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(map))));
        final Graph graphInsa = reader.read();
        Node nodeOrigin;
        Node nodeDestination;
        ShortestPathData data;
        DijkstraAlgorithm algo;
        DijkstraAlgorithm algoA;
        ShortestPathSolution solutionDijkstra;
        ShortestPathSolution solutionDijkstraA;

        Duration timeAlgo = null;
        Duration timeAlgoA = null;
        int i =0;
        Random r = new Random();
        int A, S;
        while(i < 100){
            A = r.nextInt(9000);
            S = r.nextInt(9000);
            if(S == A){
                S = r.nextInt(9000);
            }
            nodeOrigin = graphInsa.get(A);
            nodeDestination = graphInsa.get(S);
            data = new ShortestPathData(graphInsa, nodeOrigin, nodeDestination, ArcInspectorFactory.getAllFilters().get(0));
            algo = initAlgorithm(ModeParcours.Dijkstra, data);
            solutionDijkstra = algo.run();
            timeAlgo = solutionDijkstra.getSolvingTime();
            System.out.println("D" + timeAlgo.getNano() * Math.pow(10,-6)+ "\n");
            algoA = initAlgorithm(ModeParcours.AStar, data);
            solutionDijkstraA = algoA.run();
            timeAlgoA = solutionDijkstraA.getSolvingTime();
            System.out.println("A" + timeAlgoA.getNano() *Math.pow(10,-6)+ "\n");
            i +=1;
        }
        
        assertTrue(timeAlgo.compareTo(timeAlgoA) <= 0);
    }
}