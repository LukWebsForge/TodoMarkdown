package de.lukweb.tomd;

import com.intellij.lang.Language;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ui.EditorTextField;
import org.intellij.plugins.markdown.lang.MarkdownLanguage;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class TomdSettingsPage implements Configurable {

    private TomdSettings settings;

    private JPanel panelMain;
    private JLabel labelFilename;
    private JLabel labelTodoEntry;
    private EditorTextField textFilename;
    private EditorTextField textTodoEntry;

    public TomdSettingsPage(TomdSettings settings) {
        this.settings = settings;
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Todo Markdown";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        MarkdownLanguage mdLang = Language.findInstance(MarkdownLanguage.class);
        if (mdLang.getAssociatedFileType() != null) {
            textFilename.setFileType(mdLang.getAssociatedFileType());
            textTodoEntry.setFileType(mdLang.getAssociatedFileType());
        }

        textFilename.setOneLineMode(true);
        textTodoEntry.setOneLineMode(true);

        labelFilename.setText("<html><b>Filename template</b><div style=\"margin: 8px 5px\">" +
                "$name: The name of the file with extension<br/>" +
                "$path: A path relative to the markdown<br/>" +
                "$nl: Adds a new line character" +
                "</div></html>");
        labelTodoEntry.setText("<html><br/><b>Entry template</b><div style=\"margin: 8px 5px\">" +
                "$text: The text of the todo entry<br/>" +
                "$pattern: The regex pattern, which was used to find this entry<br/>" +
                "$nl: Adds a new line character" +
                "</div></html>");

        labelFilename.setLabelFor(textFilename);
        labelTodoEntry.setLabelFor(textTodoEntry);

        loadFromSettings();

        return panelMain;
    }

    @Override
    public boolean isModified() {
        TomdSettingsState state = settings.getState();
        if (isModified(textFilename, state.getFilenameTemplate())) {
            return true;
        }

        if (isModified(textTodoEntry, state.getTodoEntryTemplate())) {
            return true;
        }

        return false;
    }

    private boolean isModified(EditorTextField textField, String text) {
        return !StringUtil.equals(textField.getText(), text);
    }

    @Override
    public void apply() throws ConfigurationException {
        TomdSettingsState state = settings.getState();
        state.setFilenameTemplate(textFilename.getText());
        state.setTodoEntryTemplate(textTodoEntry.getText());
    }

    @Override
    public void reset() {
        loadFromSettings();
    }

    private void loadFromSettings() {
        textFilename.setText(settings.getState().getFilenameTemplate());
        textTodoEntry.setText(settings.getState().getTodoEntryTemplate());
    }
}
