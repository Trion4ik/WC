/*Этот javascript код предназначен для обработки входных данных полученных от пользователя
* author Maksimov Roman
* version 2.1.3
* */
  /*Блок инициализации */
var memoryN1=''; //хранит значение переменной 'number1'
var memoryN2=''; //хранит значение переменной 'number2'
var isLastSelectSqRoot=false; //была ли последней выбрана операция корень?
var exchange=false; // проводился ли обмен значений при выборе операции корень?
/*Функция быстрого соединения строковых значений в одну строку*/
function stringConcat() {
    var args = Array.prototype.slice.call(arguments);
    return args.join("");
}
/*Функция отвечающая за отправку на сервер запроса клиента без перезагрузки страницы
* параметр url - ccылка на сервер*/
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
/*Функция отвечающая за подготовку строки запроса для отправки на сервер*/
function prepareRequest(){
    var temp = stringConcat( "operation=", getSelected("operation"),
        "&number1=",getIdValue("number1"),
        "&number2=", getIdValue("number2"));
    return  temp;

}
/*Функция отвечающая за вывод исключений полученных с сервера*/
function printExeption(responseXml){
    clearExeption();
    var exeptions = responseXml.getElementsByTagName("exeptionMessage");
    for (var i in exeptions){
        var info_exeption=stringConcat("<p>", exeptions[i].childNodes[0].data, "</p>");
        document.getElementById("exeption").innerHTML += info_exeption;
    }
}
/*Функция отвечающая за удаления строк исключений полученных с сервера*/
function clearExeption(){
    document.getElementById("exeption").innerHTML = "";
}
/*Функция блокировки/разблокировки елементов формы
* Параметр arrayId - массив id eлементов
* Параметр bool булевое значение - заблокировать?(true-да, false-нет)*/
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
/*Функция меняет значения елементов формы с id елемента fromId в елемент toId*/
function change_value(fromId, toId){
    var fromIdValue=document.getElementById(fromId).value;
    document.getElementById(toId).value=fromIdValue;
}
/*Функция возвращает значение елемента формы по id*/
function getIdValue(id) {
    return document.getElementById(id).value;
}
/*Функция возвращает значение выбранного елемента формы select по id*/
function getSelected(id){
    var selectIndex = document.getElementById(id).selectedIndex;
    return document.getElementById(id).options[selectIndex].value;
}
/*Функция задает елементу формы значение*/
function setIdValue(id, value){
    document.getElementById(id).value=value;
}
/*Функция выводит на страницу результат вычеслений - число*/
function setResult(resultNumber){
    setIdValue('r3', resultNumber);
    if(getIdValue('operation')==="sqroot"){setIdValue('number2',resultNumber);}
    else{
        setIdValue('number1',resultNumber);
        setIdValue('number2','');
    }
}
/*Функция меняет значение выбраной пользователем операции с формы calculator на форму history*/
function exchangeSelect(){
    var selectIndex = document.getElementById("operation").selectedIndex;
    document.getElementById("operation_result").options[selectIndex].selected=true;
}
/*Функция выводит результаты в формы history и calculator*/
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
/*Функция выводит сообщения предупреждений*/
function printWarning(id, message){
    changeClassElement(id, 'warningInput');
    document.getElementById('warning').innerHTML+=message;
}
/*Функция меняет стиль отображения меняя его класс СSS*/
function changeClassElement(id, newClass){
    var source = document.getElementById(id);
    source.className = newClass;
}
/*Функция очищает все предупреждения*/
function clearAllWarning(){
    clearWarning('number1');
    clearWarning('number2');
}
/*Функция очищает предупреждение связанное с текстовым полем id*/
function clearWarning(id){
    changeClassElement(id, 'default');
    changeWarningMessage();
}
/*Функция очищает все предупреждения только если текстовые поля не отмечены специальным
* классом CSS warningInput для предупреждений*/
function changeWarningMessage(){
    var n1 = document.getElementById('number1');
    var n2 = document.getElementById('number2');
    if (n1.className!=='warningInput' && n2.className!==''){
        document.getElementById('warning').innerHTML="";
    }
}
/*Функция проверки введенных данных пользователя перед отправкой на сервер*/
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
    }
     //Если данные коректны отправляет их на сервер
    else startAjax('webcalc');

}
/*Функция очищает все недопустимые символы из текстовых полей калькулятора*/
function clearBadSymbol(value){
    //Удаляем все символы кроме цифр, ".", "Е" и "-"
    var n = value.replace(/[^0-9.e-]/gi,'').toUpperCase();
    //"-" может быть только первым символом и встречаться сразу после "Е"
    var temp=n.indexOf('-');
    if(n.indexOf('-')==0){
        n=stringConcat('-', n.replace(/-/g,''));
    }else {
        n = n.replace('-','');
    }
    //часть кода отвечающая за то чтоб разделитель "." не встечался после "Е"
    var e = n.indexOf('E');
    var i = n.indexOf('.');
    if(i>e&&e!=-1){
        n=n.replace(/./g,'');
    }
    //часть кода отвечающая за коректное размещение "Е"
    if(e>0){
        n=stringConcat(n.substr(0,e+1),
            n.substr(e+1).replace(/E/g, ''));
    }else {
        n= n.replace(/E/g,"");
    }
    //часть кода отвечающая за коректное размещение "."
    if(i!=-1){
        n=stringConcat(n.substr(0,i+1),
            n.substr(i+1).replace(/\./g, ''));
    }
    return n;
}
/*Функция связанная с событием выбора операции пользователем
 в случае выбора квадратного корня форма изменяется*/
function sqRootAction(){
    //проверяет не выбрана ли операция квадратного корня
    if(getIdValue('operation')==='sqroot'){
        if((getIdValue('number2')==='')&&getIdValue('number1')!=''){
            change_value('number1', 'number2');
            exchange =true;
        }
        memoryN1 = getIdValue('number1'); //запоминаем значение введенное пользователем
        setIdValue('number1', '') ;
        disable(['number1'], true );
        isLastSelectSqRoot=true; //запоминаем что последняя операция была квадратный корень
    }
    //если предпоследняя операция квадратный корень
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
/*Функция перехватывает событие нажатия клавиши "Enter"*/
function enter(d, e)
{
    if(d != "" && e.keyCode == 13) checkData();
}