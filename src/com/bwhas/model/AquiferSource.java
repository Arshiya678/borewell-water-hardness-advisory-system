package com.bwhas.model;

import java.time.LocalDate;

/**
 * Abstract base class representing any groundwater source
 * (deep borewell, shallow well, etc.)
 *
 * Demonstrates:
 *  - ENCAPSULATION: all fields are private, accessed only via getters/setters
 *  - ABSTRACTION: getRiskFactor() is declared but not implemented here -
 *    each subclass decides how "risky" its source type is
 */
public abstract class AquiferSource {

    private int sourceId;
    private String locality;
    private int wardId;
    private double depthFt;
    private LocalDate installedDate;
    private boolean active;

    public AquiferSource(int sourceId, String locality, int wardId,
                          double depthFt, LocalDate installedDate, boolean active) {
        this.sourceId = sourceId;
        this.locality = locality;
        this.wardId = wardId;
        this.depthFt = depthFt;
        this.installedDate = installedDate;
        this.active = active;
    }

    // ---------- Encapsulation: getters and setters ----------

    public int getSourceId() {
        return sourceId;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public int getWardId() {
        return wardId;
    }

    public double getDepthFt() {
        return depthFt;
    }

    public void setDepthFt(double depthFt) {
        this.depthFt = depthFt;
    }

    public LocalDate getInstalledDate() {
        return installedDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Every subclass must define what "source type" string is stored
     * in the database (e.g. "BOREWELL", "OPEN_WELL").
     */
    public abstract String getSourceType();

    /**
     * Every subclass calculates its own depth/age-based risk factor.
     * This is where POLYMORPHISM comes in when we loop over a list of
     * AquiferSource objects and call this method.
     */
    public abstract double getRiskFactor();

    @Override
    public String toString() {
        return String.format("[%s] #%d - %s (depth: %.0f ft, active: %b)",
                getSourceType(), sourceId, locality, depthFt, active);
    }
}