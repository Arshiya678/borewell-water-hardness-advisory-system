package com.bwhas.dao;

import com.bwhas.model.TestRecord;
import com.bwhas.exception.DatabaseConnectionException;
import com.bwhas.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestRecordDAOImpl implements TestRecordDAO {

    @Override
    public void addTestRecord(TestRecord record) {
        String sql = "INSERT INTO test_record " +
                "(source_id, test_date, calcium_ppm, magnesium_ppm, total_hardness_ppm, ph_value, tds_ppm, tested_by) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, record.getSourceId());
            ps.setDate(2, Date.valueOf(record.getTestDate()));
            ps.setDouble(3, record.getCalciumPpm());
            ps.setDouble(4, record.getMagnesiumPpm());
            ps.setDouble(5, record.getTotalHardnessPpm());
            ps.setDouble(6, record.getPhValue());
            ps.setDouble(7, record.getTdsPpm());
            ps.setString(8, record.getTestedBy());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to insert test record", e);
        }
    }

    @Override
    public List<TestRecord> getRecordsForSource(int sourceId) {
        List<TestRecord> records = new ArrayList<>();
        String sql = "SELECT * FROM test_record WHERE source_id = ? ORDER BY test_date";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, sourceId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    records.add(mapRowToRecord(rs));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to fetch test records for source " + sourceId, e);
        }
        return records;
    }

    @Override
    public List<TestRecord> getAllRecords() {
        List<TestRecord> records = new ArrayList<>();
        String sql = "SELECT * FROM test_record ORDER BY test_date";
        try (Statement st = DBConnection.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                records.add(mapRowToRecord(rs));
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to fetch test records", e);
        }
        return records;
    }

    private TestRecord mapRowToRecord(ResultSet rs) throws SQLException {
        return new TestRecord(
                rs.getInt("test_id"),
                rs.getInt("source_id"),
                rs.getDate("test_date").toLocalDate(),
                rs.getDouble("calcium_ppm"),
                rs.getDouble("magnesium_ppm"),
                rs.getDouble("total_hardness_ppm"),
                rs.getDouble("ph_value"),
                rs.getDouble("tds_ppm"),
                rs.getString("tested_by")
        );
    }
}