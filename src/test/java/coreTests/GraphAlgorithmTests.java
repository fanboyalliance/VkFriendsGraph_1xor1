package coreTests;

import core.graph.Graph;
import core.interfaces.IGraph;
import core.kruskal.Kruskal;
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

        IGraph<TestObject> graph = new Graph<>();

        graph.addEdge(testObject1, testObject2, 10);
        graph.addEdge(testObject2, testObject3, 11);
        graph.addEdge(testObject3, testObject2, 11);
        graph.addEdge(testObject3, testObject4, 15);
        graph.addEdge(testObject4, testObject3, 15);
        graph.addEdge(testObject4, testObject5, 18);
        graph.addEdge(testObject5, testObject6, 9);
        graph.addEdge(testObject1, testObject6, 1);
        graph.addEdge(testObject2, testObject4, 6);
        graph.addEdge(testObject2, testObject5, 12);

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
        IGraph<TestObject> graph = getTestData();
        var kruskal = new Kruskal<TestObject>();
        kruskal.findMaxTree(graph);

        Assert.assertEquals(5, graph.getMaxSpanningTreeEdges().size());
    }
}
