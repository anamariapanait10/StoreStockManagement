# Store Inventory Management

## First stage requirements:

1. System definition
- [x] Create a list based on the chosen theme of at least 10 actions/queries that can be done within the system and a list of at least 8 object types.

2. Implementation

- [x] To implement in the Java language an application based on those defined in the first point.
- [x] The application will contain:
    - [x] simple classes with private / protected attributes and access methods
    - [x] at least 2 different collections capable of managing the previously defined objects (eg: List, Set, Map, etc.) of which at least one must be sorted
    - [x] use inheritance to create additional classes and use them within collections
    - [x] at least one service class to expose system operations
    - [x] a Main class from which calls to services are made

## My implementation:

1. System definition:
 - Entities:
    - Product
    - Category
    - Supplier
    - Stock
    - Location
    - Order
    - Transaction (CashTransaction, CardTransaction)

 - Abstract classes:
   - Transaction

 - Enums:
   - ProductType
     - Perishable
     - Nonperishable
   - LocationType
     - Deposit
     - Shop

 - Actions:
   - add/delete a product
   - sort products by name
   - add/delete a category for products
   - sort categories by parent category ids
   - filter categories by parent name
   - add/delete a deposit/shop
   - update product stock of a deposit/shop
   - check if a product is in stock for a deposit/shop
   - add/remove supplier
   - order from a supplier to a deposit/shop
   - make a transaction for an order

## Second stage requirements:
In this stage I extended the first stage by implementing persistence using a relational database and JDBC. I used Docker to run the database in a container with a PostgreSQL server.

I created services that expose create, read, update and delete operations for all defined classes.

Generic singleton services were used for writing and reading from the database (Repository).

A service was created to write to a CSV file every time one of the actions available is executed (File structure: Timestamp,  Importance, Name, Action).

A logger was used to log all SQL exceptions and other errors.

A ticker was implemented using a thread that checks every second if a product has expired and if so it sets the status from "Not expired" to "Expired".

I added a Requests class in gateway package in which I retrieved information about products that are not expired. I added a mapper for this products that uses Jackson Library to map the JSON retrieved from the API into NotExpiredProducts. Inside the Requests class I use HttpClient to connect to the api and get the data from there.