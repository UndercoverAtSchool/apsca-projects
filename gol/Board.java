package gol;

interface Board {

    // Run the simulation for a given number of turns
    public void run(int turns);

    // Step the simulation forward one turn
    public void step();

    // Provide the number of neighbors a cell has
    public int countNeighbors(int x, int y);

    // Get the state of the requested cell
    public int get(int x, int y);

    // Get the internal storage array (for testing)
    public int[][] get();
}
