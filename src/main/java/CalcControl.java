import javax.servlet.http.HttpServletRequest;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Этот класс отвечает за проверку входных данных и проведение расчетов
 * Методы класса статичестки поэтому не обязательно создавать объект класса для
 * использования его методов.
 *
 * @author Максімов Роман
 * @version 1.0.1
 * **/
public class CalcControl {
   /** блок инициализации **/
    private final static Logger log= Logger.getLogger(CalcControl.class.getName());
    private static String [] operations = {"addition", "deduction", "multiplication", "division", "sqroot" };
   /** НАЧАЛО блока public методов **/

    /** Метод предназначен для обработки входных данных от пользователя и возврата результа
     * расчетов.
     * @param request - запрос полученный от пользователя одним из методов HTTP.
     *                В запросе предполагается найти параментры number1, number2, и operation.
     * @return result - объэкт для возврата пользователю, в котором хранится resultNumber - результат
     *              вычислений, boolean_exept - переменная указывающая на присутвие ошибок в результате
     *              вычислений, exeption - список всех ошибок.
     * Метод в своем теле отлавливает NumberFormatException. Данная ошибка может сгенерироваться
     * при парсинге чисел полученных от пользователя.
     * **/
     public static Result getResult(HttpServletRequest request) {
        log.info("START CalcControl.getResult()");
         /** инициализация **/
        double number1=0, number2=0;
        Result result=new Result();

         /**парсинг входных данных из запроса пользователя**/
        String operation = request.getParameter("operation");

        if (!checkOperation(result, operation)) return result; //отправка результата если операция не корректна
        try{
            if(!operation.equals("sqroot")){
                number1 = Double.parseDouble(request.getParameter("number1"));
            }
            number2 = Double.parseDouble(request.getParameter("number2"));
            double resultNumber = calculate(result, number1, number2, operation);
            log.fine(number1+operation+number2+"="+resultNumber);
            result.setNumber(resultNumber);
        }
        catch(NumberFormatException nfe){
            result.addExeption(Exept.E7);
            StringBuilder sb = new StringBuilder();
            log.log(Level.WARNING,
                    sb.append("Ceрвер не смог коректно обработать запрос клиента. Проблема формата входных данных. ")
                    .append("Число 1:")
                    .append(number1)
                    .append("Число 2:")
                    .append(number2)
                    .toString(),
                    nfe);
        }
        finally{
            log.info("END CalcControl.getResult(). Операция: "
                    .concat(operation));

             return result;
        }
    }
    /** КОНЕЦ блока public методов **/

    /** Метод предназначен для проверки коректности параметра operation полученного от пользователя*/
    public static boolean checkOperation(Result result, String operation){
        for(String s: operations){
            if (s.equals(operation)) return true;
        }
        result.addExeption(Exept.E14);
        return false;
    }
    /** Метод предназначен для обработки вычеслений и возврата результата в виде числа.
     *  В результате вычислений могут генерироваться исключения из набора enum Exept.
     *  При генерации исключений возвращается число 0;
     *  @param  r - объект возвращаемый пользователю, в который можно сохранить исключения
     *  @param number1 - первое число полученное от пользователя
     *  @param number2 - второе чило полученное от пользователя
     *  @param operation - строка, в которой указывается операция которую нужно осуществить
     *                   над числами number1 и number2
     *  @return - числовой результат вычеслений
     *
     **/
    private static double calculate (Result r, double number1, double number2, String operation){
        /**алгоритм проверки чисел работает только с положительными числами
        * создаем числа по модулю**/
        double n1=(number1>0)?number1:-number1;
         double n2=(number2>0)?number2:-number2;
        if (!checkNumbers(r, n1, n2)) return 0; //проверяем не выходят ли числа по модулю за допустимые пределы
        /**если операция умножение**/
         if(operation.equals("multiplication")){
                 if (checkMultiplication(r, n1, n2)){
                    return number1*number2;
                }
            }
         /**если операция деление**/
         else if(operation.equals("division")){
                 if (checkDivision(r, n1, n2)){
                    return number1/number2;
                 }
            }
         /**если операция сложения**/
         else if(operation.equals("addition")){
                     return number1+number2;
            }
         /**если операция вычитания**/
         else if(operation.equals("deduction")){
                    return number1-number2;
            }
         /**если операция квадратный корень**/
         else if(operation.equals("sqroot")){
                if (checkSqRoot(r, number2)){
                    return Math.sqrt(number2);
                }
            } else{
                r.addExeption(Exept.E3);
            }
         return 0;
     }
    /**Метод проверки чисел на возможность проведения операции умножения
     * Может добалять исключения к объекту Result из набора enum Exept
     **/
    private static boolean checkMultiplication(Result r, double n1, double n2){

         if ((Math.log10(n1)+Math.log10(n2))>303){
             r.addExeption(Exept.E4);
             return false;
         }
         else if(Math.log10(n1)+Math.log10(n2)<-303){
             r.addExeption(Exept.E11);
             return false;

         }
             return true;
    }
    /**Метод проверки чисел на возможность проведения операции деления
     * Может добалять исключения к объекту Result из набора enum Exept
     **/
    private static boolean checkDivision(Result r, double n1, double n2){
        boolean b=true;
        if(n2==0){
            r.addExeption(Exept.E1);
            b=false;
         }
        else if ((Math.log10(n1)-Math.log10(n2))>303){
            r.addExeption(Exept.E9);
            b=false;
        } else if(Math.log10(n1)-Math.log10(n2)<-303){
            r.addExeption(Exept.E10);
            b=false;
        }
        return b;
    }
    /**Метод проверки чисел на возможность проведения операций сложения и вычетания
     * Может добалять исключения к объекту Result из набора enum Exept
     **/
    private static boolean checkNumbers(Result r, double n1, double n2){
        boolean b=true;
        if(n1>Double.MAX_VALUE/2){
            r.addExeption(Exept.E12);
            b=false;
        }  else if(n1<Double.MIN_VALUE/2){
            r.addExeption(Exept.E5);
            b=false;
        }
         if(n2>Double.MAX_VALUE/2){
            r.addExeption(Exept.E13);
             b=false;
         } else if(n2<Double.MIN_VALUE/2) {
            r.addExeption(Exept.E6);
            b=false;
        }
        return b;
    }
    /**Метод проверки чисел на возможность проведения взятия квадратного корня из числа
     * Может добалять исключения к объекту Result из набора enum Exept
     **/
    private static boolean checkSqRoot(Result r, double n2){
        if(n2<0){
            r.addExeption(Exept.E2);
            return false;
        }
        return true;
    }

}


