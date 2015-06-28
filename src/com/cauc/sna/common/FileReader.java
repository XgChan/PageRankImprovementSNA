package com.cauc.sna.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//Myfirst git
public class FileReader {
	
	/*
	 * �������������ж�ȡtxt�ļ�
	 */
	public  ArrayList<String> readTxtFile(String filePath){
		ArrayList<String> arrayList = new ArrayList<String>();
        try {
                String encoding="GBK";
                File file=new File(filePath);
                if(file.isFile() && file.exists()){ //�ж��ļ��Ƿ����
                    InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);//���ǵ������ʽ
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String lineTxt = null;
                    while((lineTxt = bufferedReader.readLine()) != null){ 
                    	//�ȶ�ÿ��ȥ������ո��ٱ��浽arrayList
                    	 Pattern p = Pattern.compile("\t");
                         Matcher m = p.matcher(lineTxt);
                         lineTxt = m.replaceAll(" ");

                        arrayList.add(lineTxt.replaceAll(" +"," ").trim());
                    }                 
                    read.close();
                }
                else{
                	System.out.println("�Ҳ���ָ�����ļ�");
                }
        } 
        catch (Exception e) {
            System.out.println("��ȡ�ļ����ݳ���");
            e.printStackTrace();
        }
        return arrayList;
	}
}
