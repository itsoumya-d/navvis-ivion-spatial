// React/TS floorplan viewer: renders POIs from /api/pois onto SVG, viewport transform.
import React from 'react';
export type PoiHit = { id: number; floor: number; x: number; y: number; z: number; label: string; distanceM: number };
export function IvionFloorplan({ hits, floor }: { hits: PoiHit[]; floor: number }) {
  const scalePx = 40;
  return (<svg viewBox="-40 -40 400 400" role="img" aria-label={`floor ${floor}`}>
    {hits.map(h => (<g key={h.id}><circle cx={h.x * scalePx} cy={h.y * scalePx} r={6} fill="#111"/><text x={h.x * scalePx + 10} y={h.y * scalePx + 4} fontSize={11}>{h.label} ({h.distanceM}m)</text></g>))}
  </svg>);
}
export default IvionFloorplan;
