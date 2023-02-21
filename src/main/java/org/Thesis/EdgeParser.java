package org.Thesis;

public class EdgeParser {
	private double length;
	private double travelTime;
	private long beginNodeOsmId;
	private long endNodeOsmId;
	private String edgeType;

	public EdgeParser(long beginNodeOsmId, long endNodeOsm, double length){
		this.beginNodeOsmId = beginNodeOsmId;
        this.endNodeOsmId = endNodeOsm;
        this.length = length;
		this.edgeType="";
	}

	public EdgeParser deepCopy(EdgeParser edge) {
		this.length = edge.length;
		this.travelTime = edge.travelTime;
		return this;
	}

	public String getEdgeType() {
		return edgeType;
	}

	public void setEdgeType(String edgeType) {
		this.edgeType = edgeType;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public double getTravelTime() {
		return travelTime;
	}

	public void setTravelTime(double travelTime) {
		this.travelTime = travelTime;
	}

	public long getBeginNodeOsmId() {
		return beginNodeOsmId;
	}

	public void setBeginNodeOsmId(long beginNodeOsmId) {
		this.beginNodeOsmId = beginNodeOsmId;
	}

	public long getEndNodeOsmId() {
		return endNodeOsmId;
	}

	public void setEndNodeOsmId(long endNodeOsmId) {
		this.endNodeOsmId = endNodeOsmId;
	}
}
