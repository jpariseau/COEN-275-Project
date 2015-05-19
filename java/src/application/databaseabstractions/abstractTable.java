package application.databaseabstractions;

/**
 * @author Joseph Pariseau
 */
public abstract class abstractTable {
    int rowCount;
    int columnCount;
    public abstract boolean addItem(Object item);
    public abstract boolean removeItem(int uniqueId);
    public abstract boolean editItem(int uniqueId);
    public abstract String[] getWholeTable();
    public abstract String[] getColumns();
    public abstract String[] getRows();
}
