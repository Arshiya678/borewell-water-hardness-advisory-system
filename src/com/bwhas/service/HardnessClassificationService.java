package com.bwhas.service;

import com.bwhas.exception.InvalidTestReadingException;

/**
 * Implements Classifiable. Contains the core business logic:
 * classify a hardness (ppm as CaCO3) reading into a category and
 * give a usage advisory. Thresholds follow BIS/WHO conventions.
 */
public class HardnessClassificationService implements Classifiable {

    // Thresholds in ppm (mg/L as CaCO3)
    private static final double SOFT_LIMIT = 60;
    private static final double MODERATE_LIMIT = 120;
    private static final double HARD_LIMIT = 180;

    @Override
    public String classify(double hardnessPpm) {
        if (hardnessPpm < 0) {
            // Defensive check - real validation happens via the exception
            // in validateReading(), this is just a safety net.
            return "INVALID";
        }
        if (hardnessPpm < SOFT_LIMIT) {
            return "Soft";
        } else if (hardnessPpm < MODERATE_LIMIT) {
            return "Moderately Hard";
        } else if (hardnessPpm < HARD_LIMIT) {
            return "Hard";
        } else {
            return "Very Hard";
        }
    }

    @Override
    public String getAdvisory(double hardnessPpm) {
        String category = classify(hardnessPpm);
        switch (category) {
            case "Soft":
                return "Safe for drinking and household use without treatment.";
            case "Moderately Hard":
                return "Generally safe; may cause mild soap scum. Occasional appliance descaling recommended.";
            case "Hard":
                return "Treatment (softener) recommended for appliances. Monitor for scaling in pipes.";
            case "Very Hard":
                return "Not recommended for drinking without RO/softening treatment. High risk of pipe/appliance scaling.";
            default:
                return "Unable to classify - invalid reading.";
        }
    }

    /**
     * Validates a raw reading before it is even classified.
     * Demonstrates EXCEPTION HANDLING with a custom checked exception.
     */
    public void validateReading(double calcium, double magnesium, double totalHardness, double ph)
            throws InvalidTestReadingException {
        if (calcium < 0 || magnesium < 0 || totalHardness < 0) {
            throw new InvalidTestReadingException(
                    "Calcium, magnesium and hardness values cannot be negative.");
        }
        if (ph < 0 || ph > 14) {
            throw new InvalidTestReadingException(
                    "pH value must be between 0 and 14. Got: " + ph);
        }
        if (totalHardness < (calcium + magnesium) * 0.5) {
            // sanity check: total hardness should roughly track Ca+Mg
            throw new InvalidTestReadingException(
                    "Total hardness reading is inconsistent with calcium/magnesium values.");
        }
    }
}