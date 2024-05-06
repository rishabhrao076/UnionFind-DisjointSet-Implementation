import java.util.ArrayList;
import java.util.Arrays;

public class UnionFind {
    public Element[] elements;
    public ArrayList<Metric> metrics;

    public UnionFind(int n) {
        this.metrics = new ArrayList<>();
        this.elements = new Element[n];
        for (int i = 1; i <= n; i++) {
            // Initialize with rank = 1, tree of 1 height
            Element element = new Element(i, 0, i);
            elements[i - 1] = element;
        }
    }

    // Overloaded to include metrics
    protected Element find(int n, Metric metric) {
        Element element = elements[n - 1];
        if (element.parent == n) return element;
        else {
            // Update Metric
            metric.incrementTraversal();

            return find(element.parent, metric);
        }
    }

    public Element find(int n) {
        Element element = elements[n - 1];
        if (element.parent == n) return element;
        else {
            return find(element.parent);
        }
    }

    public Element findWithMetrics(int n) {
        // Calculate metrics and perform find operation
        Metric metric = new Metric("Find " + n);
        long startTime = System.nanoTime();
        // perform find operation
        Element temp = this.find(n, metric);
        long endTime = System.nanoTime();
        metric.setRank(temp.rank);
        metric.setTime(endTime - startTime);
        metrics.add(metric);
        return temp;
    }

    public void union(int m, int n) {
        Element mroot = find(m);
        Element nroot = find(n);
        // Union and tie breaking
        if (mroot.rank > nroot.rank) {
            nroot.parent = mroot.value;
        } else if (mroot.rank < nroot.rank) {
            mroot.parent = nroot.value;
        } else {
            nroot.parent = mroot.value;
            mroot.rank++;
        }
    }
}
