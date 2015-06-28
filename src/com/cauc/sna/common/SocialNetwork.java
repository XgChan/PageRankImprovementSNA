package com.cauc.sna.common;

public class SocialNetwork {
	private int V;
	private String[] vName;
	private double[][] netMatrix;
	private double[] NRc;
	public int getV() {
		return V;
	}
	public void setV(int v) {
		V = v;
	}
	public String[] getvName() {
		return vName;
	}
	public void setvName(String[] vName) {
		this.vName = vName;
	}
	public double[][] getNetMatrix() {
		return netMatrix;
	}
	public void setNetMatrix(double[][] netMatrix) {
		this.netMatrix = netMatrix;
	}
	public double[] getNRc() {
		return NRc;
	}
	public void setNRc(double[] nRc) {
		NRc = nRc;
	}
	

}
