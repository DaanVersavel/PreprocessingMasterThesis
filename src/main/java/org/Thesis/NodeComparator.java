package org.Thesis;

import java.util.Comparator;

public class NodeComparator implements Comparator<NodeParser> {
    @Override
    public int compare(NodeParser a, NodeParser b) {
        return Double.compare(a.getCurrenCost(), b.getCurrenCost());
    }
}
