<%@page import="com.google.zxing.WriterException"%>
<%@page import="javax.imageio.ImageIO"%>
<%@page import="java.awt.Color"%>
<%@page import="java.awt.Graphics2D"%>
<%@page import="com.google.zxing.common.BitMatrix"%>
<%@page import="java.awt.image.BufferedImage"%>
<%@page import="com.google.zxing.BarcodeFormat"%>
<%@page import="com.google.zxing.qrcode.decoder.ErrorCorrectionLevel"%>
<%@page import="com.google.zxing.EncodeHintType"%>
<%@page import="com.google.zxing.qrcode.QRCodeWriter"%>
<%@page import="java.util.Hashtable"%>
<%@page import="java.io.File"%>
<!--
  Material Design Lite
  Copyright 2015 Google Inc. All rights reserved.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

      https://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License
-->


<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="description" content="A front-end template that helps you build fast, modern mobile web apps.">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
        <title>IM</title>

        <!-- Add to homescreen for Chrome on Android -->
        <meta name="mobile-web-app-capable" content="yes">
        <link rel="icon" sizes="192x192" href="images/android-desktop.png">

        <!-- Add to homescreen for Safari on iOS -->
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">
        <meta name="apple-mobile-web-app-title" content="Material Design Lite">
        <link rel="apple-touch-icon-precomposed" href="images/ios-desktop.png">

        <!-- Tile icon for Win8 (144x144 + tile color) -->
        <meta name="msapplication-TileImage" content="images/touch/ms-touch-icon-144x144-precomposed.png">
        <meta name="msapplication-TileColor" content="#3372DF">

        <link rel="shortcut icon" href="images/favicon.png">

        <!-- SEO: If your mobile URL is different from the desktop URL, add a canonical link to the desktop page https://developers.google.com/webmasters/smartphone-sites/feature-phones -->
        <!--
        <link rel="canonical" href="http://www.example.com/">
        -->

        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:regular,bold,italic,thin,light,bolditalic,black,medium&amp;lang=en">
        
        <link rel="stylesheet" href="https://code.getmdl.io/1.1.3/material.deep_purple-pink.min.css">
        <link href="css/icon.css" rel="stylesheet" type="text/css"/>
        <link href="mdl-template-text-only/styles.css" rel="stylesheet" type="text/css"/>
        <link href="materialize/css/materialize.css" rel="stylesheet" type="text/css"/>
        <link href="css/material.css" rel="stylesheet" type="text/css"/>
        <script src="js/material.js" type="text/javascript"></script>
        <script src="materialize/js/materialize.js" type="text/javascript"></script>
        <style>
            #view-source {
                position: fixed;
                display: block;
                right: 0;
                bottom: 0;
                margin-right: 40px;
                margin-bottom: 40px;
                z-index: 900;
            }
        </style>
    </head>
    <script>
        var xml;
        var email,feed;
        function feedback()
        {
            email=document.getElementById("text4").value;
            feed=document.getElementById("text5").value;
            xml = new XMLHttpRequest();
            xml.onreadystatechange = function () {
                if (xml.readyState == 4 && xml.status == 200)
                {
                    var res = xml.responseText;

                    if(res.trim=="ok")
                    {
                        document.getElementById("text4").value="";
                        document.getElementById("text5").value="";
                        alert("Thanks For Feedback");
                        
                    }
                   
                }
            };
            xml.open('GET', "./feedback.jsp?email=" + email+"&feed="+feed, true);
            xml.send();
            document.getElementById("text4").value="";
            document.getElementById("text5").value="";
           
            
        }
        function check()
        {
       
            setInterval("check2()", 1000);

        }

        function check2()
        {
            var flag = 0;
            var sid = document.getElementById('sid').value;
            xml = new XMLHttpRequest();
            xml.onreadystatechange = function () {
                if (xml.readyState == 4 && xml.status == 200)
                {
                    var res = xml.responseText;

                    document.getElementById('status').innerHTML = res;
                    if (res.indexOf('null') === -1)
                    {
                        if (flag === 0)
                        {
                            flag = 1;
                            alert('login successfull');
                        }
                         window.location.href = "homepage.jsp";

                    }
                }
            };
            xml.open('POST', "./getSession_info?sid=" + sid, true);
            xml.send();

        }


    </script>
    <body onload="check()" class="mdl-demo mdl-color--grey-100 mdl-color-text--grey-700 mdl-base">
        
       <%
           try
           {
               if(session.getAttribute("logout").equals("yes"))
               {
           %>
           <div id="success" style="width:30%;
	font-size: .8em;
	text-align: center;
	padding: 10px;
	background-color: #5cb85c;
	color: #fff;
	box-shadow:	0px 1px 3px 0px rgba(0, 0, 0, .3);
	text-transform: uppercase;
	position: relative;
	font-weight: bold;
	margin:auto;
	-webkit-animation: bounce 800ms ease-out;
	-moz-animation: bounce 800ms ease-out;
	-o-animation: bounce 1600ms ease-out;
	animation: bounce 2000ms ease-out;">
            Sucessfully Logged Out.
        </div>
           <%
               }
               session.setAttribute("logout", "no");
               
               
        }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
               %>

         
        <%
                //QR Code
            String myCodeText = session.getId();

            String filePath = "QR.png";
            int size = 125;
            String fileType = "png";
            File myFile = new File(getServletContext().getRealPath("/") + "\\" + filePath);
            try {
                Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
                hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
                QRCodeWriter qrCodeWriter = new QRCodeWriter();
                BitMatrix byteMatrix = qrCodeWriter.encode(myCodeText, BarcodeFormat.QR_CODE, size, size, hintMap);
                int CrunchifyWidth = byteMatrix.getWidth();
                BufferedImage image = new BufferedImage(CrunchifyWidth, CrunchifyWidth,
                        BufferedImage.TYPE_INT_RGB);
                image.createGraphics();

                Graphics2D graphics = (Graphics2D) image.getGraphics();
                graphics.setColor(Color.WHITE);
                graphics.fillRect(0, 0, CrunchifyWidth, CrunchifyWidth);
                graphics.setColor(Color.BLACK);

                for (int i = 0; i < CrunchifyWidth; i++) {
                    for (int j = 0; j < CrunchifyWidth; j++) {
                        if (byteMatrix.get(i, j)) {
                            graphics.fillRect(i, j, 1, 1);
                        }
                    }
                }
                ImageIO.write(image, fileType, myFile);
            } catch (WriterException e) {
                e.printStackTrace();
            }
            System.out.println("\n\nYou have successfully created QR Code.");

        %>
        <input type="hidden" id="sid" value="<%= myCodeText%>" />

       
        <div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
            <header class="mdl-layout__header mdl-layout__header--scroll mdl-color--primary">
                <div class="mdl-layout--large-screen-only mdl-layout__header-row" style="margin-top: 21px">
                    <h3>Indian Messenger</h3>
                    <img src="web.png" height="100px" width="100px" >

                </div>
                <div class="mdl-layout--large-screen-only mdl-layout__header-row">
                </div>
                <div class="mdl-layout__tab-bar mdl-js-ripple-effect mdl-color--primary-dark">
                    <a href="#overview" class="mdl-layout__tab is-active">Scan QR Code</a>
                    <a href="#features" class="mdl-layout__tab">Features</a>
                    <a href="#technology" class="mdl-layout__tab">Technology</a>
                    <button onclick="location.href='http://www.mediafire.com/download/1m884t95a5s4us7/IM.apk'" class="mdl-button mdl-js-button mdl-button--fab mdl-js-ripple-effect mdl-button--colored mdl-shadow--4dp mdl-color--accent" id="add" >
                    <i class="material-icons" id="tooltip" role="presentation">android</i>
                    </button>

                </div>

            </header>
            <main class="mdl-layout__content">
                <div class="mdl-layout__tab-panel is-active" id="overview">
                    <section class="section--center mdl-grid mdl-grid--no-spacing mdl-shadow--2dp">
                        <header class="section__play-btn mdl-cell mdl-cell--4-col-desktop mdl-cell--3-col-tablet mdl-cell--5-col-phone mdl-color--teal-100 mdl-color-text--white">
                            <div  id="qrimage" >
                                <img src="./QR.png" width="300" height="300" />
                            </div>

                            <h1 hidden id="status">Your Login status</h1>
                        </header>
                        <div class="mdl-card mdl-cell mdl-cell--8-col-desktop mdl-cell--5-col-tablet mdl-cell--3-col-phone">
                            <div class=" section__text mdl-card__supporting-text">
                                <h4 style="color:#0288D1">Scan QR Code From Your Android Device</h4>  
                                <div class="section__text mdl-cell mdl-cell--9-col-desktop mdl-cell--5-col-tablet mdl-cell--3-col-phone">
                                    <h6 style="font-weight: 400 ;font-size: 24sp">
                                        <br>
                                        Note:-Neither place Your Device Too Far Nor Too Close From Screen.Confirmation Will Be Shown On Login Success.
                                    </h6>
                                </div>
                            </div>
                        </div>

                    </section>
          


                </div>
                <div class="mdl-layout__tab-panel" id="features">
                 <section class="section--center mdl-grid mdl-grid--no-spacing mdl-shadow--2dp">
            <div class="mdl-card mdl-cell mdl-cell--12-col">
              <div class="mdl-card__supporting-text mdl-grid mdl-grid--no-spacing">
                <h4 class="mdl-cell mdl-cell--12-col">Features</h4>
                <div class="section__text mdl-cell mdl-cell--10-col-desktop mdl-cell--6-col-tablet mdl-cell--3-col-phone">
                  <h5>Standalone Web Client</h5>
                    Now remain connected to your friends even if your phone internet is off.
                </div>
                <div class="section__text mdl-cell mdl-cell--10-col-desktop mdl-cell--6-col-tablet mdl-cell--3-col-phone">
                  <h5>File Sending</h5>
                  Send Any Kind Of File To Your Friends From Your Phone.(Web Client does not have this Feature)
                </div>
                <div class="section__text mdl-cell mdl-cell--10-col-desktop mdl-cell--6-col-tablet mdl-cell--3-col-phone">
                  <h5>User Friendly</h5>
                  Easy to Use.<br>
                  Also Support Themes in Mobile App.
                </div>
              </div>
              
            </div>
            
          </section>
        </div>
                <div class="mdl-layout__tab-panel" id="technology">
                    
                    <section class="section--center mdl-grid mdl-grid--no-spacing mdl-shadow--2dp">
            <div class="mdl-card mdl-cell mdl-cell--12-col">
              <div class="mdl-card__supporting-text mdl-grid mdl-grid--no-spacing">
                <h4 class="mdl-cell mdl-cell--12-col">Details</h4>
                <div class="section__circle-container mdl-cell mdl-cell--2-col mdl-cell--1-col-phone">
                    <img src="Java_logo.png" width="80" height="50"  >
                   
                </div>
                <div class="section__text mdl-cell mdl-cell--10-col-desktop mdl-cell--6-col-tablet mdl-cell--3-col-phone">
                  <h5>JAVA</h5>
                  Java is a set of computer software and specifications developed by Sun Microsystems, which was later acquired by the Oracle Corporation, that provides a system for developing application software and deploying it in a cross-platform computing environment.
                </div>
                <div class="section__circle-container mdl-cell mdl-cell--2-col mdl-cell--1-col-phone">
                    <img src="bootstrap.png" width="70" height="70" style="margin-left: 10px">

                </div>
                <div class="section__text mdl-cell mdl-cell--10-col-desktop mdl-cell--6-col-tablet mdl-cell--3-col-phone">
                  <h5>BOOTSTRAP</h5>
                 Bootstrap is a free and open-source front-end library for creating websites and web applications. It contains HTML- and CSS-based design templates for typography, forms, buttons, navigation and other interface components, as well as optional JavaScript extensions.
                </div>
                <div class="section__circle-container mdl-cell mdl-cell--2-col mdl-cell--1-col-phone">
                    <img src="javascript.jpg" width="80" height="50" >
                </div>
                    <div class="section__text mdl-cell mdl-cell--10-col-desktop mdl-cell--6-col-tablet mdl-cell--3-col-phone">
                  <h5>JAVASCRIPT</h5>
                 JavaScript is a high-level, dynamic, untyped, and interpreted programming language.<br><br>
                </div>
                
                <div class="section__circle-container mdl-cell mdl-cell--2-col mdl-cell--1-col-phone">
                    <img src="mdl.png" width="80" height="50" >
                </div>
                    <div class="section__text mdl-cell mdl-cell--10-col-desktop mdl-cell--6-col-tablet mdl-cell--3-col-phone">
                  <h5>MATERIAL DESIGN LITE</h5>
                Material Design is a design language developed by Google. Expanding upon the card motifs that debuted in Google Now, Material Design makes more liberal use of grid-based layouts, responsive animations and transitions, padding, and depth effects such as lighting and shadows.
                </div>
                <br>
                <div class="section__circle-container mdl-cell mdl-cell--2-col mdl-cell--1-col-phone">
                    <img src="android.jpg" width="80" height="50" >
                </div>
                    <div class="section__text mdl-cell mdl-cell--10-col-desktop mdl-cell--6-col-tablet mdl-cell--3-col-phone">
                  <h5>Android</h5>
Android is a mobile operating system (OS) currently developed by Google, based on the Linux kernel and designed primarily for touchscreen mobile devices such as smartphones and tablets. 
                    </div>
              </div>
              
          </section>
                </div>
                <footer class="mdl-mini-footer">
                    <div class="mdl-mini-footer__left-section">
                        <div class="mdl-logo">
                            <img src="unilogo_1.png" width="100px" height="80px">
                        </div>
                        <ul class="mdl-mini-footer__link-list">
                            <pre style="padding: 0px">IM is developed by Guneet Singh(2013CSC1031) and Amanpreet Singh(2013CSC1011) Of 
Computer Science Department (MCA-fyic-6th Sem) under Six Month Training Project.
Note:-This Site Works Best On Google Chrome.  </pre>
                        </ul>
                    </div>
                    
                    <div class="mdl-mini-footer__right-section" style="padding: 5px;background-color: #727272;border-radius: 5px">
                        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                            <input class="mdl-textfield__input" type="text" id="text4">
                            <label class="mdl-textfield__label" for="text4" style="color: #212121">Email...</label>
                        </div>
                        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                            <input class="mdl-textfield__input" type="text" id="text5">
                            <label class="mdl-textfield__label" for="text5" style="color: #212121">Message...</label>
                        </div>
                        
                        
                        <div id="tooltip4" class="icon material-icons"><a href="#" onclick="feedback()">mail</a></div>
                        <div for="tooltip4" class="mdl-tooltip">Submit Feedback</div> 
                        

                    </div>

                </footer>
                
            </main>
        </div>
    </body>
</html>
