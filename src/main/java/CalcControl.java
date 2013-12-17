import javax.servlet.http.HttpServletRequest;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CalcControl {
    private final static Logger log= Logger.getLogger(CalcControl.class.getName());
    public static Result getResult(HttpServletRequest request) {
        log.info("START CalcControl.getResult()");
        double number1=0, number2=0;
        Result result=new Result();
        String operation = request.getParameter("operation");
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
            log.log(Level.WARNING,
                    "Ceрвер не смог коректно обработать запрос клиента. Проблема формата входных данных "
                    .concat(Exept.E7.getMessage()),
                    nfe);
        }
        finally{
            log.info("END CalcControl.getResult(). Операция: "
                    .concat(operation));

             return result;
        }
    }
     private static double calculate (Result r, double number1, double number2, String operation){
         double n1=(number1>0)?number1:-number1;
         double n2=(number2>0)?number2:-number2;
         if(operation.equals("multiplication")){
                 if (checkMultiplication(r, n1, n2)){
                    return number1*number2;
                }
            } else if(operation.equals("division")){
                 if (checkDivision(r, n1, n2)){
                    return number1/number2;
                 }
            } else if(operation.equals("addition")){
                 if (checkAddition(r, n1, n2)){
                     return number1+number2;
                 }
            } else if(operation.equals("deduction")){
                if (checkAddition(r, n1, n2)){
                    return number1-number2;
                }
            } else if(operation.equals("sqroot")){
                if (checkSqRoot(r, number2)){
                    return Math.sqrt(number2);
                }
            } else{
                r.addExeption(Exept.E3);
            }
         return 0;
     }
    private static boolean checkMultiplication(Result r, double n1, double n2){

         if ((Math.log10(n1)+Math.log10(n2))>308){
             r.addExeption(Exept.E4);
             return false;
         }
         else if(Math.log10(n1)+Math.log10(n2)>-323){
             r.addExeption(Exept.E11);
             return false;

         }
             return true;
    }
    private static boolean checkDivision(Result r, double n1, double n2){
        boolean b=true;
        if(n2==0){
            r.addExeption(Exept.E1);
            b=false;
         }
        else if ((Math.log10(n1)-Math.log10(n2))>308){
            r.addExeption(Exept.E9);
            b=false;
        } else if(Math.log10(n1)-Math.log10(n2)<-323){
            r.addExeption(Exept.E10);
            b=false;
        }
        return b;
    }
    private static boolean checkAddition(Result r, double n1, double n2){
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
    private static boolean checkSqRoot(Result r, double n2){
        if(n2<0){
            r.addExeption(Exept.E2);
            log.info(Exept.E2.getMessage());
            return false;
        }
        return true;
    }

}


