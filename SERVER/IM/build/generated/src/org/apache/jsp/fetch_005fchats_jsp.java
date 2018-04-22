package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.StringTokenizer;
import java.util.StringTokenizer;
import listeners.contact_jsp;
import java.awt.Cursor;
import listeners.chat_jsp;
import java.util.ArrayList;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;

public final class fetch_005fchats_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");

    ArrayList<chat_jsp> al=new ArrayList<chat_jsp>();
    ArrayList<contact_jsp> al1;
    if(session.getAttribute("contact_list")==null)
    {
        al1=new ArrayList<>();
        String s=session.getAttribute("mycontacts").toString();
   
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

      out.write("\n");
      out.write("<table class=\"table table-hover table-striped \">\n");
      out.write("    ");
 for(int i=0;i<al.size();i++)
    {
        System.out.println("array");
     String fphone=al.get(i).fphone;
     String fname=al.get(i).fname;
     String mfname=al.get(i).mfname;
     String msg=al.get(i).msg;
     int count=al.get(i).count;
     String datetime=al.get(i).datetime;
    

      out.write("\n");
      out.write("<tr>\n");
      out.write("    <td><img src=\"./ProfilePics/");
      out.print( fphone);
      out.write(".jpg\" class=\"img-circle\"  style=\"float: left; margin-top: 10px\" height=\"50\" width=\"50\"></td>\n");
      out.write("    <td><h4>");
      out.print(fname);
      out.write("</h4><br><h5>");
      out.print( mfname);
      out.write(':');
      out.print(msg);
      out.write("</h5></td>\n");
      out.write("    <td><h4>");
      out.print(datetime);
      out.write("</h4><br><h5>");
      out.print( count);
      out.write("</h5></td>\n");
      out.write("</tr>\n");
      out.write("\n");
      out.write("    ");

}

      out.write("\n");
      out.write("\n");
      out.write("</table>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
