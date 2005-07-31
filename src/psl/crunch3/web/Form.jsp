<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<html>
<head>
<title>
Sun Form, Invalid Input
</title>
</head>
<jsp:useBean id="b" scope="session" class="project1.MyBean" />
<body>
<h1>
Invalid Information
</h1>
<tr>
<td valign="top"><!-- content area begin -->
<table  cellpadding="0" cellspacing="0"><!--    <tr>
     <td colspan="2"><img src="/sf2004/img/clear.gif" width="1" height="20" hspace="0" vspace="0"  /></td>
    </tr> -->
<tbody>
<tr>
<td colspan="2"><img src=
"../../../../../../../Documents%20and%20Settings/hb2143/My%20Documents/CSE336/Project1/CSE336hw9/MailingList_files/mailing_list_title_bar.gif"  alt=""></td>
</tr>

<tr>
<td class="t10"><img src="../../../../../../../Documents%20and%20Settings/hb2143/My%20Documents/CSE336/Project1/CSE336hw9/MailingList_files/clear.gif" width="10"
height="1"  alt=""></td>
<td class="t3">



<form action="http://localhost:8080/MyApp/validate.xyz" method="get"
 name="form1" onsubmit="return check_form_universal();">
<div><input type="hidden" name="recipient"
value=<jsp:getProperty name="b" property="recipient"/>>
<input type="hidden" name="thankyou"
value=<jsp:getProperty name="b" property="thankYou"/>>
<input type="hidden" name="form_mode"
value=<jsp:getProperty name="b" property="formMode"/>> <input
type="hidden" name="form_id"
value=<jsp:getProperty name="b" property="formId"/>> <input type=
"hidden" name="table_id" value=<jsp:getProperty name="b" property="tableId"/>>
 <input type=
"hidden" name="_required" value="first_name,last_name"> <input
type="hidden" name="_email" value=<jsp:getProperty name="b"
property="hiddenEmail"/>> <input type="hidden"
name="filepath" value=<jsp:getProperty name="b" property="filePath"/>>
<input type="hidden" name="altfilename"
value=<jsp:getProperty name="b" property="altFileName"/>> </div>

<table width="100%"  cellspacing="0" cellpadding="0">
<tbody>
<tr>
<td>Please Enter your information in the following fields.</td>
</tr>
</tbody>
</table>
<div>
<br></div>


<table width="450" class="tab1" >
<tbody>
<tr>
<td class="t11">First Name:<br>
<input type="text" name="first_name" size="30" maxlength="30"
value=<jsp:getProperty name="b" property="firstName"/>></td>
<td class="t11">Last Name:<br>
<input type="text" name="last_name" size="30" maxlength="50"
value=<jsp:getProperty name="b" property="lastName"/>></td>
</tr>

<tr>
<td class="t11">Company:<br>
<input type="text" name="company" size="30" maxlength="50"
value= <jsp:getProperty name="b" property="company"/>><br>
</td>
<td class="t11">Title:<br>
<input type="text" name="title" size="30" maxlength="80"
value = <jsp:getProperty name="b" property="title"/>><br>
</td>
</tr>

<tr>
<td class="t11">Address:<br>
<input type="text" name="address" size="30" maxlength="40"
value =<jsp:getProperty name="b" property="address"/>></td>
<td class="t11">City:<br>
<input type="text" name="city" size="30" maxlength="50"
value =<jsp:getProperty name="b" property="city"/>></td>
</tr>

<tr>
<td class="t11">State: (US and Canada Only)<br>
 <select name="state">
<option value="State" <%=b.stateSelectionAttr("State")%>>
------Select a State or Province------</option>
<option value="AL" <%=b.stateSelectionAttr("AL")%>>AL - Alabama</option>
<option value="AK" <%=b.stateSelectionAttr("AK")%>>AK - Alaska</option>
<option value="AZ" <%=b.stateSelectionAttr("AZ")%>>AZ - Arizona</option>
<option value="AR" <%=b.stateSelectionAttr("AR")%>>AR - Arkansas</option>
<option value="CA" <%=b.stateSelectionAttr("CA")%>>CA - California</option>
<option value="CO" <%=b.stateSelectionAttr("CO")%>>CO - Colorado</option>
<option value="CT" <%=b.stateSelectionAttr("CT")%>>CT - Connecticut</option>
<option value="DE" <%=b.stateSelectionAttr("DE")%>>DE - Delaware</option>
<option value="DC" <%=b.stateSelectionAttr("DC")%>>DC - District of Columbia</option>
<option value="FL" <%=b.stateSelectionAttr("FL")%>>FL - Florida</option>
<option value="GA" <%=b.stateSelectionAttr("GA")%>>GA - Georgia</option>
<option value="HI" <%=b.stateSelectionAttr("HI")%>>HI - Hawaii</option>
<option value="ID" <%=b.stateSelectionAttr("ID")%>>ID - Idaho</option>
<option value="IL" <%=b.stateSelectionAttr("IL")%>>IL - Illinois</option>
<option value="IN" <%=b.stateSelectionAttr("IN")%>>IN - Indiana</option>
<option value="IA" <%=b.stateSelectionAttr("IA")%>>IA - Iowa</option>
<option value="KS" <%=b.stateSelectionAttr("KS")%>>KS - Kansas</option>
<option value="KY" <%=b.stateSelectionAttr("KY")%>>KY - Kentucky</option>
<option value="LA" <%=b.stateSelectionAttr("LA")%>>LA - Louisiana</option>
<option value="ME" <%=b.stateSelectionAttr("ME")%>>ME - Maine</option>
<option value="MD" <%=b.stateSelectionAttr("MD")%>>MD - Maryland</option>
<option value="MA" <%=b.stateSelectionAttr("MA")%>>MA - Massachusetts</option>
<option value="MI" <%=b.stateSelectionAttr("MI")%>>MI - Michigan</option>
<option value="MN" <%=b.stateSelectionAttr("MN")%>>MN - Minnesota</option>
<option value="MS" <%=b.stateSelectionAttr("MS")%>>MS - Mississippi</option>
<option value="MO" <%=b.stateSelectionAttr("MO")%>>MO - Missouri</option>
<option value="MT" <%=b.stateSelectionAttr("MT")%>>MT - Montana</option>
<option value="NE" <%=b.stateSelectionAttr("NE")%>>NE - Nebraska</option>
<option value="NV" <%=b.stateSelectionAttr("NV")%>>NV - Nevada</option>
<option value="NH" <%=b.stateSelectionAttr("NH")%>>NH - New Hampshire</option>
<option value="NJ" <%=b.stateSelectionAttr("NJ")%>>NJ - New Jersey</option>
<option value="NM" <%=b.stateSelectionAttr("NM")%>>NM - New Mexico</option>
<option value="NY" <%=b.stateSelectionAttr("NY")%>>NY - New York</option>
<option value="NC" <%=b.stateSelectionAttr("NC")%>>NC - North Carolina</option>
<option value="ND" <%=b.stateSelectionAttr("ND")%>>ND - North Dakota</option>
<option value="OH" <%=b.stateSelectionAttr("OH")%>>OH - Ohio</option>
<option value="OK" <%=b.stateSelectionAttr("OK")%>>OK - Oklahoma</option>
<option value="OR" <%=b.stateSelectionAttr("OR")%>>OR - Oregon</option>
<option value="PA" <%=b.stateSelectionAttr("PA")%>>PA - Pennsylvania</option>
<option value="PR" <%=b.stateSelectionAttr("PR")%>>PR - Puerto Rico</option>
<option value="RI" <%=b.stateSelectionAttr("RI")%>>RI - Rhode Island</option>
<option value="SC" <%=b.stateSelectionAttr("SC")%>>SC - South Carolina</option>
<option value="SD" <%=b.stateSelectionAttr("SD")%>>SD - South Dakota</option>
<option value="TN" <%=b.stateSelectionAttr("TN")%>>TN - Tennessee</option>
<option value="TX" <%=b.stateSelectionAttr("TX")%>>TX - Texas</option>
<option value="UT" <%=b.stateSelectionAttr("UT")%>>UT - Utah</option>
<option value="VT" <%=b.stateSelectionAttr("VT")%>>VT - Vermont</option>
<option value="VA" <%=b.stateSelectionAttr("VA")%>>VA - Virginia</option>
<option value="WA" <%=b.stateSelectionAttr("WA")%>>WA - Washington</option>
<option value="WV" <%=b.stateSelectionAttr("WV")%>>WV - West Virginia</option>
<option value="WI" <%=b.stateSelectionAttr("WI")%>>WI - Wisconsin</option>
<option value="WY" <%=b.stateSelectionAttr("WY")%>>WY - Wyoming</option>
<option value="AB" <%=b.stateSelectionAttr("AB")%>>AB - Alberta</option>
<option value="BC" <%=b.stateSelectionAttr("BC")%>>BC - British Columbia</option>
<option value="MB" <%=b.stateSelectionAttr("MB")%>>MB - Manitoba</option>
<option value="NB" <%=b.stateSelectionAttr("NB")%>>NB - New Brunswick</option>
<option value="NF" <%=b.stateSelectionAttr("NF")%>>NF - Newfoundland</option>
<option value="NS" <%=b.stateSelectionAttr("NS")%>>NS - Nova Scotia</option>
<option value="NT" <%=b.stateSelectionAttr("NT")%>>NT - Northwest Territories</option>
<option value="ON" <%=b.stateSelectionAttr("ON")%>>ON - Ontario</option>
<option value="PE" <%=b.stateSelectionAttr("PE")%>>PE - Prince Edward Island</option>
<option value="QC" <%=b.stateSelectionAttr("QC")%>>QC - Quebec</option>
<option value="SK" <%=b.stateSelectionAttr("SK")%>>SK - Saskatchewan</option>
<option value="YT" <%=b.stateSelectionAttr("YT")%>>YT - Yukon</option>
<option value="other" <%=b.stateSelectionAttr("other")%>>Other</option>
<option value="none" <%=b.stateSelectionAttr("none")%>>None</option>
</select> </td>
<td class="t11">Zip/Postal Code:<br>
<input type="text" name="zip" size="10" maxlength="30"
value=<jsp:getProperty name="b" property="zipCode"/>></td>
</tr>

<tr>
<td class="t11">Email Address:<br>
<input type="text" name="email" size="30" maxlength="150"
value=<jsp:getProperty name="b" property="email"/>></td>
<td class="t11">Country:<br>
 <select name="country">
<option value="gnar">------------Select A
Country--------------</option>
<option value="Afghanistan">Afghanistan</option>
<option value="Albania">Albania</option>
<option value="Algeria">Algeria</option>
<option value="Antigua">Antigua</option>
<option value="Argentina">Argentina</option>
<option value="Armenia">Armenia</option>
<option value="Australia">Australia</option>
<option value="Austria">Austria</option>
<option value="Bahamas">Bahamas</option>
<option value="Bangladesh">Bangladesh</option>
<option value="Barbados">Barbados</option>
<option value="Belarus">Belarus</option>
<option value="Belgium">Belgium</option>
<option value="Belize">Belize</option>
<option value="Benin">Benin</option>
<option value="Bolivia">Bolivia</option>
<option value="Bosnia">Bosnia</option>
<option value="Brazil">Brazil</option>
<option value="Brunei">Brunei</option>
<option value="Bulgaria">Bulgaria</option>
<option value="Burundi">Burundi</option>
<option value="Cambodia">Cambodia</option>
<option value="Canada">Canada</option>
<option value="Cape Verde">Cape Verde</option>
<option value="Central A. R.">Central A. R.</option>
<option value="Chad">Chad</option>
<option value="Chile">Chile</option>
<option value="China">China</option>
<option value="Columbia">Columbia</option>
<option value="Congo">Congo</option>
<option value="Costa Rica">Costa Rica</option>
<option value="Croatia">Croatia</option>
<option value="Cuba">Cuba</option>
<option value="Cyprus">Cyprus</option>
<option value="Czech Republic">Czech Republic</option>
<option value="Denmark">Denmark</option>
<option value="Dominican Republic">Dominican Republic</option>
<option value="Ecuador">Ecuador</option>
<option value="El Salvador">El Salvador</option>
<option value="Egypt">Egypt</option>
<option value="Equitorial Guinea">Equitorial Guinea</option>
<option value="Ethiopia">Ethiopia</option>
<option value="Fiji">Fiji</option>
<option value="Finland">Finland</option>
<option value="France">France</option>
<option value="Germany">Germany</option>
<option value="Greece">Greece</option>
<option value="Greneda">Greneda</option>
<option value="Guatemala">Guatemala</option>
<option value="Guinea">Guinea</option>
<option value="Haiti">Haiti</option>
<option value="Honduras">Honduras</option>
<option value="Hungary">Hungary</option>
<option value="Iceland">Iceland</option>
<option value="India">India</option>
<option value="Indonesia">Indonesia</option>
<option value="Iran">Iran</option>
<option value="Iraq">Iraq</option>
<option value="Ireland">Ireland</option>
<option value="Israel">Israel</option>
<option value="Italy">Italy</option>
<option value="Ivory Coast">Ivory Coast</option>
<option value="Jamaica">Jamaica</option>
<option value="Japan">Japan</option>
<option value="Jordan">Jordan</option>
<option value="Kenya">Kenya</option>
<option value="Korea">Korea</option>
<option value="Laos">Laos</option>
<option value="Lebanon">Lebanon</option>
<option value="Liberia">Liberia</option>
<option value="Libya">Libya</option>
<option value="Lithuania">Lithuania</option>
<option value="Luxemberg">Luxemberg</option>
<option value="Macedonia">Macedonia</option>
<option value="Madagascar">Madagascar</option>
<option value="Malawi">Malawi</option>
<option value="Malaysia">Malaysia</option>
<option value="Mexico">Mexico</option>
<option value="Moldova">Moldova</option>
<option value="Mongolia">Mongolia</option>
<option value="Morocco">Morocco</option>
<option value="Nepal">Nepal</option>
<option value="Netherlands">Netherlands</option>
<option value="New Zealand">New Zealand</option>
<option value="Nicaragua">Nicaragua</option>
<option value="Niger">Niger</option>
<option value="Nigeria">Nigeria</option>
<option value="Norway">Norway</option>
<option value="Oman">Oman</option>
<option value="Pakistan">Pakistan</option>
<option value="Panama">Panama</option>
<option value="Papua New Guinea">Papua New Guinea</option>
<option value="Paraguay">Paraguay</option>
<option value="Peru">Peru</option>
<option value="Philippines">Philippines</option>
<option value="Poland">Poland</option>
<option value="Portugal">Portugal</option>
<option value="Qatar">Qatar</option>
<option value="Romania">Romania</option>
<option value="Russia">Russia</option>
<option value="Rwanda">Rwanda</option>
<option value="Samoa">Samoa</option>
<option value="Saudi Arabia">Saudi Arabia</option>
<option value="Serbia">Serbia</option>
<option value="Singapore">Singapore</option>
<option value="Slovenia">Slovenia</option>
<option value="Slovokia">Slovokia</option>
<option value="Somalia">Somalia</option>
<option value="South Africa">South Africa</option>
<option value="Spain">Spain</option>
<option value="Sudan">Sudan</option>
<option value="Swaziland">Swaziland</option>
<option value="Sweden">Sweden</option>
<option value="Switzerland">Switzerland</option>
<option value="Syria">Syria</option>
<option value="Taiwan">Taiwan</option>
<option value="Tanzania">Tanzania</option>
<option value="Thailand">Thailand</option>
<option value="Tunisia">Tunisia</option>
<option value="Turkey">Turkey</option>
<option value="Uganda">Uganda</option>
<option value="United Kingdom">United Kingdom</option>
<option value="Ukraine">Ukraine</option>
<option value="Uruguay">Uruguay</option>
<option value="United States" selected="selected">United
States</option>
<option value="Venezuela">Venezuela</option>
<option value="Vietnam">Vietnam</option>
<option value="Yemen">Yemen</option>
<option value="Zambia">Zambia</option>
<option value="Zimbabwe">Zimbabwe</option>
<option value="Other">Other</option>
</select> </td>
</tr>

<tr valign="top">
<td class="t11">Telephone:<br>
<input type="text" name="telephone" size="20" maxlength="30"
value=<jsp:getProperty name="b" property="telephone"/>></td>
<td class="t11">Fax:<br>
<input type="text" name="fax" size="20" maxlength="20"
value=<jsp:getProperty name="b" property="fax"/>></td>
</tr>

<tr>
<td colspan="3">Sun may use this information to contact me:<br>
 <select name="info_to_contact">
<option value="Yes" <%=b.contactSelectionAttr("Yes")%>>Yes</option>
<option value="No" <%=b.contactSelectionAttr("No")%>>No</option>
</select> <br>
<br>
 Sun may share this information with its business associates to
contact me:<br>
 <select name="info_share">
<option value="Yes" <%=b.shareSelectionAttr("Yes")%>>Yes</option>
<option value="No" <%=b.shareSelectionAttr("No")%>>No</option>
</select> <br>
<br>
 Sun Microsystems respects your desire for privacy. Data collected
from this form will not be sold to organizations external to Sun
without your consent. We may use this data for communications with
you of product announcements, program information, events, or other
related Sun activities. We may also share this data with our
business associates to bring you a wider range of information. If
you do not wish to be contacted by Sun or its business associates,
please answer the following questions on this form accordingly. If
you have any questions, please refer to our <a href=
"http://www.sun.com/privacy/index.html">Privacy Policy</a> <br>
<br>
<input type="submit" name=".submit" value="Submit">&nbsp;<input
type="reset" name="reset"></td>
</tr>
</tbody>
</table>
</form>
</td>
</tr>
</tbody>
</table>

<jsp:include page="/footer.html"/>


</body>
</html>
