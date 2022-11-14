CREATE DATABASE bookmanagement;

use bookmanagement;

DROP Table placedspecificorder;
DROP TABLE soldbooks;
DROP Table loanedbooks;
DROP Table orderinventory;
DROP TABLE reservedBooks;
DROP TABLE studentInfo;
DROP TABLE employeeInfo;
DROP TABLE bookInfo;

CREATE TABLE StudentInfo (
    student_id INTEGER(9) PRIMARY KEY,
    first_name VARCHAR(30),
    last_name VARCHAR(30),
    email VARCHAR(30)
);

CREATE TABLE BookInfo (
	book_isbn INTEGER(10) PRIMARY KEY,
    book_name VARCHAR(50),
    author_fname VARCHAR(30),
    author_lname VARCHAR(30),
    book_publisher VARCHAR(50),
    book_price DECIMAL(10,2),
    sell_stock INTEGER(10),
    loan_stock INTEGER(10)
    );

CREATE TABLE EmployeeInfo (
	emp_id INTEGER(5) PRIMARY KEY,
    emp_fname VARCHAR(30),
    emp_lname VARCHAR(30)
);

CREATE TABLE loanedBooks(
	reservation_id INTEGER(5) PRIMARY KEY,
    book_isbn INTEGER(10),
    student_id INTEGER(9),
    loaned_date DATE,
    due_date DATE,
    FOREIGN KEY (student_id) REFERENCES StudentInfo(student_id),
    FOREIGN KEY (book_isbn) REFERENCES BookInfo(book_isbn)
);

CREATE TABLE PlacedSpecificOrder(
	order_id INTEGER(5) PRIMARY KEY,
    student_id INTEGER(9), 
    book_isbn INTEGER(10),
    emp_id INTEGER(5),
    order_date DATE,
    FOREIGN KEY (student_id) REFERENCES StudentInfo(student_id),
    FOREIGN KEY (book_isbn) REFERENCES BookInfo(book_isbn),
    FOREIGN KEY (emp_id) REFERENCES EmployeeInfo(emp_id)
);

CREATE TABLE OrderInventory(
	id INTEGER(5) PRIMARY KEY AUTO_INCREMENT,
    order_id INTEGER(5),
    book_isbn INTEGER(10),
    item_quantity INTEGER(10),
    arrival_date DATE,
    FOREIGN KEY (book_isbn) REFERENCES BookInfo(book_isbn)
);

CREATE TABLE ReservedBooks(
	reservation_id INTEGER AUTO_INCREMENT,
	student_id INTEGER(9), 
    book_isbn INTEGER(10),
    emp_id INTEGER(5),
    reservedInStock BOOLEAN,
    reserved_date DATE,
    PRIMARY KEY (reservation_id),
    FOREIGN KEY (student_id) REFERENCES StudentInfo(student_id),
    FOREIGN KEY (book_isbn) REFERENCES BookInfo(book_isbn),
    FOREIGN KEY (emp_id) REFERENCES EmployeeInfo(emp_id)
);

ALTER TABLE ReservedBooks AUTO_INCREMENT = 50001;

CREATE TABLE SoldBooks(
	purchase_id INTEGER AUTO_INCREMENT,
    student_id INTEGER(9), 
    book_isbn INTEGER(10),
    emp_id INTEGER(5),
    payment_id VARCHAR(4),
    purchase_date DATE,
    PRIMARY KEY (purchase_id),
    FOREIGN KEY (student_id) REFERENCES StudentInfo(student_id),
    FOREIGN KEY (book_isbn) REFERENCES BookInfo(book_isbn),
    FOREIGN KEY (emp_id) REFERENCES EmployeeInfo(emp_id)
);

ALTER TABLE SoldBooks AUTO_INCREMENT = 10001;

# Create user guest and assign permissions
CREATE USER 'guest'@'localhost' IDENTIFIED BY PASSWORD 'guest123';
GRANT ALL ON bookmanagement.* TO 'guest'@'localhost';