import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.*;
import java.util.LinkedList;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 13.12.13
 * Time: 19:35
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name="result")
public class Result {
    private final static Logger log = Logger.getLogger(Result.class.getName());
    private boolean boolean_exept;
    private LinkedList<Exept> exeption;
    private double number;

    public Result(){
        double number =0;
        boolean_exept = false;
        exeption = new LinkedList<Exept>();
    }
    public void addExeption(Exept exept){
        log.info("Сервис пользователю добавил к ответу ошибку №".concat(exept.getCod()));
        boolean_exept = true;
        exeption.add(exept);
    }
    @XmlElement (name = "resultNumber")
    public double getNumber() {
        return number;
    }
    public void setNumber(double number) {
        this.number = number;
    }
    @XmlElement (name = "exeptionMessage")
    public LinkedList<String> getExeptionMessage() {
        LinkedList<String> exeptionMessage = new LinkedList<String>();
        for(Exept e: exeption){
            exeptionMessage.add(e.getMessage()
                    .concat(" Код ошибки:")
                    .concat(e.getCod()));
            log.fine("Ошибка возвращенная пользователю:".concat(e.getMessage()));
        }
        return exeptionMessage;
    }
    @XmlElement (name = "boolean_exeption")
    public boolean isBoolean_exept() {
        return boolean_exept;
    }
    protected LinkedList<Exept> getExeption() {
        return exeption;
    }
}
