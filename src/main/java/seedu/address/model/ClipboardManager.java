package seedu.address.model;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import seedu.address.model.person.Person;

public class ClipboardManager {

    /**
     * Initializes a ClipboardManager
     */
    public ClipboardManager(){

    }

    /**
     * Copies the person to the system clipboard.
     * @param toCopy
     */
    public void copy(Person toCopy) {

        String str = toCopy.toString();

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = toolkit.getSystemClipboard();
        StringSelection strSel = new StringSelection(str);
        clipboard.setContents(strSel, null);

    }

}
