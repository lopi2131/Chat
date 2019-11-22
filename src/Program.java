import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.*;
import java.util.logging.Formatter;


public class Program {

    private static Logger log = Logger.getLogger(Program.class.getName());
    private static final String STOP_TALKING = "Stop talking";
    private static final String START_TALKING = "Start talking";
    private static final String USE_ANOTHER_FILE = "use another file:";
    private static final String GOODBYE = "Goodbye";

    public static String inputFile() {
        Scanner in = new Scanner(System.in);
        System.out.print("Укажите путь к файлу: ");
        return in.nextLine();
    }

    public static void main(String[] args) throws IOException {

        LogManager.getLogManager().reset();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh.mm.ss");
        String timeStamp = dateFormat.format(new Date());
        FileHandler fh = new FileHandler(timeStamp + ".txt", 30000, 4, true);
        fh.setFormatter(new MyFormatter());
        log.addHandler(fh);

        Scanner in = new Scanner(System.in);

        String filename = inputFile();
        log.info("Укажите путь к файлу: " + filename);

        List<String> listOfLines = getListOfLines(filename);

        int size = listOfLines.size();

        String comand = "firstStart";
        boolean isProgramNotFinished = true;

        while (isProgramNotFinished) {


            if (comand.equalsIgnoreCase(STOP_TALKING)) {
                while (!comand.equalsIgnoreCase(START_TALKING)) {
                    System.out.print("Введите команду: ");
                    comand = in.nextLine();
                    log.info("Введите команду: " + comand);
                }
                System.out.println("Ответное сообщение: " + listOfLines.get(rand(size)));

            } else if (comand.toLowerCase().contains(USE_ANOTHER_FILE)) {
                listOfLines.clear();
                listOfLines = getListOfLines(comand.substring(comand.lastIndexOf(":") + 1));
                System.out.println("Ответное сообщение: " + listOfLines.get(0));
                log.info("Ответное сообщение: " + listOfLines.get(0));
                size = listOfLines.size();

            } else if (comand.equalsIgnoreCase(GOODBYE)) {
                break;

            } else if (comand.equalsIgnoreCase("firstStart")) {
                System.out.println("Ответное сообщение: " + listOfLines.get(0));
                log.info("Ответное сообщение: " + listOfLines.get(0));

            } else {
                System.out.println("Ответное сообщение: " + listOfLines.get(rand(size)));
                log.info("Ответное сообщение: " + listOfLines.get(rand(size)));
            }
            System.out.print("Введите команду: ");
            comand = in.nextLine();
            log.info("Введите команду: " + comand);
        }
        in.close();
    }

    private static List<String> getListOfLines(String filename) {
        List<String> listOfLines = new ArrayList<>();
        try {
            File file = new File(filename);
            if (!file.exists()) {
                while (!file.exists()) {
                    System.out.println("Файл не найден");
                    log.info("Файл не найден");
                    filename = inputFile();
                    file = new File(filename);
                    log.info("Укажите путь к файлу: " + filename);
                }
            }
            //создаем объект FileReader для объекта File
            FileReader fr = new FileReader(file);
            //создаем BufferedReader с существующего FileReader для построчного считывания
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {
                listOfLines.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            log.log(Level.SEVERE, "Ошибка при работе приложения " + e);
            System.out.println("Ошибка при работе приложения");
        }
        return listOfLines;
    }

    private static int rand(int size) {
        Random random = new Random();
        return random.nextInt(size - 1) + 1;
    }
}
