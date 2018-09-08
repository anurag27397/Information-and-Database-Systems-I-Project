// Name  Anurag K Gupta
// Section 0804 CIS4301
// Assignment 11
// Due date April 16, 2018



import java.sql.*;
import java.util.Scanner;

 class DBConnection {
	
	  @SuppressWarnings("unused")
	private static final String JDBC_DRIVER = "org.mysql.jdbc.Driver";
	  private static final String LOCAL_HOST = "jdbc:mariadb://localhost:3306/";

	  private String db;
	  private String username;
	  private String password;
	  
	  PreparedStatement preparedStatement;
	  Statement statement;
	  Connection connection;
	  public DBConnection() {
		    this.db = "Mall";
		    this.username = "root";
		    this.password = "anurag1997";
		  }

		  public DBConnection( String db, String username, String password ) {
		    this.db = db;
			this.username = username;
			this.password = password;
		  }

		  public boolean openConnection( ) {
			  boolean result = false;

			try {
		      connection = DriverManager.getConnection( LOCAL_HOST + db, username, password );
		      System.out.println( db + " connected." );
		      System.out.println();
		      
		      statement = connection.createStatement();
		      
		      result = true;
		    }
		    catch ( SQLException sqle ) {
		      sqle.printStackTrace();
		    }
			   return result;
		  }
		  
}
 class UI
 {
	 public void printHeader() {
	      System.out.println("+-----------------------------------+");
	      System.out.println("|        Welcome to                 |");
	      System.out.println("|        Gator Mall Database        |");
	      System.out.println("+-----------------------------------+");
	  } 
	 
	 
	  public void printMenu() {
	      System.out.println("Please make a selection");
	      System.out.println("1) Entering a complete SQL command");
	      System.out.println("2) Selecting a table from the list of relations");
	      System.out.println("0) Exit");
	  }
	  public int getMenuChoice() {
	      @SuppressWarnings("resource")
		Scanner keyboard = new Scanner(System.in);
	      int choice = -1;
	      do {
	          System.out.print("Enter your choice: ");
	          try {
	              choice = Integer.parseInt(keyboard.nextLine());
	          } catch (NumberFormatException e) {
	              System.out.println("Invalid selection. Numbers only please.");
	          }
	          if (choice < 0 || choice > 2) {
	              System.out.println("Choice outside of range. Please choose again.");
	          }
	      } while (choice < 0 || choice > 2);
	      return choice;
	  }
	  
 }

class hw11 {
	 
	public static void main(String[] args) throws SQLException {
	     DBConnection dbc = new DBConnection();
	     UI ui=new UI();
	
	     try {
	     String sql,str;
	     int num;
	     double doub;
	     Date date;
	     ResultSet rs= null;
	    
	    dbc.openConnection();
	    ui.printHeader();
	    ui.printMenu();
	    int choice= ui.getMenuChoice();
	    
	    switch (choice) {
		case 0:
			System.out.println("Thank you for using our application.");
	        System.exit(0);
	        break;
	        
		case 1:{
			
			@SuppressWarnings("resource")
			Scanner scr = new Scanner(System.in);
			System.out.println("Enter SQL command without';' and if using select statement then enter without 'where' clause");
			
			sql=scr.nextLine();
			
			
			String arr[] = sql.split(" ", 3);
			String sec=arr[1];
			String first=arr[0];
			
		 	
			if(first.equalsIgnoreCase("select") )
			{
				System.out.println("Enter where clause e.g. where name='abc' and if no where clause:just press enter ");
				String w=null;
				w=scr.nextLine();
				
				if(sec.equals("*"))
				{    String co=" ";
					 rs = dbc.statement.executeQuery(sql);
					 sql=sql.substring(7);
					 String[] col=sql.trim().split(" ");
					 String b=col[2];
					 
					 ResultSetMetaData rsmd = rs.getMetaData();
					 int columnCount = rsmd.getColumnCount();
					 for (int i = 1; i <= columnCount; i++ ) {
						  String name = rsmd.getColumnName(i);
						  
						  co=co+name;
						  co=co+",";
						   
						}
					 co=co.substring(0,co.length()-1);
					 
					String sql3= "select"+ co+" from "+b;
					System.out.println(sql3);
					sql=sql3;
					sql=sql.substring(7);
				    String[] colu=sql.trim().split(" ");
				    String ba=colu[2]; 
				   
				    String a=colu[0];
				    String[]a1=a.split(",");
				    for(int j=0;j<a1.length;j++)
				    {
				    	String sql1="select "+a1[j]+ " from  "+ ba+ " "+ w;
				    	
				    	rs = dbc.statement.executeQuery(sql1);
						while(rs.next()){
					    	str  = rs.getString(a1[j]);
					    	System.out.println(str); 	
					    	}
					    while(rs.next()) {
					    	num=rs.getInt(a1[j]);
					    	System.out.println(num);
					    }
					    while(rs.next()) {
					    	doub=rs.getDouble(a1[j]);
					    	System.out.println(doub);
					    }
					    while(rs.next()) {
					    	date=rs.getDate(a1[j]);
					    	System.out.println(date);
					    
				      }
						System.out.println(" ");
						  
	                  }
				    System.out.println("---------------- ");
				   
				}
				else {
			    sql=sql.substring(7);
			    String[] col=sql.trim().split(" ");
			    String b=col[2]; 
			   
			    String a=col[0];
			    String[]a1=a.split(",");
			    for(int j=0;j<a1.length;j++)
			    {
			    	String sql1="select "+a1[j]+ " from  "+ b+ " "+ w;
			    	
			    	rs = dbc.statement.executeQuery(sql1);
					while(rs.next()){
				    	str  = rs.getString(a1[j]);
				    	System.out.println(str); 	
				    	}
				    while(rs.next()) {
				    	num=rs.getInt(a1[j]);
				    	System.out.println(num);
				    }
				    while(rs.next()) {
				    	doub=rs.getDouble(a1[j]);
				    	System.out.println(doub);
				    }
				    while(rs.next()) {
				    	date=rs.getDate(a1[j]);
				    	System.out.println(date);
				    
			      }
					System.out.println(" ");
				    
                  }
					
				}
			}
			
		    
			   else if(first.equalsIgnoreCase("insert"))
			{
				dbc.statement.executeUpdate(sql);
				System.out.println("Record inserted into the table");
				
			}
			
			else if(first.equalsIgnoreCase("delete"))
			{
				dbc.statement.executeUpdate(sql);
				System.out.println("Record deleted from the table");
			}
			else if(first.equalsIgnoreCase("update"))
			{
				dbc.statement.executeUpdate(sql);
				System.out.println("Record updated in the table");
			}
			
			else if(first.equalsIgnoreCase("create"))
			{
				dbc.statement.executeUpdate(sql);
				System.out.println("Table created in the database");
			}
			else if(first.equalsIgnoreCase("drop"))
			{
				dbc.statement.executeUpdate(sql);
				System.out.println("Table dropped from the database");
			}
			
			
			break;
			
		}
	        
		case 2: {
			@SuppressWarnings("resource")
			Scanner scr = new Scanner(System.in);
			System.out.println("Enter table name to select the table from {Employees,Employs,Stores,Items,Inventory,Customers,Transactions,Accounts}");
			String relation="";
		    relation=scr.nextLine();
		    System.out.println("Table "+relation+" selected");
			System.out.println("Enter Column Name to select");
			String column="";
			column=scr.nextLine();
			System.out.println("Column "+column+" selected");
			System.out.println("Press 1 for performing Select and 2 for performing Update Operation");
		    int ch=Integer.parseInt(scr.nextLine());
		    switch(ch) {
		    case 1:{
		    String f=null;
		    System.out.println("Enter if you want to select any other column, write in the form a,b,c");
		    String c=scr.nextLine();
		    c=","+c;
			
	
		    
		    	System.out.println("Specify range of values to search within e.g. (1,100 or a,j) ,otherwise press enter");
		    	String range=null;
		    	range=scr.nextLine();
		    	if(range.equals(""))
		    	{
		    		f="true";
		    	}
		    	else {
		    	String r[]=range.split(",");
		    	f=column+">"+"'"+r[0]+"'"+" and "+ column+"<"+"'"+r[1]+"'";
		    	}
		    
		    	System.out.println("Specify where clause if any e.g. name=='abc' and price>4, otherwise press enter");
		    	String whe=null;
		    	whe=scr.nextLine();
		    	if(whe.equals(""))
		    	{
		    		whe="true";
		    	}
		    	
		    	if(whe.contains("==")) {
		    		whe = whe.replace("==", "=");
		    	}
		   
			sql="Select"+" "+ column+c+ " "+"from"+" "+ relation+" " + " where "+ f + " and " + whe ;
			
			 sql=sql.substring(7);
			    String[] col=sql.trim().split(" ");
			    String b=col[2]; 
			   
			    String a=col[0];
			    String[]a1=a.split(",");
			    for(int j=0;j<a1.length;j++)
			    {
			    	String sql1="select "+a1[j]+ " from  "+ b+ " where "+ f + " and " + whe;
			   
			    	
			    	rs = dbc.statement.executeQuery(sql1);
					while(rs.next()){
				    	str  = rs.getString(a1[j]);
				    	System.out.println(str); 	
				    	}
				    while(rs.next()) {
				    	num=rs.getInt(a1[j]);
				    	System.out.println(num);
				    }
				    while(rs.next()) {
				    	doub=rs.getDouble(a1[j]);
				    	System.out.println(doub);
				    }
				    while(rs.next()) {
				    	date=rs.getDate(a1[j]);
				    	System.out.println(date);
				    
			      }
					System.out.println(" ");
				    
               }
			    break;
					
				}
			
			
			
			
			
			
			
		    
		    
		    
		    case 2:{
		      	String[] u = new String[100];
		       	System.out.println("What do you want " +column+" value to update with");
		     	String updat=scr.nextLine();
		      	System.out.println("Enter if you want to update any other column value, write in the form a,b,c");
		      	String c=scr.nextLine();
		      	String res[]=c.split(",");
		      	for(int i=0;i<res.length;i++) {
		      	System.out.println("What do you want "+res[i]+" value to update with");
		     	String upda=scr.nextLine();
		     	u[i]=upda;
		     	
		      	}
		     	
		     	System.out.println("Specify 'Where' columns need to be updated e.g. name='john'");
		     	String wher=scr.nextLine();
		     	String app = "";
		     	
		     	
		     	for(int k=0;k<res.length;k++) {
		        
		        app=app+","+res[k]+"="+"'"+u[k]+"'";
		      
		     	}
		  
		     	sql="Update"+" "+relation+" set "+ column+" ="+ "\""+updat +"\""+ ""+ app+ "where "+ wher;
		     	
		     	dbc.statement.executeUpdate(sql);
		     	
		     	System.out.println("Table updated in the database");
		    	
		     	break;
		    }
		     	
		
		
		    
		    	
		   
		}
		    	
	     
	  
	     }}}
	     
	     catch(SQLException se){
	         //
	         se.printStackTrace();
	      }catch(Exception e){
	         //
	         e.printStackTrace();
	      }finally{
	          //finally block used to close resources
	          try{
	             if(dbc.statement!=null)
	                dbc.connection.close();
	          }catch(SQLException se){
	          }// do nothing
	          try{
	             if(dbc.connection!=null)
	                dbc.connection.close();
	          }catch(SQLException se){
	             se.printStackTrace();
	          }//end finally try
	       }//end try
	  
	  
	  }
}
