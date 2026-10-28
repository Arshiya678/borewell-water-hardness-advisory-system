package com.bwhas.main;

import com.bwhas.dao.*;
import com.bwhas.exception.*;
import com.bwhas.model.*;
import com.bwhas.service.HardnessClassificationService;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * Main entry point - console menu driven application.
 * Ties together model, dao, service and exception layers.
 */
public class BWHASApplication {

    private static WaterSourceDAO sourceDAO = new WaterSourceDAOImpl();
    private static TestRecordDAO recordDAO = new TestRecordDAOImpl();
    private static HardnessClassificationService classifier = new HardnessClassificationService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println(" Borewell Water Hardness Advisory System ");
        System.out.println("        SDG 6 - Clean Water & Sanitation ");
        System.out.println("=========================================");

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1": addSource(); break;
                    case "2": listSources(); break;
                    case "3": addTestRecord(); break;
                    case "4": viewRecordsForSource(); break;
                    case "5": running = false; break;
                    default: System.out.println("Invalid choice, try again.");
                }
            } catch (InvalidTestReadingException e) {
                // Custom checked exceptions caught and handled gracefully
                System.out.println("Error: " + e.getMessage());
            } catch (DatabaseConnectionException e) {
                System.out.println("Database error: " + e.getMessage());
                if (e.getCause() != null) {
                    System.out.println("Real reason: " + e.getCause().getMessage());
                }
            }
        }
        System.out.println("Exiting. Stay safe, drink clean water!");
    }

    private static void printMenu() {
        System.out.println("\n1. Add Water Source");
        System.out.println("2. List All Sources");
        System.out.println("3. Add Test Record");
        System.out.println("4. View Records for a Source (with classification)");
        System.out.println("5. Exit");
        System.out.print("Choose: ");
    }

    private static void addSource() {
        System.out.print("Source type (1=Borewell, 2=OpenWell): ");
        String type = scanner.nextLine().trim();
        System.out.print("Locality: ");
        String locality = scanner.nextLine().trim();
        System.out.print("Ward ID: ");
        int wardId = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Depth (ft): ");
        double depth = Double.parseDouble(scanner.nextLine().trim());

        AquiferSource source;
        if (type.equals("1")) {
            source = new DeepBorewell(0, locality, wardId, depth, LocalDate.now(), true);
        } else {
            source = new ShallowWell(0, locality, wardId, depth, LocalDate.now(), true);
        }
        sourceDAO.addSource(source);
        System.out.println("Source added successfully.");
    }

    private static void listSources() {
        List<AquiferSource> sources = sourceDAO.getAllSources();
        System.out.println("\n--- All Water Sources ---");
        for (AquiferSource s : sources) {
            System.out.println(s.toString() + "  | Risk factor: " + String.format("%.2f", s.getRiskFactor()));
        }
    }

    private static void addTestRecord() throws InvalidTestReadingException {
        System.out.print("Source ID: ");
        int sourceId = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Calcium (ppm): ");
        double ca = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Magnesium (ppm): ");
        double mg = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Total hardness (ppm): ");
        double hardness = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("pH: ");
        double ph = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("TDS (ppm): ");
        double tds = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Tested by: ");
        String tester = scanner.nextLine().trim();

        // Validate BEFORE inserting - throws InvalidTestReadingException if bad data
        classifier.validateReading(ca, mg, hardness, ph);

        TestRecord record = new TestRecord(0, sourceId, LocalDate.now(), ca, mg, hardness, ph, tds, tester);
        recordDAO.addTestRecord(record);
        System.out.println("Test record added. Classification: " + classifier.classify(hardness));
        System.out.println("Advisory: " + classifier.getAdvisory(hardness));
    }

    private static void viewRecordsForSource() {
        System.out.print("Source ID: ");
        int sourceId = Integer.parseInt(scanner.nextLine().trim());
        List<TestRecord> records = recordDAO.getRecordsForSource(sourceId);
        if (records.isEmpty()) {
            System.out.println("No records found for this source.");
            return;
        }
        for (TestRecord r : records) {
            System.out.println(r.toString());
            System.out.println("  -> Classification: " + classifier.classify(r.getTotalHardnessPpm()));
            System.out.println("  -> Advisory: " + classifier.getAdvisory(r.getTotalHardnessPpm()));
        }
    }
}