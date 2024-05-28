package oficial_ii_poo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Conexao {
	
	private Connection connection=null;
	private Statement statement=null;
	
	public Connection conectar()
	{  String retorno=null;
	    String servidor="jdbc:mysql://localhost:3306/minhaloja";
	   String usuario="root";
	   String senha="root";
	   String driver="com.mysql.cj.jdbc.Driver";
	   try
	   {
	 	Class.forName(driver)   ;
	 	this.connection=DriverManager.getConnection(servidor,usuario,senha);
	 	this.statement=this.connection.createStatement();
	 	retorno="OK";
	   }
	   catch(Exception e)
	   {
	   	retorno="Não foi possível conectar ao banco. "+e.getMessage();
	   }
	   return this.connection;
	}







}
