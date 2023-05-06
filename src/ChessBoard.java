import java.sql.Array;
import java.util.*;

public class ChessBoard {
    int size = 8;
    boolean[][] onlyQueens = new boolean[size][size];
    Map<Integer, Integer> queensMap = new HashMap<>();
    LinkedList<Integer> listQueensRows = new LinkedList<>();


    void viewQueens() {
        for (boolean[] line : onlyQueens) {
            for (boolean item : line) {
                System.out.print(item ? "X  " : "0  ");
            }
            System.out.println();
        }
        System.out.println();
    }

    void putTheQueen(int row, int column) {
        queensMap.put(row, column);
        listQueensRows.add(row);
        onlyQueens[row][column] = true;
    }

    int[] removeLastTheQueen() {
        if (listQueensRows.size() > 1) {
            int deletingRow = listQueensRows.pollLast();
            int deletingColumn = queensMap.remove(deletingRow);
            onlyQueens[deletingRow][deletingColumn] = false;
            return new int[]{deletingRow, deletingColumn};
        }
        return new int[]{size - 1, size - 1};
    }

    void putManyOfQueens(int count) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                onlyQueens = new boolean[size][size];
                queensMap = new HashMap<>();
                listQueensRows = new LinkedList<>();
//                putTheQueen(i, j);
                tryHard(i, j, count);
//                recursivePut(i == size - 1 ? i : i + 1, j == size - 1 ? 0 : j + 1, 1, count);
            }
        }
    }

    void tryHard(int rowStart, int columnStart, int count) {
        putTheQueen(rowStart, columnStart);
        if (listQueensRows.size()==count) {
            viewQueens();
//            return;
        }
        for (int i = rowStart; i < size; i++) {
            for (int j = columnStart; j < size; j++) {
                if (allowPutTheQueen(i, j)) {
                    tryHard(j == size - 1 ? i + 1 : i, j == size - 1 ? 0 : j + 1, count);

//                    putTheQueen(i, j);

                }

            }
        }
    }

    void tryCycle(int count) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (allowPutTheQueen(i, j)) {
                    putTheQueen(i, j);
                    if (listQueensRows.size() == count) {
                        viewQueens();
//                        return;
                        onlyQueens = new boolean[size][size];
                        queensMap = new HashMap<>();
                        listQueensRows = new LinkedList<>();
                    }
                    tryCycle(count);
                }
            }
        }
    }

    void recursivePut(int rowStart, int columnStart, int currentCountOfQueens, int needCountOfQueens) {
        if (rowStart == size - 1 && columnStart == size - 1) {
            return;
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (allowPutTheQueen(rowStart, columnStart)) {
                    putTheQueen(rowStart, columnStart);
                    currentCountOfQueens++;
                    viewQueens();
                    if (listQueensRows.size() == needCountOfQueens) {
                        viewQueens();
                        int[] newStart = removeLastTheQueen();
                        recursivePut(newStart[0], newStart[1], currentCountOfQueens - 1, needCountOfQueens);
                    }
                }
            }
        }


    }

    Boolean allowPutTheQueen(int row, int column) {
        if (queensMap.containsKey(row) || queensMap.containsValue(column))
            return false;

        int jPlus = column;
        int jMinus = column;
        for (int k = row; k < size; k++) {
            if (jPlus < size)
                if (onlyQueens[k][jPlus++])
                    return false;
            if (jMinus > -1)
                if (onlyQueens[k][jMinus--])
                    return false;
        }
        jPlus = column;
        jMinus = column;
        for (int k = row; k > -1; k--) {
            if (jPlus < size)
                if (onlyQueens[k][jPlus++])
                    return false;
            if (jMinus > -1)
                if (onlyQueens[k][jMinus--])
                    return false;
        }
        return true;
    }
}