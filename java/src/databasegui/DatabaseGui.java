package databasegui;

import dataabstractions.ShopData;

import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * @author Joseph Pariseau
 */
public class DatabaseGui {
    final protected int WINDOW_HEIGHT;
    final protected int WINDOW_WIDTH;
    final private WindowFrame WINDOW = new WindowFrame();

    public DatabaseGui(int width, int height, ActionListener mainListener) {
        WINDOW_WIDTH = width;
        WINDOW_HEIGHT = height;
        WINDOW.initialize();
        WINDOW.setButtonListener(mainListener);
        WINDOW.setContentListener(mainListener);
        WINDOW.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        WINDOW.pack();
        WINDOW.setVisible(true);
    }

    public void updateContent(ArrayList<ShopData> data) {
        WINDOW.setContent(data);
        WINDOW.pack();
    }
}
