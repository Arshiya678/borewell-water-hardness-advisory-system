package com.bwhas.main;

import com.bwhas.model.AquiferSource;
import com.bwhas.model.DeepBorewell;
import com.bwhas.model.ShallowWell;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * TEMPORARY test class - just to verify the model layer compiles and
 * behaves correctly before we build the DAO/service layers.
 * You can delete this once you're confident everything works.
 */
public class ModelTestRun {

    public static void main(String[] args) {

        // Create one deep borewell and one shallow well
        AquiferSource deep = new DeepBorewell(
                1, "Ambedkar Nagar", 1, 1200, LocalDate.of(2019, 6, 12), true);

        AquiferSource shallow = new ShallowWell(
                2, "Old Town Yelahanka", 3, 60, LocalDate.of(2005, 3, 1), true);

        // Put both into a single list of the abstract type -
        // this is what enables polymorphism below
        List<AquiferSource> sources = new ArrayList<>();
        sources.add(deep);
        sources.add(shallow);

        System.out.println("---- AquiferSource Test Run ----\n");

        for (AquiferSource source : sources) {
            // Even though 'source' is declared as AquiferSource,
            // Java calls the correct subclass's version of getRiskFactor()
            // at runtime. That's polymorphism.
            System.out.println(source.toString());
            System.out.printf("  Source type   : %s%n", source.getSourceType());
            System.out.printf("  Risk factor   : %.2f%n", source.getRiskFactor());
            System.out.println();
        }
    }
}