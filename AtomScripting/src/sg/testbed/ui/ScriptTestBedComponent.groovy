package sg.testbed.ui

import java.awt.Graphics
import java.awt.event.ActionListener
import java.awt.event.ActionEvent
import javax.swing.*
import java.awt.*

public class ScriptTestBedComponent extends JPanel{
   /*
    ScriptEngine scriptEngine;
    TestBed (def scriptEngine){
    this.scriptEngine = scriptEngine;
    }
     */
    def main;
    def testObj;
    private int   m_interval  = 35;  // Milliseconds between updates.
    private Timer m_timer;           // Timer fires to anmimate one step.
    public ScriptTestBedComponent(main){
        this.main = main;
        m_timer = new Timer(m_interval, new TimerAction());
    }

    public void setAnimation(boolean turnOnOff) {
        if (turnOnOff) {
            m_timer.start();  // start animation by starting the timer.
        } else {
            m_timer.stop();   // stop timer
        }
    }

    //======================================================= paintComponent
    public void paintComponent(Graphics g) {
        super.paintComponent(g);  // Paint background, border
        //g.setColor(yellow)
        //g.fillRect(0,0,width,height)
        if ((testObj=main.getTestObject())!=null){
            testObj.update(width,height)
            testObj.onPaint(g)
            //println("Draw something!")
            //repaint();
        } else {
            //println("NULL!")
        }
    }
    
    class TimerAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            repaint();      
            //println "Clock"
        }
    }
}

