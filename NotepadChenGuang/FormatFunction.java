package NotepadChenGuang;

import java.awt.Color;
import java.awt.GraphicsEnvironment;
import javax.swing.Box;
import javax.swing.BoxLayout;
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
    private static String selectedFont;
    private static String selectedFontStyle;
    private static int selectedFontSize;
    private PopupMessage popupMessage;

    public FormatFunction(GUI gui)
    {
        this.gui = gui;
    }


    //установка шрифта и размера
    public void setTextFontAndSize() 
    {
        // Получение текущего шрифта, размера и стиля текста
        if (selectedFont == null)
        {
            selectedFont = gui.textArea.getFont().getFamily();
        }
        if (selectedFontSize == 0)
        {
            selectedFontSize = gui.textArea.getFont().getSize();
        }
        if (selectedFontStyle == null)
        {
            selectedFontStyle = "Default";
        }

        // Получение и создание выпадающего списка со шрифтами
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        JComboBox<String> fontList = new JComboBox<>(fonts);
        fontList.setEditable(true);
        fontList.setSelectedItem(selectedFont);
        
        // Создание выпадающего списка с размерами
        Integer[] fontSizes = {8, 10, 11, 12, 13, 14, 16, 18, 20, 22, 24, 26, 28, 30};
        JComboBox<Integer> fontSizeList = new JComboBox<>(fontSizes);
        fontSizeList.setEditable(false);
        fontSizeList.setSelectedItem(selectedFontSize);

        // Создание выпадающего списка со стилями текста
        String[] fontStyles = {"Default", "Bold", "Italic", "Bold Italic"};
        JComboBox<String> fontStyleList = new JComboBox<>(fontStyles);
        fontStyleList.setEditable(false);
        fontStyleList.setSelectedItem(selectedFontStyle);
        
        // Создание панели с выпадающими списками
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        // Создание первого ряда панели
        JPanel firstRow = new JPanel();
        firstRow.add(new JLabel("Font:"));
        firstRow.add(fontList);
        firstRow.add(Box.createHorizontalStrut(10)); // a spacer
        firstRow.add(new JLabel("Size:"));
        firstRow.add(fontSizeList);

        // Создание второго ряда панели
        JPanel secondRow = new JPanel(); 
        secondRow.add(new JLabel("Style:"));
        secondRow.add(fontStyleList);

        // Добавление рядов в панель
        panel.add(firstRow); panel.add(secondRow);

        // Показ окна и выбор шрифта / размера / стиля
        JOptionPane.showMessageDialog(null, panel, "Choose Font and Size", JOptionPane.QUESTION_MESSAGE);
        selectedFont = (String) fontList.getSelectedItem();
        selectedFontSize = (Integer) fontSizeList.getSelectedItem();
        selectedFontStyle = (String) fontStyleList.getSelectedItem();

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

            // switch для установки стиля текста
            switch (selectedFontStyle) 
            {
                case "Bold":
                    StyleConstants.setBold(style, true);
                    break;
                case "Italic":
                    StyleConstants.setItalic(style, true);
                    break;
                case "Bold Italic":
                    StyleConstants.setBold(style, true);
                    StyleConstants.setItalic(style, true);
                    break;
                default:
                    break;
            }

            // Применение стиля к выделенному тексту
            doc.setCharacterAttributes(start, end - start, style, false);
        } 

        else 
        {
            // Получение StyledDocument
            StyledDocument doc = gui.textArea.getStyledDocument();

            // Создание нового стиля
            Style style = doc.addStyle("NewFontAndSizeStyle", null);
            StyleConstants.setFontFamily(style, selectedFont);
            StyleConstants.setFontSize(style, selectedFontSize);

            // switch для установки стиля текста
            switch (selectedFontStyle) 
            {
                case "Bold":
                    StyleConstants.setBold(style, true);
                    break;
                case "Italic":
                    StyleConstants.setItalic(style, true);
                    break;
                case "Bold Italic":
                    StyleConstants.setBold(style, true);
                    StyleConstants.setItalic(style, true);
                    break;
                default:
                    break;
            }

            // Установка шрифта, размера и стиля шрифта для всего текста, если ничего не выделено
            MutableAttributeSet attrs = gui.textArea.getInputAttributes();
            attrs.removeAttribute(StyleConstants.FontFamily);
            attrs.removeAttribute(StyleConstants.FontSize);
            attrs.removeAttribute(StyleConstants.Bold);
            attrs.removeAttribute(StyleConstants.Italic);
            attrs.addAttributes(style);
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
                    gui.textArea.getInputAttributes().removeAttribute(StyleConstants.Foreground);
                }
                else
                {
                    StyleConstants.setBackground(style, selectedColor);
                    gui.textArea.getInputAttributes().removeAttribute(StyleConstants.Background);
                }
                // установка свойств для нового вводимого текста
                MutableAttributeSet attrs = gui.textArea.getInputAttributes();
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

    public void quickHighlight()
    {
        String selectedText = gui.textArea.getSelectedText();
        if (selectedText != null)
        {
            // Получение начала и конца выделенного текста
            int start = gui.textArea.getSelectionStart();
            int end = gui.textArea.getSelectionEnd();

            // Создание нового стиля
            StyledDocument doc = gui.textArea.getStyledDocument();
            SimpleAttributeSet attrs = new SimpleAttributeSet();
            StyleConstants.setBackground(attrs, Color.YELLOW);

            // Применение стиля
            doc.setCharacterAttributes(start, end - start, attrs, false);

            // уведомление
            popupMessage = new PopupMessage(gui, "Text highlighted!");
            popupMessage.setVisible(true);
        }
        else return;
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

        // Создание новых атрибутов текста
        MutableAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setBackground(attrs, gui.textArea.getBackground());

        // Сброс шрифта и размера текста на значение по умолчанию
        StyleConstants.setFontFamily(attrs, "Default");
        StyleConstants.setFontSize(attrs, 13);

        // Сброс цвета текста и фона на 
        gui.textArea.setCharacterAttributes(attrs, true);
        StyleConstants.setBackground(style, gui.textArea.getBackground());
        doc.setParagraphAttributes(caret, doc.getLength() - caret, style, false);

        // уведомление
        popupMessage = new PopupMessage(gui, "Text settings has been reset!");
        popupMessage.setVisible(true);
    }
}
