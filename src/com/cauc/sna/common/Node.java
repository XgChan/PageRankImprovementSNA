package com.cauc.sna.common;
import java.util.ArrayList;
public class Node {
	private String name;	
	private ArrayList<String> neighborNodesList;
	private double NRc;
	private int loc;
	private double fitValue;//加入社区时候该节点的fitness值
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<String> getNeighborNodesList() {
		return neighborNodesList;
	}
	public void setNeighborNodes(ArrayList<String> neighborNodesList) {
		this.neighborNodesList = neighborNodesList;
	}
	public Double getNRc() {
		return NRc;
	}
	public void setNRc(double nRc) {
		NRc = nRc;
	}
	public int getLoc() {
		return loc;
	}
	public void setLoc(int loc) {
		this.loc = loc;
	}
	public double getFitValue() {
		return fitValue;
	}
	public void setFitValue(double fitValue) {
		this.fitValue = fitValue;
	}
	

}
