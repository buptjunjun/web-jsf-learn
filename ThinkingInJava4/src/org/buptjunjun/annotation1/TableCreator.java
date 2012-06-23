package org.buptjunjun.annotation.database;

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;

public class TableCreator 
{
	public String  createDBTableSql(String className) throws Exception
	{
		Class<?> cl = UserMember.class;
		System.out.println("load "+ className);
		
		DBTable dbTable = cl.getAnnotation(DBTable.class);
		
		if (dbTable == null)
		{
			System.out.println("no annotation in class " + className);
			return null;
		}
		
		String TableName = dbTable.name();
		
		// if the name is empty, use the name of the class
		if(TableName.length() <= 0)
		{
			TableName = cl.getName().toUpperCase();
		}
		
		List<String> columnDefines = new ArrayList<String>();
		
		for(Field field: cl.getDeclaredFields())
		{
			String columnName = null;
			Annotation [] annos = field.getAnnotations();
			
			// if it is not a column of DBTable
			if(annos == null || annos.length <= 0)
				continue;
			
			if(annos[0] instanceof SQLString)
			{
				SQLString sqls = (SQLString) annos[0];
				
				// if the name if not specified ,use the name of the Field
				if(sqls.name() == null || sqls.name().length() <= 0)
				{
					columnName = field.getName().toUpperCase();
				}
				else 
				{
					columnName = sqls.name();
				}
				// get the constraints of this column
				String constraints = this.getConstraint(sqls.constrains());				
				String cDefine = columnName +" VARCHAR(" + sqls.value()+" )" + constraints;
				
				columnDefines.add(cDefine);
			}
			else if(annos[0] instanceof SQLInteger)
			{
				SQLInteger sqli = (SQLInteger) annos[0];
				
				// if the name if not specified ,use the name of the Field
				if(sqli.name() == null || sqli.name().length() <= 0)
				{
					columnName = field.getName().toUpperCase();
				}
				else 
				{
					columnName = sqli.name();
				}
				// get the constraints of this column
				String constraints = this.getConstraint(sqli.constrains());				
				String cDefine = columnName +"  INT " + constraints;
				
				columnDefines.add(cDefine);
			}
		}
		
		
		// create the sql of create a table;
		StringBuffer sb = new StringBuffer("CREATE TABLE " + TableName + "(");
		
		for(String columnDef: columnDefines)
		{
			sb.append("\n" + columnDef +",");
		}
		
		String tableCreate = sb.substring(0, sb.length()-1) +" );";
		
		return tableCreate;
	}
	
	public static void main(String[] args) throws Exception 
	{
		String createTable = new TableCreator().createDBTableSql("UserMember.class");
		System.out.println(createTable);
	}

	public String getConstraint(Constrains con)
	{
		String constrains = "";
		if(!con.allowNull())
			constrains += " NOT NULL ";
		if(con.primaryKey())
			constrains += " PRIMARY KEY";
		if(con.unique())
			constrains += " UNIQUE ";
		
		return constrains;
	}
}
