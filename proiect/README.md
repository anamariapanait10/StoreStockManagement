# Store Inventory Management

## First stage requirements:

1. System definition
- [x] Create a list based on the chosen theme of at least 10 actions/queries that can be done within the system and a list of at least 8 object types.

2. Implementation

- [x] To implement in the Java language an application based on those defined in the first point.
- [x] The application will contain:
    - [x] simple classes with private / protected attributes and access methods
    - [ ] at least 2 different collections capable of managing the previously defined objects (eg: List, Set, Map, etc.) of which at least one must be sorted
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
   - add/delete/ product to the stock of a deposit/shop
   - sort categories by parent category and names
   - filter categories by parent category
   - update product stock of a deposit/shop
   - check if a product is in stock for a deposit/shop
   - add/remove a product category
   - order stock from a supplier to a deposit/shop
   - cancel stock order from a supplier (if the transport didn't leave) 
   - make a transaction for a stock order
   - add transport for a stock
   - update product placement in a deposit/shop
   - add/remove a deposit/shop
   - add/remove a supplier

## Second stage requirements: