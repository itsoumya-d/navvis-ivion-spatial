package com.itsoumya.ivion;
import com.itsoumya.ivion.model.Poi;
import com.itsoumya.ivion.service.SpatialSearch;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
class SpatialSearchTest {
  private final List<Poi> data = List.of(
    new Poi(1L, 1, 0.0, 0.0, 1.5, "a"),
    new Poi(2L, 1, 10.0, 0.0, 1.5, "b"),
    new Poi(3L, 1, 10.0, 10.0, 1.5, "c"),
    new Poi(4L, 2, 0.0, 0.0, 3.0, "d")
  );
  @Test void filtersByFloor() { var hits = SpatialSearch.withinRadius(data, 1, 0, 0, 100); assertEquals(3, hits.size()); assertTrue(hits.stream().allMatch(h -> h.poi().floor() == 1)); }
  @Test void sortsByDistance() { var hits = SpatialSearch.withinRadius(data, 1, 0, 0, 20); assertEquals("a", hits.get(0).poi().label()); assertEquals("b", hits.get(1).poi().label()); }
  @Test void deterministicTieBreakById() { var pois = List.of(new Poi(2L, 1, 1.0, 0.0, 0.0, "b"), new Poi(1L, 1, -1.0, 0.0, 0.0, "a")); var hits = SpatialSearch.withinRadius(pois, 1, 0, 0, 5); assertEquals(1L, hits.get(0).poi().id()); }
  @Test void rejectsNegativeRadius() { assertThrows(IllegalArgumentException.class, () -> SpatialSearch.withinRadius(data, 1, 0, 0, -1)); }
  @Test void nearestEmpty() { assertTrue(SpatialSearch.nearest(List.of(), 1, 0, 0).isEmpty()); }
  @Test void nearestFindsClosest() { var hit = SpatialSearch.nearest(data, 2, 0.5, 0.5).orElseThrow(); assertEquals("d", hit.poi().label()); }
}
