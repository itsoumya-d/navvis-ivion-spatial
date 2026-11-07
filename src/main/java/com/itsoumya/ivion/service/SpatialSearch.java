package com.itsoumya.ivion.service;
import com.itsoumya.ivion.model.Poi;
import java.util.*;
/** Indoor POI radius + nearest search. Deterministic ordering by distance then id. */
public final class SpatialSearch {
  private SpatialSearch() {}
  public record Hit(Poi poi, double distanceM) {}
  public static List<Hit> withinRadius(List<Poi> pois, Integer floor, double x, double y, double radiusM) {
    if (radiusM < 0) throw new IllegalArgumentException("radiusM must be >= 0");
    List<Hit> out = new ArrayList<>();
    for (Poi p : pois) {
      if (floor != null && !floor.equals(p.floor())) continue;
      double d = Math.sqrt(Math.pow(p.x() - x, 2) + Math.pow(p.y() - y, 2));
      if (d <= radiusM) out.add(new Hit(p, d));
    }
    out.sort(Comparator.comparingDouble(Hit::distanceM).thenComparing(h -> h.poi().id() == null ? Long.MAX_VALUE : h.poi().id()));
    return out;
  }
  public static Optional<Hit> nearest(List<Poi> pois, Integer floor, double x, double y) {
    var hits = withinRadius(pois, floor, x, y, Double.MAX_VALUE);
    return hits.isEmpty() ? Optional.empty() : Optional.of(hits.get(0));
  }
}
