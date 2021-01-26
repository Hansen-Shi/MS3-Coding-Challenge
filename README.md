# MS3 Coding Challenge

This is a simple Java app used to parse specifically formatted .csv files (10 columns labeled A-J).
Fully filled out rows are considered valid records and are inserted into a SQLite database
while rows with missing values are collectively written into a separate .csv file. 
A log of basic stats can be found post processing. The app hinges on the [opencsv](http://opencsv.sourceforge.net/) open source library
and [this JDBC driver](https://github.com/xerial/sqlite-jdbc).

## Running and Testing

Assuming you have Java downloaded (if not it can be found [here](https://www.java.com/en/)) the project should be runnable when downloaded and loaded into an IDE, 
though the Intellij specific files (.iml, .idea) that I forgot to omit might be troublesome on say, Eclipse.
The project is also runnable via the .jar file located in ```out->artifacts->CSVParserApp_jar```.

### Run via .jar

Download the .jar and put it in a convenient place (ex. an empty Test folder on the desktop)
```
C:\...\...\Desktop\Test
```
Open up your command line and CD into your Test folder (the files created will end up here) and run the following:
```
java -jar CSVParserApp.jar
```
You should then be prompted for an input
```
Enter File Path
```
Paste in the path of your .csv, hit enter, and you should see 2 lines of text.
```
Database Connection Successful
Table Created
```
And there should be 3 new files in your Test folder: a .db, a bad.csv, and a .log.


**important notes:** 
1. When prompted for a filepath you must specify the absolute file path (quotes are fine).
2. The csv used must have exactly 10 columns with headers A-J, or there will be an out-of-bounds exception.
For example, the ```ms3Interview.csv``` technically had 15 columns (despite those cells being completely empty).
I assumed that this was unintentional, so I deleted the excess columns and used that for testing, I put that version in the ```Test File``` folder.    
   
### Design Overview
Upon starting the project I knew there had to be a convenient way to read, write, and parse through a .csv, 
since it's such a widely used file format. I stumbled upon opencsv which happened to have a nice way to parse each row of data via 
their csv to Java bean api. Instead of trying to validate a String array
I could just check the fields of a Java bean for empty spaces. 

The only issue with this approach is that
up until implementing the csv to bean method I had been trying to keep everything dynamically generated in case of future developments
where someone wants to create more tables with varying sizes, etc., etc. and also because hard coding things is generally
bad. But dynamically generating a class seemed excessive at best and bad design at worst.

So afterwards I went back and changed some methods to better fit the small scale of the project, going with the assumption that this
app would ever only need to do what was specified and nothing more. For example, I removed a createTable method and just hardcoded in the 
SQL statement to create the table as I assumed that only the one table is needed. Some other assumptions I made include: I trusted the user
to enter a valid path that isn't already a .db file, I assumed that the only thing that makes a row invalid is missing values and not 
the data itself, I assumed that the user is okay with no GUI, and I assumed for logging that I didn't need to re-verify the amount of data post insertion.

As I was chugging along I always tried to research best design practices and attempted to implement them where applicable, here's a few off the top of my head: utility classes are final, have
a private constructor, and are stateless, insertion into the SQLite database was done in batches with prepared statements
and autocommit turned off to further improve performance, make separate smaller methods where applicable, comment more or less everything, etc.




