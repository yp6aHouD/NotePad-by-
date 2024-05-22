package GuangNotepad;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;

// TODO: сделать вкладки с разными файлами, чтобы можно было открывать несколько за раз

public class GUI implements ActionListener
{
    // Main window
    JFrame window;

    // Popup message
    PopupMessage currentPopup;
    
    // TextArea
    JTextPane textArea;
    JScrollPane scrollPane;

    // Top Menu Bar
    JMenuBar menuBar;
    JMenu fileMenu, editMenu, formatMenu;
    JMenuItem fNew, fOpen, fSave, fSaveAs, fExit,
    eUndo, eRedo, eCut, eCopy, ePaste, eFind,
    fFontAndSize, fTextColor, fTextHighlightColor,fBackgroundColor;
    
    // Buttons resetText and quickHighlight
    JButton resetText = new JButton("Reset text settings");
    JButton quickHighlight = new JButton("Highlight");

    // Functions for frame
    FileFunction fileFunction = new FileFunction(this);
    FormatFunction formatFunction = new FormatFunction(this);
    EditFunction editFunction = new EditFunction(this);
    HotkeyHandler hotkeyHandler = new HotkeyHandler(this);
    UndoManager um = new UndoManager();

    // TODO: make right mouse click menu

    // Constructor
    public GUI()
    {
        // Create window
        createWindow();

        // Create text area
        createTextPane();

        // Create menu bar
        createMenuBar();

        // Create menu "File"
        createFileMenu();

        // Create menu "Edit"
        createEditMenu();

        // Create menu "Format"
        createFormatMenu();
        
        // Set window visible
        window.setVisible(true);
    }

    // Create window
    public void createWindow()
    {
        window = new JFrame("NotePad by Neliubin Daniil");
        window.setSize(650, 450);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
    }

    // Create text area
    public void createTextPane()
    {
        textArea = new JTextPane();
        textArea.addKeyListener(hotkeyHandler);

        // Добавление undo/redo
        textArea.getDocument().addUndoableEditListener(new UndoableEditListener() {
            public void undoableEditHappened(UndoableEditEvent e)
            {
                um.addEdit(e.getEdit());
            }
        });

        // Создание и добавление scrollbar
        scrollPane = new JScrollPane(textArea, 
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        this.scrollPane.setBorder(BorderFactory.createEmptyBorder());
        window.add(scrollPane);
    }

    // Create menu bar
    public void createMenuBar()
    {
        menuBar = new JMenuBar();

        fileMenu = new JMenu("File");
        editMenu = new JMenu("Edit");
        formatMenu = new JMenu("Format");

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(formatMenu);
        
        menuBar.add(resetText);
        resetText.addActionListener(this);
        resetText.setActionCommand("Reset");
        resetText.setBorderPainted(false);
        
        menuBar.add(quickHighlight);
        quickHighlight.addActionListener(this);
        quickHighlight.setActionCommand("QuickHighlight");
        quickHighlight.setBackground(Color.YELLOW);
        quickHighlight.setOpaque(true);
        quickHighlight.setBorderPainted(false);   

        this.menuBar.setBorder(BorderFactory.createEmptyBorder());
        window.setJMenuBar(menuBar);
    }

    public void createFileMenu()
    {
        fNew = new JMenuItem("<html>New<span style='color: gray;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ctrl+N</span></html>");
        fNew.addActionListener(this);
        fNew.setActionCommand("New");
        fileMenu.add(fNew);
        
        fOpen = new JMenuItem("<html>Open<span style='color: gray;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ctrl+O</span></html>");
        fOpen.addActionListener(this);
        fOpen.setActionCommand("Open");
        fileMenu.add(fOpen);

        fSave = new JMenuItem("<html>Save<span style='color: gray;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ctrl+S</span></html>");
        fSave.addActionListener(this);
        fSave.setActionCommand("Save");
        fileMenu.add(fSave);

        fSaveAs = new JMenuItem("<html>Save as<span style='color: gray;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ctrl+Shift+S</span></html>");
        fSaveAs.addActionListener(this);
        fSaveAs.setActionCommand("SaveAs");
        fileMenu.add(fSaveAs);
        fileMenu.addSeparator();

        fExit = new JMenuItem("<html>Exit<span style='color: gray;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Alt + F4</span></html>");
        fExit.addActionListener(this);
        fExit.setActionCommand("Exit");
        fileMenu.add(fExit);

    }

    public void createEditMenu()
    {
        eUndo = new JMenuItem("<html>Undo<span style='color: gray;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ctrl + Z</span></html>");
        eUndo.addActionListener(this);
        eUndo.setActionCommand("Undo");
        editMenu.add(eUndo);

        eRedo = new JMenuItem("<html>Redo<span style='color: gray;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ctrl + Shift + Z</span></html>");
        eRedo.addActionListener(this);
        eRedo.setActionCommand("Redo");
        editMenu.add(eRedo);

        eCut = new JMenuItem("<html>Cut<span style='color: gray;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ctrl + X</span></html>");
        eCut.addActionListener(this);
        eCut.setActionCommand("Cut");
        editMenu.add(eCut);
        
        eCopy = new JMenuItem("<html>Copy<span style='color: gray;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ctrl + C</span></html>");
        eCopy.addActionListener(this);
        eCopy.setActionCommand("Copy");
        editMenu.add(eCopy);
        
        ePaste = new JMenuItem("<html>Paste<span style='color: gray;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ctrl + V</span></html>");
        ePaste.addActionListener(this);
        ePaste.setActionCommand("Paste");
        editMenu.add(ePaste);
        editMenu.addSeparator();

        eFind = new JMenuItem("<html>Find<span style='color: gray;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ctrl + F</span></html>");
        eFind.addActionListener(this);
        eFind.setActionCommand("Find");
        editMenu.add(eFind);
    }

    public void createFormatMenu()
    {
        fFontAndSize = new JMenuItem("<html>Font<span style='color: gray;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ctrl+Shift+T</span></html>");
        fFontAndSize.addActionListener(this);
        fFontAndSize.setActionCommand("FontAndSize");
        formatMenu.add(fFontAndSize);     
        
        fTextColor = new JMenuItem("<html>Text color<span style='color: gray;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ctrl+Shift+C</span></html>");
        fTextColor.addActionListener(this);
        fTextColor.setActionCommand("SetTextColor");
        formatMenu.add(fTextColor);

        fTextHighlightColor = new JMenuItem("<html>Text highlight<span style='color: gray;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ctrl+B</span></html>");
        fTextHighlightColor.addActionListener(this);
        fTextHighlightColor.setActionCommand("SetTextHighlight");
        formatMenu.add(fTextHighlightColor);
        formatMenu.addSeparator();

        fBackgroundColor = new JMenuItem("<html>Area color<span style='color: gray;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ctrl+Shift+B</span></html>");
        fBackgroundColor.addActionListener(this);
        fBackgroundColor.setActionCommand("SetBackground");
        formatMenu.add(fBackgroundColor);
    }

    // Обработка нажатия кнопок меню
    @Override
    public void actionPerformed(ActionEvent e)
    {
        String command = e.getActionCommand();

        switch(command)
        {
            case "New": 
                fileFunction.newFile(); break;
            case "Open":
                fileFunction.openFile(); break;
            case "Save":
                fileFunction.save(); break;
            case "SaveAs":
                fileFunction.saveAs(); break;
            case "Exit":
                fileFunction.exit(); break;
            case "FontAndSize":
                formatFunction.setTextFontAndSize(); break;
            case "SetTextColor":
                formatFunction.setTextColor(true); break;
            case "SetTextHighlight":
                formatFunction.setTextColor(false); break;
            case "SetBackground":
                formatFunction.setBackgroundColor(); break;
            case "Reset":
                formatFunction.resetText(); break;
            case "QuickHighlight":
                formatFunction.quickHighlight(); break;
            case "Undo":
                editFunction.undo(); break;
            case "Redo":
                editFunction.redo(); break;
            case "Cut":
                editFunction.cut(); break;
            case "Copy":
                editFunction.copy(); break;
            case "Paste":
                editFunction.paste(); break;
            case "Find":
                editFunction.find(); break;
        }
    }
}