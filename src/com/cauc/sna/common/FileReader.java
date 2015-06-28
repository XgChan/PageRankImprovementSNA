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
	 * 功能描述：按行读取txt文件
	 */
	public  ArrayList<String> readTxtFile(String filePath){
		ArrayList<String> arrayList = new ArrayList<String>();
        try {
                String encoding="GBK";
                File file=new File(filePath);
                if(file.isFile() && file.exists()){ //判断文件是否存在
                    InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);//考虑到编码格式
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String lineTxt = null;
                    while((lineTxt = bufferedReader.readLine()) != null){ 
                    	//先对每行去除多余空格，再保存到arrayList
                    	 Pattern p = Pattern.compile("\t");
                         Matcher m = p.matcher(lineTxt);
                         lineTxt = m.replaceAll(" ");

                        arrayList.add(lineTxt.replaceAll(" +"," ").trim());
                    }                 
                    read.close();
                }
                else{
                	System.out.println("找不到指定的文件");
                }
        } 
        catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return arrayList;
	}
}
