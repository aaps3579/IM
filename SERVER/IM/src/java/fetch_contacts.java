/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.StringTokenizer;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author HP_PC
 */
public class fetch_contacts extends HttpServlet {
     

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String result="";
              try
        {
            PrintWriter out=resp.getWriter();
            Class.forName("com.mysql.jdbc.Driver");
           System.out.println("Registered");
           
           Connection conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/IM", "root", "Cocaaps");
           System.out.println("Connection Created");
           
           Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
           System.out.println("Statement Created");
            ServletInputStream sis=req.getInputStream();
    
        /*Long l=Long.parseLong(req.getHeader("Content Length"));
        System.out.println("Length="+l);*/
        DataInputStream dis=new DataInputStream(sis);
        String s=dis.readLine();
        System.out.println(s);
        StringTokenizer st=new StringTokenizer(s,";");
        while(st.hasMoreTokens())
        {
            String PHONE=st.nextToken();
            ResultSet rs=stmt.executeQuery("select * from users where phone like '%"+PHONE+"%'");
           
            if(rs.next())
            {
                String phone=rs.getString("phone");
                System.out.println(phone+"\n");
                String status=rs.getString("Status");
                System.out.println(status+"\n");
                result=result+phone+"~"+status+"`";
                
            }
            
        }
            out.println(result);
            System.out.println("hi,"+result);
    
    
           
          
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    
       
    }

    
}
