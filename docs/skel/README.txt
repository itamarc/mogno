Mogno Skel README

To begin a new Mogno application from this skel:

1) Copy all it's files and directories (except this README.txt) to your
application development dir and edit the files:
* build.properties
* xml/MognoApplication.xml

2) Put your source files under "src" and the XML files under "xml".

3) You need the following libraries:
* Mogno - http://www.oktiva.com.br
* ITemplate - http://www.gjt.org/pkgdoc/org/gjt/itemplate/index.html
* Log4j (>=1.2.7) - http://jakarta.apache.org/log4j/
* XercesImpl - http://xml.apache.org/xerces2-j/index.html
* XML Writer - http://megginson.com/Software/index.html

Any extra libraries your application needs can be put in the "lib" dir, and
will be deployed with your application.

4) Your application will be at the URL:
http://[host-of-your-web-server]/[app.name]/app

If you want another URL, edit the file "config/web.xml"

5) Restart your web server

If you use Tomcat and the manager application is running, all you need is to 
call the following URL (Tomcat will ask you a login and password for a user
with "manager" role at conf/tomcat-users.xml):

http://[host]/manager/install?path=/[app.name]&war=jar:file:/usr/local/tomcat/webapps/[app.name].war!/

(the "!/" at the end is not a typo.)



Any questions? Contact me: Itamar Carvalho <itamar@oktiva.com.br>

(c) 2003 Oktiva Telecomunicações e Informática Ltda - http://www.oktiva.com.br
$Id$

