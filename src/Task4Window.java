import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Task4Window extends JFrame {
    private static JLabel label = null;

    public Task4Window() {
        JFrame frame = new JFrame("check");
        JFrame.setDefaultLookAndFeelDecorated(true);

        label = new JLabel("hi");
        frame.getContentPane().add(label);

        frame.setPreferredSize(new Dimension(250, 80));
        frame.pack();
        frame.setVisible(true);
    }
}