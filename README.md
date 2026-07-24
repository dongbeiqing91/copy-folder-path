# Copy Folder Path

A lightweight VS Code / Cursor extension that adds a status bar button for copying the current workspace folder's absolute path to the clipboard.

## Features

- **Status bar button** — a `$(folder) Copy Path` item appears in the bottom-right corner of the editor window at all times.
- **Single folder** — clicking the button copies the path immediately and shows a notification confirming what was copied.
- **Multiple workspace folders** — clicking opens a QuickPick list so you can select which folder's path to copy.
- **Hover tooltip** — hovering over the status bar item shows the path of the first workspace folder (`Copy: /absolute/path`), or `No folder open` when no folder is loaded.
- **Command Palette** — the same action is available via `Copy Folder Path: Copy Workspace Path` (command ID: `copy-folder-path.copy`).

## Requirements

VS Code 1.75.0 or later (or any compatible Cursor build).

## Installation

Install from the pre-built `.vsix` file:

1. Open the Command Palette (`Cmd+Shift+P` / `Ctrl+Shift+P`).
2. Run **Extensions: Install from VSIX...**.
3. Select `copy-folder-path-0.1.0.vsix`.

Or install from the terminal:

```bash
code --install-extension copy-folder-path-0.1.0.vsix
```

## Usage

Once installed, the `$(folder) Copy Path` button appears in the status bar (bottom right).

| Scenario | Result |
|---|---|
| No folder open | Warning: "No folder is currently open." |
| One workspace folder | Path copied; notification: "Copied: /your/path" |
| Multiple workspace folders | QuickPick to choose; path copied after selection |

The copied value is the absolute filesystem path (`uri.fsPath`) of the selected folder.

## Development

```bash
# Install dependencies
npm install

# Compile TypeScript
npm run compile

# Watch mode (recompiles on change)
npm run watch

# Build a .vsix package
npm run package
```

The compiled output lands in `out/`. The entry point is `src/extension.ts`.

## License

No license file is present in this repository. All rights reserved by the author unless otherwise stated.
