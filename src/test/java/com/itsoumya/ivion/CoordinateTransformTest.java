package com.itsoumya.ivion;
import com.itsoumya.ivion.geometry.CoordinateTransform;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class CoordinateTransformTest {
  private final CoordinateTransform t = new CoordinateTransform();
  @Test void identity() { var p = t.identity().apply(3, 4); assertEquals(3.0, p.x()); assertEquals(4.0, p.y()); }
  @Test void translation() { var p = new CoordinateTransform.Transform(10, 20, 1.0, 0.0).apply(1, 2); assertEquals(11.0, p.x()); assertEquals(22.0, p.y()); }
  @Test void scale() { var p = new CoordinateTransform.Transform(0, 0, 2.0, 0.0).apply(3, 4); assertEquals(6.0, p.x()); assertEquals(8.0, p.y()); }
  @Test void rotation90() { var p = new CoordinateTransform.Transform(0, 0, 1.0, 90.0).apply(1, 0); assertEquals(0.0, p.x(), 1e-9); assertEquals(1.0, p.y(), 1e-9); }
  @Test void centered() { var p = t.centered(5, 5, 1.5).apply(2, 0); assertEquals(8.0, p.x()); assertEquals(5.0, p.y()); }
}
