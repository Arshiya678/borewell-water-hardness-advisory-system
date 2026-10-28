-- ============================================================
-- Borewell Water Hardness Advisory System (BWHAS)
-- Database Schema (MySQL)
-- SDG 6: Clean Water and Sanitation
-- ============================================================

CREATE DATABASE IF NOT EXISTS bwhas_db;
USE bwhas_db;

-- ------------------------------------------------------------
-- Table: ward
-- Represents administrative wards/localities in Bengaluru
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS ward (
    ward_id       INT AUTO_INCREMENT PRIMARY KEY,
    ward_name     VARCHAR(100) NOT NULL,
    zone          VARCHAR(50)  NOT NULL,     -- e.g. East, West, South, North, RR Nagar
    is_high_risk  BOOLEAN DEFAULT FALSE      -- flagged by admin dashboard logic
);

-- ------------------------------------------------------------
-- Table: water_source
-- Base entity for all water sources (borewell / open well)
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS water_source (
    source_id     INT AUTO_INCREMENT PRIMARY KEY,
    source_type   VARCHAR(20) NOT NULL,      -- 'BOREWELL' or 'OPEN_WELL'
    locality      VARCHAR(100) NOT NULL,
    ward_id       INT NOT NULL,
    depth_ft      DOUBLE NOT NULL,
    installed_date DATE,
    active        BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (ward_id) REFERENCES ward(ward_id)
);

-- ------------------------------------------------------------
-- Table: test_record
-- Water quality test readings tied to a source, over time
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS test_record (
    test_id           INT AUTO_INCREMENT PRIMARY KEY,
    source_id         INT NOT NULL,
    test_date         DATE NOT NULL,
    calcium_ppm       DOUBLE NOT NULL,
    magnesium_ppm     DOUBLE NOT NULL,
    total_hardness_ppm DOUBLE NOT NULL,       -- as CaCO3
    ph_value          DOUBLE,
    tds_ppm           DOUBLE,
    tested_by         VARCHAR(100),
    FOREIGN KEY (source_id) REFERENCES water_source(source_id)
);

-- ------------------------------------------------------------
-- Table: app_user
-- Admin / field-tester login
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS app_user (
    user_id       INT AUTO_INCREMENT PRIMARY KEY,
    username      VARCHAR(50) UNIQUE NOT NULL,
    password      VARCHAR(100) NOT NULL,
    role          VARCHAR(20) NOT NULL       -- 'ADMIN' or 'FIELD_TESTER'
);

-- ------------------------------------------------------------
-- Sample seed data
-- ------------------------------------------------------------
INSERT INTO ward (ward_name, zone) VALUES
('Whitefield', 'East'),
('Mahadevapura', 'East'),
('Yelahanka', 'North'),
('Jayanagar', 'South'),
('Rajarajeshwari Nagar', 'West');

INSERT INTO water_source (source_type, locality, ward_id, depth_ft, installed_date) VALUES
('BOREWELL', 'Ambedkar Nagar', 1, 950, '2019-06-12'),
('BOREWELL', 'ITPL Road', 2, 1200, '2021-01-20'),
('OPEN_WELL', 'Old Town Yelahanka', 3, 60, '2005-03-01'),
('BOREWELL', 'JP Nagar Extension', 4, 700, '2017-11-10'),
('BOREWELL', 'Kengeri Satellite Town', 5, 1450, '2022-08-15');

INSERT INTO test_record (source_id, test_date, calcium_ppm, magnesium_ppm, total_hardness_ppm, ph_value, tds_ppm, tested_by) VALUES
(1, '2026-01-15', 80, 40, 210, 7.1, 850, 'Field Team A'),
(2, '2026-01-16', 110, 55, 320, 7.4, 1020, 'Field Team A'),
(3, '2026-01-17', 30, 15, 90,  6.9, 400, 'Field Team B'),
(4, '2026-01-18', 60, 25, 150, 7.0, 610, 'Field Team B'),
(5, '2026-01-19', 130, 70, 380, 7.6, 1150, 'Field Team A');

-- No sample app_user rows are inserted. Never store plaintext passwords.
-- If authentication is added, store a securely salted password hash, not the password itself.
