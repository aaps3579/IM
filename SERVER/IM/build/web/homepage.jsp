<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>

<!--  <script>
           function preventback(){window.history.forward();}
           setTimeout("preventback()",0);
           window.onunload=function(){null};
           
           </script>-->

<script language="javascript" >
history.go(1); /* undo user navigation (ex: IE Back Button) */
</script>
    
  
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    response.addHeader("Cache-Control", "no-cache,no-store,private,must-revalidate,max-stale=0,post-check=0,pre-check=0"); 
   response.addHeader("Pragma", "no-cache"); 
   response.addDateHeader ("Expires", 0);
   
    %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="css/bootstrap.css" rel="stylesheet" type="text/css"/>
        <link href="css/lightbox.css" rel="stylesheet" type="text/css"/>
        <link href="css/anim.css" rel="stylesheet" type="text/css"/>
        <link href="css/button.css" rel="stylesheet" type="text/css"/>
        <link href="css/chatbubble.css" rel="stylesheet" type="text/css"/>
        <link href="css/styles.css" rel="stylesheet" type="text/css"/>
        <link href="css/anim.css" rel="stylesheet" type="text/css"/>
        <script src="jquery.js" type="text/javascript"></script>
        <script src="js/bootstrap.js" type="text/javascript"></script>
       <link rel="stylesheet" type="text/css" href="source/shadowbox.css">
     
<script type="text/javascript" src="source/shadowbox.js"></script>
<script type="text/javascript">
Shadowbox.init();
</script> 
        <title>JSP Page</title>
    
        <script type="text/javascript">

            var cs;
            var name = "", phone = "";
            var id;
            setInterval("check_msg()", 1000);
            
            function check_msg()
            {

                if (name === "" && phone === "")
                {
                    document.getElementById("chats").innerHTML = "<center><h3 style=\"color:salmon;font-family: Impact, Charcoal, sans-serif;color:#FF4081\"><br><br><br><br><br><br><br><br>Select any Contact to show Chat</h3></center>";
                    document.getElementById("chats").style = "border:0px";
                } else
                {

                    id = document.getElementById("msgid").value;
                    var xml = new XMLHttpRequest();
                    xml.onreadystatechange = function () {

                        if (xml.status === 200 && xml.readyState === 4)
                        {
                            var res = xml.responseText.trim();
                            if (res === "yes")
                            {
                                fetch_singlechat();
                            }
                        }
                    };
                    xml.open("GET", "check_msg.jsp?phone=" + phone + "&id=" + id, true);
                    xml.send();
                }
            }
            function hello_chat(fphone, fname)
            {
                phone = fphone;
                name = fname;
                fetch_singlechat();
            }
            function fetch_singlechat()
            {

                var xml = new XMLHttpRequest();
                xml.onreadystatechange = function () {

                    if (xml.status == 200 && xml.readyState == 4)
                    {
                        document.getElementById("chats").style = "height:490px ;border: 1 px #d4d4d4 solid ;border-radius: 10 px";
                        document.getElementById("bottomdiv").style = "background: whitesmoke ;padding: 0px;height: 35px;visibility: visible";
                        document.getElementById("chats").innerHTML = xml.responseText;
                        var objdiv = document.getElementById("chatdetail");
                        objdiv.scrollTop = objdiv.scrollHeight;
                    }

                };
                xml.open("GET", "fetch_singlechat.jsp?phone=" + phone + "&name=" + name, true);
                xml.send();
            }
            function fetchcontacts()
            {

                clearInterval(cs);
                var xml = new XMLHttpRequest();
                xml.onreadystatechange = function () {

                    if (xml.status == 200 && xml.readyState == 4)
                    {
                        document.getElementById("content").innerHTML = xml.responseText;
                    }
                };
                xml.open("GET", "fetch_contacts.jsp", true);
                xml.send();
            }

            function go()
            {

                cs = setInterval("fetchchats()", 500);
            }
            function fetchchats()
            {

                var xml = new XMLHttpRequest();
                xml.onreadystatechange = function () {

                    if (xml.status == 200 && xml.readyState == 4)
                    {
                        document.getElementById("content").innerHTML = xml.responseText;
                    }
                };
                xml.open("GET", "fetch_chats.jsp", true);
                xml.send();
            }
           function enter(event)
           {
               if(event.keyCode===13)
               {
                   sendmsg();
               }
               
           }
            function sendmsg()
            {
                var msg = document.getElementById("textfield").value;
                if (msg != "")
                {
                    var xml = new XMLHttpRequest();
                    xml.onreadystatechange = function () {

                        if (xml.status == 200 && xml.readyState == 4)
                        {
                            document.getElementById("textfield").value = "";
                        }
                    };
                    xml.open("GET", "send_msg.jsp?message_from=" + phone + "&message=" + msg, true);
                    xml.send();
                }
            }
            function show()
            {
                document.getElementById("im1").src = "";
                document.getElementById("f1").value = "";
                document.getElementById("lb1").innerHTML = "";
                document.getElementById("label1").style.display = "none";
                document.getElementById("progress1").style.display = "none";
                $("#myModal1").modal("show");
            }
            function readandpreview(fileobj, imageid)
            {
                var firstfile = fileobj.files[0];
                var reader = new FileReader();
                
                reader.onload = (function (f)
                {
                    return function read12(e)
                    {
                        if (firstfile.type.indexOf("image") !== -1)
                        {
                            document.getElementById(imageid).src = e.target.result;
                            document.getElementById("b1").style.display = "inline";
                        } else
                        {
                            alert("choose correct file");
                            document.getElementById("b1").style.display = "none";
                        }

                    };
                })(firstfile);
                reader.readAsDataURL(firstfile);
            }


            function upload_image()
            {

                document.getElementById("fphone1").value = phone;
                var ans = "";
                //NEW CODE
                var formdata = new FormData();
                var controls = document.getElementById("form1").elements;
                var flag = 0;
                for (var i = 0; i < controls.length; i++)
                {
                    if (controls[i].name == "" || controls[i].name == null)
                    {
                        flag = 1;
                    }

                    if (controls[i].type == "file")
                    {
                        if (controls[i].files.length > 0)
                        {
                            ans = ans + controls[i].name + ": " + controls[i].files[0].name + "\n";
                            formdata.append(controls[i].name, controls[i].files[0]);
                        } else
                        {
                            flag = 2;
                        }
                    } else    // for other input types  text,password,select
                    {
                        ans = ans + controls[i].name + ": " + controls[i].value + "\n";
                        //formdata.append("rn",document.getElementById("rn").value);
                        //formdata.append(firstfile.name,firstfile);

                        formdata.append(controls[i].name, controls[i].value);
                    }
                }


                if (flag == 1)
                {
                   
                } else if (flag == 2)
                {
                } else
                {
                    var xhr = new XMLHttpRequest;
                    xhr.upload.addEventListener("progress", updateProgress, false);
                    xhr.open("POST", "./ImageUploader", true);
                   
                    xhr.onreadystatechange = function ()
                    {
                        if (xhr.readyState === 4 && xhr.status == 200)
                        {
                            document.getElementById("lb1").innerHTML = "Success from Server Side";
                            document.getElementById("lb1").style.color = "green";
                            alert("uploaded successfully");
                            $("#myModal1").modal("hide");
                        }
                    };
                    document.getElementById("label1").style.display = "block";
                    document.getElementById("progress1").style.display = "block";
                    xhr.send(formdata);
                }

            }

            function updateProgress(evt)
            {
                if (evt.lengthComputable)
                {
                    var percentComplete = parseInt((evt.loaded * 100) / evt.total);
                    document.getElementById("label1").innerHTML = percentComplete + " %";
                    document.getElementById("progress1").value = percentComplete;
                }
            }

            function show1()
            {
                document.getElementById("im2").src = "";
                document.getElementById("f2").value = "";
                document.getElementById("lb2").innerHTML = "";
                document.getElementById("label2").style.display = "none";
                document.getElementById("progress2").style.display = "none";
                $("#myModal2").modal("show");
            }
            function readandpreview1(fileobj, imageid)
            {
                var firstfile = fileobj.files[0];
                var reader = new FileReader();
               
                reader.onload = (function (f)
                {
                    return function read12(e)
                    {
                        if (firstfile.type.indexOf("video") !== -1)
                        {
                            document.getElementById(imageid).src = "video.jpg";
                            document.getElementById("b2").style.display = "inline";
                        } else
                        {
                            alert("choose correct file");
                            document.getElementById("b2").style.display = "none";
                        }

                    };
                })(firstfile);
                reader.readAsDataURL(firstfile);
            }


            function upload_image1()
            {

                document.getElementById("fphone2").value = phone;
                var ans = "";
                //NEW CODE
                var formdata = new FormData();
                var controls = document.getElementById("form2").elements;
               
                var flag = 0;
                for (var i = 0; i < controls.length; i++)
                {
                    if (controls[i].name == "" || controls[i].name == null)
                    {
                        flag = 1;
                    }

                    if (controls[i].type == "file")
                    {
                        if (controls[i].files.length > 0)
                        {
                            ans = ans + controls[i].name + ": " + controls[i].files[0].name + "\n";
                            formdata.append(controls[i].name, controls[i].files[0]);
                        } else
                        {
                            flag = 2;
                        }
                    } else    // for other input types  text,password,select
                    {
                        ans = ans + controls[i].name + ": " + controls[i].value + "\n";
                        //formdata.append("rn",document.getElementById("rn").value);
                        //formdata.append(firstfile.name,firstfile);

                        formdata.append(controls[i].name, controls[i].value);
                    }
                }


                if (flag == 1)
                {
                   
                } else if (flag == 2)
                {
                } else
                {
                    var xhr = new XMLHttpRequest;
                    xhr.upload.addEventListener("progress", updateProgress1, false);
                    xhr.open("POST", "./VideoUploader", true);
                    //for response receiving
                    xhr.onreadystatechange = function ()
                    {
                        if (xhr.readyState === 4 && xhr.status == 200)
                        {
                            
                            document.getElementById("lb2").innerHTML = "Success from Server Side";
                            document.getElementById("lb2").style.color = "green";
                            alert("uploaded successfully");
                            $("#myModal2").modal("hide");
                        }
                    };
                    document.getElementById("label2").style.display = "block";
                    document.getElementById("progress2").style.display = "block";
                    xhr.send(formdata);
                }

            }

            function updateProgress1(evt)
            {
                if (evt.lengthComputable)
                {
                    var percentComplete = parseInt((evt.loaded * 100) / evt.total);
                    document.getElementById("label2").innerHTML = percentComplete + " %";
                    document.getElementById("progress2").value = percentComplete;
                }
            }
            function show2()
            {
                document.getElementById("im3").src = "";
                document.getElementById("f3").value = "";
                document.getElementById("lb3").innerHTML = "";
                document.getElementById("label3").style.display = "none";
                document.getElementById("progress3").style.display = "none";
                
                $("#myModal3").modal("show");
            }
            function readandpreview2(fileobj, imageid)
            {
                var firstfile = fileobj.files[0];
                var reader = new FileReader();
                
                reader.onload = (function (f)
                {
                    return function read12(e)
                    {
                        if (firstfile.type.indexOf("image") !== -1)
                        {
                            document.getElementById(imageid).src = e.target.result;
                            document.getElementById("b3").style.display = "inline";
                        } else
                        {
                            alert("choose correct file");
                            document.getElementById("b3").style.display = "none";
                        }

                    };
                })(firstfile);
                reader.readAsDataURL(firstfile);
            }


            function upload_image2()
            {

                
                var ans = "";
                //NEW CODE
                var formdata = new FormData();
                var controls = document.getElementById("form3").elements;
                
                var flag = 0;
                for (var i = 0; i < controls.length; i++)
                {
                    if (controls[i].name == "" || controls[i].name == null)
                    {
                        flag = 1;
                    }

                    if (controls[i].type == "file")
                    {
                        if (controls[i].files.length > 0)
                        {
                            ans = ans + controls[i].name + ": " + controls[i].files[0].name + "\n";
                            formdata.append(controls[i].name, controls[i].files[0]);
                        } else
                        {
                            flag = 2;
                        }
                    } else    // for other input types  text,password,select
                    {
                        ans = ans + controls[i].name + ": " + controls[i].value + "\n";
                        //formdata.append("rn",document.getElementById("rn").value);
                        //formdata.append(firstfile.name,firstfile);

                        formdata.append(controls[i].name, controls[i].value);
                    }
                }


                if (flag == 1)
                {
                    
                } else if (flag == 2)
                {
                    
                } else
                {
                    
                    var xhr = new XMLHttpRequest;
                    xhr.upload.addEventListener("progress", updateProgress2, false);
                    xhr.open("POST", "./change_pic", true);
                    //for response receiving
                    xhr.onreadystatechange = function ()
                    {
                        if (xhr.readyState === 4 && xhr.status == 200)
                        {
                           
                          
                            document.getElementById("lb3").innerHTML = "Success from Server Side";
                            document.getElementById("lb3").style.color = "green";
                            var res = xhr.responseText.trim();
                            
                         
                            alert("uploaded successfully");
                            $("#myModal3").modal("hide");
                            window.location.reload();
                              
                        }
                    };
                    document.getElementById("label3").style.display = "block";
                    document.getElementById("progress3").style.display = "block";
                    xhr.send(formdata);
                }

            }

            function updateProgress2(evt)
            {
                if (evt.lengthComputable)
                {
                    var percentComplete = parseInt((evt.loaded * 100) / evt.total);
                    document.getElementById("label3").innerHTML = percentComplete + " %";
                    document.getElementById("progress3").value = percentComplete;
                }
            }

            function openmodal()
            {
                $("#myModal4").modal("show");
                
            }
            function change_status()
            {
                
                var stat=document.getElementById("editstatus").value;
               var phone = document.getElementById("phone").value;
                
                
                var xml = new XMLHttpRequest();
                xml.onreadystatechange = function () {

                    if (xml.status == 200 && xml.readyState == 4)
                    {
                        
                        document.getElementById("status").innerHTML = stat;
                        $("#myModal4").modal("hide");
                    }

                };
                xml.open("GET", "./set_status?phone=" + phone + "&Status="+stat, true);
                xml.send();
                 
                
                
                
            }
            


        </script>
    </head>
    <body onload="go()">
        
        
        <%
            try
           {
            session.getAttribute("phone").toString();
            
           }catch(Exception ex)
           {
                response.sendRedirect("<h1>Please Login Again</h1>");
            
           }
 
    
        
            String status="";
            String phone = session.getAttribute("phone").toString();
            String name = session.getAttribute("NAME").toString().toUpperCase();
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
            
            status=rs.getString("Status");
           
            
        }
            }catch(Exception ex)
            {
                ex.printStackTrace();
            }

        %>
        
        <input type="hidden" value="<%= phone %>" id="phone" />
            <div style="background-color:#0288D1 ;height: 200px">
                <center> <h1 style="color: #000;font-family: ‘Lucida Console’, Monaco, monospace;margin-top: 0px">  Welcome <%= name%><br><h3 style="color:#B3E5FC;padding: 0px" id="status"><%=status%></h3></h1> </center>




                <div class="dropdown" style="float: right ;right: 5px; position: absolute; top: 10px;">
                    <div id="faltu">
                    <img src="./ProfilePics/<%=phone%>.jpg" id="profile" width="100" height="100" class="img-circle tilt blur dropbtn" onclick="options()" style="float: right;border: 2px solid #286090;cursor: pointer; ">
</div>

                    <div class="dropdown-content" style="border-radius: 10px; background-color:#FF4081; width: 150px;height:150px;position: absolute;right: 1px;top: 100px;z-index: 1000">
                        <button class="btn " style="background-color:#FF4081;margin-top: 5px;width: 140px;color:#FFFFFF" onclick="show2()">Change Profile Picture</button>
                        <button class="btn" style="background-color:#FF4081;margin-top: 5px;color:#FFFFFF" onclick="openmodal()">Change Status</button>
                        <a href="logout.jsp" style="margin-top: 4px;color:#FFFFFF" >Logout</a>
                    </div>
                </div>
            </div>  

            <!-- -----------------------------Start of Image Uploader Div-------------------------------------- -->
            <div id="myModal1" class="modal fade" role="dialog">
                <div class="modal-dialog">

                    <!-- Modal content-->
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Upload Image</h4>
                        </div>
                        <div class="modal-body">
                            <form action="#" method="post" enctype="multipart/form-data" id="form1">  <br>
                                <input type="hidden" name="fphone1" value="test" id="fphone1" />
                                <table  style="width: 400px;">

                                    <tr>
                                        <td><img src="" id="im1" style="width: 100px;height: 100px;border: solid 2px green"></td>  
                                        <td><input type="file" id="f1" name="f1"  onchange="readandpreview(this, 'im1')" /> </td>          
                                    </tr>

                                </table>
                            </form>

                            <br>

                            <progress id="progress1" min="0" max="100" value="0" style="display: none; width: 200px;"></progress>
                            <label id="label1" style="display: none;"></label>
                            <label id="lb1"></label>
                            <br>

                        </div>

                        <div class="modal-footer">
                            <input type="button" class="btn btn-default" id="b1" value="Submit" style="display: none;" onclick="upload_image()" >
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        </div>
                    </div>

                </div>
            </div>

            <!-- -----------------------------End of Video Uploader Div-------------------------------------- -->
            <div id="myModal2" class="modal fade" role="dialog">
                <div class="modal-dialog">

                    <!-- Modal content-->
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Upload Video</h4>
                        </div>
                        <div class="modal-body">
                            <form action="#" method="post" enctype="multipart/form-data" id="form2">  <br>
                                <input type="hidden" name="fphone2" value="test" id="fphone2" />
                                <table  style="width: 400px;">

                                    <tr>
                                        <td><img src="" id="im2" style="width: 100px;height: 100px;border: solid 2px green"></td>  
                                        <td><input type="file" id="f2" name="f2"  onchange="readandpreview1(this, 'im2')" /> </td>          
                                    </tr>

                                </table>
                            </form>

                            <br>

                            <progress id="progress2" min="0" max="100" value="0" style="display: none; width: 200px;"></progress>
                            <label id="label2" style="display: none;"></label>
                            <label id="lb2"></label>
                            <br>

                        </div>

                        <div class="modal-footer">
                            <input type="button" class="btn btn-default" id="b2" value="Submit" style="display: none;" onclick="upload_image1()" >
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        </div>
                    </div>

                </div>
            </div>

            <!-- -----------------------------End of Image Uploader Div-------------------------------------- -->
    <!-- -----------------------------Start of Profile Uploader Div-------------------------------------- -->

        <div id="myModal3" class="modal fade" role="dialog">
            <div class="modal-dialog">

                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Upload Image</h4>
                    </div>
                    <div class="modal-body">
                        <form action="#" method="post" enctype="multipart/form-data" id="form3">  <br>
                            
                            <table  style="width: 400px;">

                                <tr>
                                    <td><img src="" id="im3" style="width: 100px;height: 100px;border: solid 2px green"></td>  
                                    <td><input type="file" id="f3" name="f3"  onchange="readandpreview2(this, 'im3')" /> </td>          
                                </tr>

                            </table>
                        </form>

                        <br>

                        <progress id="progress3" min="0" max="100" value="0" style="display: none; width: 200px;"></progress>
                        <label id="label3" style="display: none;"></label>
                        <label id="lb3"></label>
                          <br>

                    </div>

                    <div class="modal-footer">
                        <input type="button" class="btn btn-default" id="b3" value="Submit" style="display: none;" onclick="upload_image2()" >
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    </div>
                </div>

            </div>
        </div>
        <!--End Of Profile Uploader -->
        <!-- Start of Status div -->
          <div id="myModal4" class="modal fade" role="dialog">
                <div class="modal-dialog">

                    <!-- Modal content-->
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Change Status</h4>
                        </div>
                        <div class="modal-body">
                              <br>
                              <input type="text" maxlength="50" id="editstatus" placeholder="Enter New Status...(50 Characters only)" style="width: 400px">               
                              <br>
                              <br>
                         </div>

                        <div class="modal-footer">
                            <input type="button" class="btn btn-default" id="b1" value="Submit" onclick="change_status()" >
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        </div>
                    </div>

                </div>
            </div>

            <!-- -----------------------------End of Status Div-------------------------------------- -->
          


        <div style="background-color: gray">


            <div id="screen" class="container demo-container mdl-grid" style="position: relative;bottom: 80px;z-index: 100;border-radius: 10px;padding: 20px;background-color: whitesmoke ;-moz-box-shadow: 3px 3px 5px 6px #ccc; -webkit-box-shadow: 3px 3px 5px 6px #ccc;  box-shadow:         3px 3px 5px 6px #ccc;" >
                <div id="sidebar" class="  col-sm-4 " style="height: 530px ;padding: 0px;border-right: 0px" >
                    <button id="chatbutton" type="button" onclick="go()" class="btn  col-sm-6 button " style="border-color: #d4d4d4;background-color:#FF4081">CHATS</button>
                    <button id="contactbutton" type="button" onclick="fetchcontacts()" class="btn  col-sm-6 button" style="border-color: #d4d4d4;background-color: #FF4081">CONTACTS</button>

                    <div id="content" class="table-responsive table-condensed table-hover"  style= "cursor: pointer;-moz-user-select: none; -webkit-user-select: none; -ms-user-select:none; user-select:none;-o-user-select:none;height: 467px;padding:10px ;margin-top: 30px ;border: 1px solid #d4d4d4;border-top: 0px;border-radius: 10px">


                    </div>
                </div>
                <div id="chats" class="col-sm-8" >

                </div>
                <div  id="bottomdiv" style="background: whitesmoke ;height: 35px;visibility: hidden">
                    <div class="col-sm-6" >
                        <textarea id="textfield" maxlength="900" onkeyup="enter(event)" placeholder="Type Message..." style="border-radius: 5px;width: 100%;height: 35px;background-color: whitesmoke"></textarea>
                    </div>
                    <div class="col-sm-2" style="padding: 0px ;">
                        <button id="sendbutton" onclick="sendmsg()" type="button" class="btn" style="width: 100%;height: 35px;background-color: #2196F3;color:#b9def0">SEND</button>
                    </div>

                </div>                


            </div>
            </div>
    </body>
</html>
