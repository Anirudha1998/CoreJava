package FinalProject;



import java.sql.*;
import java.util.Scanner;

public class FinalProject {
	Scanner sc = new Scanner(System.in);
	ResultSet result;

	
	//Method to make connection to database
	PreparedStatement createConection(String querry) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		
		//Create connection
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","");
				
		//Creating prepared Statement
		PreparedStatement pt = conn.prepareStatement(querry);
		
		return pt;
		//conn.close();
	}

	
	//Method to add entry to database
	void addEntry(PreparedStatement pt)  {	
		
		sc.nextLine();

		System.out.print("Enter name of book: ");
		String name = sc.nextLine();
		
		System.out.print("Enter name of book auther: ");
		String auther = sc.nextLine();
		
		System.out.print("Enter published year of book: ");
		String year = sc.nextLine();
		
		try {
			pt.setString(1, name);
			pt.setString(2, auther);
			pt.setString(3, year);
			
			int i = pt.executeUpdate();
			System.out.println("Data inserted to database successfully");
		} catch (SQLException e) {
			System.out.println(e);
		}
		
	}
	
	//Method to get and print
	void getAllBookData(PreparedStatement pt) {
		try {
			 result = pt.executeQuery();
			 System.out.println("\t  ________________________________________");

			 System.out.println("\t | Id\tBook Name\tAuther\t Year\t  |");
			 System.out.println("\t |________________________________________|");
			 
			 while(result.next()) {
				 int id = result.getInt("bookid");
				 String name = result.getString("bookname");
				 String auther = result.getString("auther");
				 String year = result.getString("publishedyear");
				 
				 System.out.println("\t |" + id + "\t" + name + "\t\t" + auther + "\t" + year + " \t  |");
			 }
			 System.out.println("\t |________________________________________|");

		}catch(SQLException e) {
			System.out.println(e);
		}
	}
	
	
	//Method to get and print Library data by Book id
	void getBookDataById(PreparedStatement pt) {
		System.out.print("\t Enter book Id: ");
		int id = sc.nextInt();
		try {
			pt.setInt(1, id);
			result = pt.executeQuery();
			 System.out.println("\t  ________________________________________");

			 System.out.println("\t | Id\tBook Name\t\tAuther\tPublished Year\t  |");
			 System.out.println("\t |________________________________________|");
			 
			 while(result.next()) {
				 int bookid = result.getInt("bookid");
				 String name = result.getString("bookname");
				 String auther = result.getString("auther");
				 String year = result.getString("publishedyear");
				 
				 System.out.println("\t |" + bookid + "\t" + name + "\t\t" + auther + "\t" + year + " \t  |");
			 }
			 System.out.println("\t |________________________________________|");

		}catch(SQLException e) {
			System.out.println(e);
		}
		
	}
	
	
	//Method to delete Library data by Book id
	void delBookDataById(PreparedStatement pt) {
		System.out.print("\t Enter Book Id: ");
		int id = sc.nextInt();
		try {
			pt.setInt(1, id);
			int i = pt.executeUpdate();
			System.out.println("\t Data deleted successfully");
		}catch(SQLException e) {
			System.out.println(e);
		}
		
		
	}
	
	//Method to delete Library data by Book name
	void delBookDataByName(PreparedStatement pt) {
		System.out.print("\t Enter book name: ");
		String name = sc.nextLine();
		try {
			pt.setString(1, name);
			int i = pt.executeUpdate();
			System.out.println("\t Data deleted successfully");
		}catch(SQLException e) {
			System.out.println(e);
		}
		
		
	}
	
	
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		// Scanner object
		Scanner sc = new Scanner(System.in);
		
		//Object of class
		FinalProject fp = new FinalProject();
		
		//Get prepared statement
		PreparedStatement pt;
		
		//variables to continue loop
		char repeat;
		char repeatGetData;
		char repeatDelData;
		
		
		//Main loop
		do {
			System.out.println("------------------------------");
			System.out.println("1. Enter book data");
			System.out.println("2. Get book data");
			System.out.println("3. Delete book data");
			System.out.print("Choose from above options.");
			
			int ch = sc.nextInt();

			switch(ch) {
			// Case 1
			case 1:
				String getDataQuerry = "INSERT INTO books(bookname, auther, publishedyear) VALUES (?,?,?)";
				pt = fp.createConection(getDataQuerry);
				fp.addEntry(pt);
			break;
			
			//Case 2
			case 2:
				//Fetch data loop
				do {
				System.out.println("****************************************************");
				System.out.println("\t 1. Get all book data");
				System.out.println("\t 2. Get book data by id");
				System.out.print("\t Choose from above options: ");
				int getDataCh = sc.nextInt();
				switch(getDataCh) {
				case 1:
					String getAllDataQuerry = "SELECT * FROM books";
					pt = fp.createConection(getAllDataQuerry);
					fp.getAllBookData(pt);
					break;
					
				case 2:
					String getDataByIdQuerry = "SELECT * FROM books WHERE bookid = (?)";
					pt = fp.createConection(getDataByIdQuerry);
					
					fp.getBookDataById(pt);
					break;
					
				default:System.out.println("Enter valid options");
					
				}
				System.out.print("\t Get another book data? (Press y to continue): ");
				repeatGetData = sc.next().charAt(0);
				}while(repeatGetData == 'y' || repeatGetData == 'Y');
				System.out.println("****************************************************");
			break;//main switch case 2 break
			
			//case 3
			case 3:
				//Delete data loop
				do {
					System.out.println("****************************************************");
					System.out.println("\t 1. Delete all book data");
					System.out.println("\t 2. Delete book data by id");
					System.out.println("\t 3. Delete book data by name");
					System.out.print("\t Choose from above options: ");
					int delDataCh = sc.nextInt();
					switch(delDataCh) {
					case 1:
						String delAllDataQuerry = "TRUNCATE books";
						pt = fp.createConection(delAllDataQuerry);
						pt.execute(delAllDataQuerry);
						break;
						
					case 2:
						String delDataByIdQuerry = "DELETE FROM books WHERE bookid = ?";
						pt = fp.createConection(delDataByIdQuerry);
						
						fp.delBookDataById(pt);
						break;
						
					case 3:
						String delDataByNameQuerry = "DELETE FROM books WHERE bookname = ?";
						pt = fp.createConection(delDataByNameQuerry);
						
						fp.delBookDataByName(pt);
						break;
						
					default:System.out.println("Enter valid options");
						
					}
					System.out.print("\t Delete another book data? (Press y to continue): ");
					repeatDelData = sc.next().charAt(0);
					}while(repeatDelData == 'y' || repeatDelData == 'Y');
					System.out.println("****************************************************");
				break;
			
			default:System.out.println("Enter valid option.");
			
			}

			System.out.print("Do you want to Continue?: (Press y to continue):");
			 repeat = sc.next().charAt(0);
			 
		}while(repeat == 'y' || repeat == 'Y');

		System.out.println("Bye");
		}

}
