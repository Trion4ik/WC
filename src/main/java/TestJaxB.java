import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 14.12.13
 * Time: 15:37
 * To change this template use File | Settings | File Templates.
 */
public class TestJaxB {
    public static void main (String[] args){
        Result r = new Result();
        r.setNumber(1000);
        r.addExeption(Exept.E1);
        r.addExeption(Exept.E6);
        JAXBContext context = null;
        try {
            context = JAXBContext.newInstance(Result.class);
            Marshaller marshller =context.createMarshaller();
            marshller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            // JAXBElement<Result> jaxbElement = new JAXBElement<Result>(new QName("result"),Result.class, result);
            marshller.marshal(r, System.out);
        } catch (JAXBException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

}
