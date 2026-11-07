package com.itsoumya.ivion.geometry;
/** Floorplan local metres -> global: x' = ox + s*(c*x - sn*y), y' = oy + s*(sn*x + c*y). Deterministic, no I/O. */
public class CoordinateTransform {
  public CoordinateTransform() {}
  public record Point(double x, double y) {}
  public record Transform(double originX, double originY, double scale, double rotationDeg) {
    public Point apply(double x, double y) {
      double theta = Math.toRadians(rotationDeg);
      double c = Math.cos(theta), sn = Math.sin(theta);
      return new Point(originX + scale * (c * x - sn * y), originY + scale * (sn * x + c * y));
    }
  }
  public Transform identity() { return new Transform(0.0, 0.0, 1.0, 0.0); }
  public Transform centered(double originX, double originY, double scale) { return new Transform(originX, originY, scale, 0.0); }
  static double distance(double x1, double y1, double x2, double y2) {
    double dx = x2 - x1, dy = y2 - y1;
    return Math.sqrt(dx * dx + dy * dy);
  }
}
