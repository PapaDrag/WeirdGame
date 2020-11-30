package Codes;

import GameObjects.ID;
import GameObjects.Player;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.HashMap;

// https://www.youtube.com/watch?v=0T1U0kbu1Sk 18:30


public class Game extends Canvas implements Runnable {

    public int WIDTH = 640, HEIGHT = WIDTH/12*9;
    private Thread thread;
    private boolean running;
    private Handler handler;


    public Game(){
        Window window = new Window(WIDTH, HEIGHT, "RETARD", this);
        handler = new Handler();
        handler.addObject(new Player(100,100, ID.PLAYER));
        this.addKeyListener(new KeyInput(this.handler));
    }

    public synchronized void start(){
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop(){
        try{
            thread.join();
            running = false;
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        long lastTime = System.nanoTime();
        final double amountOfTicks = 60.0;
        final double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1){
                tick();
                delta--;
            }
            frames++;
            if (running){
                render();
            }

            if (System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                //System.out.println("FPS: " + Integer.toString(frames));
                frames = 0;
            }
        }
        stop();
    }

    private void tick(){
        handler.tick();
    }


    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null){
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.RED);
        g.fillRect(0,0,WIDTH,HEIGHT);


        handler.render(g);

        g.dispose();
        bs.show();
    }

    public static void main(String[] args){
        new Game();
    }
}