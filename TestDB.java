import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

/**
   Tests a database installation by creating and querying
   a sample table. Call this program as
   java -classpath driver_class_path;. TestDB database.properties
*/
public class TestDB 
{
   public static void main(String[] args) throws Exception
   {   
      if (args.length == 0)
      {   
         System.out.println(
               "Usage: java -classpath driver_class_path" 
               + File.pathSeparator 
               + ". TestDB database.properties");
         return;
      }
      else 
		{
		   System.out.println("args[0] = " + args[0]);
         SimpleDataSource.init(args[0]);
		}
      
      Connection conn = SimpleDataSource.getConnection();
      Statement stat = conn.createStatement();     
 	   try {  
		  stat.execute("DROP TABLE Test2"); 
      }
	   catch (Exception e)
		{ System.out.println("drop failed"); }      

      try
      {
   
         stat.execute("CREATE TABLE Test2 (Name CHAR(20),Age INTEGER, Active BOOLEAN)");
         stat.execute("INSERT INTO Test2 VALUES ('Romeo',27, true)");
         stat.execute("INSERT INTO Test2 VALUES ('Juliet',25, true)");
         stat.execute("INSERT INTO Test2 VALUES ('Tom',64, true)");
         stat.execute("INSERT INTO Test2 VALUES ('Dick',55, false)");
         stat.execute("INSERT INTO Test2 VALUES ('Harry',33, true)");
         ResultSet result = stat.executeQuery("SELECT * FROM Test2");
			  
			System.out.println("after inserts");
			ResultSetMetaData rsm = result.getMetaData();
			int cols = rsm.getColumnCount();
			  while(result.next())
			  {
			    for(int i = 1; i <= cols; i++)
               System.out.print(result.getString(i)+" ");
             System.out.println("");      
			  }
			try {  
		     stat.execute("DROP TABLE Test2"); 
         }
			catch (Exception e)
			{ System.out.println("drop failed"); }    
		}
      finally
      {
         conn.close();
			System.out.println("dropped Table Test2, closed connection and ending program");  
      }
   }
}
