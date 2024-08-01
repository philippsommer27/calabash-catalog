# Pattern Catalogs

| Pattern ID | Pattern Description | Energy Savings (%) |
|------------|---------------------|---------------------|
| P0 | Force usage of FetchType LAZY for collection on JPA entities | -0.26* |
| P1 | Avoid String concatenation in loops | 98.07 - 99.62 |
| P1 Java | Avoid String concatenation in loops (Java) | 76.91 |
| P1 Python | Avoid String concatenation in loops (Python) | 58.67 |
| P1 Rust | Avoid String concatenation in loops (Rust) | 92.14** |
| P3 | Initialize builder/buffer with the appropriate size | 29.91 |
| P4 int | Avoid multiple if-else statements (int) | 9.63 |
| P4 String | Avoid multiple if-else statements (String) | -7.29 |
| P5 | Prefer comparison-to-0 in loop conditions | 0.62 |
| P6 | Inline temporary variables | 29.02 |

*Italicized in the original table, indicating that statistical significance could not be ascertained.
**Different internal repetitions were used for P1 Rust.