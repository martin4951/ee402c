package ee402a;

import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class BadCOunterApp extends Frame implements ActionListener {

        private int count = 0;
        private Button start, stop;
        private TextField countText;
        private boolean running = true;
        
        public BadCOunterApp(){
                super("Bad Counter");
                this.setLayout(new FlowLayout());
                this.countText = new TextField(10);
                this.add(countText);
                this.start = new Button("Start");
                this.add(start);
                this.start.addActionListener(this);
                this.stop = new Button("Stop");
                this.add(stop);
                this.stop.addActionListener(this);
                this.pack();
                this.setVisible(true);
        }        
        
        public void go(){
                while(running){
                        this.countText.setText("Count: " + count++);
                        try{
                                Thread.sleep(100);
                        }
                        catch(InterruptedException e){
                                System.out.println("Thread was Interrupted!");
                        }
                }
        }
        
        public static void main(String[] args) {
                new BadCOunterApp();
        }

        public void actionPerformed(ActionEvent e) {
                if (e.getSource().equals(start)){
                        this.go();
                }
                else if (e.getSource().equals(stop)){
                        this.running = false;
                }
        }
}