<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%
       String phone=request.getParameter("message_from");
       String msg=request.getParameter("message");
       String PHONE=session.getAttribute("phone").toString();
         try
        {
            Class.forName("com.mysql.jdbc.Driver");
           System.out.println("Registered");
           
           Connection conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/IM", "root", "Cocaaps");
           System.out.println("Connection Created");
           
           Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
           System.out.println("Statement Created");
           
           ResultSet rs=stmt.executeQuery("select * from user_messages ");
           System.out.println("Query Executed");
           rs.moveToInsertRow();
           rs.updateString("message", msg);
           rs.updateString("message_from", PHONE);
           rs.updateString("message_to", phone);
           rs.updateString("type","text");
           rs.insertRow();
        }
         catch(Exception ex)
         {
             ex.printStackTrace();
         }

%>