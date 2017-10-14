public class DataThread implements Runnable {
    private double[] data;

    @Override
    public void run() {
        for (int j = 0; j < MainSystem.COUNT_DATATHREAD; j++) {
            for (int i = 0; i < MainSystem.DATASET.length; i++) {
                data = MainSystem.DATASET[i];
                try {
                    Thread.sleep(MainSystem.DT);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(Thread.class.getSimpleName() + " DataThread остановлен");

    }

    public double[] getData() {
        return data;
    }
}
