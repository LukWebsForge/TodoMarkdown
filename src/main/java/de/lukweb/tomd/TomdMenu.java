package de.lukweb.tomd;

import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

public class TomdMenu extends AnAction {

    private NotificationGroup notificationGroup;
    private TomdSettings settings;

    public TomdMenu() {
        this.notificationGroup =
                new NotificationGroup("TodoMarkdown", NotificationDisplayType.BALLOON, false);
        this.settings = TomdSettings.getInstance();
    }

    @Override
    public void update(@NotNull AnActionEvent event) {
        Project project = event.getData(PlatformDataKeys.PROJECT);

        if (project == null) {
            event.getPresentation().setEnabled(false);
            return;
        }

        event.getPresentation().setEnabled(true);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Project project = event.getData(PlatformDataKeys.PROJECT);
        VirtualFile virtualFile = event.getData(PlatformDataKeys.VIRTUAL_FILE);
        PsiFile psiFile = event.getData(PlatformDataKeys.PSI_FILE);
        Editor editor = event.getData(PlatformDataKeys.EDITOR);

        if (project == null) {
            showNotification("An error occured while getting the Project (null)", NotificationType.ERROR);
            return;
        }

        TomdWriter writer = new TomdWriter(settings, project);

        if (editor != null && virtualFile != null && TomdWriter.isMarkdownExtension(virtualFile.getExtension())) {
            writer.createUpdateSingle(editor, psiFile);
        } else {
            new Task.Backgroundable(project, "Updating TODO in Markdown Files...") {
                @Override
                public void run(@NotNull ProgressIndicator indicator) {
                    indicator.setIndeterminate(true);

                    int updatedFiles = writer.updateAll();
                    showNotification("Updated " + updatedFiles +
                            " markdown file(s) with TODO lists", NotificationType.INFORMATION);
                }
            }.queue();
        }
    }

    private void showNotification(String text, NotificationType type) {
        notificationGroup.createNotification(text, type).notify(null);
    }
}
