<%@ page language="java" contentType="text/html; charset=utf8"
         pageEncoding="utf8"%>
<html>
<meta content="text/html; charset=UTF-8"/>
<head>
<title>Страница точных расчетов</title>
<link rel="stylesheet" type="text/css" href="css/site.css">

<script type="text/javascript" src="js/clientControl.js"></script>
</head>
<body >
<div class=block>
<div id="result" style="display: none" disabled>
    <form id="history">

        <input id="r1" name="num1" type="text" size="25" disabled/>
        <select id="operation_result" disabled>
            <option>+</option>
            <option>-</option>
            <option>*</option>
            <option>/</option>
            <option>&#8730;</option>
        </select>
        <input type="text" id="r2" name="num2" size="25" disabled/> =
        <input type="text" id="r3" name="resultNumber" size="25" disabled/>
    </form>
 </div>
<div id="exeption" style="color: #ff0000"></div>
<div id="warning" style="color: #c71585"></div>
<div id="calc">
    <form id="calculator">

        <input id="number1" name="numb1" type="text" size="25"
               onchange="var n=this.value; this.value=clearBadSymbol(n);"
               onkeyup="var n=this.value; this.value=clearBadSymbol(n);"
               onkeypress="enter(this.value, event)" />
        <select id="operation" onchange="sqRootAction()">
            <option value="addition">+</option>
            <option value="deduction">-</option>
            <option value="multiplication">*</option>
            <option value="division">/</option>
            <option value="sqroot">&#8730;</option>
        </select>
        <input type="text" id="number2" name="num2" size="25"
               onchange="var n=this.value; this.value=clearBadSymbol(n);"
               onkeyup="var n=this.value; this.value=clearBadSymbol(n);"
               onkeypress="enter(this.value, event)"/> =
        <input type="text" id="number3" name="num2" size="25" disabled/>

        <input type="button" name="button" class="button" size="25" onclick="checkData();" value="Расчитать" >
    </form>

</div>
    <br/>
</div>
</body>
</html>