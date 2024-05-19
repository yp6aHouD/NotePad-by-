package NotepadChenGuang;

import java.awt.Color;
import java.awt.GraphicsEnvironment;
import javax.swing.Box;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class FormatFunction
{
    private GUI gui;

    public FormatFunction(GUI gui)
    {
        this.gui = gui;
    }


    //установка шрифта и размера
    public void setTextFontAndSize() 
    {
        // Получение списка доступных шрифтов
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        JComboBox<String> fontList = new JComboBox<>(fonts);
        fontList.setEditable(true);

        // Создание выпадающего списка с размерами шрифтов
        Integer[] fontSizes = {10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30};
        JComboBox<Integer> fontSizeList = new JComboBox<>(fontSizes);
        fontSizeList.setEditable(true);

        // Создание панели с выпадающими списками
        JPanel panel = new JPanel();
        panel.add(new JLabel("Font:"));
        panel.add(fontList);
        panel.add(Box.createHorizontalStrut(15)); // a spacer
        panel.add(new JLabel("Size:"));
        panel.add(fontSizeList);

        // Создание окна с выбором шрифта и размера шрифта
        JOptionPane.showMessageDialog(null, panel, "Choose Font and Size", JOptionPane.QUESTION_MESSAGE);
        String selectedFont = (String) fontList.getSelectedItem();
        Integer selectedFontSize = (Integer) fontSizeList.getSelectedItem();

        // Получение выделенного текста
        String selectedText = gui.textArea.getSelectedText();

        if (selectedText != null) 
        {
            // Получение начала и конца выделенного текста
            int start = gui.textArea.getSelectionStart();
            int end = gui.textArea.getSelectionEnd();

            // Получение StyledDocument
            StyledDocument doc = gui.textArea.getStyledDocument();

            // Создание нового стиля
            Style style = doc.addStyle("NewFontAndSizeStyle", null);
            StyleConstants.setFontFamily(style, selectedFont);
            StyleConstants.setFontSize(style, selectedFontSize);

            // Применение стиля к выделенному тексту
            doc.setCharacterAttributes(start, end - start, style, false);
        } 

        else 
        {
            // Получение StyledDocument
            StyledDocument doc = gui.textArea.getStyledDocument();
            int caret = gui.textArea.getCaretPosition();

            // Создание нового стиля
            Style style = doc.addStyle("NewFontAndSizeStyle", null);
            StyleConstants.setFontFamily(style, selectedFont);
            StyleConstants.setFontSize(style, selectedFontSize);

            // Установка шрифта и размера шрифта для всего текста, если ничего не выделено
            doc.setParagraphAttributes(caret, doc.getLength() - caret, style, false);
        }
    }


    // Изменение цвета текста и выделение текста цветом
    public void setTextColor(boolean isTextColor)
    {
        // Отображение диалогового окна
        String dialogName = isTextColor ? "Choose text color" : "Choose text background color";
        Color defaultColor = isTextColor ? Color.BLACK : Color.WHITE;
        Color selectedColor = JColorChooser.showDialog(null, dialogName, defaultColor);
        String selectedText = gui.textArea.getSelectedText();
        
        // если был выделен текст
        if (selectedText != null)
        {
            // Создание стиля для изменения цвета только выделенного текста
            StyledDocument doc = gui.textArea.getStyledDocument();
            Style style = doc.addStyle("newStyle", null);

            // Получение начала и конца выделенного текста
            int start = gui.textArea.getSelectionStart();
            int end = gui.textArea.getSelectionEnd();
            // Получение StyledDocument
            
            if (selectedColor != null)
            {
                if (isTextColor)
                {
                    StyleConstants.setForeground(style, selectedColor);
                    doc.setCharacterAttributes(start, end - start, style, false);
                }
                else
                {
                    StyleConstants.setBackground(style, selectedColor);
                    doc.setCharacterAttributes(start, end - start, style, false);
                }
            }
        }
        
        // если никакая часть текста не была выделена
        else if (selectedText == null)
        {
            if (selectedColor != null)
            {
                StyledDocument doc = gui.textArea.getStyledDocument();
                // создание нового стиля
                Style style = doc.addStyle("NewColorStyle", null);

                if (isTextColor)
                {
                    StyleConstants.setForeground(style, selectedColor);
                }
                else
                {
                    StyleConstants.setBackground(style, selectedColor);
                }
                // установка свойств для нового вводимого текста
                MutableAttributeSet attrs = gui.textArea.getInputAttributes();
                attrs.removeAttributes(attrs);
                attrs.addAttributes(style);
            }
        }

    }

    // изменение цвета бэкграунда
    public void setBackgroundColor() 
    {
        // Отображение диалогового окна JColorChooser и получение выбранного цвета
        Color selectedColor = JColorChooser.showDialog(null, "Выберите цвет фона", Color.WHITE);

        if (selectedColor != null) {
            // Если цвет был выбран, установите его в качестве цвета фона JTextArea
            gui.textArea.setBackground(selectedColor);
        }
    }

    // reset цвета текста и фона текста
    public void resetText()
    {
        int caret = gui.textArea.getCaretPosition();
        StyledDocument doc = gui.textArea.getStyledDocument();
        Style style = doc.addStyle("newStyle", null);

        // Сброс цвета текста на значение по умолчанию
        StyleConstants.setForeground(style, Color.BLACK);
        doc.setParagraphAttributes(caret, doc.getLength() - caret, style, false);

        // Сброс цвета фона на значение по умолчанию
        MutableAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setBackground(attrs, gui.textArea.getBackground());

        gui.textArea.setCharacterAttributes(attrs, true);
        StyleConstants.setBackground(style, gui.textArea.getBackground());
        doc.setParagraphAttributes(caret, doc.getLength() - caret, style, false);
    }
}
