-- Spring Batch metadata tables
CREATE TABLE IF NOT EXISTS BATCH_JOB_INSTANCE  (
                                                   JOB_INSTANCE_ID BIGINT PRIMARY KEY ,
                                                   VERSION BIGINT,
                                                   JOB_NAME VARCHAR(100) NOT NULL ,
    JOB_KEY VARCHAR(32) NOT NULL
    );

CREATE TABLE IF NOT EXISTS BATCH_JOB_EXECUTION  (
                                                    JOB_EXECUTION_ID BIGINT PRIMARY KEY ,
                                                    VERSION BIGINT,
                                                    JOB_INSTANCE_ID BIGINT NOT NULL,
                                                    CREATE_TIME TIMESTAMP NOT NULL,
                                                    START_TIME TIMESTAMP DEFAULT NULL,
                                                    END_TIME TIMESTAMP DEFAULT NULL,
                                                    STATUS VARCHAR(10),
    EXIT_CODE VARCHAR(20),
    EXIT_MESSAGE VARCHAR(2500),
    LAST_UPDATED TIMESTAMP,
    constraint JOB_INSTANCE_EXECUTION_FK foreign key (JOB_INSTANCE_ID)
    references BATCH_JOB_INSTANCE(JOB_INSTANCE_ID)
    );

CREATE TABLE IF NOT EXISTS BATCH_JOB_EXECUTION_PARAMS  (
                                                           JOB_EXECUTION_ID BIGINT NOT NULL ,
                                                           PARAMETER_NAME VARCHAR(100) NOT NULL ,
    PARAMETER_TYPE VARCHAR(100) NOT NULL ,
    PARAMETER_VALUE VARCHAR(2500) ,
    IDENTIFYING CHAR(1) NOT NULL ,
    constraint JOB_EXEC_PARAMS_FK foreign key (JOB_EXECUTION_ID)
    references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
    );

CREATE TABLE IF NOT EXISTS BATCH_STEP_EXECUTION  (
                                                     STEP_EXECUTION_ID BIGINT NOT NULL PRIMARY KEY ,
                                                     VERSION BIGINT NOT NULL,
                                                     STEP_NAME VARCHAR(100) NOT NULL,
    JOB_EXECUTION_ID BIGINT NOT NULL,
    CREATE_TIME TIMESTAMP NOT NULL,
    START_TIME TIMESTAMP DEFAULT NULL ,
    END_TIME TIMESTAMP DEFAULT NULL,
    STATUS VARCHAR(10),
    COMMIT_COUNT BIGINT ,
    READ_COUNT BIGINT ,
    FILTER_COUNT BIGINT ,
    WRITE_COUNT BIGINT ,
    READ_SKIP_COUNT BIGINT ,
    WRITE_SKIP_COUNT BIGINT ,
    PROCESS_SKIP_COUNT BIGINT ,
    ROLLBACK_COUNT BIGINT ,
    EXIT_CODE VARCHAR(20) ,
    EXIT_MESSAGE VARCHAR(2500) ,
    LAST_UPDATED TIMESTAMP,
    constraint JOB_EXECUTION_STEP_FK foreign key (JOB_EXECUTION_ID)
    references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
    );

CREATE TABLE IF NOT EXISTS BATCH_JOB_EXECUTION_CONTEXT  (
                                                            JOB_EXECUTION_ID BIGINT PRIMARY KEY,
                                                            SHORT_CONTEXT VARCHAR(2500) NOT NULL,
    SERIALIZED_CONTEXT CLOB,
    constraint JOB_EXEC_CTX_FK foreign key (JOB_EXECUTION_ID)
    references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
    );

CREATE TABLE IF NOT EXISTS BATCH_STEP_EXECUTION_CONTEXT  (
                                                             STEP_EXECUTION_ID BIGINT PRIMARY KEY,
                                                             SHORT_CONTEXT VARCHAR(2500) NOT NULL,
    SERIALIZED_CONTEXT CLOB,
    constraint STEP_EXEC_CTX_FK foreign key (STEP_EXECUTION_ID)
    references BATCH_STEP_EXECUTION(STEP_EXECUTION_ID)
    );

-- Sequence generators for batch tables
CREATE SEQUENCE IF NOT EXISTS BATCH_JOB_EXECUTION_SEQ;
CREATE SEQUENCE IF NOT EXISTS BATCH_JOB_SEQ;
CREATE SEQUENCE IF NOT EXISTS BATCH_STEP_EXECUTION_SEQ;

-- Your application tables
CREATE TABLE IF NOT EXISTS BENEFICIARY (
                                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                           STATE VARCHAR(255),
    NOM VARCHAR(255),
    PRENOM VARCHAR(255),
    DATEDEPOT TIMESTAMP,
    DATENAISSANCE TIMESTAMP,
    GENRE VARCHAR(50),
    ADRESSE_BENEFICIAIRE VARCHAR(255),
    VILLE_BENEFICIAIRE VARCHAR(100),
    FILIERE VARCHAR(255),
    DUREE_ETUDE VARCHAR(100),
    NOMECOLE VARCHAR(255)
    );

-- Add some sample data
INSERT INTO BENEFICIARY (STATE, NOM, PRENOM, DATEDEPOT, DATENAISSANCE, GENRE, ADRESSE_BENEFICIAIRE, VILLE_BENEFICIAIRE, FILIERE, DUREE_ETUDE, NOMECOLE)
VALUES
    ('ACTIVE', 'Doe', 'John', CURRENT_TIMESTAMP, '1995-05-15', 'Male', '123 Main St', 'New York', 'Computer Science', '4 years', 'Tech University'),
    ('ACTIVE', 'Smith', 'Jane', CURRENT_TIMESTAMP, '1998-08-21', 'Female', '456 Oak Ave', 'Boston', 'Engineering', '5 years', 'Engineering College'),
    ('PENDING', 'Brown', 'Michael', CURRENT_TIMESTAMP, '1997-03-10', 'Male', '789 Pine Rd', 'Chicago', 'Business', '3 years', 'Business School');