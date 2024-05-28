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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class test_djikstra {

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

    ModeParcours mode = ModeParcours.Dijkstra;



    @Test
    // Vérifie que le chemin construit par l'algo est valide en comparant avec Bellman.
    public void testCheminCourtValideTouteRoute() throws Exception {
        final String mapInsa = "C:\\Users\\damie\\Downloads\\map\\insa.mapgr";
        final GraphReader readerInsa = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(Files.newInputStream(Paths.get(mapInsa)))));
        final Graph graphInsa = readerInsa.read();
        final Node nodeOrigin = graphInsa.get(109);
        final Node nodeDestination = graphInsa.get(140);
        final ShortestPathData data = new ShortestPathData(graphInsa, nodeOrigin, nodeDestination, ArcInspectorFactory.getAllFilters().get(0));
        final DijkstraAlgorithm algo = initAlgorithm(this.mode, data);
        final ShortestPathSolution solutionDijkstra = algo.run();
        final BellmanFordAlgorithm bellman = new BellmanFordAlgorithm(data);
        final ShortestPathSolution solutionBellman = bellman.run();
        assertEquals(solutionBellman.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0.0001);
    }

    @Test
    // Vérifie que le chemin construit par l'algo est valide en comparant avec Bellman.
    public void testCheminCourtValideVoitureUniquement() throws Exception {
        final String mapInsa = "C:\\Users\\damie\\Downloads\\map\\insa.mapgr";
        final GraphReader readerInsa = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapInsa))));
        final Graph graphInsa = readerInsa.read();
        final Node nodeOrigin = graphInsa.get(100);
        final Node nodeDestination = graphInsa.get(140);
        final ShortestPathData data = new ShortestPathData(graphInsa, nodeOrigin, nodeDestination, ArcInspectorFactory.getAllFilters().get(1));
        final DijkstraAlgorithm algo = initAlgorithm(this.mode, data);
        final ShortestPathSolution solutionDijkstra = algo.run();
        final BellmanFordAlgorithm bellman = new BellmanFordAlgorithm(data);
        final ShortestPathSolution solutionBellman = bellman.run();
        assertEquals(solutionBellman.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0.0001);
    }

    @Test
    // Vérifie que le chemin construit par l'algo est valide en comparant avec Bellman.
    public void testDijkstraCheminRapideValideTouteRoute() throws Exception {
        final String mapInsa = "C:\\Users\\damie\\Downloads\\map\\insa.mapgr";
        final GraphReader readerInsa = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapInsa))));
        final Graph graphInsa = readerInsa.read();
        final Node nodeOrigin = graphInsa.get(100);
        final Node nodeDestination = graphInsa.get(140);
        final ShortestPathData data = new ShortestPathData(graphInsa, nodeOrigin, nodeDestination, ArcInspectorFactory.getAllFilters().get(2));
        final DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(data);
        final ShortestPathSolution solutionDijkstra = dijkstra.run();
        final BellmanFordAlgorithm bellman = new BellmanFordAlgorithm(data);
        final ShortestPathSolution solutionBellman = bellman.run();
        assertEquals(solutionBellman.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0.0001);
    }

    @Test
    // Vérifie que le chemin construit par l'algo est valide en comparant avec Bellman.
    public void testDijkstraCheminRapideValideVoitureUniquement() throws Exception {
        final String mapInsa = "C:\\Users\\damie\\Downloads\\map\\insa.mapgr";
        final GraphReader readerInsa = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapInsa))));
        final Graph graphInsa = readerInsa.read();
        final Node nodeOrigin = graphInsa.get(100);
        final Node nodeDestination = graphInsa.get(140);
        final ShortestPathData data = new ShortestPathData(graphInsa, nodeOrigin, nodeDestination, ArcInspectorFactory.getAllFilters().get(3));
        final DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(data);
        final ShortestPathSolution solutionDijkstra = dijkstra.run();
        final BellmanFordAlgorithm bellman = new BellmanFordAlgorithm(data);
        final ShortestPathSolution solutionBellman = bellman.run();
        assertEquals(solutionBellman.getPath().getLength(), solutionDijkstra.getPath().getLength(), 0.0001);
    }

    @Test
    // Verifie que la solution n'est pas possible
    public void testCheminValideNonValide() throws IOException {
        final String map = "C:\\Users\\damie\\Downloads\\map\\bretagne.mapgr";
        final GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(map))));
        final Graph graphInsa = reader.read();
        final Node nodeOrigin = graphInsa.get(546721);
        final Node nodeDestination = graphInsa.get(383731);
        final ShortestPathData data = new ShortestPathData(graphInsa, nodeOrigin, nodeDestination, ArcInspectorFactory.getAllFilters().get(0));
        final DijkstraAlgorithm algo = initAlgorithm(this.mode, data);
        final ShortestPathSolution solutionDijkstra = algo.run();
        assertEquals(solutionDijkstra.getStatus(), ShortestPathSolution.Status.INFEASIBLE);
    }

    @Test
    // Verifie que la solution n'est pas possible pour les voitures
    public void testCheminValideNonValidePourVoiture() throws IOException {
        final String map = "C:\\Users\\damie\\Downloads\\map\\insa.mapgr";
        final GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(map))));
        final Graph graphInsa = reader.read();
        final Node nodeOrigin = graphInsa.get(458);
        final Node nodeDestination = graphInsa.get(140);
        final ShortestPathData data = new ShortestPathData(graphInsa, nodeOrigin, nodeDestination, ArcInspectorFactory.getAllFilters().get(1));
        final DijkstraAlgorithm algo = initAlgorithm(this.mode, data);
        final ShortestPathSolution solutionDijkstra = algo.run();
        assertEquals(solutionDijkstra.getStatus(), ShortestPathSolution.Status.INFEASIBLE);
    }

    @Test
    // Verifie que la solution d'un
    public void testCheminDeLongeurNulle() throws IOException {
        final String map = "C:\\Users\\damie\\Downloads\\map\\insa.mapgr";
        final GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(map))));
        final Graph graphInsa = reader.read();
        final Node nodeOrigin = graphInsa.get(100);
        final Node nodeDestination = nodeOrigin;
        final ShortestPathData data = new ShortestPathData(graphInsa, nodeOrigin, nodeDestination, ArcInspectorFactory.getAllFilters().get(0));
        final DijkstraAlgorithm algo = initAlgorithm(this.mode, data);
        final ShortestPathSolution solutionDijkstra = algo.run();
        assertEquals(solutionDijkstra.getPath().getLength(), 0, 0.0001);
    }

    @Test
    // Verifie que la solution donné par Dijkstra a ete trouvé plus rapidement que celle de Bellman sur un grand chemin

    public void testComparaisonVitesseBellman() throws IOException {
        final String map = "C:\\Users\\damie\\Downloads\\map\\toulouse.mapgr";
        final GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(map))));
        final Graph graphInsa = reader.read();
        final Node nodeOrigin = graphInsa.get(3024);
        final Node nodeDestination = graphInsa.get(5402);
        final ShortestPathData data = new ShortestPathData(graphInsa, nodeOrigin, nodeDestination, ArcInspectorFactory.getAllFilters().get(0));
        final DijkstraAlgorithm algo = initAlgorithm(this.mode, data);
        final ShortestPathSolution solutionDijkstra = algo.run();
        Duration timeAlgo = solutionDijkstra.getSolvingTime();
        System.out.println(timeAlgo.getNano());
        final BellmanFordAlgorithm bellman = new BellmanFordAlgorithm(data);
        final ShortestPathSolution solutionBellman = bellman.run();
        Duration timeBellman = solutionBellman.getSolvingTime();
        assertTrue(timeAlgo.compareTo(timeBellman) <= 0);
    }
}