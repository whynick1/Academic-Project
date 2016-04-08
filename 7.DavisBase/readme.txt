1. The project should be built under eclipse, and a executable xxx.jar file is expected to create.


2. Run davisBase.jar in terminal using: 

(1). java Ðjar davisBase.jar


3. Operation:(not case sensitive for command)

(1). show schemas;

(2). create schema xxx;

(3). show schemas;

(4). use xxx;

(5). create table student (Age SHORT not null, Name Varchar(10) primary key);

(6). show tables;

(7). insert into table student values (18, Jack);
     insert into table student values (20, Nick);

(8). show tables; (you can see the table_row has increment by 1)

(9). select * from student where Age > 16;

(10). drop table student;

(11). show tables; (now student disappear in system TABLE)

(12). exit;


4. Exception Handling 
(please finish the steps above, then follow the steps below)

(1). show schem; Ñ> You have an error in your SQL syntax;

(2). create schema xxx; Ñ> Schema "xxx" already exists.

(3). use xxy; -> xxy: is not a valid schema name.

(4). create table student (Age INT not null, Name Varchar(10) primary key);
     -> Error: you must choose schema first.

(5). use xxx;create table student (Age INT not null, Name Varchar(10) primary key);
     -> Error: Table "student" already exists.

(6). create table professor ( Name Varch(aabbcc) );
     -> Error: Invalid data type.

(7). insert into table student values (15, Nick);
     -> Error: Violate Primary Key.

(8). insert into table student values (null, Mary);
     -> Error: Unknown column 'null' for type 'SHORT'

(9). drop table xxy;
     -> Error: Table 'xxy' does not exist.










