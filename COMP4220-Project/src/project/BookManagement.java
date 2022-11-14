package project;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class BookManagement extends Throwable{
	
	/*
	 * Getting the current date
	 */
	public static String getDate() {
		
		  LocalDate date = LocalDate.now();
	      String str = "'" + date + "'";
	      
	      return str;
	}
	
	/*
	 * Implementing the function for TestClass2
	 */
	public String reserveInStock(long student_id, long book_isbn, int emp_id, String email) throws InputException, DatabaseException, SQLException{
		
		String outputString ="";
		
		//Checking if the first three inputs are valid
		if(student_id > 999999999 || student_id < 100000001 || book_isbn > 9999999999l || book_isbn < 1000000001 || emp_id > 99999 || emp_id < 10001)
			throw new InputException();
		
		//Checking if the email is valid by checking the email type
		if(email.length() <= 12 || !email.substring(email.length()-12).equals("@uwindsor.ca"))
			throw new InputException();
		
		//Connecting to MySQL database
		Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/bookmanagement", "guest", "guest123");
        Statement exist = connect.createStatement();
        String sql = "SELECT email FROM studentInfo WHERE student_id = "+ student_id + " AND EXISTS (SELECT student_id from studentInfo where student_id ="+ student_id + ") AND EXISTS (SELECT book_isbn from bookInfo WHERE book_isbn = "+ book_isbn + ") AND EXISTS (SELECT emp_id from employeeInfo WHERE emp_id = "+ emp_id + ")";

        ResultSet rs = exist.executeQuery(sql);
        
        //If the inputs exist in the database, insert the data into the reservedBook
        if(rs.next()) {
        	
        	//Throwing Exception if email is not verified 
        	if(!rs.getString(1).equals(email))
        		throw new DatabaseException();
        	
        	//Inserting the data into reservedBook
        	Statement insert = connect.createStatement();
        	sql = "INSERT into ReservedBooks (student_id, book_isbn, emp_id, reservedInStock, reserved_date)VALUES (" + student_id + ", " + book_isbn +  ", " + emp_id +  ", " + 1 + ", " + getDate() + ")";
        	int ret = insert.executeUpdate(sql);
        	
        	//Retrieving the reservation_id for print out
        	if(ret == 1) {
        		
        		Statement res_id = connect.createStatement();
        		sql = "SELECT reservation_id FROM ReservedBooks WHERE student_id = " + student_id +" AND book_isbn = " + book_isbn;
        		ResultSet rs2 = res_id.executeQuery(sql);
        		if(rs2.next()) {
        			
        			outputString = "Reservation#" + rs2.getString(1) + "\n\nStudent Number: " + student_id +"\n\nE-mail:"+ email + "\n\nISBN-10: " + book_isbn + "\n\nEmployee Number: " + emp_id + "\n\nDate: " + getDate() + "\n\nYour reservation period is 7 days from " + getDate() + "!";
        		}
        		
        		//Changing the sell stock as the book was reserved for selling
            	Statement stockchange = connect.createStatement();
            	sql = "UPDATE bookInfo SET sell_stock = sell_stock - 1 WHERE book_isbn = " + book_isbn + " AND sell_stock > 0";
            	int ret2 = stockchange.executeUpdate(sql);
            	
            	//Throw exception if stock wasn't changed
            	if(ret2 != 1) {
            		throw new SQLException();
            	}
        	}
        	
        	//Throwing exception for insertion failure
        	else {
        		
        		throw new SQLException();
        	}
        }
        
        //Throw exception if the inputs don't exist in the database
        else {
        	
        	throw new DatabaseException();
        }
		return outputString;
	}
	
	
	/*
	 * Implementing the InputException for any other combinations of input of TestClass2
	 */
	public String reserveInStock(String student_id, long book_isbn, int emp_id, String email) throws InputException{
		 
		throw new InputException();
	}
	
	public String reserveInStock(long student_id, String book_isbn, int emp_id, String email) throws InputException{
		 
		throw new InputException();
	}
	
	public String reserveInStock(long student_id, long book_isbn, String emp_id, String email) throws InputException{
	 
		throw new InputException();
	}
	
	public String reserveInStock(String student_id, String book_isbn, String emp_id, String email) throws InputException{
		 
		throw new InputException();
	}
	
	/*
	 * Implementing the function for TestClass4
	 */
	public String sell(long student_id, long book_isbn, int emp_id, long card_code) throws InputException, DatabaseException, SQLException{
		
		String outputString ="";
		
		//Checking if the inputs are valid
		if(student_id > 999999999 || student_id < 100000001 || book_isbn > 9999999999l || book_isbn < 1000000001 || emp_id > 99999 || emp_id < 10001 || card_code > 99999999999999l || card_code < 10000000000001l)
			throw new InputException();
		
		//Connecting to MySQL database
		Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/bookmanagement", "guest", "guest123");
		Statement exist = connect.createStatement();
		String sql = "SELECT * FROM studentInfo WHERE student_id = "+ student_id + " AND EXISTS (SELECT student_id from studentInfo where student_id ="+ student_id + ") AND EXISTS (SELECT book_isbn from bookInfo WHERE book_isbn = "+ book_isbn + ") AND EXISTS (SELECT emp_id from employeeInfo WHERE emp_id = "+ emp_id + ")";

        ResultSet rs = exist.executeQuery(sql);
        
        //If the first three inputs exist in the database, inserting into the soldBook database
        if(rs.next()) {
        	
        	//Checking if a reservation was done
        	Statement exist2 = connect.createStatement();
        	sql = "SELECT reservation_id from ReservedBooks where student_id ="+ student_id + " AND book_isbn = "+ book_isbn;
        	
        	ResultSet rs2 = exist2.executeQuery(sql);
        	
        	//If reservation exist, don't change the sell stock of the book
    		if(rs2.next()) {
    			
    			int res_id = rs2.getInt(1);
    			//Delete the data from reserveBook
            	Statement delete = connect.createStatement();
            	sql = "DELETE FROM ReservedBooks WHERE reservation_id=" + res_id;
            	int ret2 = delete.executeUpdate(sql);
            	
            	//Entry deleted from reservedBooks
            	if(ret2 == 1) {}
                
            	//Throwing exception for deletion failure
            	else {
            		
            		throw new SQLException();
            	}
    		}
    		
    		//Decrement the sell stock of the book if the reservation was not done
    		else {
    			
    			//Changing the sell stock as the book was reserved for selling
            	Statement stockchange = connect.createStatement();
            	sql = "UPDATE bookInfo SET sell_stock = sell_stock - 1 WHERE book_isbn = " + book_isbn + " AND sell_stock > 0";
            	int ret2 = stockchange.executeUpdate(sql);
            	
            	//Throw exception if stock wasn't changed
            	if(ret2 != 1) {
            		throw new SQLException();
            	}
    		}
    		
        	//Taking the last4 digits of the card
        	String last4digitString = Long.toString(card_code).substring(Long.toString(card_code).length() - 4);
        	
			//Inserting the data into soldBook
        	Statement insert = connect.createStatement();
        	sql = "INSERT into SoldBooks (student_id, book_isbn, emp_id, payment_id, purchase_date)VALUES (" + student_id + ", " + book_isbn +  ", " + emp_id +  ", " + last4digitString + ", " + getDate() + ")";
        	int ret = insert.executeUpdate(sql);
        	
        	//Retrieving the purchase_id for print out
        	if(ret == 1) {
        		
        		Statement pur_id = connect.createStatement();
        		sql = "SELECT purchase_id FROM SoldBooks WHERE student_id = " + student_id +" AND book_isbn = " + book_isbn;
        		ResultSet rs3 = pur_id.executeQuery(sql);
        		if(rs3.next()) {
        			
        			outputString = "Order#" + rs3.getString(1) + "\n\nStudent Number: " + student_id +"\n\nISBN-10: " + book_isbn + "\n\nEmployee Number: " + emp_id + "\n\nDate: " + getDate() + "\n\nYour reservation period is 7 days from " + getDate() + "!";
        		}	
        	}
        	
        	//Throwing exception for insertion failure
        	else {
        		
        		throw new SQLException();
        	}
        }
        
        //Throw exception if the inputs don't exist in the database
        else {
        	
        	throw new DatabaseException();
        }
		
		return outputString;
	}
	
	/*
	 * Implementing the InputException for any other combinations of input of TestClass2
	 */
	public String sell(String student_id, long book_isbn, int emp_id, long card_code) throws InputException{
		 
		throw new InputException();
	}
	
	public String sell(long student_id, String book_isbn, int emp_id, long card_code) throws InputException{
		 
		throw new InputException();
	}
	
	public String sell(long student_id, long book_isbn, String emp_id, long card_code) throws InputException{
	 
		throw new InputException();
	}
	
	public String sell(long student_id, long book_isbn, int emp_id, String card_code) throws InputException{
		 
		throw new InputException();
	}
	
	public String sell(String student_id, String book_isbn, String emp_id, String email) throws InputException{
		 
		throw new InputException();
	}
}