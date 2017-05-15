import javafx.application.Application;

import javax.swing.*;
import java.awt.*;
import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;

/**
 * Created by Michał on 03.05.2017.
 */
public class Main {
    public static void main(String[] args) throws MyException {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TreeViewer();
            }
        });

    }
}
