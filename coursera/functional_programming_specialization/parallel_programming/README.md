#Course summary

## Week 1 - Parallel Programming overview

Parallelism manifests itself at different granularity levels such as bit-level, instruction-level and task-level.
There equally many different forms of parallel hardware such as multi-core processors, GPUs, FPGAs and computer clusters.

### Parallelism on the JVM

Operating systems multiplex many different processes by giving them time slices of execution. This is called multitasking.
Each process can contain multiple independent concurrency units called *threads*. Threads share the same memory, but each thread has a program counter and a program stack. Each JVM process starts with a **main thread**.

To start an additional thread we need to
1. Define a Thread subclass
2. Instantiate a new Thread object
3. Call start on the Thread object

We call an operation *atomic* if it appears as if it occured instantaneously from the point of view from the thread.

Synchronization primitives such as ```synchronized``` allow threads to agree on unique values.

**Deadlock** is a scenario in which two or more threads compete for resources and wait for each other to finish without releasing the already acquired resource.

Parallelism requires we call methods by name, not by value. Recursion is a powerful construction when performing parallel computations.

```parallel``` and ```tasks``` are two constructs for parallel computations.

### Performance Estimation

We could use different methods to estimate performance of a computations, such as

- Empirical Measurement
- Asymptotic Analysis

#### Asymptotic analysis
