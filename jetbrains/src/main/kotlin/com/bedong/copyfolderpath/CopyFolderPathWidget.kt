package com.bedong.copyfolderpath

import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.wm.StatusBar
import com.intellij.openapi.wm.StatusBarWidget
import com.intellij.openapi.wm.StatusBarWidget.TextPresentation
import com.intellij.util.Consumer
import java.awt.datatransfer.StringSelection
import java.awt.event.MouseEvent
import java.awt.Toolkit

class CopyFolderPathWidget(private val project: Project) : StatusBarWidget, TextPresentation {

    private var statusBar: StatusBar? = null

    override fun ID(): String = WIDGET_ID

    override fun getPresentation(): StatusBarWidget.WidgetPresentation = this

    override fun install(statusBar: StatusBar) {
        this.statusBar = statusBar
    }

    override fun dispose() {
        statusBar = null
    }

    // TextPresentation

    override fun getText(): String = "📁 Copy Path"

    override fun getTooltipText(): String =
        project.basePath?.let { "Copy: $it" } ?: "No project path"

    override fun getAlignment(): Float = 0f

    override fun getClickConsumer(): Consumer<MouseEvent> = Consumer {
        val path = project.basePath ?: return@Consumer
        Toolkit.getDefaultToolkit().systemClipboard.setContents(StringSelection(path), null)
        NotificationGroupManager.getInstance()
            .getNotificationGroup("Copy Folder Path")
            .createNotification("Copied: $path", NotificationType.INFORMATION)
            .notify(project)
    }

    companion object {
        const val WIDGET_ID = "CopyFolderPathWidget"
    }
}
