package com.bwhas.model;

import java.time.LocalDate;

/**
 * A traditional shallow/open well. These are shallow and their risk is
 * driven more by age/contamination exposure than depth.
 *
 * Demonstrates INHERITANCE and POLYMORPHISM (different getRiskFactor()
 * logic compared to DeepBorewell, even though both extend AquiferSource).
 */
public class ShallowWell extends AquiferSource {

    public ShallowWell(int sourceId, String locality, int wardId,
                        double depthFt, LocalDate installedDate, boolean active) {
        super(sourceId, locality, wardId, depthFt, installedDate, active);
    }

    @Override
    public String getSourceType() {
        return "OPEN_WELL";
    }

    @Override
    public double getRiskFactor() {
        // Shallow wells: risk grows with age since installation (surface
        // exposure, contamination), not depth.
        int yearsInService = LocalDate.now().getYear() - getInstalledDate().getYear();
        if (yearsInService <= 5) {
            return 0.8;
        }
        return 0.8 + (yearsInService - 5) * 0.1;
    }
}