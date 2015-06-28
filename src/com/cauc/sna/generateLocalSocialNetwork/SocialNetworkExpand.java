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
	//���������Ѿ�������һ���ֲ������Ľڵ�
	private static ArrayList<Node> inList=new ArrayList<Node>();
	public static ArrayList<Node> getLan(ArrayList<Node> nodeList,ArrayList<Node> nodeList0,SocialNetwork socialNetwork,double c,int from){
		double[][] netMatrix=socialNetwork.getNetMatrix();		
		ArrayList<Node> lanList=new ArrayList<Node>();	
		ArrayList<Node> neighborList=new ArrayList<Node>();
		//�����Ѽ���ֲ�����
		lanList.add(nodeList.get(from));
		//�����ѱ�һ���ֲ������������ڵĽڵ�
		inList.add(nodeList.get(from));
		nodeList0.remove(from);
		neighborList=addNeighborNodes(lanList,nodeList0,netMatrix);
		ArrayList<Node> list=new ArrayList<Node>();
		//��û���ھӽڵ��Լ����е�ǰ�ھӽڵ㶼������fitness����ʱֹͣ
		while(neighborList.size()>0&&list.size()<lanList.size()){
			list.clear();
			list.addAll(lanList);
			lanList=addLanNodes(lanList,neighborList,nodeList0,netMatrix);
			neighborList=addNeighborNodes(lanList,nodeList0,netMatrix);
		}
		
		return lanList;
	}
	
	/*
	 * Description  �����Ѽ��뵱ǰ����ڵ��ʣ��ڵ㣬��ʣ��ڵ����ҳ���ǰ�����ֱ���ھӽڵ�����ھ�neighborNodes
	 * @param lanList:��ǰ�������нڵ�
	 * @param restList:ʣ�����нڵ�
	 * @param netMatrix:�����������
	 * @return ArrayList<Node> neihborNodes ��ǰ���������ֱ���ھӽڵ�List
	 */
	public static ArrayList<Node> addNeighborNodes(ArrayList<Node> lanList,ArrayList<Node> restList,double[][] netMatrix){
		ArrayList<Node> neighborNodeList=new ArrayList<Node>();
		for(int i=0;i<lanList.size();i++){
			for(int j=0;j<restList.size();j++){
				if(netMatrix[lanList.get(i).getLoc()][restList.get(j).getLoc()]!=0){
					boolean add=true;
					//��neighborNodeListû��restList.get(j)ʱ�������
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
	 * Description �����������ĵ�ǰ����ֱ���ھӽڵ����
	 * @param lanList:��ǰ�������нڵ�
	 * @param neighborNodeList:��ǰ��������֮���ھӽڵ�
	 * @param restList:ʣ������������ڵ�
	 * @param netMatrix:�����������
	 * @return ArrayList<Node> lanList ��Ӻ������
	 */
	public static ArrayList<Node> addLanNodes(ArrayList<Node> lanList,ArrayList<Node> neighborNodeList,ArrayList<Node> restNodeList,double[][] netMatrix){
		
		//�����ѡ�ھӽڵ���Ӧ��ֵ
		for(int i=0;i<neighborNodeList.size();i++){
			neighborNodeList.get(i).setFitValue(FitnessCalculate.fitValue(socialNetwork,lanList,restNodeList,neighborNodeList.get(i), 0.8));
		}
		
		//��ȡfitValue�����ھӽڵ�
		Node bestFitNode=new Node();
		bestFitNode=neighborNodeList.get(0);
		for(int j=1;j<neighborNodeList.size();j++){
			if(neighborNodeList.get(j).getFitValue()>bestFitNode.getFitValue())
				bestFitNode=neighborNodeList.get(j);
		}
		
		double fs=bestFitNode.getFitValue();
		//����fitness��������Ӹýڵ�			
		if(fs>0){
			//1�����ú�ѡ�ڵ����lanList
			lanList.add(bestFitNode);
			//�Ƿ񽫸ýڵ����inList
			boolean add=true;
			//���bestFitNode����inList��ߣ��ż���
			for(Node node:inList){
				if(node.getName().equals(bestFitNode.getName())){
					add=false;
					break;
				}
			}
			if(add)
				inList.add(bestFitNode);
			//2�����ú�ѡ�ڵ��ʣ��ڵ�restNodeListɾ��
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
		System.out.println("�������ļ���ַ��");
		String url=scanner.next();
		while(url!="0"){			
			socialNetwork=SocialNetworkInitialization.SNInitialization(url);		
			System.out.println("������cֵ��");
			double c=Double.parseDouble(scanner.next());
			//��������������нڵ�
			ArrayList<Node> nodeList=new ArrayList<Node>();
			ArrayList<Node> nodeList0=new ArrayList<Node>();
			nodeList=PageRank.pagerank(socialNetwork, c);
			String result="";//������
			int count=0;//���ӽڵ����
			FileWriter output;
			BufferedWriter bf;
			try{
				output = new FileWriter("D://result.txt");
				bf= new BufferedWriter(output);
				for(int i=0;i<nodeList.size();i++){
					boolean expand=true;
					for(Node node:inList){
						//�����Ľڵ�û�б��κξֲ���������������Ϊ����
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
						result+="�Ե�"+count+"�����Ľڵ�:"+(lanList.get(0).getLoc()+1)+"Ϊ���ĵľֲ�������СΪ��"+lanList.size()+ "\r\n";
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
			System.out.println("�������ѱ�������D:/result.txt");
			System.out.println("�������ļ���ַ��");
			inList.clear();
			url=scanner.next();
		}
		scanner.close();
		
    }

}
