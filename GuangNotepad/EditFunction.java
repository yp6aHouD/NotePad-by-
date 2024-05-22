package GuangNotepad;

public class EditFunction 
{
    private GUI gui;

    public EditFunction(GUI gui)
    {
        this.gui = gui;
    }

    // Функция "отмена назад"
    public void undo()
    {
        gui.um.undo();
        gui.currentPopup = new PopupMessage(gui, "Undo");
        gui.currentPopup.setVisible(true);
    }

    // Функция "возврат вперёд"
    public void redo()
    {
        gui.um.redo();
        gui.currentPopup = new PopupMessage(gui, "Redo");
        gui.currentPopup.setVisible(true);
    }


    // Функция вырезания текста
    public void cut()
    {
        String selectedText = gui.textArea.getSelectedText();

        if (selectedText != null)
        {
            gui.textArea.copy();
            gui.textArea.replaceSelection("");
        }
        else 
        {
            gui.currentPopup = new PopupMessage(gui, "No text selected");
            gui.currentPopup.setVisible(true);
        }
    }

    // Функция копирования текста
    public void copy()
    {
        String selectedText = gui.textArea.getSelectedText();

        if (selectedText != null)
        {
            gui.textArea.copy();
            gui.currentPopup = new PopupMessage(gui, "Copied!");
            gui.currentPopup.setVisible(true);
        }
        else
        {
            gui.currentPopup = new PopupMessage(gui, "No text selected");
            gui.currentPopup.setVisible(true);
        }
    }

    // Функция вставки
    public void paste()
    {
        // сохраняем текущую позицию курсора
        int position = gui.textArea.getCaretPosition(); 

        // вставляем текст
        gui.textArea.paste();

        // устанавливаем позицию курсора в конец вставленного текста
        gui.textArea.setCaretPosition(position + gui.textArea.getSelectedText().length());
    }

    // Функция поиска
    public void find() 
    {
        new SearchFunction(gui);
    }
}
