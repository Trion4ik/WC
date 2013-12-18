<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<html>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<head>
    <title>Страница точных расчетов</title>

    <link rel="stylesheet" type="text/css" href="css/site.css">
    <!--[if lte IE 10]><link href="css/ie-style.css" rel="stylesheet" type="text/css" /<![endif]-->

    <script type="text/javascript" src="js/clientControl.js"></script>
</head>
<body>

<div class=block>
    <h1>ВебКалькулятор</h1>
    <div id=rule>
       <p>Приветсвуем, <strong>дорогой гость</strong>, на странице Вебкалькулятора.
           <strong>Наш сервис </strong> предоставляет возможность проводить точные расчеты над числами.
            Для этого вам достаточно ввести числа в форме представленной ниже и выбрать необходимую операцию.
        </p>
        Для ввода чисел вы можете использовать следующий набор символов: <strong>'-'</strong>(минус),
        <strong>'.'</strong>(разделитель между целой и дробной частью), <strong>'E'</strong>
        (степень по основе 10), ну и конечно же <strong>0,1,2,3,4,5,6,7,8,9</strong>
        </p>
    </div>
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
                   onkeypress="enter(this.value, event)"/>
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

            <input type="button" name="button" class="submit" onclick="checkData();" value="Расчитать">
        </form>

    </div>
    <br/>
</div>
</body>
</html>