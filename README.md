# Matrix iteration program

Java console program and browser UI for the Z1–Z16 matrix iteration rules (C1–C16, C18, C20).

## Java

```bash
javac MatrixIteration.java
java MatrixIteration
```

Enter `L` (number of iterations) when prompted.

## Web (Vercel)

Open `index.html` locally or deploy this repo to [Vercel](https://vercel.com) as a static site (no build step).

```bash
npx vercel --prod
```

## Rules

- Randomly select four distinct patterns from Z1–Z16 → C1, C2, C3, C4
- C5–C8: horizontal flip of C1–C4
- C9–C16: pairwise horizontal addition
- Arithmetic: Y+Y=Y, Y+X=X, X+X=Y
- Restart iteration if C15 is Z1, Z7, or Z11
- C18 = C1 + C4 + C7 + C10
- C20 = C3 + C7 + C11 + C15 (print if Z1, Z6, Z10, or Z15; else Nil)
