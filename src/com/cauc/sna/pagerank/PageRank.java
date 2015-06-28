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
		System.out.println("请输入文件地址：");
		String url=scanner.next();
		while(url!="0"){			
			socialNetwork=SocialNetworkInitialization.SNInitialization(url);
			
//			for(double []a:socialNetwork.getNetMatrix()){			
//				for(double b:a)			
//					System.out.print(b+" ");	
//				System.out.println();
//			}				
			System.out.println("请输入c值：");
			double c=Double.parseDouble(scanner.next());
			pagerank(socialNetwork, c);
			System.out.println("请输入文件地址：");
			url=scanner.next();
		}
		scanner.close();
		
    }
	
	/*
	 * @Description 改进的PagerRank输出节点中心度
	 * @param socialNetwork 输入的社会网络
	 * @param c 缩放因子 
	 * @return socialNetwork SocialNetwork对象
	 */
	public  static ArrayList<Node> pagerank(SocialNetwork socialNetwork,double c){
		
		ArrayList<Node> nodeList=new ArrayList<Node>();
		int v=socialNetwork.getV();
		String[] vName=socialNetwork.getvName();
		double[][] netMatrix=socialNetwork.getNetMatrix();
		double[] dw=new double[v];//节点度
		double[] PRcens=new double[v];
		double[] PRcent=new double[v];		
		//初始化每个节点的中心度
		for(int i=0;i<v;i++){
			PRcens[i]=(double)1/v;					
		}
		
		for(int i=0;i<v;i++){
			//保存每个节点的直接邻居名
			ArrayList<String> neighborNodesList=new ArrayList<String>();
			//每个几点度值计算
			for(int j=0;j<v;j++){					
				if(i!=j&&(netMatrix[i][j]!=0)){
					dw[i]+=netMatrix[i][j];						
					neighborNodesList.add(vName[j]);
				}
			}
			Node node=new Node();
			node.setNeighborNodes(neighborNodesList);
			nodeList.add(node);//把节点对象添加到nodeList
		}
		double t=1;
		//循环执行pagerank算法直到t达到阈值
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
			
			//用缩放因子c对节点中心度值进行缩放
			for(int i=0;i<v;i++){
				PRcent[i]=c*PRcent[i]+(1-c)/v;					
			}			
			t=calculateT(PRcent,PRcens);
			for(int i=0;i<v;i++){
				PRcens[i]=PRcent[i];	
			}
		}
		
		//保存PRcens到socialNetwork
		socialNetwork.setNRc(PRcens);		

		for(int i=0;i<v;i++){						
			nodeList.get(i).setName(vName[i]);
			nodeList.get(i).setNRc(PRcens[i]);	
			nodeList.get(i).setLoc(i);
		}
		//对nodeList按照节点NRc排序
		QuickSort comparator=new QuickSort();
		Collections.sort(nodeList, comparator);
		//输出排序好的节点到txt文件	
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
	
	//计算计算结果与前一次差的模T
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
