package com.bwhas.dao;

import com.bwhas.model.AquiferSource;
import com.bwhas.exception.SourceNotFoundException;

import java.util.List;

/**
 * INTERFACE defining how the rest of the app talks to the water_source table,
 * without caring about the actual JDBC/SQL details underneath.
 */
public interface WaterSourceDAO {
    void addSource(AquiferSource source);
    AquiferSource getSourceById(int sourceId) throws SourceNotFoundException;
    List<AquiferSource> getAllSources();
}