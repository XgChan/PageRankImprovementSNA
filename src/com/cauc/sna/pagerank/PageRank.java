package com.cauc.sna.pagerank;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import com.cauc.sna.common.SocialNetwork;
import com.cauc.sna.common.SocialNetworkInitialization;
import com.cauc.sna.common.QuickSort;
import com.cauc.sna.common.Node;

public class PageRank {
	
	private static  SocialNetwork socialNetwork;
	public static void main(String[] args){
		Scanner scanner = new Scanner(System.in);
		System.out.println("�������ļ���ַ��");
		String url=scanner.next();
		while(url!="0"){			
			socialNetwork=SocialNetworkInitialization.SNInitialization(url);
			
//			for(double []a:socialNetwork.getNetMatrix()){			
//				for(double b:a)			
//					System.out.print(b+" ");	
//				System.out.println();
//			}				
			System.out.println("������cֵ��");
			double c=Double.parseDouble(scanner.next());
			pagerank(socialNetwork, c);
			System.out.println("�������ļ���ַ��");
			url=scanner.next();
		}
		scanner.close();
		
    }
	
	/*
	 * @Description �Ľ���PagerRank����ڵ����Ķ�
	 * @param socialNetwork ������������
	 * @param c �������� 
	 * @return socialNetwork SocialNetwork����
	 */
	public  static ArrayList<Node> pagerank(SocialNetwork socialNetwork,double c){
		
		ArrayList<Node> nodeList=new ArrayList<Node>();
		int v=socialNetwork.getV();
		String[] vName=socialNetwork.getvName();
		double[][] netMatrix=socialNetwork.getNetMatrix();
		double[] dw=new double[v];//�ڵ��
		double[] PRcens=new double[v];
		double[] PRcent=new double[v];		
		//��ʼ��ÿ���ڵ�����Ķ�
		for(int i=0;i<v;i++){
			PRcens[i]=(double)1/v;					
		}
		
		for(int i=0;i<v;i++){
			//����ÿ���ڵ��ֱ���ھ���
			ArrayList<String> neighborNodesList=new ArrayList<String>();
			//ÿ�������ֵ����
			for(int j=0;j<v;j++){					
				if(i!=j&&(netMatrix[i][j]!=0)){
					dw[i]+=netMatrix[i][j];						
					neighborNodesList.add(vName[j]);
				}
			}
			Node node=new Node();
			node.setNeighborNodes(neighborNodesList);
			nodeList.add(node);//�ѽڵ������ӵ�nodeList
		}
		double t=1;
		//ѭ��ִ��pagerank�㷨ֱ��t�ﵽ��ֵ
		while(t>(1/10*v)){
			
			for(int i=0;i<v;i++){
				PRcent[i]=0;					
			}			
			
			for(int i=0;i<v;i++){
				for(int j=0;j<v;j++){
					if(i!=j&&(netMatrix[i][j]!=0)){
						PRcent[i]+=PRcens[j]*(netMatrix[i][j]/dw[j]);
					}									
				}
			}
			
			//����������c�Խڵ����Ķ�ֵ��������
			for(int i=0;i<v;i++){
				PRcent[i]=c*PRcent[i]+(1-c)/v;					
			}			
			t=calculateT(PRcent,PRcens);
			for(int i=0;i<v;i++){
				PRcens[i]=PRcent[i];	
			}
		}
		
		//����PRcens��socialNetwork
		socialNetwork.setNRc(PRcens);		

		for(int i=0;i<v;i++){						
			nodeList.get(i).setName(vName[i]);
			nodeList.get(i).setNRc(PRcens[i]);	
			nodeList.get(i).setLoc(i);
		}
		//��nodeList���սڵ�NRc����
		QuickSort comparator=new QuickSort();
		Collections.sort(nodeList, comparator);
		//�������õĽڵ㵽txt�ļ�	
		try {
			FileWriter output;
			BufferedWriter bf;
			output = new FileWriter("D://ranknode.txt");
			bf= new BufferedWriter(output);
			for(int i=0;i<nodeList.size();i++){
				String loc=nodeList.get(i).getLoc()+"\t";
				String name=nodeList.get(i).getName()+"\t";
				double NRc=nodeList.get(i).getNRc();
				bf.write(loc+name+NRc+ "\r\n");
			}
			bf.flush();
			bf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nodeList;	
	}
	
	//�����������ǰһ�β��ģT
	public static double calculateT(double[] PRcent,double[] PRcens){
		DecimalFormat df=new DecimalFormat(".########");
		double t=0;
		for(int i=0;i<PRcent.length;i++){
			t+=(PRcent[i]-PRcens[i])*(PRcent[i]-PRcens[i]);
		}
		t=Math.sqrt(t);
		t=Double.parseDouble(df.format(t));
		return t;		
	}

}
