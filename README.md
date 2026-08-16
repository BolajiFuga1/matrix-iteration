# A / N Column Simulator

Single-page web app that simulates **L** iterations across 5 columns. Each cell draws a random number from a fixed pool of 32 values and outputs **A** or **N**.

## Live site

- **GitHub Pages:** https://bolajifuga1.github.io/matrix-iteration/
- **Vercel:** https://matrix-iteration.vercel.app

## Run locally

Open `index.html` in a browser, or:

```bash
python3 -m http.server 8765
```

Then visit http://localhost:8765/

## Features

- Set **L** (default 69), Generate, Copy Table, Download CSV / TXT
- Scrollable table: `Iter, 1, 2, 3, 4, 5`
- Counter: total A's and N's

## Java (legacy)

The original Z1–Z16 matrix console program is still in `MatrixIteration.java`:

```bash
javac MatrixIteration.java
java MatrixIteration
```
