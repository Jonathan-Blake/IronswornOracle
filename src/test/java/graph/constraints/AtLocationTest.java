package graph.constraints;

import graph.Edge;
import graph.Vertex;
import graph.structs.CampaignLocation;
import graph.structs.NPC;
import graph.structs.NamedCampaignItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AtLocationTest {
    @Test
    <T extends NamedCampaignItem> void test_AllowsNPCSAtLocation() {
        final NPC frank = new NPC("Frank");
        final NPC bill = new NPC("Bill");
        final NPC ben = new NPC("Ben");
        final CampaignLocation sunny_town = new CampaignLocation("Sunny Town");
        final Vertex<T> location = new Vertex<>((T) sunny_town);
        assertTrue(new AtLocation().testEdgeConstraint(
                (Edge<NamedCampaignItem>) new Edge<>(
                        new Vertex<>((T) frank),
                        location,
                        AtLocation.RELATIONSHIP)));
        assertTrue(new AtLocation().testEdgeConstraint(
                (Edge<NamedCampaignItem>) new Edge<>(
                        new Vertex<>((T) bill),
                        location,
                        AtLocation.RELATIONSHIP)));
        assertTrue(new AtLocation().testEdgeConstraint(
                (Edge<NamedCampaignItem>) new Edge<>(
                        new Vertex<>((T) ben),
                        location,
                        AtLocation.RELATIONSHIP)));
    }

    @Test
    <T extends NamedCampaignItem> void test_DoesNot_AllowNPCAtNPC() {
        final NPC frank = new NPC("Frank");
        final NPC bill = new NPC("Bill");
        assertFalse(new AtLocation().testEdgeConstraint(
                new Edge<>(
                        new Vertex<>(frank),
                        new Vertex<>(bill),
                        AtLocation.RELATIONSHIP)));
    }

    @Test
    <T extends NamedCampaignItem> void test_AllowsLocationAtLocation() {
        final CampaignLocation sunny_town = new CampaignLocation("Sunny Town");
        final CampaignLocation rainy_village = new CampaignLocation("Rainy Village");
        final CampaignLocation misty_city = new CampaignLocation("Misty City");
        final Vertex<T> sVertex = new Vertex<>((T) sunny_town);
        assertTrue(new AtLocation().testEdgeConstraint(
                (Edge<NamedCampaignItem>) new Edge<>(
                        new Vertex<>((T) rainy_village),
                        sVertex,
                        AtLocation.RELATIONSHIP)));
        assertThenLink(sVertex, new Vertex<>((T) misty_city));
    }

    @Test
    <T extends NamedCampaignItem> void test_DoesNot_AllowLocationsAtNPCs() {
        final NPC frank = new NPC("Frank");
        final CampaignLocation sunny_town = new CampaignLocation("Sunny Town");
        assertFalse(new AtLocation().testEdgeConstraint(
                new Edge<>(
                        new Vertex<>(sunny_town),
                        new Vertex<>(frank),
                        AtLocation.RELATIONSHIP)));
    }

    @Test
    <T extends NamedCampaignItem> void test_DoesNot_AllowNPCsAtMultipleLocations() {
        final NPC frank = new NPC("Frank");
        final CampaignLocation sunny_town = new CampaignLocation("Sunny Town");
        final CampaignLocation rainy_village = new CampaignLocation("Rainy Village");
        final Vertex<T> fVertex = new Vertex<>((T) frank);
        final Vertex<T> sVertex = new Vertex<>((T) sunny_town);
        assertTrue(new AtLocation().testEdgeConstraint(
                (Edge<NamedCampaignItem>) new Edge<>(
                        fVertex,
                        sVertex,
                        AtLocation.RELATIONSHIP)));
        fVertex.link(sVertex, AtLocation.get());
        assertFalse(new AtLocation().testEdgeConstraint(
                (Edge<NamedCampaignItem>) new Edge<>(
                        fVertex,
                        new Vertex<>((T) rainy_village),
                        AtLocation.RELATIONSHIP)));
    }

    @Test
    <T extends NamedCampaignItem> void test_DoesNotAllowCircularLocationRelations() {
        final CampaignLocation sunny_town = new CampaignLocation("Sunny Town");
        final CampaignLocation rainy_village = new CampaignLocation("Rainy Village");
        final CampaignLocation misty_city = new CampaignLocation("Misty City");
        final Vertex<T> rVertex = new Vertex<>((T) rainy_village);
        final Vertex<T> sVertex = new Vertex<>((T) sunny_town);
        final Vertex<T> mVertex = new Vertex<>((T) misty_city);
        assertThenLink(sVertex, mVertex);

        assertThenLink(rVertex, sVertex);

        assertFalse(new AtLocation().testEdgeConstraint(
                (Edge<NamedCampaignItem>) new Edge<>(
                        mVertex,
                        rVertex,
                        AtLocation.RELATIONSHIP)));
    }

    @Test
    <T extends NamedCampaignItem> void test_LocationsAreTransitive() {
        final CampaignLocation sunny_town = new CampaignLocation("Sunny Town");
        final CampaignLocation rainy_village = new CampaignLocation("Rainy Village");
        final CampaignLocation misty_city = new CampaignLocation("Misty City");
        final Vertex<T> rVertex = new Vertex<>((T) rainy_village);
        final Vertex<T> sVertex = new Vertex<>((T) sunny_town);
        final Vertex<T> mVertex = new Vertex<>((T) misty_city);
        assertThenLink(sVertex, mVertex);

        assertThenLink(rVertex, sVertex);

        assertTrue(new AtLocation().testPossibleConnection((Vertex<NamedCampaignItem>) rVertex, (Vertex<NamedCampaignItem>) mVertex));
    }

    private <T extends NamedCampaignItem> void assertThenLink(Vertex<T> first, Vertex<T> second) {
        assertTrue(new AtLocation().testEdgeConstraint(
                (Edge<NamedCampaignItem>) new Edge<>(
                        first,
                        second,
                        AtLocation.RELATIONSHIP)));
        assertTrue(new AtLocation().testPossibleConnection((Vertex<NamedCampaignItem>) first, (Vertex<NamedCampaignItem>) second));
        assertFalse(new AtLocation().testPossibleConnection((Vertex<NamedCampaignItem>) second, (Vertex<NamedCampaignItem>) first));
    }
}