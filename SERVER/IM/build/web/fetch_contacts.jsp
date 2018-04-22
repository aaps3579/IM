<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.io.File"%>
<%@page import="listeners.contact_jsp"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.StringTokenizer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<table class="table table-striped ">
<%
    
    String pic="",status="Loving IM!";
    ArrayList<contact_jsp> al;
    if(session.getAttribute("contact_list")==null)
    {
    al=new ArrayList<>();
    al.clear();
    String s=session.getAttribute("mycontacts").toString();
    System.out.println(s);
   
    StringTokenizer st = new StringTokenizer(s, "~~");
    while(st.hasMoreTokens())
    {
        String s1=st.nextToken();
        StringTokenizer st1 = new StringTokenizer(s1, "``");
        while(st1.hasMoreTokens())
        {
            String s2=st1.nextToken();
            String s3=st1.nextToken();
            al.add(new contact_jsp(s2,s3));
        }
    }
    }
    else
    {
        al=(ArrayList<contact_jsp>)session.getAttribute("contact_list");
    }
    try
    {
          Class.forName("com.mysql.jdbc.Driver");
           System.out.println("Registered");
           
           Connection conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/IM", "root", "Cocaaps");
           System.out.println("Connection Created");
           
           Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
           System.out.println("Statement Created");
            
           
    for(int i=0;i<al.size();i++)
    {
        File f=new File(getServletContext().getRealPath("/ProfilePics")+"//"+al.get(i).phone+".jpg");
        if(f.exists())
        {
            pic="./ProfilePics/"+al.get(i).phone+".jpg";
        }
        else
        {
            pic="./ProfilePics/default.jpg";
        }
        ResultSet rs=stmt.executeQuery("select * from users where phone='"+al.get(i).phone+"'");
        if(rs.next())
        {
            
            status=rs.getString("Status");
           
            
        }
          
%>

    <tr onclick="hello_chat('<%=al.get(i).phone%>','<%=al.get(i).name%>')">
        
        <td  style="padding: 10px">
            <img src=<%= pic %> class="img-circle" width="50" height="50" />    
        </td>
        <td  style="color:#269abc ">
            <%=al.get(i).name%>
            <br>
            <br>
            <h4 style="color:#212121"><%= status%></h4>
            
        
        </td>
        
            
    </tr>

            <% }
    }catch(Exception ex)
{
ex.printStackTrace();
}
session.setAttribute("contact_list", al);

            %>
            
            </table>