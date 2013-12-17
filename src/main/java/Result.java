import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.*;
import java.util.LinkedList;
import java.util.logging.Logger;

/**
 * Клас Result предназначен для хранения всех данных отправляемых пользователю в качестве результата.
 * Объекты класса могут конвертироваться в XML с помощью библиотеки JAXB. Поля доступные для
 * конвертации отмечены аннотациями
 *
 * @author Maksimov Roman
 * @version 1.0.0
 */
@XmlRootElement(name="result")
public class Result {
    /**инициализация*/
    private final static Logger log = Logger.getLogger(Result.class.getName());
    private boolean boolean_exept=false;
    private LinkedList<Exept> exeption;
    private double number =0;

    /** Конструктор класса без параметров
     *  number - число в которое сохраняется результат вычеслений
     *  boolean_exept - есть ли исключения в процессе работы сервиса?
     *  exeption - список всех исключений возникших в результате обработки запроса пользователя
     * */
    public Result(){
        double number =0;
        boolean_exept = false;
        exeption = new LinkedList<Exept>();
    }
    /**Метод добавления исключений в список exeption*/
    public void addExeption(Exept exept){
        /** отлавливаем все исключения отправляемые пользователю */
        log.info("Сервис пользователю добавил к ответу ошибку №".concat(exept.getCod()));
        boolean_exept = true;
        exeption.add(exept);
    }
    /**Метод возвращает число number. Число может быть конвертировано в Хml*/
    @XmlElement (name = "resultNumber")
    public double getNumber() {
        return number;
    }
    /**Метод задает новое значение для числа number.*/
    public void setNumber(double number) {
        this.number = number;
    }
    /**Метод возвращает список строк с сообщениями и кодами всех исключений. Список может быть
     * конвертирован в Хml*/
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
    /**Метод возвращает булевое значение, которое хранит информацию о том возникали ли исключения в процессе обработки.
     * данных. Значение может быть конвертировано в Хml*/
    @XmlElement (name = "boolean_exeption")
    public boolean isBoolean_exept() {
        return boolean_exept;
    }
    /**Метод возвращает список всех исключений.*/
    protected LinkedList<Exept> getExeption() {
        return exeption;
    }
}
