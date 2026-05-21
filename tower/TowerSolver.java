package tower;

public class TowerSolver {
    private TowerModel model;

    public TowerSolver()
    {
        // Nothing to do here
    }

    public void solve(TowerModel model)
    {
        this.model = model;
        solve(model.height(), 0, 2, 1);
    }

    private void solve(int n, int s, int d, int a)
    {
        if (n == 1) { model.move(s, d); return; }
        solve(n - 1, s, a, d);
        model.move(s, d);
        solve(n - 1, a, d, s);
    }

}
