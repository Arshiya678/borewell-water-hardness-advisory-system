package com.bwhas.dao;

import com.bwhas.model.AquiferSource;
import com.bwhas.model.DeepBorewell;
import com.bwhas.model.ShallowWell;
import com.bwhas.exception.SourceNotFoundException;
import com.bwhas.exception.DatabaseConnectionException;
import com.bwhas.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC implementation of WaterSourceDAO. This is where actual SQL
 * queries run against MySQL through the DBConnection utility.
 */
public class WaterSourceDAOImpl implements WaterSourceDAO {

    @Override
    public void addSource(AquiferSource source) {
        String sql = "INSERT INTO water_source (source_type, locality, ward_id, depth_ft, installed_date, active) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, source.getSourceType());
            ps.setString(2, source.getLocality());
            ps.setInt(3, source.getWardId());
            ps.setDouble(4, source.getDepthFt());
            ps.setDate(5, Date.valueOf(source.getInstalledDate()));
            ps.setBoolean(6, source.isActive());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to insert water source", e);
        }
    }

    @Override
    public AquiferSource getSourceById(int sourceId) throws SourceNotFoundException {
        String sql = "SELECT * FROM water_source WHERE source_id = ?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, sourceId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToSource(rs);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to fetch water source #" + sourceId, e);
        }
        throw new SourceNotFoundException("No water source found with ID " + sourceId);
    }

    @Override
    public List<AquiferSource> getAllSources() {
        List<AquiferSource> sources = new ArrayList<>();
        String sql = "SELECT * FROM water_source";
        try (Statement st = DBConnection.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                sources.add(mapRowToSource(rs));
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to fetch water sources", e);
        }
        return sources;
    }

    /**
     * Converts one row of the ResultSet into the correct subclass object
     * (DeepBorewell or ShallowWell) based on source_type.
     * This is POLYMORPHISM applied to database rows.
     */
    private AquiferSource mapRowToSource(ResultSet rs) throws SQLException {
        int id = rs.getInt("source_id");
        String type = rs.getString("source_type");
        String locality = rs.getString("locality");
        int wardId = rs.getInt("ward_id");
        double depth = rs.getDouble("depth_ft");
        java.time.LocalDate installedDate = rs.getDate("installed_date").toLocalDate();
        boolean active = rs.getBoolean("active");

        if ("BOREWELL".equals(type)) {
            return new DeepBorewell(id, locality, wardId, depth, installedDate, active);
        } else {
            return new ShallowWell(id, locality, wardId, depth, installedDate, active);
        }
    }
}