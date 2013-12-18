import java.util.logging.Logger;

/**
 * Enum класс предназначен для хранения всех исключений(текста и кода), которые могут генерироваться
 * сервисом
 * @author Maksimov Roman
 * @version 1.0.2
 */
public enum Exept {
    E1("Недопустимая операция - деление на ноль.", "001"),
    E2("Недопустимая операция - корень отрицательного числа.", "002"),
    E3("Ошибка передачи операции вычисления", "003"),
    E4("Результат умножения больше центилиона(10 в 303 степени), выходит за пределы верхней границы диапазона.", "004"),
    E5("Первое чило слишком маленькое для проведения операций над ним. ","005"),
    E6("Второе число выходит за нижнюю границу диапазона. Попробуйте числа побольше.", "006"),
    E7("Введены не коректные данные. Проверьте пожалуйста коректность ввода.", "007"),
    E8("Критическая ошибка парсинга Xml", "008"),
    E9("Результат деления слишком большое число. Больше чем атомов во Вселенной.","009"),
    E10("Результат деления слишком маленькое число, выходящее за допустимые пределы. ", "010"),
    E11("Результат умножения слишком малое число, выходящее за пределы диапазона.", "011"),
    E12("Первое число больше(по модулю) гранично допустимого. Попробуйте прибавлять/отнимать числа меньше чем 10 в 300-ой степени.", "012"),
    E13("Второе число больше(по модулю) гранично допустимого. Попробуйте прибавлять/отнимать числа меньше чем 10 в 300-ой степени..", "013"),
    E14("Неизвестная операция над числами. <a href='webcalc'>Кликните и узнайте допустимые операции</а>", "014" );


    private String message;
    private String cod;
    /** Конструктор класса
     * @param message - текст исключения
     * @param cod - код исключения
    */
    Exept(String message, String cod){
        this.message=message;
        this.cod=cod;
    }
    /** Метод возвращающий текст исключения
    */
    public String getMessage() {
        return message;
    }
    /** Метод возвращающий код исключения
     */
    public String getCod() {
        return cod;
    }
}
