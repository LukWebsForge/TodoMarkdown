package de.lukweb.tomd;

public class TomdSettingsState {

    private String filenameTemplate;
    private String todoEntryTemplate;

    public TomdSettingsState() {
        filenameTemplate = "**[$name](./$path)**:";
        todoEntryTemplate = "* $text / $pattern";
    }

    public String getFilenameTemplate() {
        return filenameTemplate;
    }

    public void setFilenameTemplate(String filenameTemplate) {
        this.filenameTemplate = filenameTemplate;
    }

    public String getTodoEntryTemplate() {
        return todoEntryTemplate;
    }

    public void setTodoEntryTemplate(String todoEntryTemplate) {
        this.todoEntryTemplate = todoEntryTemplate;
    }
}
