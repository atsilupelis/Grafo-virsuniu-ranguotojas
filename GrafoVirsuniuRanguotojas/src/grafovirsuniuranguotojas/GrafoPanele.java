package grafovirsuniuranguotojas;

import java.awt.Color;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.event.MouseInputAdapter;


/**
 * Grafų atvaizdavimo panelės klasė
 * @date 2013-11-29
 * @author Paulius Šukys
 */
public class GrafoPanele extends JPanel
{
    int plotis;
    int ilgis;
    Virsune[] virsunes;
    /**
     * Paruošia grafų atvaizdavimo panelę
     * @since 2013-11-29
     */
    public GrafoPanele(int plo, int ilg)
    {
        plotis = plo;
        ilgis = ilg;
        MouseInputAdapter mia = new MouseInputAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e) 
            {
                for (Virsune v : virsunes) 
                {
                    if (new java.awt.Rectangle(v.getX(), v.getY(),20,20).contains(e.getX(), e.getY())) 
                    {
                        v.setMoving(true);
                    }
                }
            }
            
            @Override
            public void mouseReleased(MouseEvent e) 
            {
                for (Virsune v : virsunes) 
                {
                    v.setMoving(false);
                }
            }
            
            @Override
            public void mouseDragged(MouseEvent e) 
            {
                for (Virsune v : virsunes) 
                {
                    if (v.isMoving()) 
                    {
                        boolean move = true;
                        int x = e.getX() - 10;
                        int y = e.getY() - 10;
                        for (Virsune vk : virsunes) 
                        {
                            if (!vk.isMoving()) 
                            {
                                int dx = vk.getX() - x;
                                int dy = vk.getY() - y;
                                if ((dx*dx+dy*dy<=20*20)) 
                                {
                                    move = false;
                                    break;
                                }
                            }
                        }
                        if (move) 
                        {
                            v.setX(x);
                            v.setY(y);
                            repaint();
                        }
                        break;
                    }
                }
            }
        };
        addMouseListener(mia);
        addMouseMotionListener(mia);
    }
    
    public void atvaizduotiVirsunes(Virsune[] virs)
    {
        //virsuniu manipuliavimui veliau prireiks
        virsunes=virs;
        int i = 0;
        for (Virsune v : virsunes)
        {
            v.setX((int) ((plotis/2-100) * Math.cos((2*Math.PI/virsunes.length)*(i+1)) + plotis/2));
            v.setY((int) ((ilgis/2-100) * Math.sin((2*Math.PI/virsunes.length)*(i+1)) + ilgis/2));
            i++;
        }
    }
    
    @Override
    public Dimension getPreferredSize() 
    {
       return new Dimension(plotis, ilgis);
    }
    
    @Override
    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);       
        //nupaiso rysius
        for (Virsune v : virsunes) 
        {
            for (int rysys : v.getRysiai()) 
            {
                g.setColor(Color.BLACK);
                g.drawLine(v.getX()+10, v.getY()+10, virsunes[rysys].getX()+10, virsunes[rysys].getY()+10);   
            }
        }
        
        for (Virsune v : virsunes) 
        {
            g.setColor(Color.DARK_GRAY);
            g.fillOval(v.getX(), v.getY(), 20, 20);
            g.setColor(Color.WHITE);
            int raidziuPlotis = 5*((int)(v.getRangas()/10)+1);
            g.drawString(new Integer(v.getRangas()).toString(), v.getX()+((20-raidziuPlotis)/2), v.getY()+15);
        }

    } 
}
