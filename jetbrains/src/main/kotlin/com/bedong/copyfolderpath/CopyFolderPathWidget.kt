package com.bedong.copyfolderpath

import com.intellij.icons.AllIcons
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.CustomStatusBarWidget
import com.intellij.ui.components.JBLabel
import com.intellij.util.ui.JBUI
import java.awt.Cursor
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JComponent
import javax.swing.SwingConstants

class CopyFolderPathWidget(private val project: Project) : CustomStatusBarWidget {

    private val label = JBLabel("Copy Path", AllIcons.Nodes.Folder, SwingConstants.CENTER).apply {
        cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        border = JBUI.Borders.empty(0, 4)
        toolTipText = projectTooltip()
        addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) = copyProjectPath()
        })
    }

    override fun ID(): String = WIDGET_ID

    override fun getComponent(): JComponent = label

    override fun dispose() = Unit

    private fun copyProjectPath() {
        val path = project.basePath ?: return
        Toolkit.getDefaultToolkit().systemClipboard.setContents(StringSelection(path), null)
        NotificationGroupManager.getInstance()
            .getNotificationGroup("Copy Folder Path")
            .createNotification("Copied: $path", NotificationType.INFORMATION)
            .notify(project)
    }

    private fun projectTooltip(): String =
        project.basePath?.let { "Copy: $it" } ?: "No project path"

    companion object {
        const val WIDGET_ID = "CopyFolderPathWidget"
    }
}
