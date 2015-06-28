package com.cauc.sna.fitness;
import java.util.ArrayList;

import com.cauc.sna.common.SocialNetwork;
import com.cauc.sna.common.Node;

public class FitnessCalculate {
	
	/*
	 * Describe ��������½ڵ����Ӧ��ֵ��ֵ
	 * @mainSocialNetwork ��������
	 * @ArrayList<Node> selNode�Ѽ���ڵ�
	 * @restNode:δ����ֲ���������нڵ�
	 * @Node:�������ѡ�ڵ�
	 * @a a��һ�����Կ���������ģ��С����ʵ������
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
		//���нڵ��������
		double[][] netMatrix=mainSocialNetwork.getNetMatrix();
		//Kin
		double Kin=0;
		//Kin+Kout
		double Kout=0;
		//����ֲ������ڲ��ڵ����֮��Kinֵ
		for(int i=0;i<selNodeTem.size();i++){
			for(int j=0;j<selNodeTem.size();j++){				
				if(0!=netMatrix[selNodeTem.get(i).getLoc()][selNodeTem.get(j).getLoc()])
					Kin+=netMatrix[selNodeTem.get(i).getLoc()][selNodeTem.get(j).getLoc()];
			}
		}
		//����Koutֵ
		for(int i=0;i<selNodeTem.size();i++){
			for(int j=0;j<restNodeTem.size();j++)
				if(0!=netMatrix[selNodeTem.get(i).getLoc()][restNodeTem.get(j).getLoc()])
					Kout+=netMatrix[selNodeTem.get(i).getLoc()][restNodeTem.get(j).getLoc()];
		}
		//�������������Ӧ��ֵ
		fitValue=Kin/(Math.pow(Kin+Kout, a));
				
		//�����������½ڵ�fitValue1
		//1����ʱ���½ڵ����ֲ�����
		selNodeTem.add(newNode);
		//2����ʱ��������ڵ��δ����ڵ����Ƴ�
		for(int i=0;i<restNodeTem.size();i++){
			if(newNode.getName().equals(restNodeTem.get(i).getName()))
				restNodeTem.remove(i);
		}
		Kin=0;
		Kout=0;
		//��������½ڵ��ֲ������ڲ��ڵ����֮��Kinֵ
		for(int i=0;i<selNodeTem.size();i++){
			for(int j=0;j<selNodeTem.size();j++){				
				if(0!=netMatrix[selNodeTem.get(i).getLoc()][selNodeTem.get(j).getLoc()])
					Kin+=netMatrix[selNodeTem.get(i).getLoc()][selNodeTem.get(j).getLoc()];
			}
		}
		//����Koutֵ
		for(int i=0;i<selNodeTem.size();i++){
			for(int j=0;j<restNodeTem.size();j++)
				if(0!=netMatrix[selNodeTem.get(i).getLoc()][restNodeTem.get(j).getLoc()])
					Kout+=netMatrix[selNodeTem.get(i).getLoc()][restNodeTem.get(j).getLoc()];
		}
		//�������������Ӧ��ֵ
		fitValue1=Kin/(Math.pow(Kin+Kout, a));
		double fit=fitValue1-fitValue;
		return fit;
	}
}
