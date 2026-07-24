import * as vscode from 'vscode';

export function activate(context: vscode.ExtensionContext) {
    const COMMAND_ID = 'copy-folder-path.copy';

    const copyPath = async () => {
        const folders = vscode.workspace.workspaceFolders;
        if (!folders || folders.length === 0) {
            vscode.window.showWarningMessage('No folder is currently open.');
            return;
        }

        // If multiple workspace folders, let user pick
        if (folders.length === 1) {
            const path = folders[0].uri.fsPath;
            await vscode.env.clipboard.writeText(path);
            vscode.window.showInformationMessage(`Copied: ${path}`);
        } else {
            const items = folders.map(f => ({
                label: f.name,
                description: f.uri.fsPath,
                fsPath: f.uri.fsPath,
            }));
            const picked = await vscode.window.showQuickPick(items, {
                placeHolder: 'Select a workspace folder to copy its path',
            });
            if (picked) {
                await vscode.env.clipboard.writeText(picked.fsPath);
                vscode.window.showInformationMessage(`Copied: ${picked.fsPath}`);
            }
        }
    };

    const command = vscode.commands.registerCommand(COMMAND_ID, copyPath);

    const statusBar = vscode.window.createStatusBarItem(
        vscode.StatusBarAlignment.Right,
        1000
    );
    statusBar.command = COMMAND_ID;
    statusBar.text = '$(folder) Copy Path';
    statusBar.tooltip = 'Copy workspace folder path to clipboard';
    statusBar.show();

    // Update tooltip to show current path on hover
    const updateTooltip = () => {
        const folders = vscode.workspace.workspaceFolders;
        if (folders && folders.length > 0) {
            statusBar.tooltip = `Copy: ${folders[0].uri.fsPath}`;
            statusBar.show();
        } else {
            statusBar.tooltip = 'No folder open';
        }
    };

    updateTooltip();
    const workspaceListener = vscode.workspace.onDidChangeWorkspaceFolders(updateTooltip);

    context.subscriptions.push(command, statusBar, workspaceListener);
}

export function deactivate() {}
