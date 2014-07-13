CREATE TABLE TASK (
    ID BIGINT PRIMARY KEY NOT NULL,
    TITLE VARCHAR(50) NOT NULL,
    STATUS TINYINT NOT NULL,
    DETAIL VARCHAR(1000),
    REGISTERED_DATE TIMESTAMP NOT NULL,
    COMPLETED_DATE TIMESTAMP,
    IMPORTANCE TINYINT NOT NULL,
    PERIOD TIMESTAMP NOT NULL
);

CREATE TABLE WORK_TIME (
    TASK_ID BIGINT NOT NULL,
    START_TIME TIMESTAMP NOT NULL,
    END_DATE TIMESTAMP NOT NULL,
    CONSTRAINT WORK_TIME_UK_1 UNIQUE (TASK_ID, START_TIME, END_DATE),
    CONSTRAINT WORK_TIME_FK_1 FOREIGN KEY (TASK_ID) REFERENCES TASK (ID)
);

INSERT INTO TASK VALUES(1,'作業中タスク',1,NULL,'2014-07-10 12:00:00.000000',NULL,0,'2014-07-15 13:00:00.000000');
INSERT INTO TASK VALUES(2,'未着手１',0,NULL,'2014-07-10 12:00:00.000000',NULL,1,'2014-07-15 12:10:00.000000');
INSERT INTO TASK VALUES(3,'未着手２',0,NULL,'2014-07-10 12:00:00.000000',NULL,3,'2014-07-15 12:10:00.000000');
INSERT INTO TASK VALUES(4,'中断１',2,NULL,'2014-07-10 12:00:00.000000',NULL,3,'2014-08-15 12:10:00.000000');
INSERT INTO TASK VALUES(5,'今日完了１',3,NULL,'2014-07-10 10:05:00.000000','2014-07-14 00:00:00.000000',0,'2014-07-15 13:00:00.000000');
INSERT INTO TASK VALUES(6,'昨日完了',3,NULL,'2014-07-10 10:05:00.000000','2014-07-13 23:59:59.000000',0,'2014-07-15 13:00:00.000000');
INSERT INTO TASK VALUES(7,'今日完了２',3,NULL,'2014-07-10 10:05:00.000000','2014-07-14 10:00:00.000000',3,'2014-07-15 13:00:00.000000');
INSERT INTO TASK VALUES(8,'中断２',2,NULL,'2014-07-10 12:00:00.000000',NULL,0,'2014-08-15 12:10:00.000000');