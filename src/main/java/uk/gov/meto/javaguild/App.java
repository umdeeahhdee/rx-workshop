package uk.gov.meto.javaguild;

import javax.swing.*;
import java.util.ArrayDeque;
import java.util.Random;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 */
public class App {

    static ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
    static PriceService priceService = new PriceService();
    static PriceDisplay priceDisplay = new PriceDisplay();
    static RollingBuffer rollingBuffer = new RollingBuffer();

    public static void main(String[] args) throws InterruptedException {
        Runtime.getRuntime().addShutdownHook(new Thread(executor::shutdown));

        executor.scheduleWithFixedDelay(() -> {
            final int price = priceService.getPrice();
            rollingBuffer.add(price);
            SwingUtilities.invokeLater(() -> priceDisplay.setPrice(price));
            SwingUtilities.invokeLater(() -> priceDisplay.setAverage(rollingBuffer.getAverage()));
        }, 0, 500, TimeUnit.MILLISECONDS);

    }

    public static class RollingBuffer {

        private final ArrayDeque<Integer> buffer = new ArrayDeque<>(5);
        private final int bufferSize = 5;

        public synchronized void add(int value) {
            if (buffer.size() == bufferSize) {
                buffer.removeFirst();
            }
            buffer.addLast(value);
        }

        public synchronized double getAverage() {
            if (buffer.size() < bufferSize) {
                return 0;
            } else {
                return buffer.stream().mapToInt(Integer::valueOf).average().orElseGet(() -> 0);
            }
        }
    }

    public static class PriceDisplay {

        private final JLabel priceLabel;
        private final JLabel averageLabel;
        private final JFrame jFrame;

        public PriceDisplay() {
            jFrame = new JFrame("Price Monitor");
            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jFrame.getContentPane().setLayout(new BoxLayout(jFrame.getContentPane(), BoxLayout.Y_AXIS));

            priceLabel = new JLabel("Price: - ");
            averageLabel = new JLabel("Average Price: - ");

            jFrame.add(Box.createVerticalGlue());
            jFrame.add(priceLabel);
            jFrame.add(Box.createVerticalGlue());
            jFrame.add(averageLabel);
            jFrame.add(Box.createVerticalGlue());
            jFrame.setSize(200, 100);
            jFrame.setVisible(true);
        }

        public void setPrice(int price) {
            priceLabel.setText("Price: " + price);
        }

        public void setAverage(double average) {
            averageLabel.setText("Average Price: " + average);
        }
    }


    public static class PriceService {

        private final int maxDelay;
        private final int minDelay;
        private final Random random = new Random();

        public PriceService() {
            maxDelay = 500;
            minDelay = 200;
        }

        public int getPrice() {
            try {
                Thread.sleep((int) (Math.random() * (maxDelay - minDelay) + minDelay));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return random.nextInt(1000);
        }
    }
}
