package coreTests;

import core.graph.Graph;
import core.interfaces.IGraph;
import core.kruskal.Kruskal;
import core.graph.Vertex;
import org.junit.Assert;
import org.junit.Test;

public class GraphAlgorithmTests {

    private class TestObject {
        String name = "name";
        long id = 0;
        String lastName = "lastName";
    }

    private IGraph<TestObject> getTestData() {
        var testObject1 = new TestObject();
        var testObject2 = new TestObject();
        var testObject3 = new TestObject();
        var testObject4 = new TestObject();
        var testObject5 = new TestObject();
        var testObject6 = new TestObject();
        var v1 = new Vertex<>(testObject1);
        var v2 = new Vertex<>(testObject2);
        var v3 = new Vertex<>(testObject3);
        var v4 = new Vertex<>(testObject4);
        var v5 = new Vertex<>(testObject5);
        var v6 = new Vertex<>(testObject6);

        IGraph<TestObject> graph = new Graph<>();

        graph.addEdge(v1, v2, 10);
        graph.addEdge(v2, v3, 11);
        graph.addEdge(v3, v4, 15);
        graph.addEdge(v4, v5, 18);
        graph.addEdge(v5, v6, 9);
        graph.addEdge(v1, v6, 1);
        graph.addEdge(v2, v4, 6);
        graph.addEdge(v2, v5, 12);

        return graph;
    }


    @Test
    public void Test_Graph_Adding_Edges_Should_Contain_8_Edges() {
        var graph = getTestData();
        var numberOfEdges = graph.getEdges().size();

        Assert.assertEquals(8, numberOfEdges);
    }

    @Test
    public void Kraskal_Algorithm_With_5_Vertices() {
        var graph = getTestData();
        var kruskal = new Kruskal<>(graph);
        kruskal.findMaxTree();

        Assert.assertEquals(5, graph.getMaxSpanningTreeEdges().size());
    }
}
