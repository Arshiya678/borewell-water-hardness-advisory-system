package com.bwhas.dao;

import com.bwhas.model.TestRecord;

import java.util.List;

public interface TestRecordDAO {
    void addTestRecord(TestRecord record);
    List<TestRecord> getRecordsForSource(int sourceId);
    List<TestRecord> getAllRecords();
}