package uk.gov.meto.javaguild;

import javax.swing.*;
import java.util.Random;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 */
 public class App {

    static ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
    static PriceService priceService = new PriceService();
    static PriceDisplay priceDisplay = new PriceDisplay();

    public static void main(String[] args) throws InterruptedException {
        Runtime.getRuntime().addShutdownHook(new Thread(executor::shutdown));

        executor.scheduleWithFixedDelay(() -> {
            final int price = priceService.getPrice();
            SwingUtilities.invokeLater(() -> priceDisplay.setPrice(price));
        }, 0, 500, TimeUnit.MILLISECONDS);

    }

    public static class PriceDisplay {

        private final JLabel priceLabel;
        private final JFrame jFrame;

        public PriceDisplay() {
            jFrame = new JFrame("Price Monitor");
            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jFrame.getContentPane().setLayout(new BoxLayout(jFrame.getContentPane(), BoxLayout.Y_AXIS));

            priceLabel = new JLabel("Price: - ");

            jFrame.add(Box.createVerticalGlue());
            jFrame.add(priceLabel);
            jFrame.add(Box.createVerticalGlue());
            jFrame.setSize(200, 100);
            jFrame.setVisible(true);
        }

        public void setPrice(int price) {
            priceLabel.setText("Price: " + price);
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
