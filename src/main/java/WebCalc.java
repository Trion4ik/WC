import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**  Класс-сервлет обрабатывающий запросы от пользователей Http методами Get и Post
 * Наследуетсz от класса javax.servlet.http.HttpServlet
 */
@SuppressWarnings("serial")
public class WebCalc extends HttpServlet {

    private final static Logger log = Logger.getLogger(WebCalc.class.getName());
    private volatile StringBuilder sbild = new StringBuilder();

    /**метод обрабатывает запросы полученные Get методом*/
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    {   log.info("WebCalc.doGet() Start. Обращение к сервису методом Get. ");
        try{
            response.setContentType("text/html");
            response.setCharacterEncoding("utf8");
            PrintWriter pw = response.getWriter();
            sbild.delete(0, sbild.length());
            pw.print(sbild
                    .append("Use utf-8 encoding in your browser.</br>")
                    .append("Вы обратились к сервису  методом Get.</br>")
                    .append("Для доступа к всем возможностям сервиса рекомендуем воспользоваться методом Post.</br>")
                    .append("Сервис поддерживает операции над числами: </br> ")
                    .append("'addition' - сложение</br>")
                    .append("'deduction' - отнимание </br>")
                    .append("'multiplication'- умножение</br>")
                    .append("'division' - деление</br>")
                    .append("'sqroot' - квадратный корень")
                    .toString());
        } catch (IOException io){
            log.log(Level.WARNING,"WebCalc.doGet() - словлено исключение ввода/вывода IOException", io);
        }
        log.info("WebCalc.doPost END. Обращение к сервису методом Get успешно обработано. ");
    }
    /**Метод обрабатывает запросы полученные Post методом*/
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    {   log.info("WebCalc.doPost START. Обращение к сервису методом Post.");
        try{
            Result result = CalcControl.getResult(request);
            response.setContentType("text/xml");
            response.setCharacterEncoding("utf8");
            PrintWriter pw = response.getWriter();
            toXml(result, pw);
        }catch (IOException io) {
            log.log(Level.SEVERE, "WebCalc.doPost() - словлена ошибка ввода/вывода IOException", io);
        }
        log.info("WebCalc.doPost START. Обращение к сервису методом Post успешно обработано. ");
    }
    /**Метод представляет объекты типа Result в Xml используя стандартную библиотеку JDK - JAXB.
     * Добавляет в поток вывода объект Xml
     */
    protected void toXml (Result result, PrintWriter printWriter){
        log.info("WebCalc.toXml START. Начало преобразования объекта в XML");
        try {
            JAXBContext context = JAXBContext.newInstance(Result.class);
            Marshaller marshller =context.createMarshaller();
            marshller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshller.marshal(result, System.out);
            marshller.marshal(result, printWriter);
        } catch (JAXBException e) {
            log.log(Level.SEVERE, "WebCalc.toXml() - cловлена ошибка парсера ХМЛ", e);
            printWriter.print("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                    "<result>" +
                    "    <boolean_exeption>true</boolean_exeption>" +
                    "    <exeptionMessage>"+Exept.E8.getMessage()+Exept.E8.getCod()+"</exeptionMessage>" +
                    "</result>");
        }
        log.info("WebCalc.toXml END. Успешно закончено преобразования объекта в XML");
    }
    private void process(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setStatus(200);
        log.info("Ответ сервером успешно отправлен");
    }
}
