package com.bwhas.service;

/**
 * INTERFACE: any class that implements this promises to provide
 * a way to classify a hardness value and give an advisory message.
 * Demonstrates the "Interfaces" requirement from your guidelines.
 */
public interface Classifiable {
    String classify(double hardnessPpm);
    String getAdvisory(double hardnessPpm);
}