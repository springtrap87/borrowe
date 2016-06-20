//package com.example.cjlam.borrowe;
import java.sql.*;
/**
 * Created by cjlam on 5/26/2016.
 */
public class Shortcuts {

        static String username,password;
        static Connection conn;

        static void connect() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn =
                    DriverManager.getConnection("jdbc:mysql://72.167.233.15/borrowe?" +
                            "user=borrowe&password=Senior2016!");
        }

        static void singleQuery(String s) throws SQLException{Statement statement = conn.createStatement();

            statement.executeUpdate(s);

        }

        static ResultSet selectSet(String s) throws SQLException{
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(s);
            return rs;
        }

        static void signUp(String u,String p) throws SQLException
        {
            singleQuery("INSERT INTO users VALUES ('"+u+",'"+p+"')");

            Shortcuts.username=u;Shortcuts.password=p;}

        static String inSingleQuotes(String s){return "'"+s+"'";}

        static boolean logInCheck(String u,String p) throws SQLException{

            int count=0;
            ResultSet rs=selectSet("SELECT * FROM users WHERE username="+inSingleQuotes(u)
                    +" AND password="+inSingleQuotes(p));

            while(rs.next()){
                count++;
            }
            if (count==1){Shortcuts.username=u;}
            return count==1;

        }


}
