# Graph-Coloring-Problem  

This repository provides an implementation of graph coloring algorithms using Backtracking, Greedy, and Dynamic Programming approaches. Users can select their preferred method to efficiently color a given graph while comparing performance across different algorithms.  

## Features  

- **Backtracking Algorithm**: Ensures an optimal solution by exhaustively exploring all possibilities but may be computationally expensive for large graphs.  
- **Greedy Algorithm**: A fast heuristic approach that quickly assigns colors, though it may not always achieve an optimal solution.  
- **Dynamic Programming Approach**: Balances efficiency and optimality by reducing redundant computations, making it suitable for structured graphs.  
- **Graph Data Input Options**:  
  - Manually enter vertex and edge data into the provided text fields.  
  - Use the **Graph Data Extractor** to extract vertex and edge information from a `.COL` file, then manually paste it into the input fields.  
  - Import one or multiple `.COL` files using the **Import Graph** feature. The program automatically processes the data and compiles a CSV file containing key graph attributes, including the number of vertices, number of edges, minimum colors used, and execution time per algorithm.  
- **Graphical User Interface (GUI)**: Provides an interactive visualization of the graph structure and its assigned colors, allowing users to observe how different algorithms perform.  
- **Benchmarking Support**: Includes real-world graph datasets sourced from the *Graph Coloring Benchmark Instances* collection, available at [Graph Coloring Benchmark Instances](https://dynaroars.github.io/npbench/graphcoloring.html). This enables testing on complex graph structures for deeper algorithm analysis.  

## Usage  

Users can define graphs manually, extract vertex and edge data from `.COL` files using the built-in extractor, or import graph datasets in `.COL` format for batch processing. The program applies the selected algorithm and outputs results, including execution time and the minimum number of colors needed.  

This repository is ideal for researchers, students, and enthusiasts exploring the computational challenges of the graph coloring problem.
