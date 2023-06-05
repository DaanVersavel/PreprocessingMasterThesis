package org.Thesis;

import java.util.Comparator;

public class DistanceComparator implements Comparator<NodeParser> {
    @Override
    public int compare(NodeParser a, NodeParser b) {
        return Double.compare(a.getDistanceToCenter(), b.getDistanceToCenter());
    }

}
