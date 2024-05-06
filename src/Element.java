public class Element{
    int parent;
    int rank;
    int value;

    public Element(int parent, int rank, int value) {
        // Parent of the set it belongs to
        this.parent = parent;
        // Rank of the set it belongs to
        this.rank = rank;
        // Value of the element
        this.value = value;
    }

    @Override
    public String toString() {
        return "Element{" +
                "parent=" + parent +
                ", rank=" + rank +
                ", value=" + value +
                '}';
    }
}
