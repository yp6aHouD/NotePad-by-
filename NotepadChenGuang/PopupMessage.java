package NotepadChenGuang;

import java.awt.Dimension;
import java.awt.Point;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class PopupMessage extends JWindow 
{
    // время исчезновения в миллисекундах
    private static final int FADE_TIME = 1000; 
    private JLabel label;

    public PopupMessage(GUI gui, String message) 
    {
        if (gui.currentPopup != null) 
        {
            gui.currentPopup.dispose();
        }

        setAlwaysOnTop(true);
        
        // Добавление сообщения
        label = new JLabel(message, SwingConstants.CENTER);

        // Добавление отступов
        label.setBorder(new EmptyBorder(1, 10, 1, 10)); 
        add(label);

        // Установка размера окна под содержимое
        pack();

        // Расположение окна в верхнем правом углу
        Point location = gui.menuBar.getLocationOnScreen();
        Dimension screenSize = gui.menuBar.getSize();
        setLocation(location.x + screenSize.width - getWidth() - 10, location.y + 7);

        // Добавление сообщения
        add(new JLabel(message, SwingConstants.CENTER));

        // Создание таймера для анимации исчезновения
        Timer timer = new Timer(FADE_TIME / 100, e -> 
        {
            float opacity = getOpacity();
            opacity -= 0.01f;
            setOpacity(Math.max(opacity, 0));
            if (opacity <= 0) 
            {
                ((Timer) e.getSource()).stop();
                dispose();
            }
        });
        timer.start();
    }
}
