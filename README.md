## Toy-Language-Interpreter
 - Implemented a Language Interpreter with a "debugger" like GUI (in JavaFX) showing execution of a program step by step. 
 - Handles expressions and statements.
 - Structures used (for a programState):
   - Execution Stack (ExeStack): stores statements to be executed by the current program;
   - Symbol Table (SymTable): keeps variables and their values;
   - Output (Out): contains messages that will be printed by the program;
   - FileTable: manages the files used;
   - HeapTable: dictionary which maps an address to its content.
 - Provides synchronization mechanisms such as:
   - Count down latch;
   - Semaphore;
   - Cyclic barrier;
   - Locks.

