import java.util.Arrays;
import java.awt.Point;

@SuppressWarnings("unchecked")
public class KnightBoard
{
    static final int[][] KNIGHT_MOVES = {{-1,  2},
                                         { 1, -2},
                                         {-2,  1},
                                         { 2, -1},
                                         {-2, -1},
                                         { 2,  1},
                                         {-1, -2},
                                         { 1,  2}};
    private GridNode<Point>[][] grid;
    private int size;
    private boolean cycle;

    public KnightBoard(int size)
    {
        this(size, false);
    }

    public KnightBoard(int size, boolean cycle)
    {
        this.cycle = cycle;

        if (size < 1)
            throw new IllegalArgumentException("Size must be positive.");

        this.size = size;
        grid = new GridNode[size][size];

        int i;
        int j;
        int k;
        for (i = 0; i < size; i++)
            for (j = 0; j < size; j++)
                grid[i][j] = new GridNode<Point>(new Point(i, j));

        int moveX;
        int moveY;
        for (i = 0; i < size; i++)
        {
            for (j = 0; j < size; j++)
            {
                for (k = 0; k < KNIGHT_MOVES.length; k++)
                {
                    moveX = i + KNIGHT_MOVES[k][0];
                    moveY = j + KNIGHT_MOVES[k][1];

                    if (0 <= moveX && moveX < size
                        && 0 <= moveY && moveY < size)
                        grid[i][j].addLink(grid[moveX][moveY]);
                }
            }
        }
    }

    public ArrayList<GridNode<Point>> findTour(int i, int j)
    {
        ArrayList<GridNode<Point>> list = new ArrayList<GridNode<Point>>();
        GridNode<Point> node = grid[i][j];

        return recursiveTour(node, list);
    }

    private static int fails = 0;
    private ArrayList<GridNode<Point>> recursiveTour(
        GridNode<Point> current, ArrayList<GridNode<Point>> list)
    {
        if (list.size() == size * size)
        {
            if (cycle)
            {
                if(list.getLast().hasLink(grid[0][0]))
                {
                    list.add(new GridNode<Point>(new Point(0, 0)));
                    return list;
                }
            }
            else
            {
                return list;
            }
        }

        if (list.contains(current))
            return null;

        ArrayList<GridNode<Point>> newList = list.clone();

        newList.add(current);

        ArrayList<GridNode<Point>> result;

        ArrayList<MoveSorter> nextMoves =
            new ArrayList<MoveSorter>(8);

        int numMoves;
        for (GridNode<Point> nextMove : current)
        {
            numMoves = 0;
            for (GridNode<Point> nextNextMove : nextMove)
                if (!list.contains(nextNextMove))
                    numMoves++;

            nextMoves.add(new MoveSorter(nextMove, numMoves));
        }

        for (MoveSorter nextMoveSorter : nextMoves)
        {
            result = recursiveTour(nextMoveSorter.getNode(), newList);

            if (result != null)
                return result;
        }

        return null;
    }

    private class MoveSorter implements Comparable<MoveSorter>
    {
        private GridNode<Point> node;
        private int numMoves;

        public MoveSorter(GridNode<Point> node, int numMoves)
        {
            this.node = node;
            this.numMoves = numMoves;
        }

        public GridNode<Point> getNode()
        {
            return node;
        }

        public int getNumMoves()
        {
            return numMoves;
        }

        public int compareTo(MoveSorter other)
        {
            return this.numMoves - other.numMoves;
        }
    }

    public GridNode<Point> get(int i, int j)
    {
        if (i < 0 || i >= size || j < 0 || j >= size)
            throw new ArrayIndexOutOfBoundsException();

        return grid[i][j];
    }

    public String toString()
    {
        String result = "";
        int i;
        int j;
        for (i = 0; i < size; i++)
        {
            for (j = 0; j < size; j++)
            {
                result += String.format("%4s  ", grid[i][j]);
            }
            if (i < size - 1)
                result += "\n\n\n";
        }
        return result;
    }
}
