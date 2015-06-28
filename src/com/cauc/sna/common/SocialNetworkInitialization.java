package com.cauc.sna.common;
import java.util.ArrayList;

import com.cauc.sna.common.SocialNetwork;
public class SocialNetworkInitialization {
	private static SocialNetwork socialNetWork=new SocialNetwork();
	public static SocialNetwork SNInitialization(String url){
		 FileReader fr=new FileReader();
		 ArrayList<String> snaList=fr.readTxtFile(url);
		 //获取节点数目
		 int v=Integer.parseInt(snaList.get(0).toString().split(" ")[1]);
		 socialNetWork.setV(v);
		 //建立节点对应名称数组
		 String[] vName=new String[v];
		 for(int i=1;i<1+v;i++){
			 String[] s=snaList.get(i).toString().split(" ");
			 vName[i-1]=s[1];
		 }
		 socialNetWork.setvName(vName);
		 //创建网络矩阵
		 double netMatrix[][]=new double[v][v];
    	 for(int i=v+2;i<snaList.size()-1;i++){
    		int v1=Integer.parseInt(snaList.get(i).toString().split(" ")[0])-1;
    		int v2=Integer.parseInt(snaList.get(i).toString().split(" ")[1])-1;
    		double w=1;
    		if(!(snaList.get(i).toString().split(" ").length<3))
    			w=Double.parseDouble(snaList.get(i).toString().split(" ")[2]);
    		netMatrix[v1][v2]=w;
    		netMatrix[v2][v1]=w;
    	 }	
    	 socialNetWork.setNetMatrix(netMatrix);
		 return socialNetWork;
	}

}
