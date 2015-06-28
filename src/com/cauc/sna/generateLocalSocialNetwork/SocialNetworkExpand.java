package com.cauc.sna.generateLocalSocialNetwork;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.cauc.sna.common.Node;
import com.cauc.sna.common.SocialNetwork;
import com.cauc.sna.common.SocialNetworkInitialization;
import com.cauc.sna.fitness.FitnessCalculate;
import com.cauc.sna.pagerank.PageRank;
public class SocialNetworkExpand {
	private static  SocialNetwork socialNetwork;
	//保存至少已经包含在一个局部社区的节点
	private static ArrayList<Node> inList=new ArrayList<Node>();
	public static ArrayList<Node> getLan(ArrayList<Node> nodeList,ArrayList<Node> nodeList0,SocialNetwork socialNetwork,double c,int from){
		double[][] netMatrix=socialNetwork.getNetMatrix();		
		ArrayList<Node> lanList=new ArrayList<Node>();	
		ArrayList<Node> neighborList=new ArrayList<Node>();
		//保存已加入局部社区
		lanList.add(nodeList.get(from));
		//保存已被一个局部社区包含在内的节点
		inList.add(nodeList.get(from));
		nodeList0.remove(from);
		neighborList=addNeighborNodes(lanList,nodeList0,netMatrix);
		ArrayList<Node> list=new ArrayList<Node>();
		//当没有邻居节点以及所有当前邻居节点都不满足fitness条件时停止
		while(neighborList.size()>0&&list.size()<lanList.size()){
			list.clear();
			list.addAll(lanList);
			lanList=addLanNodes(lanList,neighborList,nodeList0,netMatrix);
			neighborList=addNeighborNodes(lanList,nodeList0,netMatrix);
		}
		
		return lanList;
	}
	
	/*
	 * Description  遍历已加入当前网络节点和剩余节点，在剩余节点中找出当前网络的直接邻居节点加入邻居neighborNodes
	 * @param lanList:当前网络所有节点
	 * @param restList:剩余所有节点
	 * @param netMatrix:输入网络矩阵
	 * @return ArrayList<Node> neihborNodes 当前网络的所有直接邻居节点List
	 */
	public static ArrayList<Node> addNeighborNodes(ArrayList<Node> lanList,ArrayList<Node> restList,double[][] netMatrix){
		ArrayList<Node> neighborNodeList=new ArrayList<Node>();
		for(int i=0;i<lanList.size();i++){
			for(int j=0;j<restList.size();j++){
				if(netMatrix[lanList.get(i).getLoc()][restList.get(j).getLoc()]!=0){
					boolean add=true;
					//当neighborNodeList没有restList.get(j)时，才添加
					for(Node node:neighborNodeList)
						if(node.getLoc()==restList.get(j).getLoc()){
							add=false;
							break;
						}
					if(true==add)
						neighborNodeList.add(restList.get(j));
				}
			}
		}
		return neighborNodeList;
		
	}
	
	/*
	 * Description 将符合条件的当前网络直接邻居节点加入
	 * @param lanList:当前网络所有节点
	 * @param neighborNodeList:当前网络所有之外邻居节点
	 * @param restList:剩余所有网络外节点
	 * @param netMatrix:输入网络矩阵
	 * @return ArrayList<Node> lanList 添加后的网络
	 */
	public static ArrayList<Node> addLanNodes(ArrayList<Node> lanList,ArrayList<Node> neighborNodeList,ArrayList<Node> restNodeList,double[][] netMatrix){
		
		//计算候选邻居节点适应度值
		for(int i=0;i<neighborNodeList.size();i++){
			neighborNodeList.get(i).setFitValue(FitnessCalculate.fitValue(socialNetwork,lanList,restNodeList,neighborNodeList.get(i), 0.8));
		}
		
		//获取fitValue最大的邻居节点
		Node bestFitNode=new Node();
		bestFitNode=neighborNodeList.get(0);
		for(int j=1;j<neighborNodeList.size();j++){
			if(neighborNodeList.get(j).getFitValue()>bestFitNode.getFitValue())
				bestFitNode=neighborNodeList.get(j);
		}
		
		double fs=bestFitNode.getFitValue();
		//满足fitness条件，添加该节点			
		if(fs>0){
			//1、将该候选节点加入lanList
			lanList.add(bestFitNode);
			//是否将该节点加入inList
			boolean add=true;
			//如果bestFitNode不在inList里边，才加入
			for(Node node:inList){
				if(node.getName().equals(bestFitNode.getName())){
					add=false;
					break;
				}
			}
			if(add)
				inList.add(bestFitNode);
			//2、将该候选节点从剩余节点restNodeList删除
			for(int j=0;j<restNodeList.size();j++){
				Node node=restNodeList.get(j);
				if(bestFitNode.getName().equals(node.getName())){
					restNodeList.remove(j);
					break;
				}
			}
		}
			
		return lanList;
	}
	
	public static void main(String[] args){
		Scanner scanner = new Scanner(System.in);
		System.out.println("请输入文件地址：");
		String url=scanner.next();
		while(url!="0"){			
			socialNetwork=SocialNetworkInitialization.SNInitialization(url);		
			System.out.println("请输入c值：");
			double c=Double.parseDouble(scanner.next());
			//保存排名后的所有节点
			ArrayList<Node> nodeList=new ArrayList<Node>();
			ArrayList<Node> nodeList0=new ArrayList<Node>();
			nodeList=PageRank.pagerank(socialNetwork, c);
			String result="";//输出结果
			int count=0;//种子节点个数
			FileWriter output;
			BufferedWriter bf;
			try{
				output = new FileWriter("D://result.txt");
				bf= new BufferedWriter(output);
				for(int i=0;i<nodeList.size();i++){
					boolean expand=true;
					for(Node node:inList){
						//若中心节点没有被任何局部社区包含才能作为中心
						if(node.getName().equals(nodeList.get(i).getName())){
							expand=false;
							break;
						}					
					}
					if(expand){
						nodeList0.clear();
						nodeList0.addAll(nodeList);
						ArrayList<Node> lanList=getLan(nodeList,nodeList0,socialNetwork, c,i);	
						count++;
						result+="以第"+count+"个中心节点:"+(lanList.get(0).getLoc()+1)+"为中心的局部社区大小为："+lanList.size()+ "\r\n";
						for(Node node0:lanList)
							//result+=node0.getName()+ "\r\n";
							result+=node0.getLoc()+1+ "\r\n";
					}
				}
				bf.write(result);
				bf.flush();
				bf.close();
			} catch(IOException e){
				e.printStackTrace();
			}
			System.out.println("计算结果已保存至：D:/result.txt");
			System.out.println("请输入文件地址：");
			inList.clear();
			url=scanner.next();
		}
		scanner.close();
		
    }

}
