package com.cauc.sna.common;

import java.util.Comparator;

import com.cauc.sna.common.Node;

public class QuickSort implements Comparator<Node>{	
	
	/*
	 * @Describeð������
	 */
//	public static void sort(double[] a,String []vName){
//		double temp=0;
//		String temp1;
//		for(int i=a.length-1;i>0;--i){
//			for(int j=0;j<i;++j){
//				if(a[j+1]>a[j]){
//					temp=a[j];
//					temp1=vName[j];
//					a[j]=a[j+1];
//					vName[j]=vName[j+1];
//					a[j+1]=temp;
//					vName[j+1]=temp1;
//				}
//			}
//		}
//	} 
//	
	
	/*
	 * @Describe��������
	 */
//	public static void Qsort(double[] a,String []vName,int low,int high){
//		if(low>=high){
//			return;
//		}
//	 	int first=low;
//	 	int last=high;
//	 	double key=a[first];/*���ֱ�ĵ�һ����¼��Ϊ����*/
//	 	String key2=vName[first];/*���ֱ�ĵ�һ����¼��Ϊ����*/
//	 	while(first<last){
//	 		while(first<last&&a[last]<key)
//	 			--last;
//	 		a[first]=a[last];/*���ȵ�һ��С���Ƶ��Ͷ�*/
//	 		vName[first]=vName[last];/*���ȵ�һ��С���Ƶ��Ͷ�*/
//	 		while(first<last&&a[first]>key)
//	 			++first;
//	 		a[last]=a[first];/*���ȵ�һ������Ƶ��߶�*/
//	 		vName[last]=vName[first];/*���ȵ�һ������Ƶ��߶�*/
//	 	}
//	 	a[first]=key;/*�����¼��λ*/
//	 	vName[first]=key2;/*�����¼��λ*/
//	 	Qsort(a,vName,low,first-1);
//	 	Qsort(a,vName,first+1,high);
//	}
	
	public int compare(Node node0, Node node1) {
		 
		int flag=node1.getNRc().compareTo(node0.getNRc());
		return flag;
	}
	
	
//	public static void main(String args [])
//	{
//		double a[]={57,68,59,52,72,28,96,33,24};
//		String b[]={"a","b","c","d","e","f","g","h","i"};
//		Qsort(a,b,0,a.length-1);/*����ԭ�ĵ���������Ҫ��1�����ڴ�й¶*/	 
//		for(int i=0;i<a.length;i++)
//		{
//			System.out.println(b[i]+":"+a[i]);
//		}
//	}/*�ο����ݽṹp274(�廪��ѧ�����磬��ε��)*/	

}
