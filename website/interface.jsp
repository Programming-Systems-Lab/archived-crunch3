<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Crunch</title>
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
	   <h1>Crunch Settings</h1>
      <form action="" method="get">
        
		<table width="90%" border="0">
          <th bgcolor="#AFE4E4"> <div align="center">CSS Settings</div></th>
          <th bgcolor="#AFE4E4">Ads and Scripts</th>
          <tr> 
            <td  class="settd"> <div align="left"> 
                <input name="IgnoreAds" type="checkbox" value="Ignore Advertisements">
                Ignore External Stylesheets</div></td>
            <td class="settd"> <div align="left"> 
                <input name="IgnoreAds" type="checkbox" value="Ignore Advertisements">
                Ignore All Advertisements</div></td>
          </tr>
          <tr> 
            <td class="settd"> <div align="left"> 
                <input name="IgnoreAds" type="checkbox" value="Ignore Advertisements">
                Ignore Styles</div></td>
            <td class="settd"> <div align="left"> 
                <input name="IgnoreAds" type="checkbox" value="Ignore Advertisements">
                Ignore Scripts</div></td>
          </tr>
          <tr> 
            <td class="settd"> <div align="left"> 
                <input name="IgnoreAds" type="checkbox" value="Ignore Advertisements">
                Ignore Style Attribute in ALL tags</div></td>
            <td class="settd"> <div align="left"> 
                <input name="IgnoreAds" type="checkbox" value="Ignore Advertisements">
                Ignore &lt;NOSCRIPT&gt; tag</div></td>
          </tr>
          <tr> 
            <td class="settd"> <div align="left"> 
                <input name="IgnoreAds" type="checkbox" value="Ignore Advertisements">
                Ignore Style Attribute in &lt;DIV&gt; tag</div></td>
            <td class="settd"> <div align="left"></div></td>
          </tr>
        </table>
		
		<table width="90%" border="0">
          <th bgcolor="#AFE4E4"> <div align="center">Images</div></th>
          <th bgcolor="#AFE4E4">Ignore Tags</th>
          <tr> 
            <td  class="settd"> <div align="left"> 
                <input name="IgnoreAds" type="checkbox" value="Ignore Advertisements">
                Ignore Non-Link Images</div></td>
            <td class="settd"> <div align="left"> 
                <input name="IgnoreAds" type="checkbox" value="Ignore Advertisements">
                Ignore &lt;INPUT&gt; tags</div></td>
          </tr>
          <tr> 
            <td class="settd"> <div align="left"> 
                <input name="IgnoreAds" type="checkbox" value="Ignore Advertisements">
                Display ALT Text</div></td>
            <td class="settd"> <div align="left"> 
                <input name="IgnoreAds" type="checkbox" value="Ignore Advertisements">
                Ignore &lt;SELECT&gt; tags</div></td>
          </tr>
          <tr> 
            <td class="settd"> <div align="left"> 
                <input name="IgnoreAds" type="checkbox" value="Ignore Advertisements">
                Ignore Image Links</div></td>
            <td class="settd"> <div align="left"> 
                <input name="IgnoreAds" type="checkbox" value="Ignore Advertisements">
                Ignore &lt;IFRAME&gt; tags</div></td>
          </tr>
          <tr> 
            <td class="settd"> <div align="left"> 
                <input name="IgnoreAds" type="checkbox" value="Ignore Advertisements">
                Display ALT Links</div></td>
            <td class="settd"> <div align="left"> 
                <input name="IgnoreAds2" type="checkbox" value="Ignore Advertisements">
                Ignore &lt;EMBED&gt; tags</div></td>
          </tr>
          <tr>
            <td class="settd">&nbsp;</td>
            <td class="settd"><input name="IgnoreAds3" type="checkbox" value="Ignore Advertisements">
              Ignore &lt;BUTTON&gt; tags</td>
          </tr>
          <tr>
            <td class="settd">&nbsp;</td>
            <td class="settd"><input name="IgnoreAds4" type="checkbox" value="Ignore Advertisements">
              Ignore &lt;META&gt; tags</td>
          </tr>
        </table>
			
        <table width="90%" border="0">
          <th bgcolor="#AFE4E4"> <div align="center">Lists of Links</div></th>
          <th bgcolor="#AFE4E4">Empty Tables</th>
          <tr> 
            <td  class="settd"> <div align="left"> 
                <input name="IgnoreAds" type="checkbox" value="Ignore Advertisements">
                Ignore Ignore Link Lists</div></td>
            <td class="settd"> <div align="left"> 
                <input name="IgnoreAds" type="checkbox" value="Ignore Advertisements" checked>
                Remove Empty Tables</div></td>
          </tr>
          <tr> 
            <td class="settd"> <div align="left"> 
                <input name="IgnoreAds" type="checkbox" value="Ignore Advertisements">
                Text Links</div></td>
            <td class="settd"> <div align="center"><strong><span align="left"> 
                Tags to Consider as Substance</span></strong></div></td>
          </tr>
          <tr> 
            <td class="settd"> <div align="left"> 
                <input name="IgnoreAds" type="checkbox" value="Ignore Advertisements">
                Image Links</div></td>
            <td class="settd"> <div align="left"> 
                <input name="IgnoreAds" type="checkbox" value="Ignore Advertisements" checked>
                &lt;IMG&gt; 
                <input name="IgnoreAds5" type="checkbox" value="Ignore Advertisements" checked>
                &lt;A&gt;</div></td>
          </tr>
          <tr> 
            <td class="settd"> <div align="left"> 
                <input name="IgnoreAds" type="checkbox" value="Ignore Advertisements">
                Ignore Only Text and Links</div></td>
            <td class="settd"> <div align="left"> 
                <input name="IgnoreAds2" type="checkbox" value="Ignore Advertisements" checked>
                &lt;INPUT&gt; 
                <input name="IgnoreAds22" type="checkbox" value="Ignore Advertisements" checked>
                &lt;SELECT&gt;</div></td>
          </tr>
          <tr> 
            <td class="settd">Link/Text removal ratio 
              <input name="textfield" type="text" value="0.25"></td>
            <td class="settd"><input name="IgnoreAds23" type="checkbox" value="Ignore Advertisements" checked> 
              &lt;TEXTAREA&gt; <input name="IgnoreAds222" type="checkbox" value="Ignore Advertisements" checked> 
              &lt;BUTTON&gt;</td>
          </tr>
          <tr>
            <td class="settd">&nbsp;</td>
            <td class="settd"><input name="IgnoreAds24" type="checkbox" value="Ignore Advertisements" checked> 
              &lt;FORM&gt; <input name="IgnoreAds223" type="checkbox" value="Ignore Advertisements" checked> 
              &lt;IFRAME&gt;</td>
          </tr>
          <tr> 
            <td class="settd">&nbsp;</td>
            <td class="settd">Minimum Text Length 
              <input name="textfield2" type="text" value="12"></td>
          </tr>
        </table>
		
		<table width="90%" border="0">
          <th bgcolor="#AFE4E4"> <div align="center">Output Format</div></th>
          <tr> 
            <td  class="settd"> <div align="left"> 
                <input name="radiobutton" type="radio" value="radiobutton" checked class="output">
                HTML</div></td>
            <td class="settd">&nbsp;</td>
          </tr>
          <tr> 
            <td class="settd"> <div align="left"> 
                <input type="radio" name="radiobutton" value="radiobutton" class="output">
                Text </div></td>
            <td class="settd">&nbsp;</td>
          </tr>
          <tr> 
            <td class="settd"> <div align="left"> 
                <input name="IgnoreAds" type="checkbox" value="Ignore Advertisements">
                Append Links to Bottom of Page</div></td>
            <td class="settd">&nbsp;</td>
          </tr>
          <tr> 
            <td class="settd"> <div align="left"> 
                <input name="IgnoreAds" type="checkbox" value="Ignore Advertisements">
                Limit the Number of Line Breaks</div></td>
            <td class="settd">&nbsp;</td>
          </tr>
          <tr> 
            <td class="settd">Maximum Number of Line Breaks 
              <input name="textfield3" type="text" value="2"></td>
            <td class="settd"><div align="right"> 
                <input type="submit" name="Submit" value="Submit">
              </div></td>
          </tr>
        </table>
		
        <p>&nbsp;</p>
		
		
		
       
        
      </form>
      
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