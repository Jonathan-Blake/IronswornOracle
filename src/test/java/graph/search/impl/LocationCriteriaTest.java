package graph.search.impl;

import graph.Graph;
import graph.Vertex;
import graph.constraints.AtLocation;
import graph.search.Criteria;
import graph.structs.CampaignLocation;
import graph.structs.NamedCampaignItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LocationCriteriaTest {
    private static Graph<NamedCampaignItem> simpleGraph;
    @Mock
    private static NamedCampaignItem bill;
    @Mock
    private static NamedCampaignItem ted;
    @Mock
    private static CampaignLocation town;

    @BeforeEach
    void setup() {
        simpleGraph = new Graph<>(new AtLocation());
        simpleGraph.addEdge(AtLocation.get(), bill, town);
        simpleGraph.addVertex(ted);
    }

    @Test
    void LocationContainsTown_returnsBill() {
        final Vertex<NamedCampaignItem> results = simpleGraph.search(Criteria.allOf(LocationCriteria.contentsOfLocation(town)).build());
        assertNotNull(results);
        assertEquals(bill, results.getContents());
    }

    @Test
    void LocationContainsTed_returnsNull() {
        final Vertex<NamedCampaignItem> results = simpleGraph.search(Criteria.allOf(LocationCriteria.contentsOfLocation(ted)).build());
        assertNull(results);
    }

    @Test
    void LocationOfBill_returnsTown() {
        final Vertex<NamedCampaignItem> results = simpleGraph.search(Criteria.allOf(LocationCriteria.locationOf(bill)).build());
        assertNotNull(results);
        assertEquals(town, results.getContents());
    }

    @Test
    void LocationOfTed_returnsNull() {
        final Vertex<NamedCampaignItem> results = simpleGraph.search(Criteria.allOf(LocationCriteria.locationOf(ted)).build());
        assertNull(results);
    }

    @Test
    void NoLocation_returnsTed() {
        final Vertex<NamedCampaignItem> results = simpleGraph.search(Criteria.allOf(LocationCriteria.noLocation()).build());
        assertNotNull(results);
        assertEquals(ted, results.getContents());
    }
}