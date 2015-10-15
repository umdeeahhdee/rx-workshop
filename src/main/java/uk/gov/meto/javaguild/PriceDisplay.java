package uk.gov.meto.javaguild;

import javax.swing.*;

public class PriceDisplay {

    private final JLabel priceLabel;

    public PriceDisplay() {
        JFrame jFrame = new JFrame("Price Monitor");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.getContentPane().setLayout(new BoxLayout(jFrame.getContentPane(), BoxLayout.Y_AXIS));

        priceLabel = new JLabel("Price: - ");

        jFrame.add(Box.createVerticalGlue());
        jFrame.add(priceLabel);
        jFrame.add(Box.createVerticalGlue());
        jFrame.setSize(200, 100);
        jFrame.setVisible(true);
    }

    public void setPrice(Integer price) {
        priceLabel.setText("Price: " + price);
    }

}
