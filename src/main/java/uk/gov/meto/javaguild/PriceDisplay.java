package uk.gov.meto.javaguild;

import javax.swing.*;

public class PriceDisplay {

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
