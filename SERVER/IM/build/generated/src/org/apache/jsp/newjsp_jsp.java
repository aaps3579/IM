package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.google.zxing.WriterException;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import com.google.zxing.common.BitMatrix;
import java.awt.image.BufferedImage;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.QRCodeWriter;
import java.util.Hashtable;
import java.io.File;

public final class newjsp_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!--\n");
      out.write("  Material Design Lite\n");
      out.write("  Copyright 2015 Google Inc. All rights reserved.\n");
      out.write("\n");
      out.write("  Licensed under the Apache License, Version 2.0 (the \"License\");\n");
      out.write("  you may not use this file except in compliance with the License.\n");
      out.write("  You may obtain a copy of the License at\n");
      out.write("\n");
      out.write("      https://www.apache.org/licenses/LICENSE-2.0\n");
      out.write("\n");
      out.write("  Unless required by applicable law or agreed to in writing, software\n");
      out.write("  distributed under the License is distributed on an \"AS IS\" BASIS,\n");
      out.write("  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n");
      out.write("  See the License for the specific language governing permissions and\n");
      out.write("  limitations under the License\n");
      out.write("-->\n");
      out.write("<html lang=\"en\">\n");
      out.write("    <head>\n");
      out.write("        <meta charset=\"utf-8\">\n");
      out.write("        <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
      out.write("        <meta name=\"description\" content=\"A front-end template that helps you build fast, modern mobile web apps.\">\n");
      out.write("        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, minimum-scale=1.0\">\n");
      out.write("        <title>Material Design Lite</title>\n");
      out.write("\n");
      out.write("        <!-- Add to homescreen for Chrome on Android -->\n");
      out.write("        <meta name=\"mobile-web-app-capable\" content=\"yes\">\n");
      out.write("        <link rel=\"icon\" sizes=\"192x192\" href=\"images/android-desktop.png\">\n");
      out.write("\n");
      out.write("        <!-- Add to homescreen for Safari on iOS -->\n");
      out.write("        <meta name=\"apple-mobile-web-app-capable\" content=\"yes\">\n");
      out.write("        <meta name=\"apple-mobile-web-app-status-bar-style\" content=\"black\">\n");
      out.write("        <meta name=\"apple-mobile-web-app-title\" content=\"Material Design Lite\">\n");
      out.write("        <link rel=\"apple-touch-icon-precomposed\" href=\"images/ios-desktop.png\">\n");
      out.write("\n");
      out.write("        <!-- Tile icon for Win8 (144x144 + tile color) -->\n");
      out.write("        <meta name=\"msapplication-TileImage\" content=\"images/touch/ms-touch-icon-144x144-precomposed.png\">\n");
      out.write("        <meta name=\"msapplication-TileColor\" content=\"#3372DF\">\n");
      out.write("\n");
      out.write("        <link rel=\"shortcut icon\" href=\"images/favicon.png\">\n");
      out.write("\n");
      out.write("        <!-- SEO: If your mobile URL is different from the desktop URL, add a canonical link to the desktop page https://developers.google.com/webmasters/smartphone-sites/feature-phones -->\n");
      out.write("        <!--\n");
      out.write("        <link rel=\"canonical\" href=\"http://www.example.com/\">\n");
      out.write("        -->\n");
      out.write("\n");
      out.write("        <link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/css?family=Roboto:regular,bold,italic,thin,light,bolditalic,black,medium&amp;lang=en\">\n");
      out.write("        <link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/icon?family=Material+Icons\">\n");
      out.write("        <link rel=\"stylesheet\" href=\"https://code.getmdl.io/1.1.3/material.deep_purple-pink.min.css\">\n");
      out.write("        <link href=\"css/material.css\" rel=\"stylesheet\" type=\"text/css\"/>\n");
      out.write("        <link href=\"mdl-template-text-only/styles.css\" rel=\"stylesheet\" type=\"text/css\"/>\n");
      out.write("        \n");
      out.write("        <script src=\"js/material.js\" type=\"text/javascript\"></script>\n");
      out.write("        <script src=\"materialize/js/materialize.js\" type=\"text/javascript\"></script>\n");
      out.write("        <style>\n");
      out.write("            #view-source {\n");
      out.write("                position: fixed;\n");
      out.write("                display: block;\n");
      out.write("                right: 0;\n");
      out.write("                bottom: 0;\n");
      out.write("                margin-right: 40px;\n");
      out.write("                margin-bottom: 40px;\n");
      out.write("                z-index: 900;\n");
      out.write("            }\n");
      out.write("        </style>\n");
      out.write("    </head>\n");
      out.write("    <script>\n");
      out.write("        var xml;\n");
      out.write("        function check()\n");
      out.write("        {\n");
      out.write("            setInterval(\"check2()\", 1000);\n");
      out.write("\n");
      out.write("        }\n");
      out.write("\n");
      out.write("        function check2()\n");
      out.write("        {\n");
      out.write("            var flag = 0;\n");
      out.write("            var sid = document.getElementById('sid').value;\n");
      out.write("            xml = new XMLHttpRequest();\n");
      out.write("            xml.onreadystatechange = function () {\n");
      out.write("                if (xml.readyState == 4 && xml.status == 200)\n");
      out.write("                {\n");
      out.write("                    var res = xml.responseText;\n");
      out.write("\n");
      out.write("                    document.getElementById('status').innerHTML = res;\n");
      out.write("                    if (res.indexOf('null') === -1)\n");
      out.write("                    {\n");
      out.write("                        if (flag === 0)\n");
      out.write("                        {\n");
      out.write("                            flag = 1;\n");
      out.write("                            alert('login successfull');\n");
      out.write("                        }\n");
      out.write("                        (function () {\n");
      out.write("                            'use strict';\n");
      out.write("                            var snackbarContainer = document.querySelector('#demo-snackbar-example');\n");
      out.write("\n");
      out.write("\n");
      out.write("                            var data = {\n");
      out.write("                                message: 'Login Success',\n");
      out.write("                            };\n");
      out.write("                            snackbarContainer.MaterialSnackbar.showSnackbar(data);\n");
      out.write("\n");
      out.write("                        }());\n");
      out.write("\n");
      out.write("                        window.location.href = \"homepage.jsp\";\n");
      out.write("\n");
      out.write("                    }\n");
      out.write("                }\n");
      out.write("            };\n");
      out.write("            xml.open('POST', \"./getSession_info?sid=\" + sid, true);\n");
      out.write("            xml.send();\n");
      out.write("\n");
      out.write("        }\n");
      out.write("\n");
      out.write("\n");
      out.write("    </script>\n");
      out.write("    <body class=\"mdl-demo mdl-color--grey-100 mdl-color-text--grey-700 mdl-base\">\n");
      out.write("        ");


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

        
      out.write("\n");
      out.write("        <input type=\"hidden\" id=\"sid\" value=\"");
      out.print( myCodeText);
      out.write("\" />\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("        <div class=\"mdl-layout mdl-js-layout mdl-layout--fixed-header\">\n");
      out.write("            <header class=\"mdl-layout__header mdl-layout__header--scroll mdl-color--primary\">\n");
      out.write("                <div class=\"mdl-layout--large-screen-only mdl-layout__header-row\" style=\"margin-top: 28px\">\n");
      out.write("                    <h3>Indian Messenger</h3>\n");
      out.write("                    <img src=\"web.png\" height=\"100px\" width=\"100px\" >\n");
      out.write("\n");
      out.write("                </div>\n");
      out.write("                <div class=\"mdl-layout--large-screen-only mdl-layout__header-row\">\n");
      out.write("                </div>\n");
      out.write("                <div class=\"mdl-layout__tab-bar mdl-js-ripple-effect mdl-color--primary-dark\">\n");
      out.write("                    <a href=\"#overview\" class=\"mdl-layout__tab is-active\">Scan QR Code</a>\n");
      out.write("                    <a href=\"#features\" class=\"mdl-layout__tab\">Features</a>\n");
      out.write("                    <a href=\"#features\" class=\"mdl-layout__tab\">Details</a>\n");
      out.write("                    <a href=\"#features\" class=\"mdl-layout__tab\">Technology</a>\n");
      out.write("                    <button class=\"mdl-button mdl-js-button mdl-button--fab mdl-js-ripple-effect mdl-button--colored mdl-shadow--4dp mdl-color--accent\" id=\"add\" >\n");
      out.write("                        <i class=\"material-icons\" id=\"tooltip\" role=\"presentation\">android</i>\n");
      out.write("                    </button>\n");
      out.write("\n");
      out.write("                </div>\n");
      out.write("\n");
      out.write("            </header>\n");
      out.write("            <main class=\"mdl-layout__content\">\n");
      out.write("                <div class=\"mdl-layout__tab-panel is-active\" id=\"overview\">\n");
      out.write("                    <section class=\"section--center mdl-grid mdl-grid--no-spacing mdl-shadow--2dp\">\n");
      out.write("                        <header class=\"section__play-btn mdl-cell mdl-cell--4-col-desktop mdl-cell--3-col-tablet mdl-cell--5-col-phone mdl-color--teal-100 mdl-color-text--white\">\n");
      out.write("                            <div  id=\"qrimage\" >\n");
      out.write("                                <img src=\"./QR.png\" width=\"300\" height=\"300\" />\n");
      out.write("                            </div>\n");
      out.write("\n");
      out.write("                            <h1 hidden id=\"status\">Your Login status</h1>\n");
      out.write("                        </header>\n");
      out.write("                        <div class=\"mdl-card mdl-cell mdl-cell--8-col-desktop mdl-cell--5-col-tablet mdl-cell--3-col-phone\">\n");
      out.write("                            <div class=\" section__text mdl-card__supporting-text\">\n");
      out.write("                                <h4 style=\"color:#0288D1\">Scan QR Code From Your Android Device</h4>  \n");
      out.write("                                <div class=\"section__text mdl-cell mdl-cell--9-col-desktop mdl-cell--5-col-tablet mdl-cell--3-col-phone\">\n");
      out.write("                                    <h6 style=\"font-weight: 400 ;font-size: 24sp\">\n");
      out.write("                                        <br>\n");
      out.write("                                        Note:-Neither place Your Device Too Far Nor Too Close From Screen.Confirmation Will Be Shown On Login Success.\n");
      out.write("                                    </h6>\n");
      out.write("                                </div>\n");
      out.write("                            </div>\n");
      out.write("                        </div>\n");
      out.write("\n");
      out.write("                    </section>\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("                </div>\n");
      out.write("                <footer class=\"mdl-mini-footer\">\n");
      out.write("                    <div class=\"mdl-mini-footer__left-section\">\n");
      out.write("                        <div class=\"mdl-logo\">\n");
      out.write("                            <img src=\"unilogo_1.png\" width=\"100px\" height=\"80px\">\n");
      out.write("                        </div>\n");
      out.write("                        <ul class=\"mdl-mini-footer__link-list\">\n");
      out.write("                            <pre style=\"padding: 0px\">IM is developed by Guneet Singh(2013CSC1031) and Amanpreet Singh(2013CSC1011) Of \n");
      out.write("Computer Science Department (MCA-fyic-6th Sem) under Six Month Training Project.\n");
      out.write("Project is built using Newly introduced Google's Material Design Guidelines.  </pre>\n");
      out.write("                        </ul>\n");
      out.write("                    </div>\n");
      out.write("                    \n");
      out.write("                    <div class=\"mdl-mini-footer__right-section\" style=\"padding: 5px;background-color: #727272;border-radius: 5px\">\n");
      out.write("                        <div class=\"mdl-textfield mdl-js-textfield mdl-textfield--floating-label\">\n");
      out.write("                            <input class=\"mdl-textfield__input\" type=\"text\" id=\"text4\">\n");
      out.write("                            <label class=\"mdl-textfield__label\" for=\"text4\">Email...</label>\n");
      out.write("                        </div>\n");
      out.write("                        <div class=\"mdl-textfield mdl-js-textfield mdl-textfield--floating-label\">\n");
      out.write("                            <input class=\"mdl-textfield__input\" type=\"text\" id=\"text5\">\n");
      out.write("                            <label class=\"mdl-textfield__label\" for=\"text5\">Message...</label>\n");
      out.write("                        </div>\n");
      out.write("                        \n");
      out.write("                        <div id=\"tooltip4\" class=\"icon material-icons\"><a href=\"#\" >mail</a></div>\n");
      out.write("                        <div for=\"tooltip4\" class=\"mdl-tooltip\">Submit Feedback</div> \n");
      out.write("                        \n");
      out.write("\n");
      out.write("                    </div>\n");
      out.write("\n");
      out.write("                </footer>\n");
      out.write("                <div id=\"demo-snackbar-example\" class=\"mdl-js-snackbar mdl-snackbar\">\n");
      out.write("                    <div class=\"mdl-snackbar__text\"></div>\n");
      out.write("                    <button class=\"mdl-snackbar__action\" type=\"button\"></button>\n");
      out.write("                </div>\n");
      out.write("            </main>\n");
      out.write("        </div>\n");
      out.write("    </body>\n");
      out.write("</html>\n");
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
