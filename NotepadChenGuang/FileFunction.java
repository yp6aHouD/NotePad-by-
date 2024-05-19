package NotepadChenGuang;

import java.awt.FileDialog;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.text.Document;

public class FileFunction
{
    private Document d;
    private GUI gui;
    private String fileName, fileAddress;
    private PopupMessage popupMessage;

    private boolean isNewFile = true;

    public FileFunction(GUI gui)
    {
        this.gui = gui;
    }

    public void newFile()
    {
        isNewFile = true;
        d = gui.textArea.getDocument();
        if (d.getLength() > 0)
        {
            // сделать вопрос "сохранить ли файл"
            // если да, то вызвать saveFile()
        }
        gui.textArea.setText("");
        gui.window.setTitle(("New"));

        popupMessage = new PopupMessage(gui, "File Created!");
        popupMessage.setVisible(true);
    }
    
    public void openFile()
    {
        FileDialog fd = new FileDialog(gui.window, "Open", FileDialog.LOAD);
        fd.setVisible(true);

        if (fd.getFile() != null)
        {
            fileName = fd.getFile();
            fileAddress = fd.getDirectory();
            gui.window.setTitle(fileName);
            isNewFile = false;
        }
        else 
        {
            popupMessage = new PopupMessage(gui, "File wasn't opened!");
            popupMessage.setVisible(true);
            return;
        }

        try
        {
            d = gui.textArea.getDocument();
            BufferedReader br = new BufferedReader(new FileReader(fileAddress + fileName));
            gui.textArea.setText("");

            String line = null;
            while ((line = br.readLine()) != null)
            {
                d.insertString(0, line, null);
            }
            br.close();
        }
        catch (Exception e)
        {
            popupMessage = new PopupMessage(gui, "Exception when opening file!\n" + e.toString());
            popupMessage.setVisible(true);
        }
    }

    public void save()
    {
        if (isNewFile)
        {
            saveAs();
        }
        else
        {
            try
            {
                FileWriter fw = new FileWriter(fileAddress + fileName);
                fw.write(gui.textArea.getText());
                fw.close();
                popupMessage = new PopupMessage(gui, "File Saved!");
                popupMessage.setVisible(true);
            }
            catch (Exception e)
            {
                popupMessage = new PopupMessage(gui, "Exception when saving file!\n" + e.toString());
                popupMessage.setVisible(true);
            }
        }
    }

    public void saveAs()
    {
        FileDialog fd = new FileDialog(gui.window, "save", FileDialog.SAVE);
        fd.setVisible(true);

        if (fd.getFile() != null)
        {
            fileName = fd.getFile();
            fileAddress = fd.getDirectory();
            gui.window.setTitle(fileName);
        }
        else
        {
            popupMessage = new PopupMessage(gui, "File wasn't saved!");
            popupMessage.setVisible(true);
            return;
        }

        try 
        {
            FileWriter fw = new FileWriter(fileAddress + fileName);
            fw.write(gui.textArea.getText());
            fw.close();
            popupMessage = new PopupMessage(gui, "File saved!");
            popupMessage.setVisible(true);
        }
        catch (Exception e)
        {
            popupMessage = new PopupMessage(gui, "Exception when saving file!\n" + e.toString());
            popupMessage.setVisible(true);
        }
    }

    public void exit()
    {
        System.exit(0);
    }
}
