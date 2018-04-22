<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%
    String PHONE=session.getAttribute("phone").toString();
    String phone=request.getParameter("phone");
    int id=Integer.parseInt(request.getParameter("id"));
    try
    {
                    Class.forName("com.mysql.jdbc.Driver");
                    System.out.println("Registered");

                    Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/IM", "root", "Cocaaps");
                    System.out.println("Connection Created");

                    Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    System.out.println("Statement Created");

                    ResultSet rs = stmt.executeQuery("select * from user_messages where (( message_to='" + phone + "' and message_from='" + PHONE + "') or ( message_to='" + PHONE + "' and message_from='" + phone + "')) and ID>'"+id+"'");
                    System.out.println("Query Executed");
                    if (rs.next()) {
                    
%>
yes
<%
    }
}catch(Exception ex)
{
ex.printStackTrace();
}
    %>