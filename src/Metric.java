public class Metric {
    String operation;
    int traversals;
    int rank;

    long time;

    public void setTime(long time) {
        this.time = time;
    }

    public Metric(String operation) {
        this.operation = operation;
        this.traversals = 0;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void incrementTraversal() {
        this.traversals++;
    }

    @Override
    public String toString() {
        return "Metric{" +
                "operation='" + operation + '\'' +
                ", traversals=" + traversals +
                ", rank=" + rank +
                '}';
    }
}