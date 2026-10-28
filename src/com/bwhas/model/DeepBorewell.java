package com.bwhas.model;

import java.time.LocalDate;

/**
 * A drilled borewell. Deeper borewells generally pass through more
 * mineral-rich rock layers, so risk of hardness increases with depth.
 *
 * Demonstrates INHERITANCE (extends AquiferSource) and POLYMORPHISM
 * (its own version of getRiskFactor()).
 */
public class DeepBorewell extends AquiferSource {

    public DeepBorewell(int sourceId, String locality, int wardId,
                         double depthFt, LocalDate installedDate, boolean active) {
        super(sourceId, locality, wardId, depthFt, installedDate, active);
    }

    @Override
    public String getSourceType() {
        return "BOREWELL";
    }

    @Override
    public double getRiskFactor() {
        // Simple weighted model: every 200 ft beyond 500 ft adds 0.5 risk points
        double depth = getDepthFt();
        if (depth <= 500) {
            return 1.0;
        }
        return 1.0 + ((depth - 500) / 200.0) * 0.5;
    }
}