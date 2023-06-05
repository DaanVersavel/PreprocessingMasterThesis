package org.Thesis;

import java.util.*;

public class NodeParser {
	private long osmId;
	private double latitude;
	private double longitude;
	private transient double distanceToCenter;
	private transient double currenCost;
	private long cellID;
	private List<EdgeParser> outgoingEdges = new ArrayList<>();

	public NodeParser(long osmId, double latitude, double longitude) {
		this.osmId = osmId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.currenCost=0.0;
	}

	public NodeParser(NodeParser node) {
		this.osmId = node.getOsmId();
		this.latitude = node.getLatitude();
		this.longitude = node.getLongitude();
		this.cellID = node.getCelID();
		this.outgoingEdges = node.getCopyOfOutgoingEdges();
	}

	private List<EdgeParser> getCopyOfOutgoingEdges() {
		List<EdgeParser> copy = new ArrayList<>();
		for (EdgeParser edge : outgoingEdges) {
            copy.add(new EdgeParser(edge));
        }
		return copy;
	}


	public double getCurrenCost() {
		return currenCost;
	}

	public void setCurrenCost(double currenCost) {
		this.currenCost = currenCost;
	}

	public long getOsmId() {
		return osmId;
	}

	public void setOsmId(long osmId) {
		this.osmId = osmId;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public void addOutgoingEdge(EdgeParser edge) {
		outgoingEdges.add(edge);
	}

	public List<EdgeParser> getOutgoingEdges() {
		return outgoingEdges;
	}

	public void setOutgoingEdges(List<EdgeParser> outgoingEdges) {
		this.outgoingEdges = outgoingEdges;
	}

	@Override
	public String toString() {
		return (latitude + "," + longitude);
	}

	public long getCelID() {
		return cellID;
	}

	public void setCelID(long celID) {
		this.cellID = celID;
	}

	public double getDistanceToCenter() {
		return distanceToCenter;
	}

	public void setDistanceToCenter(double distanceToCenter) {
		this.distanceToCenter = distanceToCenter;
	}
}


