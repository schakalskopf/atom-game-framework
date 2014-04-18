package sg.atom.logic.fuzzy.variables.solvers;

import java.util.List;

import sg.atom.logic.fuzzy.variables.memberships.PointMembership;

public interface Solver {
    PointMembership solve(List<PointMembership> points);
}
