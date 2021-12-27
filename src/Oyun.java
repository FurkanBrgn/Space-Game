import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

class Ates{
    private int x;
    private int y;

    public Ates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

public class Oyun extends JPanel implements KeyListener,ActionListener{
    Timer timer =new Timer(5, this);
    private ArrayList<Ates> atesler=new ArrayList<Ates>();
    private int gecenSure=0;
    private int harcananAtes=0;
    private BufferedImage image;
    private int atesDirY=1;
    private int topX=0;
    private int topDirX=2;
    private int uzayGemisiX=0;
    private int DirUzayGemisiX=20;
    
    public boolean kontrolEt(){
        for(Ates ates:atesler){
            /*if(((!(ates.getX()>=(topX+20)) && !(ates.getX()<=(topX-10))))&&(ates.getY()<=20)){
                return true; //Benim kendi yazdığım sorgu
            }*/
            if(new Rectangle(ates.getX(), ates.getY(), 10, 20).intersects(new Rectangle(topX,0,20,20))){
                return true;
            }
        }
        return false;
    }
    
    public Oyun() {
        try {
            image=ImageIO.read(new FileImageInputStream(new File("uzaygemisi.png")));
        } catch (IOException ex) {
            Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
        }
        setBackground(Color.BLACK);
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); 
        gecenSure+=5;
        g.drawImage(image, uzayGemisiX, 500, image.getWidth()/10, image.getHeight()/10, this);
        g.setColor(Color.red);
        g.fillOval(topX, 0, 20, 20);
        for(Ates ates: atesler){
           if(ates.getY()<0){
               atesler.remove(ates);
           } 
        }
        g.setColor(Color.blue);
        for(Ates ates : atesler){
           g.fillRect(ates.getX(),ates.getY(), 10, 20);
        }
        if(kontrolEt()){
            timer.stop();
            String message="Kazandınız...\n"
                    + "Harcanan Atış: "+harcananAtes
                    +"\n Geçen Süre: "+gecenSure/1000.0;
            JOptionPane.showMessageDialog(this, message);
            System.exit(0);
        }
    }

    @Override
    public void repaint() {
        super.repaint();
    }
    
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int c= e.getKeyCode();
        if(c == KeyEvent.VK_LEFT){
           if(uzayGemisiX<=0){
              uzayGemisiX=0; 
           }
           else{
              uzayGemisiX-=DirUzayGemisiX; 
           }
        }
        else if(c == KeyEvent.VK_RIGHT){
           if(uzayGemisiX>=740){
              uzayGemisiX=750; 
           }
           else{
              uzayGemisiX+=DirUzayGemisiX; 
           }
        }
        else if(c == KeyEvent.VK_CONTROL){
            atesler.add(new Ates(uzayGemisiX+15, 475));
            harcananAtes++;
        }
            
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(Ates ates: atesler){
            ates.setY((ates.getY())-(atesDirY));
        }
        topX+=topDirX;
        if (topX >= 780) {
           topDirX=-topDirX; 
        }
        if(topX <= 0){
           topDirX=-topDirX; 
        }
        repaint();
    }
    
}
