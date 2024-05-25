package GuangNotepad;

import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;

/* 
 * All formatting in the program works through StyledDocument and Document class
 * These classes allow you to format parts of the text, not the entire text
 * Including you can change the color, font, text size in the selected area
 * 
 * 这个程序中的所有格式化都是通过StyledDocument和Document类来实现的
 * 这些类允许你格式化文本的部分，而不是整个文本
 * 包括你可以在选定区域改变颜色，字体，文本大小
 */


// "Format" menu class
// 功能"字体和大小"的类

public class FormatFunction
{
    private GUI gui;
    private static String selectedFont;
    private static String selectedFontStyle;
    private static int selectedFontSize;

    public FormatFunction(GUI gui)
    {
        this.gui = gui;
    }

    // Method "Font and size"
    // "字体和大小" 方法
    public void setTextFontAndSize() 
    {
        // Getting the current font, size, and style of the text
        // 获取当前文本的字体、大小和样式
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
        
        // Getting and creating a dropdown list with fonts
        // 获取并创建一个带有字体的下拉列表
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        JComboBox<String> fontList = new JComboBox<>(fonts);
        fontList.setEditable(true);
        fontList.setSelectedItem(selectedFont);
        
        // Creating a dropdown list with sizes
        // 创建一个包含大小的下拉列表
        Integer[] fontSizes = {8, 10, 11, 12, 13, 14, 16, 18, 20, 22, 24, 26, 28, 30};
        JComboBox<Integer> fontSizeList = new JComboBox<>(fontSizes);
        fontSizeList.setEditable(false);
        fontSizeList.setSelectedItem(selectedFontSize);

        // Creating a dropdown list with text styles
        // 创建一个包含文本样式的下拉列表
        String[] fontStyles = {"Default", "Bold", "Italic", "Bold Italic"};
        JComboBox<String> fontStyleList = new JComboBox<>(fontStyles);
        fontStyleList.setEditable(false);
        fontStyleList.setSelectedItem(selectedFontStyle);
        
        // Creating a panel with dropdown lists
        // 创建一个带有下拉列表的面板
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        // Creating the first row of the panel
        // 创建面板的第一行
        JPanel firstRow = new JPanel();
        firstRow.add(new JLabel("Font:"));
        firstRow.add(fontList);
        firstRow.add(Box.createHorizontalStrut(10));
        firstRow.add(new JLabel("Size:"));
        firstRow.add(fontSizeList);

        // Creating the second row of the panel
        // 创建面板的第二行
        JPanel secondRow = new JPanel(); 
        secondRow.add(new JLabel("Style:"));
        secondRow.add(fontStyleList);

        // Adding rows to the panel
        // 将行添加到面板
        panel.add(firstRow); panel.add(secondRow);

        // Show the window and choose the font / size / style
        // 显示窗口并选择字体/大小/样式
        JOptionPane.showMessageDialog(null, panel, "Choose Font and Size", JOptionPane.QUESTION_MESSAGE);
        selectedFont = (String) fontList.getSelectedItem();
        selectedFontSize = (Integer) fontSizeList.getSelectedItem();
        selectedFontStyle = (String) fontStyleList.getSelectedItem();

        // Getting the selected text
        // 获取选定的文本
        String selectedText = gui.textArea.getSelectedText();

        if (selectedText != null) 
        {
            // Getting the start and end of the selected text
            // 获取选定文本的开始和结束
            int start = gui.textArea.getSelectionStart();
            int end = gui.textArea.getSelectionEnd();

            // Getting the StyledDocument
            // 获取StyledDocument
            StyledDocument doc = gui.textArea.getStyledDocument();

            // Creating a new style
            // 创建新样式
            Style style = doc.addStyle("NewFontAndSizeStyle", null);
            StyleConstants.setFontFamily(style, selectedFont);
            StyleConstants.setFontSize(style, selectedFontSize);

            // Switch for setting the text style
            // Switch用于设置文本样式
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

            // Applying the style to the selected text
            // 将样式应用于选定的文本
            doc.setCharacterAttributes(start, end - start, style, false);
        } 

        else 
        {
            // Getting the StyledDocument
            // 获取StyledDocument
            StyledDocument doc = gui.textArea.getStyledDocument();

            // Creating a new style
            // 创建新样式
            Style style = doc.addStyle("NewFontAndSizeStyle", null);
            StyleConstants.setFontFamily(style, selectedFont);
            StyleConstants.setFontSize(style, selectedFontSize);

            // Switch for setting the text style
            // 用Switch于设置文本样式
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

            // Setting the font, size and font style for all text if nothing is selected
            // 如果没有选择任何内容，则为所有文本设置字体、大小和字体样式
            MutableAttributeSet attrs = gui.textArea.getInputAttributes();
            attrs.removeAttribute(StyleConstants.FontFamily);
            attrs.removeAttribute(StyleConstants.FontSize);
            attrs.removeAttribute(StyleConstants.Bold);
            attrs.removeAttribute(StyleConstants.Italic);
            attrs.addAttributes(style);
        }
    }


    // Method "Text color"
    // "文本颜色" 方法
    public void setTextColor(boolean isTextColor)
    {
        // Displaying a dialog box
        // 显示对话框
        String dialogName = isTextColor ? "Choose text color" : "Choose text background color";
        Color defaultColor = isTextColor ? Color.BLACK : Color.WHITE;
        Color selectedColor = JColorChooser.showDialog(null, dialogName, defaultColor);
        String selectedText = gui.textArea.getSelectedText();
        
        // If text was selected
        // 如果选择了文本
        if (selectedText != null)
        {
            // Creating a style to change the color of only the selected text
            // 创建一个样式，只改变选定文本的颜色
            StyledDocument doc = gui.textArea.getStyledDocument();
            Style style = doc.addStyle("newStyle", null);

            // Getting the start and end of the selected text
            // 获取选定文本的开始和结束
            int start = gui.textArea.getSelectionStart();
            int end = gui.textArea.getSelectionEnd();
            
            // If the text color is selected
            // 如果选择了文本颜色
            if (selectedColor != null)
            {
                // Setting the color of the selected text using setAttributes to Document
                // 使用setAttributes将选定文本的颜色设置为Document
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
        
        // If no text was selected
        // 如果没有选择文本
        else if (selectedText == null)
        {
            if (selectedColor != null)
            {
                // Receive the StyledDocument
                // 获取StyledDocument
                StyledDocument doc = gui.textArea.getStyledDocument();

                // Creating a new style
                // 创建新样式
                Style style = doc.addStyle("NewColorStyle", null);

                // Setting new style
                // 设置新样式
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
                
                // Setting properties for new input text
                // 为新输入的文本设置属性
                MutableAttributeSet attrs = gui.textArea.getInputAttributes();
                attrs.addAttributes(style);
            }
        }

    }

    // Method "Area color"
    // "区域颜色" 方法
    public void setBackgroundColor() 
    {
        // Displaying the JColorChooser dialog box and getting the selected color
        // 显示JColorChooser对话框并获取选定的颜色
        Color selectedColor = JColorChooser.showDialog(null, "Choose area color", Color.WHITE);

        if (selectedColor != null) {
            // If a color was selected, setting it as the background color of JTextArea
            // 如果选择了颜色，则将其设置为JTextArea的背景颜色
            gui.textArea.setBackground(selectedColor);
        }
    }

    // Method “Quick highlight”
    // “快速高亮” 方法
    public void quickHighlight()
    {
        // Getting the selected text
        // 获取选定的文本
        String selectedText = gui.textArea.getSelectedText();
        if (selectedText != null)
        {
            // Receiving the start and end of the selected text
            // 获取选定文本的开始和结束
            int start = gui.textArea.getSelectionStart();
            int end = gui.textArea.getSelectionEnd();

            // Creating new style
            // 创建新样式
            StyledDocument doc = gui.textArea.getStyledDocument();
            SimpleAttributeSet attrs = new SimpleAttributeSet();
            StyleConstants.setBackground(attrs, Color.YELLOW);

            // Applying new style to selected region
            // 将新样式应用于选定区域
            doc.setCharacterAttributes(start, end - start, attrs, false);

            // Popup message
            // 弹出消息
            gui.currentPopup = new PopupMessage(gui, "Text highlighted!");
            gui.currentPopup.setVisible(true);
        }
        else return;
    }

    // Method "Reset text"
    // "重置文本" 方法
    public void resetText()
    {
        // Getting the current caret position and StyledDocument
        // 获取当前插入符位置和StyledDocument
        int caret = gui.textArea.getCaretPosition();
        StyledDocument doc = gui.textArea.getStyledDocument();
        Style style = doc.addStyle("newStyle", null);

        // Setting the color of the text to black
        // 将文本颜色设置为黑色
        StyleConstants.setForeground(style, Color.BLACK);
        doc.setParagraphAttributes(caret, doc.getLength() - caret, style, false);

        // Setting the background color of the text to the background color of JTextArea
        // 将文本的背景颜色设置为JTextArea的背景颜色
        MutableAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setBackground(attrs, gui.textArea.getBackground());

        // Setting the font and size of the text to the default
        // 将文本的字体和大小设置为默认值
        StyleConstants.setFontFamily(attrs, "Default");
        StyleConstants.setFontSize(attrs, 13);

        // Applying the attributes to the text
        // 将属性应用于文本
        gui.textArea.setCharacterAttributes(attrs, true);
        StyleConstants.setBackground(style, gui.textArea.getBackground());
        doc.setParagraphAttributes(caret, doc.getLength() - caret, style, false);

        // Popup message
        // 弹出消息
        gui.currentPopup = new PopupMessage(gui, "Text settings has been reset!");
        gui.currentPopup.setVisible(true);
    }
}
