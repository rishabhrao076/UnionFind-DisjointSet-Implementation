public class UnionFindWithPathCompression extends UnionFind {
    public UnionFindWithPathCompression(int n) {
        super(n);
    }

    @Override
    public Element find(int n) {
        Element element = elements[n - 1];
        if (element.parent == n)
            return element;
        else {
            Element root = find(element.parent);
            // Path compression: update parent pointer to root
            elements[n - 1].parent = root.value;
            return root;
        }
    }

    @Override
    protected Element find(int n, Metric metric) {
        Element element = elements[n - 1];
        if (element.parent == n)
            return element;
        else {
            // Update metric
            metric.incrementTraversal();
            Element root = find(element.parent);
            // Path compression: update parent pointer to root
            elements[n - 1].parent = root.value;
            return root;
        }
    }
}
