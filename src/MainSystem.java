import java.util.ArrayList;

public class MainSystem {
    public static final double[][] DATASET = {
            {0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 0.8},
            {0.0, 0.5, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
            {0.0, 0.0, 0.5, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
            {0.0, 0.0, 0.0, 0.5, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
            {0.0, 0.0, 0.0, 0.0, 0.5, 0.0, 0.0, 0.0, 0.0, 0.0},
            {0.0, 0.0, 0.0, 0.0, 0.0, 0.5, 0.0, 0.0, 0.0, 0.0},
            {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.5, 0.0, 0.0, 0.0},
            {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.5, 0.0, 0.0},
            {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.5, 0.0}};
    public static final int SYS_QUANTITY = 10; // кол-во систем в сети
    public static final int DT = 100; //Пауза в миллисекундах для DATASET
    public static final int DT_SYS = 10;//Пауза в миллисекундах для SYS
    public static final int COUNT_SYS = 1000;//Количество итераций для SYS
    public static final int COUNT_DATATHREAD = 1500;//Количество итераций для SYS
    public static final int INPUT = 3; // индекс входной системы
    public static final int OUTPUT = 5; // индекс выходной системы
    public static DataThread dataThread;
    public static ArrayList<Sys> syses = new ArrayList<>();


    private static void createNet() {
        for (int i = 0; i < SYS_QUANTITY; i++) {
            if (i == INPUT) {
                syses.add(new Sys(1));// Признак входной системы
            } else if (i == OUTPUT) {
                syses.add(new Sys(0));// Признак выходной системы
            } else syses.add(new Sys(0)); // Признак скрытой системы



        }
    }

    private static void startDataThread() {
        //в отдельной ните запускаем поток данных
        dataThread = new DataThread();
        Thread thread = new Thread(dataThread);
        thread.start();
    }

    private static void startNet() {
        for (Sys sys: syses) {
            Thread th = new Thread(sys);
            th.start();
        }


    }

    private static void monitor() {
        Thread thread = new Thread(new Monitor());
        thread.start();
    }

    public static void main(String[] args) {
        MainSystem mainSystem = new MainSystem();
        //Запускаем цикличный поток данных в отдельной нити (для теста).
        startDataThread();

        //Создаем сеть систем
        createNet();

        //Запускаем работу сети в отдельной нити.
        startNet();
        //Монитор сети
        monitor();

    }



}
