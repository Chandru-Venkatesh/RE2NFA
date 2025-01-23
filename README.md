```markdown
# Conversion of Regular Expression into Non-Deterministic Finite Automaton (NFA)

Thompson's construction algorithm, which converts the RE step-by-step into an NFA by breaking down the expression into smaller sub-expressions. The most common operations in regular expressions include:

### Common Operations
- **Concatenation**: For example, `ab`
- **Union (OR)**: For example, `a|b`
- **Kleene Star**: For example, `a*`

### NFAState Class
Represents a state in the NFA, with transitions for specific symbols and ε (epsilon) transitions.

### NFA Class
Represents the entire NFA with a start state and an accept state.

### Steps to Run the Code
1. Install **Java Development Kit (JDK)** 8 or higher.
2. Open the terminal.
3. Compile the code using:
   ```sh
   javac ThompsonConstruction.java
   ```
4. Run the program with:
   ```sh
   java ThompsonConstruction
   ```
```

