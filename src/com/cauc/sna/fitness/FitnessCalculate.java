package com.cauc.sna.fitness;
import java.util.ArrayList;

import com.cauc.sna.common.SocialNetwork;
import com.cauc.sna.common.Node;

public class FitnessCalculate {
	
	/*
	 * Describe 计算加入新节点的适应度值差值
	 * @mainSocialNetwork 整个网络
	 * @ArrayList<Node> selNode已加入节点
	 * @restNode:未加入局部网络的所有节点
	 * @Node:待加入候选节点
	 * @a a是一个可以控制社区规模大小的正实数参数
	 */
	public static double fitValue(SocialNetwork mainSocialNetwork,ArrayList<Node> 
	selNode,ArrayList<Node> restNode,Node newNode,double a){
		double fitValue=0;
		double fitValue1=0;
		ArrayList<Node> selNodeTem=new ArrayList<Node>();
		ArrayList<Node> restNodeTem=new ArrayList<Node>();
		for(Node node:restNode)
			restNodeTem.add(node);
		for(Node node:selNode)
			selNodeTem.add(node);
		//所有节点网络矩阵
		double[][] netMatrix=mainSocialNetwork.getNetMatrix();
		//Kin
		double Kin=0;
		//Kin+Kout
		double Kout=0;
		//计算局部网络内部节点度数之和Kin值
		for(int i=0;i<selNodeTem.size();i++){
			for(int j=0;j<selNodeTem.size();j++){				
				if(0!=netMatrix[selNodeTem.get(i).getLoc()][selNodeTem.get(j).getLoc()])
					Kin+=netMatrix[selNodeTem.get(i).getLoc()][selNodeTem.get(j).getLoc()];
			}
		}
		//计算Kout值
		for(int i=0;i<selNodeTem.size();i++){
			for(int j=0;j<restNodeTem.size();j++)
				if(0!=netMatrix[selNodeTem.get(i).getLoc()][restNodeTem.get(j).getLoc()])
					Kout+=netMatrix[selNodeTem.get(i).getLoc()][restNodeTem.get(j).getLoc()];
		}
		//计算出该网络适应度值
		fitValue=Kin/(Math.pow(Kin+Kout, a));
				
		//计算若加入新节点fitValue1
		//1、暂时将新节点加入局部网络
		selNodeTem.add(newNode);
		//2、暂时将待加入节点从未加入节点中移除
		for(int i=0;i<restNodeTem.size();i++){
			if(newNode.getName().equals(restNodeTem.get(i).getName()))
				restNodeTem.remove(i);
		}
		Kin=0;
		Kout=0;
		//计算加入新节点后局部网络内部节点度数之和Kin值
		for(int i=0;i<selNodeTem.size();i++){
			for(int j=0;j<selNodeTem.size();j++){				
				if(0!=netMatrix[selNodeTem.get(i).getLoc()][selNodeTem.get(j).getLoc()])
					Kin+=netMatrix[selNodeTem.get(i).getLoc()][selNodeTem.get(j).getLoc()];
			}
		}
		//计算Kout值
		for(int i=0;i<selNodeTem.size();i++){
			for(int j=0;j<restNodeTem.size();j++)
				if(0!=netMatrix[selNodeTem.get(i).getLoc()][restNodeTem.get(j).getLoc()])
					Kout+=netMatrix[selNodeTem.get(i).getLoc()][restNodeTem.get(j).getLoc()];
		}
		//计算出该网络适应度值
		fitValue1=Kin/(Math.pow(Kin+Kout, a));
		double fit=fitValue1-fitValue;
		return fit;
	}
}
