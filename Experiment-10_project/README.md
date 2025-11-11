# 🧩 Classic Challenges Solved: Dynamic Programming in Java

## 📘 Overview

This project demonstrates the power of **Dynamic Programming (DP)** by implementing optimized solutions to several classic algorithmic problems.  
Dynamic Programming helps solve complex problems efficiently by breaking them into smaller overlapping subproblems and storing their results to avoid redundant computations.

## 🚀 Implemented Problems

1. **Fibonacci Sequence**

   - Calculates the nth Fibonacci number using both recursive and DP approaches (top-down memoization and bottom-up tabulation).

2. **Coin Change Problem**

   - Finds the minimum number of coins required to make a given amount using available denominations.
   - Demonstrates how DP efficiently handles overlapping subproblems.

3. **0/1 Knapsack Problem**

   - Maximizes total value in a knapsack with limited capacity.
   - Implements both recursive and iterative DP solutions.

4. **Matrix Chain Multiplication**
   - Determines the most efficient way to multiply a sequence of matrices.
   - Uses DP to minimize the total number of scalar multiplications.

## 🧠 Concepts Covered

- Overlapping Subproblems
- Optimal Substructure
- Memoization (Top-Down DP)
- Tabulation (Bottom-Up DP)
- Time and Space Optimization in DP

## 💻 Tech Stack

- **Language:** Java
- **Paradigm:** Dynamic Programming
- **IDE:** IntelliJ IDEA / Eclipse / VS Code
- **JDK Version:** 17 or later

## 🗂️ Project Structure

```
DynamicProgramming/
│
├── src/
│   ├── Fibonacci.java
│   ├── CoinChange.java
│   ├── Knapsack.java
│   └── MatrixChainMultiplication.java
│
├── Main.java
└── README.md
```

## ⚙️ How to Run

1. Clone the repository
   ```bash
   git clone https://github.com/parvbansal1/DAA_23BCS13701_Parvbansal/tree/main/Experiment-10_project
   ```
2. Navigate to the project folder
   ```bash
   cd DynamicProgramming
   ```
3. Compile the Java files
   ```bash
   javac src/*.java
   ```
4. Run the main class
   ```bash
   java src.Main
   ```

## 📊 Sample Output

**Example – Fibonacci (n = 10):**

```
Fibonacci of 10 using DP: 55
```

**Example – Coin Change (coins = [1, 2, 5], amount = 11):**

```
Minimum coins required: 3
```

**Example – Knapsack:**

```
Maximum value in Knapsack: 220
```

**Example – Matrix Chain Multiplication:**

```
Minimum number of multiplications: 26000
```

## 📈 Key Learnings

- DP drastically reduces time complexity compared to recursion.
- Helps in solving real-world optimization problems efficiently.
- Recognizing overlapping and optimal substructures is key to applying DP.

## 🧑‍💻 Author

**Parv Bansal**  
📧 Email: your.email@example.com  
💼 GitHub: [github.com/yourusername](https://github.com/Parvbansal1)

---

⭐ **If you like this project, consider giving it a star on GitHub!**
