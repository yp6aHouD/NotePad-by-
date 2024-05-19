package NotepadChenGuang;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;


public class GUI implements ActionListener
{
    // Popup message
    PopupMessage popupMessage;
    JButton resetText = new JButton("Reset text settings");
    JFrame window;
    // TextArea
    JTextPane textArea;
    JScrollPane scrollPane;
    // Top Menu Bar
    JMenuBar menuBar;
    JMenu fileMenu, editMenu, formatMenu;
    JMenuItem fNew, fOpen, fSave, fSaveAs, fExit,
    eCut, eCopy, ePaste, eFind,
    fFontAndSize, fTextColor, fTextHighlightColor,fBackgroundColor;

    FileFunction fileFunction = new FileFunction(this);
    FormatFunction formatFunction = new FormatFunction(this);


    public GUI()
    {
        createWindow();
        createTextPane();
        createMenuBar();
        createFileMenu();
        createEditMenu();
        createFormatMenu();
        
        window.setVisible(true);
    }

    public void createWindow()
    {
        window = new JFrame("NotePad by Neliubin Daniil");
        window.setSize(800, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
    }

    public void createTextPane()
    {
        textArea = new JTextPane();
        scrollPane = new JScrollPane(textArea, 
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        this.scrollPane.setBorder(BorderFactory.createEmptyBorder());
        window.add(scrollPane);
    }

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

        this.menuBar.setBorder(BorderFactory.createEmptyBorder());
        window.setJMenuBar(menuBar);
    }

    public void createFileMenu()
    {
        fNew = new JMenuItem("New");
        fNew.addActionListener(this);
        fNew.setActionCommand("New");
        fileMenu.add(fNew);
        
        fOpen = new JMenuItem("Open");
        fOpen.addActionListener(this);
        fOpen.setActionCommand("Open");
        fileMenu.add(fOpen);

        fSave = new JMenuItem("Save");
        fSave.addActionListener(this);
        fSave.setActionCommand("Save");
        fileMenu.add(fSave);

        fSaveAs = new JMenuItem("Save as");
        fSaveAs.addActionListener(this);
        fSaveAs.setActionCommand("SaveAs");
        fileMenu.add(fSaveAs);
        fileMenu.addSeparator();

        fExit = new JMenuItem("Exit");
        fExit.addActionListener(this);
        fExit.setActionCommand("Exit");
        fileMenu.add(fExit);

    }

    public void createEditMenu()
    {
        eCut = new JMenuItem("Cut");
        eCut.addActionListener(this);
        eCut.setActionCommand("Cut");
        editMenu.add(eCut);
        
        eCopy = new JMenuItem("Copy");
        eCopy.addActionListener(this);
        eCopy.setActionCommand("Copy");
        editMenu.add(eCopy);
        
        ePaste = new JMenuItem("Paste");
        ePaste.addActionListener(this);
        ePaste.setActionCommand("Paste");
        editMenu.add(ePaste);
        editMenu.addSeparator();

        eFind = new JMenuItem("Find");
        eFind.addActionListener(this);
        eFind.setActionCommand("Find");
        editMenu.add(eFind);
    }

    public void createFormatMenu()
    {
        fFontAndSize = new JMenuItem("Font and size");
        fFontAndSize.addActionListener(this);
        fFontAndSize.setActionCommand("FontAndSize");
        formatMenu.add(fFontAndSize);     
        
        fTextColor = new JMenuItem("Text color");
        fTextColor.addActionListener(this);
        fTextColor.setActionCommand("SetTextColor");
        formatMenu.add(fTextColor);

        fTextHighlightColor = new JMenuItem("Highlight text");
        fTextHighlightColor.addActionListener(this);
        fTextHighlightColor.setActionCommand("SetTextHighlight");
        formatMenu.add(fTextHighlightColor);
        formatMenu.addSeparator();

        fBackgroundColor = new JMenuItem("Background color");
        fBackgroundColor.addActionListener(this);
        fBackgroundColor.setActionCommand("SetBackground");
        formatMenu.add(fBackgroundColor);
    }

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
            
        }
    }

    public static void main(String[] args)
    {
        new GUI();
    }
}