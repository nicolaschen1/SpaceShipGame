/***
 * Author: Nicolas Chen
 * SpaceShipGame
 * Version 1.0
***/

package game.spaceship;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MainBoard extends JPanel implements ActionListener {
    private Timer timer;
    private Spaceship spaceship;
    private ArrayList<Enemy> enemies;
    private boolean ingame;
    private boolean win;
    private String message;
    private final int ICRAFT_X = 80;
    private final int ICRAFT_Y = 120;
    private final int B_WIDTH = 400;
    private final int B_HEIGHT = 300;
    private final int DELAY = 15;

    private final int[][] pos = {
        {2380, 29}, {2500, 59}, {1380, 89},
        {780, 109}, {580, 139}, {680, 239},
        {790, 259}, {760, 50}, {790, 150},
        {980, 209}, {560, 45}, {510, 70},
        {930, 159}, {590, 80}, {530, 60},
        {940, 59}, {990, 30}, {920, 200},
        {900, 259}, {660, 50}, {540, 90},
        {810, 220}, {860, 20}, {740, 180},
        {820, 128}, {490, 170}, {700, 30},
        {920, 518}, {590, 190}, {800, 40}
    };

    public MainBoard() {
        initMainBoard();
    }

    private void initMainBoard() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        ingame = true;

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        spaceship = new Spaceship(ICRAFT_X, ICRAFT_Y);
        initAliens();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void initAliens() {
        enemies = new ArrayList<>();

        for (int[] p : pos) {
            enemies.add(new Enemy(p[0], p[1]));
        }
    }

    @Override
    public void paintComponent(Graphics game) {
        super.paintComponent(game);

        if (ingame) {
            drawObjects(game);
        }

         if (!ingame || win) {
            drawGameOver(game);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void drawObjects(Graphics g) {
        if (spaceship.isVisible()) {
            g.drawImage(spaceship.getImage(), spaceship.getX(), spaceship.getY(),
                    this);
        }

        ArrayList<Missile> ms = spaceship.getMissiles();

        for (Missile m : ms) {
            if (m.isVisible()) {
                g.drawImage(m.getImage(), m.getX(), m.getY(), this);
            }
        }

        for (Enemy a : enemies) {
            if (a.isVisible()) {
                g.drawImage(a.getImage(), a.getX(), a.getY(), this);
            }
        }

        g.setColor(Color.WHITE);
        g.drawString("Aliens left: " + enemies.size(), 5, 15);
    }

    private void drawGameOver(Graphics game) {
    	if (win) {
    		message = "Congratulations! You won!";
    	}
    	else {
    		message = "You lost!";
    	}

        Font small = new Font("Helvetica", Font.BOLD, 18);
        FontMetrics fm = getFontMetrics(small);

        game.setColor(Color.white);
        game.setFont(small);
        game.drawString(message, (B_WIDTH - fm.stringWidth(message)) / 2,
                B_HEIGHT / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        inGame();

        updateCraft();
        updateMissiles();
        updateEnemies();

        checkCollisions();

        repaint();
    }

    private void inGame() {
        if (!ingame) {
            timer.stop();
        }
    }

    private void updateCraft() {
        if (spaceship.isVisible()) {
            spaceship.move();
        }
    }

    private void updateMissiles() {
        ArrayList<Missile> ms = spaceship.getMissiles();

        for (int i = 0; i < ms.size(); i++) {

            Missile m = ms.get(i);

            if (m.isVisible()) {
                m.move();
            } else {
                ms.remove(i);
            }
        }
    }

    private void updateEnemies() {
        if (enemies.isEmpty()) {
            win = true;
            return;
        }

        for (int i = 0; i < enemies.size(); i++) {
            Enemy a = enemies.get(i);
            if (a.isVisible()) {
                a.move();
            } else {
                enemies.remove(i);
            }
        }
    }

    public void checkCollisions() {
        Rectangle r3 = spaceship.getBounds();

        for (Enemy alien : enemies) {
            Rectangle r2 = alien.getBounds();
            if (r3.intersects(r2)) {
                spaceship.setVisible(false);
                alien.setVisible(false);
                ingame = false;
                win = false;
            }
        }

        ArrayList<Missile> ms = spaceship.getMissiles();

        for (Missile m : ms) {
            Rectangle r1 = m.getBounds();
            for (Enemy alien : enemies) {
                Rectangle r2 = alien.getBounds();
                if (r1.intersects(r2)) {
                    m.setVisible(false);
                    alien.setVisible(false);
                }
            }
        }
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            spaceship.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            spaceship.keyPressed(e);
        }
    }
}
