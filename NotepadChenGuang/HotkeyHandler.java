package NotepadChenGuang;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// Обработчик горячих клавиш

@SuppressWarnings("unused")
public class HotkeyHandler implements KeyListener
{
    GUI gui;
    public HotkeyHandler(GUI gui)
    {
        this.gui = gui;
    }

    // Обработка нажатия клавиш
    @Override
    public void keyPressed(KeyEvent e)
    {
        if (e.isAltDown() && e.getKeyCode() == KeyEvent.VK_F4) gui.fileFunction.exit();
        
        // Ctrl +
        if (e.isControlDown() & !e.isShiftDown()) 
        {
            switch (e.getKeyCode())
            {
                case KeyEvent.VK_N: gui.fileFunction.newFile(); break; // новый файл
                case KeyEvent.VK_O: gui.fileFunction.openFile(); break; // открыть файл
                case KeyEvent.VK_S: gui.fileFunction.save(); break; // сохранить файл
                case KeyEvent.VK_Z: gui.editFunction.undo(); break; // отменить
                case KeyEvent.VK_X: gui.editFunction.cut(); break; // вырезать
                case KeyEvent.VK_C: gui.editFunction.copy(); break; // копировать
                case KeyEvent.VK_V: gui.editFunction.paste(); break; // вставить
                case KeyEvent.VK_F: gui.editFunction.find(); break; // найти
                case KeyEvent.VK_B: gui.formatFunction.setTextColor(false); break; // быстрое выделение
                case KeyEvent.VK_R: gui.formatFunction.resetText(); break; // сброс
                default: break;
            }
        }
        
        // Ctrl + Shift +
        if (e.isControlDown() & e.isShiftDown()) 
        {
            switch(e.getKeyCode())
            {
                case KeyEvent.VK_S: gui.fileFunction.saveAs(); break; // сохранить как
                case KeyEvent.VK_Z: gui.editFunction.redo(); break; // вернуть
                case KeyEvent.VK_T: gui.formatFunction.setTextFontAndSize(); break; // изменить шрифт и размер
                case KeyEvent.VK_C: gui.formatFunction.setTextColor(true); break; // изменить цвет текста
                case KeyEvent.VK_H: gui.formatFunction.quickHighlight(); break; // быстрое выделение
                case KeyEvent.VK_B: gui.formatFunction.setBackgroundColor(); break; // изменить цвет бэкграунда
                default: break;
                
            }
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e)
    {
        
    }

    @Override
    public void keyTyped(KeyEvent e) 
    {
        
    }
}