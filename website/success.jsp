<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<jsp:useBean id="rb" scope="session" class="psl.crunch3.web.RegisterBean" />
<title>Crunch - Register </title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="crunch.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/JavaScript">
<!--

<!--
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_nbGroup(event, grpName) { //v6.0
  var i,img,nbArr,args=MM_nbGroup.arguments;
  if (event == "init" && args.length > 2) {
    if ((img = MM_findObj(args[2])) != null && !img.MM_init) {
      img.MM_init = true; img.MM_up = args[3]; img.MM_dn = img.src;
      if ((nbArr = document[grpName]) == null) nbArr = document[grpName] = new Array();
      nbArr[nbArr.length] = img;
      for (i=4; i < args.length-1; i+=2) if ((img = MM_findObj(args[i])) != null) {
        if (!img.MM_up) img.MM_up = img.src;
        img.src = img.MM_dn = args[i+1];
        nbArr[nbArr.length] = img;
    } }
  } else if (event == "over") {
    document.MM_nbOver = nbArr = new Array();
    for (i=1; i < args.length-1; i+=3) if ((img = MM_findObj(args[i])) != null) {
      if (!img.MM_up) img.MM_up = img.src;
      img.src = (img.MM_dn && args[i+2]) ? args[i+2] : ((args[i+1])? args[i+1] : img.MM_up);
      nbArr[nbArr.length] = img;
    }
  } else if (event == "out" ) {
    for (i=0; i < document.MM_nbOver.length; i++) {
      img = document.MM_nbOver[i]; img.src = (img.MM_dn) ? img.MM_dn : img.MM_up; }
  } else if (event == "down") {
    nbArr = document[grpName];
    if (nbArr)
      for (i=0; i < nbArr.length; i++) { img=nbArr[i]; img.src = img.MM_up; img.MM_dn = 0; }
    document[grpName] = nbArr = new Array();
    for (i=2; i < args.length-1; i+=2) if ((img = MM_findObj(args[i])) != null) {
      if (!img.MM_up) img.MM_up = img.src;
      img.src = img.MM_dn = (args[i+1])? args[i+1] : img.MM_up;
      nbArr[nbArr.length] = img;
  } }
}
//-->
</script>
</head>

<body onLoad="MM_preloadImages('images/menu_r1_c1_f3.gif','images/menu_r1_c1_f2.gif','images/menu_r1_c2_f3.gif','images/menu_r1_c2_f2.gif','images/menu_r1_c3_f3.gif','images/menu_r1_c3_f2.gif','images/menu_r1_c4_f3.gif','images/menu_r1_c4_f2.gif','images/menu_r1_c5_f3.gif','images/menu_r1_c5_f2.gif')">
<div id="wrapper"> <br/>
  <img src="title.png" width="740" height="124"> 
  <table  align="center" border="0" cellpadding="0" cellspacing="0" width="740">
    <!-- fwtable fwsrc="menu.png" fwbase="menu.gif" fwstyle="Dreamweaver" fwdocid = "742308039" fwnested="0" -->
    <tr> 
      <td><img src="images/spacer.gif" width="148" height="1" border="0" alt=""></td>
      <td><img src="images/spacer.gif" width="148" height="1" border="0" alt=""></td>
      <td><img src="images/spacer.gif" width="148" height="1" border="0" alt=""></td>
      <td><img src="images/spacer.gif" width="148" height="1" border="0" alt=""></td>
      <td><img src="images/spacer.gif" width="148" height="1" border="0" alt=""></td>
      <td><img src="images/spacer.gif" width="1" height="1" border="0" alt=""></td>
    </tr>
    <tr> 
      <td><a href="about.htm" target="_top" onClick="MM_nbGroup('down','navbar1','menu_r1_c1','images/menu_r1_c1_f3.gif',1);" onMouseOver="MM_nbGroup('over','menu_r1_c1','images/menu_r1_c1_f2.gif','images/menu_r1_c1_f3.gif',1);" onMouseOut="MM_nbGroup('out');"><img name="menu_r1_c1" src="images/menu_r1_c1.gif" width="148" height="30" border="0" alt=""></a></td>
      <td><a href="people.htm" target="_top" onClick="MM_nbGroup('down','navbar1','menu_r1_c2','images/menu_r1_c2_f3.gif',1);" onMouseOver="MM_nbGroup('over','menu_r1_c2','images/menu_r1_c2_f2.gif','images/menu_r1_c2_f3.gif',1);" onMouseOut="MM_nbGroup('out');"><img name="menu_r1_c2" src="images/menu_r1_c2.gif" width="148" height="30" border="0" alt=""></a></td>
      <td><a href="publications.htm" target="_top" onClick="MM_nbGroup('down','navbar1','menu_r1_c3','images/menu_r1_c3_f3.gif',1);" onMouseOver="MM_nbGroup('over','menu_r1_c3','images/menu_r1_c3_f2.gif','images/menu_r1_c3_f3.gif',1);" onMouseOut="MM_nbGroup('out');"><img name="menu_r1_c3" src="images/menu_r1_c3.gif" width="148" height="30" border="0" alt=""></a></td>
      <td><a href="software.htm" target="_top" onClick="MM_nbGroup('down','navbar1','menu_r1_c4','images/menu_r1_c4_f3.gif',1);" onMouseOver="MM_nbGroup('over','menu_r1_c4','images/menu_r1_c4_f2.gif','images/menu_r1_c4_f3.gif',1);" onMouseOut="MM_nbGroup('out');"><img name="menu_r1_c4" src="images/menu_r1_c4.gif" width="148" height="30" border="0" alt=""></a></td>
      <td><a href="register.htm" target="_top" onClick="MM_nbGroup('down','navbar1','menu_r1_c5','images/menu_r1_c5_f3.gif',1);" onMouseOver="MM_nbGroup('over','menu_r1_c5','images/menu_r1_c5_f2.gif','images/menu_r1_c5_f3.gif',1);" onMouseOut="MM_nbGroup('out');"><img name="menu_r1_c5" src="images/menu_r1_c5.gif" width="148" height="30" border="0" alt=""></a></td>
      <td><img src="images/spacer.gif" width="1" height="30" border="0" alt=""></td>
    </tr>
  </table>
  <div class="box" >
  <div class="bi">
		<div class="bt"><div></div></div>
		
      <h1>Thank You</h1>
		
      <p>&nbsp;</p>
	  
	  <p> Thank You <jsp:getProperty name="rb" property="firstName"/> <jsp:getProperty name="rb" property="lastName"/>, you have successfully registered
		</p>
      
        
      <p>&nbsp;</p>
      <p>&nbsp;</p>
      <p>&nbsp;</p>
      <p align="center"><a href="index.html">Home</a> | <a href="about.htm">About</a> 
        | <a href="people.htm">People</a> | <a href="publications.htm">Publications</a> 
        | <a href="software.htm">Software</a> | <a href="register.htm">Register</a></p>
		<div></div>
		<div class="bb"><div></div></div></div>
</div>
  <div> 
    <div align="center">Copyright (c) 2005: The Trustees of Columbia University 
      in the City of New York. All Rights Reserved.</div>
  </div>

</div>


</body>
</html>