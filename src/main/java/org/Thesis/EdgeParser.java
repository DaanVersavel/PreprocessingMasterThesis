package org.Thesis;

import java.util.Map;

public class EdgeParser {
	private double length;
	private long beginNodeOsmId;
	private long endNodeOsmId;
	private String edgeType;

	public EdgeParser(long beginNodeOsmId, long endNodeOsm, double length){
		this.beginNodeOsmId = beginNodeOsmId;
        this.endNodeOsmId = endNodeOsm;
        this.length = length;
		this.edgeType="";
	}

	public EdgeParser(EdgeParser edge) {
		this.beginNodeOsmId = edge.getBeginNodeOsmId();
		this.endNodeOsmId = edge.getEndNodeOsmId();
		this.length = edge.getLength();
		this.edgeType = edge.getEdgeType();
	}
	//nodeArrivelTime= time when we reach Begin node of edge
	//return the passed time from when reaching the end node of the edge
	public double getTravelTime(double nodeArrivalTime, Map<String, Double[][]> speedMatrixMap) {
		Double[][] speedMatrix = speedMatrixMap.get(this.edgeType);

		double travelTime= 0.0;
		Double time = nodeArrivalTime;
		double distanceToGo = length;
		double speed;

		//find Row
		Double leftBorder= speedMatrix[0][0];
		Double rightBorder= speedMatrix[0][1];
		int incrementor=0;
		while(!(leftBorder<=nodeArrivalTime) || !(nodeArrivalTime<rightBorder)){
			incrementor++;
			leftBorder = speedMatrix[incrementor][0];
			rightBorder = speedMatrix[incrementor][1];
		}
		int row = incrementor;
		speed = speedMatrix[row][2];


		double expectedArrivalTime = time + (distanceToGo/speed);

		while(expectedArrivalTime> speedMatrix[row][1]){
			distanceToGo = distanceToGo-(speed*(speedMatrix[row][1]-time));
			row++;
			time = speedMatrix[row][0];
			speed = speedMatrix[row][2];
			expectedArrivalTime = time + (distanceToGo/speed);
		}
		travelTime = expectedArrivalTime-nodeArrivalTime;
		return travelTime;
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

	public double getTravelTimeDefault(Map<String, Double[][]> speedMatrixMap) {
		double defaultSpeed = speedMatrixMap.get(this.edgeType)[0][2];
		double travelTime= length/defaultSpeed;
		return travelTime;
	}
}
