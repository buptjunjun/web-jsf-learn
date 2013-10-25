package org.junjun.twitter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junjun.twitter.bean.Report;

public class ReportThead 
{
	private int success = 0;
	private int fail = 0;
	
	private List<Report> reports= new ArrayList<Report>();
	private static ReportThead reportObj = new ReportThead();
	public void addReport(Report report)
	{
		if(report!=null)
		{
			this.reports.add(report);
			if(report.isSucess())
				this.success++;
			else
				this.fail++;
		}
	}
	
	public void clear()
	{
		this.reports.clear();
	}
	
	private ReportThead() {
		
	}
	
	public void report()
	{
		Date name = new Date();
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
		String reportFileName=sdf.format(name);
		
		try {
			PrintWriter print = new PrintWriter(new FileWriter(reportFileName));
			print.println("total report:"+(this.fail+this.success)+":success:"+this.success+",fail:"+this.fail);
			for(Report r:this.reports)
			{
				if(!r.isSucess())
				{
					print.println("fail "+sdf.format(r.getDate())+":"+r.getDescription());
				}
			}
			for(Report r:this.reports)
			{
				if(r.isSucess())
				{
					print.println("succ "+sdf.format(r.getDate())+":"+r.getDescription());
				}
			}
			
			print.flush();
			print.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	static public ReportThead getInstance()
	{
		return reportObj;
	}
}
