CREATE TABLE volunteer (
    volunteer_id integer PRIMARY KEY,
    v_password varchar2(20) not null,
    v_name varchar2(50),
    v_phone integer not null check (v_phone between 1000000 and 9999999),
    v_address varchar2(50), v_city varchar2(50));

    INSERT INTO volunteer VALUES (123456, "23423dsdg", "Paul Pogba", 3215567, "567 Main Mall", "Vancouver");
    INSERT INTO volunteer VALUES (123457, "23424dsdg", "Abigail Ayala", 3215568, "567 Main Mall", "Toronto");
    INSERT INTO volunteer VALUES (123458, "23425dsdg", "David Smith", 3215569, "567 Main Mall", "Montreal");
    INSERT INTO volunteer VALUES (123459, "23426dsdg", "Paulina Payne", 3215561, "567 Main Mall", "Vancouver");
    INSERT INTO volunteer VALUES(123451, "23426dsdg", "Mathias Smith", 3215563, "567 Main Mall", "Vancouver");
CREATE TABLE director (
    director_id integer PRIMARY KEY,
    d_password varchar2(20) not null,
    d_name varchar2(50),
    d_phone integer not null check (d_phone between 1000000 and 9999999),
    d_address varchar2(50), d_city varchar2(50));
    INSERT INTO director VALUES (111111, "23423dsdggg", "jeff", 8901311, "568 Main Mall", "Vancouver");
    INSERT INTO director VALUES (222222, "23423dsdggg", "Geoff", 7771311, "669 Main Mall", "Vancouver");
    INSERT INTO director VALUES (333333, "23423dsdggg", "Doc Rivers", 6661311, "669 East Mall", "Saskatoon");

CREATE TABLE beneficiary(
    beneficiary_id integer PRIMARY KEY,  +
    b_name varchar2(50),
    age integer,
    phoneNumber integer ,
    city varchar2(50) ,
    postalCode varchar2(50));
     INSERT INTO beneficiary VALUES (123456, "Lauren Lynch", 50, 7065678, "Vancouver", "V6L1X1");
     INSERT INTO beneficiary VALUES (123457, "Cristian Lynch", 15, 7565678, "Vancouver", "V6L1X1");
     INSERT INTO beneficiary VALUES (123458, "Abel Smith", 5, 7365678, "Vancouver", "V6L1X1");
     INSERT INTO beneficiary VALUES (123459, "Allisson Thoms", 18, 7865678, "Vancouver", "V6L1X1");
     INSERT INTO beneficiary VALUES (123451, "Fione Gallad", 60, 7765678, "Montreal", "V6L1X1");
     INSERT INTO beneficiary VALUES (123452, "Peter Lynch", 20, 7965678, "Montreal", "V6L1X1");
     INSERT INTO beneficiary VALUES (123453, "Jyn Lee", 12, 6065678, "Toronto", "V6L1X1");
     INSERT INTO beneficiary VALUES (123454, "Lauren Levi", 7, 6045678, "Toronto", "V6L1X1");
     INSERT INTO beneficiary VALUES (123455, "Mirkka Puente", 35, 7765671, "Toronto", "V6L1X1");
     INSERT INTO beneficiary VALUES (123416, "John Pratt", 45, 6045674, "Montreal", "V6L1X1");

CREATE TABLE donor (
    donor_id integer  PRIMARY KEY ,
    donorName varchar2(50),
    phoneNumber integer);

    INSERT INTO donor VALUES (111111, "David Thoms", 8901311);

CREATE TABLE leads (
    director_id integer,
    volunteer_id integer,
    FOREIGN KEY (director_id) REFERENCES director(director_id) ON DELETE CASCADE,
    FOREIGN KEY (volunteer_id) REFERENCES volunteer(volunteer_id) ON DELETE CASCADE);

    INSERT INTO leads VALUES (111111,123456);

CREATE TABLE help (
    project_id integer,
    beneficiary_id integer,
    FOREIGN KEY (project_id) REFERENCES project(project_id) ON DELETE CASCADE,
    FOREIGN KEY (beneficiary_id) REFERENCES beneficiary(beneficiary_id) ON DELETE CASCADE);

    INSERT INTO help VALUES(123456,123456);
    INSERT INTO help VALUES(123456,123457);
    INSERT INTO help VALUES(123456,123458);
    INSERT INTO help VALUES(123456,123459);
    INSERT INTO help VALUES(123456,123451);
    INSERT INTO help VALUES(123456,123452);
    INSERT INTO help VALUES(123456,123453);
    INSERT INTO help VALUES(123456,123454);
    INSERT INTO help VALUES(123456,123455);
    INSERT INTO help VALUES(123456,123416);

CREATE TABLE workon (
    project_id integer,
    volunteer_id integer,
    FOREIGN KEY (project_id) REFERENCES project (project_id) ON DELETE CASCADE,
    FOREIGN KEY (volunteer_id) REFERENCES volunteer (volunteer_id) ON DELETE CASCADE);

        INSERT INTO workon VALUES(123456,123456);
        INSERT INTO workon VALUES(123456,123457);
        INSERT INTO workon VALUES(123456,123458);
        INSERT INTO workon VALUES(123456,123459);
        INSERT INTO workon VALUES(123456,123451);


