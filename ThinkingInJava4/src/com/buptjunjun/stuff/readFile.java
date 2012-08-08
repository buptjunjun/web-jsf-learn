package com.buptjunjun.stuff;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;

/**
 * ≤‚ ‘ forµƒºÚΩ‡”Ô∑®
 * @author junjun
 *
 */

public class readFile 
{
	public static void main(String []args)
	{
		File f = new File("D:\\myfile\\machine learning\\collective intelligence\\ml-100k\\u.item");
		File f1 = new File("D:\\myfile\\machine learning\\collective intelligence\\ml-100k\\u.item1");
		try {
			BufferedInputStream bf = new BufferedInputStream (new FileInputStream(f));
			BufferedReader br = new BufferedReader (new InputStreamReader(bf));
			
			BufferedOutputStream bf1 = new BufferedOutputStream (new FileOutputStream(f1));
			BufferedWriter bw = new BufferedWriter (new OutputStreamWriter(bf1));
			try {
				String line =  br.readLine();
				while(line != null)
				{
					System.out.println(line);
					line = br.readLine();
					bw.write(line+"\n");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			bw.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
