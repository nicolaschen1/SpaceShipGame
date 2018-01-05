/***
 * Author: Nicolas Chen
 * SpaceShipGame
 * Version 1.0
***/

package game.spaceship;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class GameFrame extends JFrame{

public GameFrame() {
    initUI();
    }

    private void initUI() {
        add(new MainBoard());
        setResizable(false);
        pack();
        setTitle("SpaceShipGame");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
            	GameFrame ex = new GameFrame();
                ex.setVisible(true);
            }
        });
    }
}
