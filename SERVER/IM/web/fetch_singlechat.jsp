<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.io.File"%>

<div class="row">
    <div class="col-sm-12" style="background-color: #2196F3 ;height: 60px;padding: 10px
         ;border-radius: 10px">
        <%
            String phone = request.getParameter("phone");
            String name = request.getParameter("name");
            String pic = "";
            File f = new File(getServletContext().getRealPath("/ProfilePics") + "//" 
                    + phone + ".jpg");
            if (f.exists()) {
                pic = "./ProfilePics/" + phone + ".jpg";
            } else {
                pic = "./ProfilePics/default.jpg";
            }
        %>
        <img src=<%=pic%> width="40px" height="40px"/>
        <label style="color:#b9def0"><%=name%></label>
        <button class="btn btn-default" onclick="show()" style="float: right">
            <span class="glyphicon glyphicon-picture">
        </button>
        </span>
        <button class="btn btn-default" onclick="show1()" style="float: right">
            <span class="glyphicon glyphicon-play">

            </span>
        </button>
    </div>

</div>
<div class="row" >
    <div id="chatdetail" style="height: 408px;overflow: auto ;padding: 0px;background-color: whitesmoke" >
        <table style="width:100% ">
            <%
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
                        if(type.equals("file")||msg.contains("\\uD"))
                        {
                            continue;
                        }

            %>
            <tr>
                <td>
                    <%                        if (msg_from.equals(PHONE)) {


                    %>
                    <div class="bubble me">
                        <%                            if (type.equals("text")) {
                        %>

                        <label><%=msg%></label>
                        <%
                        } else if (type.equals("image")) {
                        %>
                        <a href="images/<%= msgid%>.jpg" rel="shadowbox"> <img src="./images/<%= msgid%>.jpg"  width="100px" height="80px" /></a>
                        <%
                        } else if (type.equals("video")) {

                        %>
                        <a href="videos/<%= msgid%>.mp4" rel="shadowbox;height=140;width=120"> <video src="./videos/<%= msgid%>.mp4" width="100px" height="80px" controls></video></a>
                            <%
                            } else if (type.equals("audio")) {
                            %>
                     <!--   <audio src="./audio/<%=msgid%>.mp3" style="width: 200px; height: 50px;" autoplay  controls ></audio> -->
                        <audio controls>
                            <source src="./audio/<%= msgid %>.mp3" type="audio/mpeg">
                            Your browser does not support the <code>audio</code> tag.
                        </audio>
                        <%  }%>
                    </div>
                    <%
                    } else{
                    %>
                    <div  class="bubble you">
                        <% if (type.equals("text")) {
                        %>
                        <label><%=msg%></label>
                        <%
                        } else if (type.equals("image")) {
                        %>
                        <a href="images/<%= msgid%>.jpg" rel="shadowbox"><img src="./images/<%= msgid%>.jpg"  width="100px" height="80px"/></a>
                        <%
                        } else if (type.equals("video")) {

                        %>

                        <a href="videos/<%= msgid%>.mp4" rel="shadowbox;width:120;height:140"> <video src="./videos/<%= msgid%>.mp4" width="100px" height="80px" controls></video></a>
                            <%
                            } else if (type.equals("audio")) {
                            %>
                        <audio src="./audio/<%=msgid%>.mp3" style="width: 200px; height: 50px;" autoplay controls></audio>
                  
                    <%
                        }

                    %>
                      </div>
                      <%
                          }
                          %>
                </td>

            </tr>
            <%
                
                    
                    id = msgid;
                }

            %>
        </table> 
    </div>

</div>
<input type="hidden" value="<%=id%>" id="msgid">

<%
        Statement s2 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rs2 = s2.executeQuery("select *  from user_messages where message_from='" + phone + "' and message_to='" + PHONE + "' and status='unread'");
        while (rs2.next()) {
            rs2.updateString("status", "read");
            rs2.updateRow();
        }

    } catch (Exception ex) {
        ex.printStackTrace();
    }


%>




