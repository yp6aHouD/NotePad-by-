package GuangNotepad;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.text.DefaultEditorKit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class RightClickMenu implements ActionListener
{
    private GUI gui;
    FormatFunction formatFunction;

    public RightClickMenu(GUI gui) 
    {
        this.gui = gui;
        formatFunction = new FormatFunction(gui);

        // Create the right-click menu
        JPopupMenu popupMenu = new JPopupMenu();

        // Create the menu items and add action listeners
        JMenuItem mouseCut = new JMenuItem("Cut");
        mouseCut.addActionListener(this);
        mouseCut.setActionCommand(DefaultEditorKit.cutAction);

        JMenuItem mouseCopy = new JMenuItem("Copy");
        mouseCopy.addActionListener(this);
        mouseCopy.setActionCommand(DefaultEditorKit.copyAction);

        JMenuItem mousePaste = new JMenuItem("Paste");
        mousePaste.addActionListener(this);
        mousePaste.setActionCommand(DefaultEditorKit.pasteAction);

        JMenuItem mouseFont = new JMenuItem("Font settings");
        mouseFont.addActionListener(this);
        mouseFont.setActionCommand("Font");

        // Add the menu items to the popup menu
        popupMenu.add(mouseCut);
        popupMenu.add(mouseCopy);
        popupMenu.add(mousePaste);
        popupMenu.add(mouseFont);
        gui.textArea.setComponentPopupMenu(popupMenu);

        // Attach the popup menu to the text component
        gui.textArea.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseReleased(MouseEvent e)
            {
                if (e.isPopupTrigger())
                {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        switch (e.getActionCommand())
        {
            case "Cut": gui.textArea.cut(); break;
            case "Copy": gui.textArea.copy(); break;
            case "Paste": gui.textArea.paste(); break;
            case "Font": formatFunction.setTextFontAndSize(); break;
        }
    }
}