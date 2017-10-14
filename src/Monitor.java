public class Monitor implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            System.out.println(MainSystem.syses.get(0).getOutput());
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.class.getSimpleName() + " Monitor остановлен");
    }
}
