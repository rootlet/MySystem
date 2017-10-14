

public class Sys implements Runnable{
    int mark; // признак входа, выхода: 1-входная система. 2 -выходная систем. 0-скрытая система.
    final double WEIGHT_FORCING = 1.1;
    // Кол-во входов
    final int INPUT_QUANTITY = 10;
    // входы
    public double[] input;
    // веса
    public double[] weight;
    // индексы систем, к которым прикрепляемся
    private int[] indexesOfSyses;

    private double output;
    private double importance;
    //private ArrayList<Sys> syses = new ArrayList<>();

    private int i; //итератор текущей системы

    public Sys(int mark) {
        this.mark = mark;
        //инициализируем входы и веса
        input = new double[INPUT_QUANTITY];
        weight = new double[INPUT_QUANTITY];
        for (int i = 0; i < INPUT_QUANTITY; i++) {
            weight[i] = Math.random() * 0.5;
        }

        switch (mark) {
            case 1:
                setInput();
                break;
            case 2:
                setOutput();
                break;
            default:
                setLikeHidden();
                break;
        }
    }

    private void setInput() {
        double[] data = MainSystem.dataThread.getData();
        for (int j = 0; j < input.length; j++) {
            input[j] = data[j];
        }

    }

    private void setOutput() {
    }

    private void setLikeHidden() {
        //Прикрепляемся входами к выходам других систем рамдомно.
        indexesOfSyses = new int[INPUT_QUANTITY];

        for (int i = 0; i < INPUT_QUANTITY; i++) {
            int rand = (int) (Math.random() * MainSystem.syses.size());
            indexesOfSyses[i] = rand;
        }



    }

    public double getOutput(){
        double result = 0;

        result = output;
        return result;
    }

    public int getI() {
        return i;
    }

    @Override
    public void run() {
        for (i = 0; i < MainSystem.COUNT_SYS; i++) {
            // читаем входы. Сравниваем побитно значения с предыдущими. Если совпадают - усиливаем вес входа
            int j = 0;// индекс веса
            double sum = 0;//сумма взвешенных входов

            if (mark == 1) {
                for (double element: input) {
                    sum += element * weight[j];
                    if (element > 0) weight[j] = weight[j] *= WEIGHT_FORCING;

                    j++;
                }
            } else {
                for (int index: indexesOfSyses) {
                    sum += MainSystem.syses.get(index).getOutput() * weight[j];
                    if (MainSystem.syses.get(index).getOutput() >= 1) weight[j] = weight[j] *= WEIGHT_FORCING;

                    j++;
                }
            }


            // На выходе выдаем импульс, продолжительность его зависит от суммы взвешенных входов
            output = sum;
            try {
                Thread.sleep(MainSystem.DT_SYS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.class.getSimpleName() + " SYS остановлен");
    }
}
