---

# Graph-Coloring-Problem  

This repository provides an implementation of graph coloring algorithms using Backtracking, Greedy, and Dynamic Programming approaches. Users can select their preferred method to efficiently color a given graph while comparing performance across different algorithms.  

## Features  

- **Backtracking Algorithm**: Ensures an optimal solution by exhaustively exploring all possibilities but may be slow for large graphs.  
- **Greedy Algorithm**: A fast heuristic approach that provides a quick solution, though it may not always result in an optimal coloring.  
- **Dynamic Programming Approach**: Balances performance and optimality in specific cases, offering a middle ground between Backtracking and Greedy methods.  
- **Graph Import and Processing**: Users can manually enter graph data or import multiple graph datasets in COL format. The imported graphs are processed, analyzed, and stored in a CSV file with key details, including the number of vertices, number of edges, minimum colors used, and runtime per algorithm.  
- **Graphical User Interface (GUI)**: The program includes a visualization feature that displays the graph structure and its assigned colors, allowing users to interactively observe the algorithm's performance.  
- **Benchmarking Support**: The repository includes real-world graph datasets sourced from the *Graph Coloring Benchmark Instances* collection, available at [Graph Coloring Benchmark Instances](https://dynaroars.github.io/npbench/graphcoloring.html). This allows users to test the algorithms on complex graph structures.  

## Usage  

Users can either input their own graph data or import datasets from external sources. The program processes the graphs using the selected algorithm and outputs the coloring results, along with performance metrics such as execution time and the minimum number of colors required. The "Import Graph" feature is particularly useful for large datasets, making it easier to analyze multiple graphs efficiently.  

This repository is ideal for researchers, students, and anyone interested in exploring the computational challenges of the graph coloring problem.
