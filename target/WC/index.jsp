<%@ page language="java" contentType="text/html; charset=utf8"
         pageEncoding="utf8"%>
<html>
 <head>
    <title>Страница точных расчетов</title>
     <style>
         .warningInput {
             border-color: #ff0000; /* Цвет фона */
             color: #ff3f04; /* Цвет текста */
         }
     </style>
  <script type="text/javascript">
      var memoryN1=''; //хранит значение переменной 'number1'
      var memoryN2=''; //хранит значение переменной 'number2'
      var isLastSelectSqRoot=false; //была ли последней выбрана операция корень?
      var exchange=false; // проводился ли обмен значений при выборе операции корень?
    function stringConcat() {
        var args = Array.prototype.slice.call(arguments);
        return args.join("");
    }
    function startAjax(url){
        var request;
        if(window.XMLHttpRequest){
            request = new XMLHttpRequest();
        } else if(window.ActiveXObject){
            request = new ActiveXObject("Microsoft.XMLHTTP");
        } else {
            return;
        }

        request.onreadystatechange = function(){
            if (request.readyState==4) {
                    if(request.status==200){
                        var boolean_exeption = request.responseXML.getElementsByTagName("boolean_exeption")[0].firstChild.data;
                        if(boolean_exeption==="true"){
                               printExeption(request.responseXML);
                        }
                        else {
                            clearExeption();
                            memoryN1='';
                            var resultNumber = request
                                    .responseXML
                                    .getElementsByTagName("resultNumber")[0]
                                    .firstChild.data;
                            print_result(resultNumber);
                        }


                    }else if(request.status==404){
                        alert("Ошибка: запрашиваемый скрипт не найден!");
                    }
                    else alert("Ошибка: сервер вернул статус: "+ request.status);
            }
        }
        request.open("POST",url, true);
        request.setRequestHeader("Content-type","application/x-www-form-urlencoded");
        request.send(prepareRequest());
    }

    function prepareRequest(){
        var temp = stringConcat( "operation=", getSelected("operation"),
                "&number1=",getIdValue("number1"),
                "&number2=", getIdValue("number2"));
        return  temp;

    }
    function printExeption(responseXml){
        clearExeption();
        var exeptions = responseXml.getElementsByTagName("exeptionMessage");
        for (var i in exeptions){
            var info_exeption=stringConcat("<p>", exeptions[i].childNodes[0].data, "</p>");
            document.getElementById("exeption").innerHTML += info_exeption;
        }
    }
    function clearExeption(){
        document.getElementById("exeption").innerHTML = "";
    }
    function disable(arrayId, bool) {
        if (bool==true || bool==false){
            for(var id in arrayId){
                document.getElementById(arrayId[id]).disabled=bool;
            }
        }
        else {
            alert("Oшибка вызова функции disable()") ;
        }
    }
    function change_value(fromId, toId){
        var fromIdValue=document.getElementById(fromId).value;
        document.getElementById(toId).value=fromIdValue;
    }
    function getIdValue(id) {
         return document.getElementById(id).value;
    }
    function getSelected(id){
         var selectIndex = document.getElementById(id).selectedIndex;
         return document.getElementById(id).options[selectIndex].value;
    }
    function setIdValue(id, value){
        document.getElementById(id).value=value;
    }
    function setResult(resultNumber){
        setIdValue('r3', resultNumber);
        if(getIdValue('operation')==="sqroot"){setIdValue('number2',resultNumber);}
        else{
            setIdValue('number1',resultNumber);
            setIdValue('number2','');
        }
    }
    function exchangeSelect(){
        var selectIndex = document.getElementById("operation").selectedIndex;
        document.getElementById("operation_result").options[selectIndex].selected=true;
    }
    function print_result(resultNumber){
        document.getElementById("result").style.display='inline';
        var arrayId= new Array('r1', 'r2', 'r3', 'operation_result');
        disable(arrayId, false);
        exchangeSelect();
        change_value('number1', 'r1');
        change_value('number2', 'r2');
        setResult(resultNumber);
        disable(arrayId, true);
    }
    function printWarning(id, message){
        changeClassElement(id, 'warningInput');
        document.getElementById('warning').innerHTML+=message;
    }
    function changeClassElement(id, newClass){
        var source = document.getElementById(id);
        source.className = newClass;
    }
    function clearAllWarning(){
        clearWarning('number1');
        clearWarning('number2');
    }
    function clearWarning(id){
        changeClassElement(id, 'default');
        changeWarningMessage();
    }
    function changeWarningMessage(){
        var n1 = document.getElementById('number1');
        var n2 = document.getElementById('number2');
        if (n1.className!=='warningInput' && n2.className!=='warningInput'){
            document.getElementById('warning').innerHTML="";
        }
    }
    function checkData(){
        clearAllWarning();
        var n2 = getIdValue('number2');
        var n1 = getIdValue('number1');
        //Проверка не заканчивается ли строка символом 'E'
        e=function(id, value){
            if(/E$/i.test(value)){
                printWarning(id, "Число не может заканчиваться 'Е'.");
                return true;
            }
        }
        if(e('number1',n1)) return;//проверяем число 1
        if(e('number2',n2)) return; //проверяем число 2
        var operation = getIdValue("operation");
        if(n1===''&&operation!='sqroot'){
            printWarning('number1', 'Заполните пожалуйста текстовое поле');
        }else if(n2===''){
            printWarning('number2', 'Для проведения расчетов нужно заполнить текстовое поле');
        }else startAjax('/webcalc');

    }
    function clearBadSymbol(value){
        var n = value.replace(/[^0-9.e-]/gi,'').toUpperCase();
        var temp=n.indexOf('-');
        if(n.indexOf('-')==0){
            n=stringConcat('-', n.replace(/-/g,''));
        }else {
            n = n.replace('-','');
        }
        var e = n.indexOf('E');
        var i = n.indexOf('.');
        if(i>e&&e!=-1){
            n=n.replace(/./g,'');
        }
        if(e>0){
            n=stringConcat(n.substr(0,e+1),
                    n.substr(e+1).replace(/E/g, ''));
        }else {
            n= n.replace(/E/g,"");
        }
        if(i!=-1){
            n=stringConcat(n.substr(0,i+1),
                    n.substr(i+1).replace(/\./g, ''));
        }
        return n;
    }
    function sqRootAction(){
        if(getIdValue('operation')==='sqroot'){
            if((getIdValue('number2')==='')&&getIdValue('number1')!=''){
                change_value('number1', 'number2');
                exchange =true;
            }
            memoryN1 = getIdValue('number1');
            setIdValue('number1', '') ;
            disable(['number1'], true );
            isLastSelectSqRoot=true;
        }
        else if(isLastSelectSqRoot){
            disable(['number1'], false);
            setIdValue('number1', memoryN1);
            memoryN1='';
            if(exchange && getIdValue('number2')===memoryN1){
                setIdValue(['number2'], '');
            }
        }
        else {disable(['number1'], false)}
    }
      function enter(d, e)
      {
          if(d != "" && e.keyCode == 13) checkData();
      }
</script>

</head>
<body>
<div id="result" style="display: none" disabled>
    <form>
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
    <form>
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
               onkeypress="enter(this.value, event)"/>
        <input type="button" name="button" onclick="checkData();" value="Расчитать" >
    </form>
</div>
<br/>

</body>
</html>