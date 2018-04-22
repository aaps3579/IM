package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.DriverManager;
import java.sql.Connection;
import java.io.File;

public final class fetch_005fsinglechat_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<div class=\"row\">\n");
      out.write("    <div class=\"col-sm-12\" style=\"background-color: #2196F3 ;height: 60px;padding: 10px;border-radius: 10px\">\n");
      out.write("        ");

            String phone = request.getParameter("phone");
            String name = request.getParameter("name");
            String pic = "";
            File f = new File(getServletContext().getRealPath("/ProfilePics") + "//" + phone + ".jpg");
            if (f.exists()) {
                pic = "./ProfilePics/" + phone + ".jpg";
            } else {
                pic = "./ProfilePics/default.jpg";
            }
        
      out.write("\n");
      out.write("        <img src=");
      out.print(pic);
      out.write(" width=\"40px\" height=\"40px\"/>\n");
      out.write("        <label style=\"color:#b9def0\">");
      out.print(name);
      out.write("</label>\n");
      out.write("        <button class=\"btn btn-default\" onclick=\"show()\" style=\"float: right\">\n");
      out.write("            <span class=\"glyphicon glyphicon-picture\">\n");
      out.write("        </button>\n");
      out.write("        </span>\n");
      out.write("        <button class=\"btn btn-default\" onclick=\"show1()\" style=\"float: right\">\n");
      out.write("            <span class=\"glyphicon glyphicon-play\">\n");
      out.write("\n");
      out.write("            </span>\n");
      out.write("        </button>\n");
      out.write("    </div>\n");
      out.write("\n");
      out.write("</div>\n");
      out.write("<div class=\"row\" >\n");
      out.write("    <div id=\"chatdetail\" style=\"height: 408px;overflow: auto ;padding: 0px;background-color: whitesmoke\" >\n");
      out.write("        <table style=\"width:100% \">\n");
      out.write("            ");

                int id = 0;
                String PHONE = session.getAttribute("phone").toString();
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    System.out.println("Registered");

                    Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/IM", "root", "Cocaaps");
                    System.out.println("Connection Created");

                    Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    System.out.println("Statement Created");

                    ResultSet rs = stmt.executeQuery("select * from user_messages where ( message_to='" + phone + "' and message_from='" + PHONE + "') or ( message_to='" + PHONE + "' and message_from='" + phone + "')");
                    System.out.println("Query Executed");
                    while (rs.next()) {
                        int msgid = rs.getInt("ID");
                        String msg = rs.getString("message");
                        String msg_from = rs.getString("message_from");
                        String msg_to = rs.getString("message_to");
                        String type = rs.getString("type");
                        if(type.equals("file"))
                        {
                            continue;
                        }

            
      out.write("\n");
      out.write("            <tr>\n");
      out.write("                <td>\n");
      out.write("                    ");
                        if (msg_from.equals(PHONE)) {


                    
      out.write("\n");
      out.write("                    <div class=\"bubble me\">\n");
      out.write("                        ");
                            if (type.equals("text")) {
                        
      out.write("\n");
      out.write("\n");
      out.write("                        <label>");
      out.print(msg);
      out.write("</label>\n");
      out.write("                        ");

                        } else if (type.equals("image")) {
                        
      out.write("\n");
      out.write("                        <a href=\"images/");
      out.print( msgid);
      out.write(".jpg\" rel=\"shadowbox\"> <img src=\"./images/");
      out.print( msgid);
      out.write(".jpg\"  width=\"100px\" height=\"80px\" /></a>\n");
      out.write("                        ");

                        } else if (type.equals("video")) {

                        
      out.write("\n");
      out.write("                        <a href=\"videos/");
      out.print( msgid);
      out.write(".mp4\" rel=\"shadowbox;height=140;width=120\"> <video src=\"./videos/");
      out.print( msgid);
      out.write(".mp4\" width=\"100px\" height=\"80px\" controls></video></a>\n");
      out.write("                            ");

                            } else if (type.equals("audio")) {
                            
      out.write("\n");
      out.write("                     <!--   <audio src=\"./audio/");
      out.print(msgid);
      out.write(".mp3\" style=\"width: 200px; height: 50px;\" autoplay  controls ></audio> -->\n");
      out.write("                        <audio controls>\n");
      out.write("                            <source src=\"./audio/");
      out.print( msgid );
      out.write(".mp3\" type=\"audio/mpeg\">\n");
      out.write("                            Your browser does not support the <code>audio</code> tag.\n");
      out.write("                        </audio>\n");
      out.write("                        ");
  }
      out.write("\n");
      out.write("                    </div>\n");
      out.write("                    ");

                    } else{
                    
      out.write("\n");
      out.write("                    <div  class=\"bubble you\">\n");
      out.write("                        ");
 if (type.equals("text")) {
                        
      out.write("\n");
      out.write("                        <label>");
      out.print(msg);
      out.write("</label>\n");
      out.write("                        ");

                        } else if (type.equals("image")) {
                        
      out.write("\n");
      out.write("                        <a href=\"images/");
      out.print( msgid);
      out.write(".jpg\" rel=\"shadowbox\"><img src=\"./images/");
      out.print( msgid);
      out.write(".jpg\"  width=\"100px\" height=\"80px\"/></a>\n");
      out.write("                        ");

                        } else if (type.equals("video")) {

                        
      out.write("\n");
      out.write("\n");
      out.write("                        <a href=\"videos/");
      out.print( msgid);
      out.write(".mp4\" rel=\"shadowbox;width:120;height:140\"> <video src=\"./videos/");
      out.print( msgid);
      out.write(".mp4\" width=\"100px\" height=\"80px\" controls></video></a>\n");
      out.write("                            ");

                            } else if (type.equals("audio")) {
                            
      out.write("\n");
      out.write("                        <audio src=\"./audio/");
      out.print(msgid);
      out.write(".mp3\" style=\"width: 200px; height: 50px;\" autoplay controls></audio>\n");
      out.write("                  \n");
      out.write("                    ");

                        }

                    
      out.write("\n");
      out.write("                      </div>\n");
      out.write("                      ");

                          }
                          
      out.write("\n");
      out.write("                </td>\n");
      out.write("\n");
      out.write("            </tr>\n");
      out.write("            ");

                
                    
                    id = msgid;
                }

            
      out.write("\n");
      out.write("        </table> \n");
      out.write("    </div>\n");
      out.write("\n");
      out.write("</div>\n");
      out.write("<input type=\"hidden\" value=\"");
      out.print(id);
      out.write("\" id=\"msgid\">\n");
      out.write("\n");

        Statement s2 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rs2 = s2.executeQuery("select *  from user_messages where message_from='" + phone + "' and message_to='" + PHONE + "' and status='unread'");
        while (rs2.next()) {
            rs2.updateString("status", "read");
            rs2.updateRow();
        }

    } catch (Exception ex) {
        ex.printStackTrace();
    }



      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
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
