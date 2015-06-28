package com.cauc.sna.common;

import java.util.Comparator;

import com.cauc.sna.common.Node;

public class QuickSort implements Comparator<Node>{	
	
	/*
	 * @Describe冒泡排序
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
	 * @Describe快速排序
	 */
//	public static void Qsort(double[] a,String []vName,int low,int high){
//		if(low>=high){
//			return;
//		}
//	 	int first=low;
//	 	int last=high;
//	 	double key=a[first];/*用字表的第一个记录作为枢轴*/
//	 	String key2=vName[first];/*用字表的第一个记录作为枢轴*/
//	 	while(first<last){
//	 		while(first<last&&a[last]<key)
//	 			--last;
//	 		a[first]=a[last];/*将比第一个小的移到低端*/
//	 		vName[first]=vName[last];/*将比第一个小的移到低端*/
//	 		while(first<last&&a[first]>key)
//	 			++first;
//	 		a[last]=a[first];/*将比第一个大的移到高端*/
//	 		vName[last]=vName[first];/*将比第一个大的移到高端*/
//	 	}
//	 	a[first]=key;/*枢轴记录到位*/
//	 	vName[first]=key2;/*枢轴记录到位*/
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
//		Qsort(a,b,0,a.length-1);/*这里原文第三个参数要减1否则内存泄露*/	 
//		for(int i=0;i<a.length;i++)
//		{
//			System.out.println(b[i]+":"+a[i]);
//		}
//	}/*参考数据结构p274(清华大学出版社，严蔚敏)*/	

}
