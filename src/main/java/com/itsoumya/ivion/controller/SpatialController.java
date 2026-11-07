package com.itsoumya.ivion.controller;
import com.itsoumya.ivion.geometry.CoordinateTransform;
import com.itsoumya.ivion.model.Poi;
import com.itsoumya.ivion.service.SpatialSearch;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController
@RequestMapping("/api")
public class SpatialController {
  private final JdbcTemplate jdbc;
  public SpatialController(JdbcTemplate jdbc) { this.jdbc = jdbc; }
  public record TransformReq(double originX, double originY, double scale, double rotationDeg, double x, double y) {}
  private List<Poi> all() {
    return jdbc.query("SELECT id, floor, x, y, z, label FROM poi", (rs, n) ->
      new Poi(rs.getObject("id", Long.class), rs.getInt("floor"), rs.getDouble("x"), rs.getDouble("y"), rs.getDouble("z"), rs.getString("label")));
  }
  @GetMapping("/pois")
  public List<Map<String, Object>> pois(@RequestParam(required = false) Integer floor,
                                         @RequestParam double x, @RequestParam double y, @RequestParam double radius) {
    List<Map<String, Object>> out = new ArrayList<>();
    for (var h : SpatialSearch.withinRadius(all(), floor, x, y, radius)) {
      out.add(Map.of("id", h.poi().id(), "floor", h.poi().floor(), "x", h.poi().x(), "y", h.poi().y(), "z", h.poi().z(), "label", h.poi().label(), "distanceM", Math.round(h.distanceM() * 1000.0) / 1000.0));
    }
    return out;
  }
  @PostMapping("/transform")
  public Map<String, Object> transform(@RequestBody TransformReq r) {
    var p = new CoordinateTransform.Transform(r.originX(), r.originY(), r.scale(), r.rotationDeg()).apply(r.x(), r.y());
    return Map.of("x", p.x(), "y", p.y());
  }
}
