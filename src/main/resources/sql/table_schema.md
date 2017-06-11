# TABLE SCHEMA

The following tables are base schema of this project.

### User Table
```
CREATE TABLE USER (
    USERNAME VARCHAR(100) PRIMARY KEY
    , PASSWORD VARCHAR(255)
    , EMAIL VARCHAR(255)
    , IP VARCHAR(15)
    , REG_DATE DATE
    , MOD_DATE DATE
);
```

### Authority Table
```
CREATE TABLE USER_AUTHORITY (
    USERNAME VARCHAR(100)
    , AUTHORITY VARCHAR(100)
);
```
