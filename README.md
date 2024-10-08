# Simple Database GUI Application
This is a basic Java Swing application that simulates a simple database engine with a graphical user interface (GUI). The application allows users to perform basic database operations such as inserting, displaying, retrieving, and deleting data stored in tables. Each table can hold entries of different data types such as String, Character, or Number. 

Features:
Insert Data: Allows users to insert data into a specified table with the selected data type (String, Character, or Number) and an index.
Display Data: Shows all data currently stored in the database, organized by table, index, type, and value.
Retrieve Data: Fetch data from a specified table at a given index.
Clear Data: Clear all data from the database.
Delete Data: Remove a specific entry from a table using the table name and index.

How It Works: 
Database Structure: The underlying database is a simple in-memory structure built using a HashMap. Each table is represented as a HashMap of index-value pairs where values can be strings, characters, or numeric values.
Data Types Supported:
String: Text data.
Character: Single character (must be exactly one character).
Number: Can be an integer or a floating-point number (automatically detected).

GUI Overview:
The user interface consists of a set of buttons that allow interaction with the database:
Insert: Opens a window to input table name, data type, data, and index.
Display: Displays all data entries from every table in the database.
Get: Retrieves a specific data entry based on table name and index.
Clear: Clears the entire database.
Delete: Deletes a specific entry from a table by table name and index.

How to Use:
Insert Data: Click the "Insert" button, enter the table name, choose the data type, input the data and index, and click "Add Entry".
Display Data: Click the "Display" button to view all entries currently stored.
Get Data: Click the "Get" button, provide the table name and index, and retrieve the specific entry.
Clear Data: Click "Clear" to remove all data from the database.
Delete Data: Click the "Delete" button, enter the table name and index, and delete the corresponding data entry.
