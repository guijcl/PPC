# Parallel and Concurrent Programming

This repository contains all assignments developed for the Parallel and Concurrent Programming course at FCUL (Faculty of Sciences - University of Lisbon), lectured by Professor [Alcides Fonseca](https://github.com/alcides).

The course explores various paradigms of parallel and concurrent programming, primarily using Java with a dedicated section on CUDA for GPU programming.

## Assignments

### [Assignment 1] Embarrassingly Parallel Computing (Java)

DNA pattern matching using Java threads. Classic example of embarrassingly parallel computation where work can be evenly split across threads with minimal synchronization overhead.

### [Assignment 2] Java ForkJoin

Parallel coin change calculator using ForkJoin. Deep dive into work-stealing and recursive task division, with focus on handling irregular parallelism and granularity control.

### [Assignment 3] GPU Computing (CUDA/C)

Floyd-Warshall's all-pairs shortest path in CUDA. Explores GPU architecture, memory hierarchies (shared, global, local), and CUDA-specific optimizations like coalesced memory access and thread block configurations.

### [Assignment 4] Actor Model (Java)

File system batch renaming tool using a lightweight actor framework. Practical exploration of message-passing concurrency, handling concurrent reads with sequential writes. Built with the course's actor framework (alternatives included Akka or Go).

## Course Topics

- Embarrassingly Parallel Problems
- Streams and ForkJoin
- Concurrency basics
- OpenMP basics
- GPU Programming with CUDA
- Go language and co-routines
- Actor Model
- Message-Passing Interface (MPI)
- FPGAs introduction
- Rust and Memory Safety in concurrent settings

## Evaluation

### Assignments

- A1 (Java Threads): 20/20
- A2 (ForkJoin): 18/20
- A3 (CUDA): 19.8/20
- A4 (Actors): 15/20

Final Grade: 16/20
