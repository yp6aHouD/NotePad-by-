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

    @Override
    public void keyTyped(KeyEvent e) 
    {
        
    }
    // TODO: сократить количество текста, сделать более читаемым
    // Обработка нажатия клавиш
    @Override
    public void keyPressed(KeyEvent e)
    {
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_N)
            gui.fileFunction.newFile(); // новый файл
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_O)
            gui.fileFunction.openFile(); // открыть файл
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_S)
            gui.fileFunction.save(); // сохранить файл
        if (e.isAltDown() && e.getKeyCode() == KeyEvent.VK_F4)
            gui.fileFunction.exit(); // выйти из программы

        if (e.isControlDown() && !e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_Z)
            gui.editFunction.undo(); // отменить
        if (e.isControlDown() && e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_Z)
            gui.editFunction.redo(); // вернуть //
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_X)
            gui.editFunction.cut(); // вырезать
        if (e.isControlDown() && !e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_C)
            gui.editFunction.copy(); // копировать
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_V)
            gui.editFunction.paste(); // вставить
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_F)
            gui.editFunction.find(); // найти
        
        if (e.isControlDown() && e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_T)
            gui.formatFunction.setTextFontAndSize(); // изменить шрифт и размер
        if (e.isControlDown() && e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_C)
            gui.formatFunction.setTextColor(true); // изменить цвет текста
        if (e.isControlDown() && !e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_B)
            gui.formatFunction.setTextColor(false); // изменить цвет фона символов

        // TODO: исправить проблему быстрого выделения (удаляет символы)
        if (e.isControlDown() && !e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_H)
            gui.formatFunction.quickHighlight(); // быстрое выделение
        if (e.isControlDown() && e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_B)
            gui.formatFunction.setBackgroundColor(); // изменить цвет бэкграунда
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_R)
            gui.formatFunction.resetText(); // сбросить настройки
    }

    @Override
    public void keyReleased(KeyEvent e)
    {

    }
}
