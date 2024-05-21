package NotepadChenGuang;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class SearchFunction implements ActionListener, KeyListener
{
    private GUI gui;
    private JDialog findReplaceDialog;
    private JTextField searchField, replaceField;
    private JButton closeButton, searchButton, replaceButton,
        prevButton, nextButton;
    private JLabel searchLabel, counterLabel;
    private List<Integer> indices;
    private int currentIndex;

    public SearchFunction(GUI gui)
    {
        this.gui = gui;

        // Создаем окно и расположение объектов
        Point location = gui.menuBar.getLocationOnScreen();
        Dimension screenSize = gui.menuBar.getSize();
        findReplaceDialog = new JDialog(gui.window, false);
        GridBagLayout dialogLayout = new GridBagLayout();

        // Задаем размер и стандартные настройки окна
        findReplaceDialog.setLayout(dialogLayout);
        findReplaceDialog.setSize(250, 150);
        findReplaceDialog.setUndecorated(true);
        findReplaceDialog.setLocationRelativeTo(gui.menuBar);
        
        // Устанавливаем изначальное положение окна
        findReplaceDialog.setLocation(location.x + screenSize.width - 
            findReplaceDialog.getWidth() - 10, location.y + 40);

        // Создание кнопки для закрытия окна поиска
        closeButton = new JButton("X");
        Dimension closeButtonSize = new Dimension(15, 15);
        closeButton.setPreferredSize(closeButtonSize);
        closeButton.addActionListener(this);
        closeButton.setActionCommand("Close");

        // Создаем объект GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();

        // Устанавливаем метку поиска
        searchLabel = new JLabel("Search in the text");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        findReplaceDialog.add(searchLabel, gbc);

        // Устанавливаем кнопку выхода в верхний правый угол
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        findReplaceDialog.add(closeButton, gbc);
        
        // Создаем поля ввода и кнопки поиска и замены
        searchField = new JTextField(10);
        searchField.addKeyListener(this);

        searchButton = new JButton("Search");
        searchButton.addActionListener(this);
        searchButton.setActionCommand("Search");

        replaceField = new JTextField(10);
        replaceField.addKeyListener(this);

        replaceButton = new JButton("Replace");
        replaceButton.addActionListener(this);
        replaceButton.setActionCommand("Replace");

        nextButton = new JButton("Next");
        nextButton.addActionListener(this);
        nextButton.setActionCommand("Next");

        // панель для помещения 2 объектов в 1 ячейку
        JPanel panel = new JPanel();
        counterLabel = new JLabel("0/0");
        prevButton = new JButton("Prev");
        
        panel.add(counterLabel); panel.add(prevButton);

        prevButton.addActionListener(this);
        prevButton.setActionCommand("Prev");

        // Сбрасываем значения GridBagConstraints
        gbc.anchor = GridBagConstraints.WEST;

        // Поле поиска и кнопка "Search"
        gbc.gridx = 0;
        gbc.gridy = 1;
        findReplaceDialog.add(searchField, gbc);

        gbc.gridx = 1;
        findReplaceDialog.add(searchButton, gbc);

        // панель с кнопкой prev и счетчиком + кнопка next
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        findReplaceDialog.add(panel, gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        findReplaceDialog.add(nextButton, gbc);

        // Поле замены и кнопка "Replace"
        gbc.gridx = 0;
        gbc.gridy = 3;
        findReplaceDialog.add(replaceField, gbc);

        gbc.gridx = 1;
        findReplaceDialog.add(replaceButton, gbc);



        // Обработка перемещения окна при перемещении родительского
        gui.window.addComponentListener(new ComponentAdapter() 
        {
            // Прописываем, как будет вести себя окно при сдвиге родительского компонента
            @Override
            public void componentMoved(ComponentEvent e) 
            {
                Point newLocation = gui.menuBar.getLocationOnScreen();
                findReplaceDialog.setLocation(newLocation.x + screenSize.width - 
                    findReplaceDialog.getWidth() - 10, newLocation.y + 40);
            }
        });

        // Отображаем окно
        findReplaceDialog.setVisible(true);

        // Устанавливаем каретку в поле поиска
        searchField.requestFocusInWindow();

        // Добавляем слушатель для поля ввода текста после создания окна
        gui.textArea.addKeyListener(this);
    }

    // Функция поиска
    public void performSearch()
    {
        String text = gui.textArea.getText(); // текст, в котором ищем
        String word = searchField.getText(); // слово, которое ищем
        
        // Список для хранения индексов всех вхождений
        indices = new ArrayList<>();

        // Ищем все вхождения слова в текст
        if (!word.equals("") && !text.equals(""))
        {
            currentIndex = text.indexOf(word);
            while (currentIndex != -1) 
            {
                indices.add(currentIndex);
                currentIndex = text.indexOf(word, currentIndex + 1);
            }
            currentIndex = 0;
            // Если вхождений нет, показываем всплывающее сообщение
            if (indices.isEmpty()) 
            {
                counterLabel.setText("0/0");
                gui.currentPopup = new PopupMessage(gui, "No matches found!");
                gui.currentPopup.setVisible(true);
            } 

            else 
            {
                // Обновляем counterLabel
                counterLabel.setText("1/" + indices.size());
                
                // Устанавливаем курсор на первое вхождение
                gui.textArea.setCaretPosition(indices.get(0));
                
                // Выделяем найденное слово
                gui.textArea.setSelectionStart(indices.get(0));
                gui.textArea.setSelectionEnd(indices.get(0) + word.length());
            }
        }

        else if (word.equals(""))
        {
            gui.currentPopup = new PopupMessage(gui, "You did'nt enter anything to search!");
            gui.currentPopup.setVisible(true);
        }

        else if (text.equals(""))
        {
            gui.currentPopup = new PopupMessage(gui, "There's no text to search!");
            gui.currentPopup.setVisible(true);
        }

        else
        {
            gui.currentPopup = new PopupMessage(gui, "Search error");
            gui.currentPopup.setVisible(true);
        }
    }

    // Функция для переключения на следующее вхождение
    private void nextMatch()
    {
        if (!indices.isEmpty())
        {
            // Увеличиваем текущий индекс и оборачиваем его, если он превышает размер списка
            currentIndex = (currentIndex + 1) % indices.size();
    
            // Обновляем counterLabel
            counterLabel.setText((currentIndex + 1) + "/" + indices.size());
    
            // Устанавливаем курсор на следующее вхождение
            gui.textArea.setCaretPosition(indices.get(currentIndex));
    
            // Выделяем найденное слово
            gui.textArea.setSelectionStart(indices.get(currentIndex));
            gui.textArea.setSelectionEnd(indices.get(currentIndex) + searchField.getText().length());
        }
    }
    
    // Функция для переключения на предыдущее вхождения
    private void prevMatch()
    {
        if (!indices.isEmpty())
        {
            // Уменьшаем текущий индекс и оборачиваем его, если он становится отрицательным
            currentIndex = (currentIndex - 1 + indices.size()) % indices.size();
    
            // Обновляем counterLabel
            counterLabel.setText((currentIndex + 1) + "/" + indices.size());
    
            // Устанавливаем курсор на предыдущее вхождение
            gui.textArea.setCaretPosition(indices.get(currentIndex));
    
            // Выделяем найденное слово
            gui.textArea.setSelectionStart(indices.get(currentIndex));
            gui.textArea.setSelectionEnd(indices.get(currentIndex) + searchField.getText().length());
        }
    }


    // Функция замены
    public void replaceText()
    {
        if (indices != null)
        {
            if (!indices.isEmpty()) 
            {
                try 
                {
                    // Получаем текст для замены из соответствующего текстового поля
                    String replacement = replaceField.getText();

                    // Получаем документ из JTextPane
                    Document doc = gui.textArea.getDocument();

                    // Получаем начало и конец выделенного текста
                    int start = indices.get(currentIndex);
                    int end = start + searchField.getText().length();

                    // Заменяем выделенный текст
                    doc.remove(start, end - start);
                    doc.insertString(start, replacement, null);

                    // Обновляем список индексов, так как текст был изменен
                    performSearch();
                } 
                catch (BadLocationException e) 
                {
                    e.printStackTrace();
                }
            }

            else
            {
                gui.currentPopup = new PopupMessage(gui, "There's no text to replace");
                gui.currentPopup.setVisible(true);
            }
        }
        else
        {
            gui.currentPopup = new PopupMessage(gui, "There's no text to replace or no text to enter replaced text");
            gui.currentPopup.setVisible(true);
        }
    }

    // Обработка нажатий кнопок
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        switch (e.getActionCommand())
        {
            case "Close":
                findReplaceDialog.dispose(); break;
            case "Search":
                performSearch(); break;
            case "Next":
                nextMatch(); break;
            case "Prev":
                prevMatch(); break;
            case "Replace":
                replaceText(); break;
        }
    }


    // Обработка нажатий клавиш
    @Override
    public void keyPressed(KeyEvent e) 
    {
        if (e.getSource() == gui.textArea)
        {
            if (e.getKeyCode() == 27) findReplaceDialog.dispose();  
        }
        else if (e.getSource() == replaceField)
        {
            if (e.getKeyCode() == 27) findReplaceDialog.dispose();
        }
        else
        {
            switch (e.getKeyCode())
            {
                case 27:
                    findReplaceDialog.dispose(); break;
                case 10:
                    performSearch(); break;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    @Override
    public void keyReleased(KeyEvent e)
    {

    }
}
