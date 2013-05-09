package com.buptjunjun.stuff;

public class Split
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		String file="Java Web开发 Java面试 J2ME J2EE Java相关 Eclipse J2SE .NET分析设计 .NET Framework .NET面试 .NET组件控件 VC J# Delphi .NET报表 C# ASP.NET LINQ .NET新技术 VB Dotnet Web Service .NET相关 Oracle面试 Oracle认证考试 Oracle管理 Oracle开发 Oracle技术 Sql Server VC/MFC VB Office Windows Windows2000 Windows xp Windows7 BlackBerry Symbian QT开发 MeeGo Android Iphone Brew Windows Mobile 移动平台 移动软件开发 电信IT应用开发 移动应用 嵌入开发 Wireless 驱动开发 VxWorks WinCE 硬件开发 单片机 PHP JBoss VBA 其他开发语言 汇编语言 WebSphere Exchange SharePoint 企业信息化 Weblogic 企业开发 GIS Lotus Tivoli SAP 报表 其他数据库 Informix Sybase SQL 数据仓库 MySQL VFP DB2 Access ColdFusion Ruby/Rails Web开发 CGI Ajax 应用服务器 XML/SOAP vbScript JavaScript Apache 跨浏览器开发 IIS ASP HTML/CSS Delphi C++ Builder 高性能计算 高性能WEB开发 高性能数据库开发 网络设计维护 数码设备 电脑整机及配件 装机与升级 外设及办公设备 电脑硬件 交换机/路由器 PB CUDA 共享软件 计算机图书 计算机英语 Linux/Unix Solaris AIX C++ C语言 软件测试 开发过程 开发方法 CVS/SVN 敏捷开发 微创软件开发 VSTS 软件设计 PowerDesigner 项目管理 Rational Flex 交互式开发 Flash 网页设计 多媒体设计 图像工具使用 AutoCAD Silverlight Intel AMT 网络通信 游戏开发 Open API 信息/网络安全 IBM云计算 Paypal 多核软件开发 数据结构与算法 VOIP 设计模式 Google技术 多媒体/流媒体开发 人工智能 搜索引擎 图形/图像 IT课程 软件培训 软件水平考试 IT认证 移动开发 Web前端 软件架构设计 编程 数据库 系统运维 云计算 研发管理 综合 互联网 开源软件 操作系统 行业应用 Java Exception DotNet Exception Oracle Exception";
		String [] ret = file.split(" ");
		for(String r : ret)
		{	
			System.out.println(r+"="+r);
		}

	}

}
