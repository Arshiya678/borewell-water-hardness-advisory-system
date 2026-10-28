package com.bwhas.model;

import java.time.LocalDate;

/**
 * A single water quality test reading tied to a source.
 * Plain model class - encapsulation only (no inheritance needed here).
 */
public class TestRecord {

    private int testId;
    private int sourceId;
    private LocalDate testDate;
    private double calciumPpm;
    private double magnesiumPpm;
    private double totalHardnessPpm;
    private double phValue;
    private double tdsPpm;
    private String testedBy;

    public TestRecord(int testId, int sourceId, LocalDate testDate,
                       double calciumPpm, double magnesiumPpm, double totalHardnessPpm,
                       double phValue, double tdsPpm, String testedBy) {
        this.testId = testId;
        this.sourceId = sourceId;
        this.testDate = testDate;
        this.calciumPpm = calciumPpm;
        this.magnesiumPpm = magnesiumPpm;
        this.totalHardnessPpm = totalHardnessPpm;
        this.phValue = phValue;
        this.tdsPpm = tdsPpm;
        this.testedBy = testedBy;
    }

    public int getTestId() { return testId; }
    public int getSourceId() { return sourceId; }
    public LocalDate getTestDate() { return testDate; }
    public double getCalciumPpm() { return calciumPpm; }
    public double getMagnesiumPpm() { return magnesiumPpm; }
    public double getTotalHardnessPpm() { return totalHardnessPpm; }
    public double getPhValue() { return phValue; }
    public double getTdsPpm() { return tdsPpm; }
    public String getTestedBy() { return testedBy; }

    @Override
    public String toString() {
        return String.format("Test #%d [Source #%d] %s - Hardness: %.1f ppm, pH: %.1f, TDS: %.0f ppm",
                testId, sourceId, testDate, totalHardnessPpm, phValue, tdsPpm);
    }
}