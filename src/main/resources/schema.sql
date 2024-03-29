CREATE TABLE reminder
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    title             VARCHAR(255) NOT NULL,
    notes             VARCHAR(255),
    category          VARCHAR(255),
    location          VARCHAR(255),
    priority          VARCHAR(10),
    completion_status VARCHAR(20),
    due_date_time     datetime
);
