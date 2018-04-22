<%@page import="java.io.File"%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="listeners.contact_jsp"%>
<%@page import="java.awt.Cursor"%>
<%@page import="listeners.chat_jsp"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%
    ArrayList<chat_jsp> al=new ArrayList<chat_jsp>();
    ArrayList<contact_jsp> al1;
    if(session.getAttribute("contact_list")==null)
    {
        al1=new ArrayList<>();
        String s=session.getAttribute("mycontacts").toString();
   al1.clear();
    StringTokenizer st = new StringTokenizer(s, "~~");
    while(st.hasMoreTokens())
    {
        String s1=st.nextToken();
        StringTokenizer st1 = new StringTokenizer(s1, "``");
        while(st1.hasMoreTokens())
        {
            String s2=st1.nextToken();
            String s3=st1.nextToken();
            al1.add(new contact_jsp(s2,s3));
        }
    }
    }else
    {
        al1=(ArrayList<contact_jsp>)session.getAttribute("contact_list");
    }
    
    
    String phone=session.getAttribute("phone").toString();
    String username="";
    try
    {
            Class.forName("com.mysql.jdbc.Driver");
           System.out.println("Registered");
           
           Connection conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/IM", "root", "Cocaaps");
           System.out.println("Connection Created");
           
           Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
           System.out.println("Statement Created");
           
           ResultSet rs=stmt.executeQuery("select * from users where phone='"+phone+"'");
           if(rs.next())
           {
               username=rs.getString("username");
           }
           rs=stmt.executeQuery("select * from user_messages where message_to='"+phone+"' or message_from='"+phone+"' order by ID desc");
        
           while(rs.next())
           {
               int id=rs.getInt("ID");
               String msg=rs.getString("message");
               String msg_from=rs.getString("message_from");
               String msg_to=rs.getString("message_to");
               String datetime=rs.getString("datetime");
               String type=rs.getString("type");
                int flag=0;
                String fphone = "", fname = "", mfname = "";
                if (msg_from.equals(phone)) {
                    mfname = username;
                    fphone = msg_to;
                } else {
                    fphone = msg_from;

                }
                for (int i = 0; i < al.size(); i++) {
                    if (al.get(i).fphone.equals(fphone)) {
                        flag = 1;
                        break;
                    }
                }
                if (flag == 0)
                {
                    for(int i=0;i<al1.size();i++)
                    {
                        if(fphone.equals(al1.get(i).phone))
                        {
                            fname=al1.get(i).name;
                            if(mfname.equals(""))
                            {
                                mfname=fname;
                                
                            }
                            break;
                        }
                        
                    }
                   
                
                Statement stmt2=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                ResultSet rs2=stmt2.executeQuery("select count(*) as c1 from user_messages where message_from='"+fphone+"' and message_to='"+phone+"' and status='unread'");

                int count=0;
                if(rs2.next())
                {
                    count=rs2.getInt("c1");
                    System.out.println(count);
                }
                al.add(new chat_jsp(fphone,fname,mfname,msg,datetime,type,count));
           }
           }
           
    }
    catch(Exception ex)
    {
        ex.printStackTrace();
    }
%>
<table class="table table-striped ">
    <% for(int i=0;i<al.size();i++)
    {
        String pic="";
        System.out.println("array");
     String fphone=al.get(i).fphone;
     String fname=al.get(i).fname;
     String mfname=al.get(i).mfname;
     String msg=al.get(i).msg;
     int count=al.get(i).count;
     String datetime=al.get(i).datetime;
     String date=datetime.substring(0,10);
     String time=datetime.substring(11,16);
        File f=new File(getServletContext().getRealPath("/ProfilePics")+"//"+al.get(i).fphone+".jpg");
        if(f.exists())
        {
            pic="./ProfilePics/"+al.get(i).fphone+".jpg";
        }
        else
        {
            pic="./ProfilePics/default.jpg";
        }
%>
<tr onclick="hello_chat('<%=fphone%>','<%=fname%>')" style="color:#727272">
    <td><img src=<%=pic%> class="img-circle"  style="float: left; margin-top: 10px" height="50" width="50"/><h6 style="float: bottom"><abbr title="unread"><%= count%></abbr></h6></td>
    <td><h4> <%=fname%></h4><br><h5> <%= mfname%>:<%=msg%></h5></td>
    <td><h4><%=date%></h4><br><h4 style="padding: 0px"><%=time%><br></h4></td>
</tr>

    <%
}
%>

</table>