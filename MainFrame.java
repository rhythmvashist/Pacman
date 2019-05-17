NOTimport javax.swing.*;
import java.awt.*;

public class MainFrame {
    public static void main(String []args) {
        JFrame pac = new JFrame();
        pac.setTitle("Pacman");
        pac.setSize(1000,1000);
        pac.setBackground(Color.black);
        pac.setTitle("Pacman");
        pac.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PacPanel p=new PacPanel();
        pac.add(p);

        pac.setVisible(true);
    }
}
