package NotepadChenGuang;

// TODO: make hotkeys

public class EditFunction 
{
    private GUI gui;
    private PopupMessage popupMessage;

    public EditFunction(GUI gui)
    {
        this.gui = gui;
    }

    // Функция "отмена назад"
    public void undo()
    {
        gui.um.undo();
        popupMessage = new PopupMessage(gui, "Undo");
        popupMessage.setVisible(true);
    }

    // Функция "возврат вперёд"
    public void redo()
    {
        gui.um.redo();
        popupMessage = new PopupMessage(gui, "Redo");
        popupMessage.setVisible(true);
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
            popupMessage = new PopupMessage(gui, "No text selected");
            popupMessage.setVisible(true);
        }
    }

    // Функция копирования текста
    public void copy()
    {
        String selectedText = gui.textArea.getSelectedText();

        if (selectedText != null)
        {
            gui.textArea.copy();
        }
        else
        {
            popupMessage = new PopupMessage(gui, "No text selected");
            popupMessage.setVisible(true);
        }
    }

    // Функция вставки
    public void paste()
    {
        gui.textArea.paste();
    }

    // Функция поиска
    public void find() 
    {
        new SearchFunction(gui);
    }
}
