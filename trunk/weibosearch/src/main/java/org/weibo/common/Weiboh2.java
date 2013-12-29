package org.weibo.common;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.h2.tools.RunScript;
import org.h2.tools.Server;

public class Weiboh2 
{
	private static Logger logger = Logger.getLogger(Weiboh2.class);
	private static Server server = null;  
    private String port = "9080";  
    private String dbDir = "./h2db/sample";  
    private String user = "sa";  
    private String password = "";  
    private String sqlpath = "src/main/resources/weibo.sql";
    private Connection conn =  null;
    
    private String sql_insert_weiboid = "";
    private String sql_insert_weibos = "";
    private String sql_query_weiboid_by_key_time = "";
    private String sql_update_weiboid = "";
   
    public Weiboh2() 
    {
    		// check if the server is not running, create and start it 
    		sql_insert_weiboid = ParamStore.getMessage("sql_insert_weiboid");
    		sql_query_weiboid_by_key_time = ParamStore.getMessage("sql_query_weiboid_by_key_time");
    		sql_update_weiboid = ParamStore.getMessage("sql_update_weiboid");
    		
    		 try {
				this.conn = DriverManager.getConnection("jdbc:h2:" + dbDir,user, password);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
    		
    		 if(this.conn!=null)
    			 return;
    		 
    		if(server == null || server.isRunning(false))
    		{	
    			synchronized(Weiboh2.class)
    	    	{
    				if(server == null || server.isRunning(false))
    					this.createStartServer();
    	    	}
    		}
			this.createTables();
    	
	}
    
    /**
     * start create and start server; 
     */
    private void createStartServer() 
    {  
        try 
        {  
           logger.info("starting h2...");  
           server = Server.createTcpServer(new String[] { "-tcpPort", port }).start();
        } 
        catch (SQLException e) 
        {  
           
            e.printStackTrace();  
            logger.info("start h2 error" + e.toString()); 
            throw new RuntimeException(e);  
        }  
    }  

    
    /**
     * create table from sql file.
     */
    private void createTables() 
    {  
        try 
        {  
        	// load driver and get collection to the server
            Class.forName("org.h2.Driver");  
            this.conn = DriverManager.getConnection("jdbc:h2:" + dbDir,user, password);  
            Statement stat = conn.createStatement();              
            
            
            // check if the table is exist
            ResultSet rs= conn.getMetaData().getTables(null, null, "WEIBOID", null);             
            if(!rs.next())
            {
            	 //run the sql script and create the table we defined in the script
            	 RunScript.execute(conn, new FileReader(sqlpath));
            }
            
            rs.close();
         /*   stat.close();  
            conn.close();  */
        }
        catch (Exception e) 
        {  
            e.printStackTrace();  
        }  
    }  
    

    /**
     * update the flag of a item
     * @param type
     * @param id
     * @param flag
     * @return
     */
    public String update(int type,String id, int flag) 
    {
    	PreparedStatement pstmt  = null;
    	try
    	{
    	 
	    	pstmt = conn.prepareStatement(this.sql_update_weiboid);
	    	
	    	if(StringUtils.isEmpty(id))
	    	   return null;
	    	
		
			pstmt.setInt(1, flag);              						 //id
			pstmt.setString(2, id);          							// keyword
			pstmt.setInt(3,type);   									// type
	    	pstmt.execute();

    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		logger.error("update "+ (type==1?"sian":"tx")+":"+id+"  error:"+e.getMessage());
    	}
    	finally
    	{
    		if(pstmt!=null)
	    	{
    			try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		pstmt = null;
	    	}
    	}
    	return null;
    }
    
    /**
     * insert ids to database
     * @param searchIDs
     * @return
     * @throws SQLException
     */
    public String insert(SearchResultID searchIDs) 
    {
    	PreparedStatement pstmt  = null;
    	try
    	{
    	 
    	pstmt = conn.prepareStatement(this.sql_insert_weiboid);
    	
    	List<String> ids = searchIDs.getIds();
    	String keyword = searchIDs.getKeyword();
    	if(StringUtils.isEmpty(keyword) || ids == null || ids.isEmpty())
    	   return null;
    	
    	// insert all the ids to database.
    	for(String id : ids)
    	{
    		Date date = new Date(new java.util.Date().getTime());
    		pstmt.setString(1, id);              						//id
    		pstmt.setString(2, keyword);          						// keyword
    		pstmt.setInt(3, searchIDs.getType());   					// type
    		pstmt.setInt(4,Constants.UNFETCH);                  	    // flag
    		pstmt.setDate(5, date); 									//date
    		
    		try
    		{
    			// perform insert operation
    			pstmt.execute();
    			logger.info("insert:"+id+","+keyword+","+searchIDs.getType()+","+date);
    		}
    		catch(Exception e)
    		{
    			//logger.error(e.toString());
    		}
    	}
    	}catch(Exception e)
    	{
    		
    	}
    	finally
    	{
    		if(pstmt!=null)
	    	{
    			try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		pstmt = null;
	    	}
    	}
    	return null;
    }
    
    
    
    public SearchResultID search(String keyword,int flag,int type) throws SQLException
    {
    	SearchResultID sri = new SearchResultID();
    	sri.setKeyword(keyword.trim());
    	sri.setType(type);
  
    	if(StringUtils.isEmpty(keyword))
     	   return null;
  
    	PreparedStatement pstmt = conn.prepareStatement(this.sql_query_weiboid_by_key_time);
    	
    	pstmt.setInt(1, type);
    	pstmt.setInt(2, flag);
    	
    	ResultSet rs = pstmt.executeQuery();
    	while(rs.next())
    	{
    		String id = rs.getString(1);
    		sri.getIds().add(id);
    	}
    		
    	return sri;
    }
  
    
    /**
     * stop the server
     */
    public void stopServer() 
    {  
    	synchronized(Weiboh2.class)
    	{
	        if (server != null) 
	        {  
	        	
	        	try
	        	{
		           logger.info("stopping h2...");  
		           server.stop();  
		           logger.info("stop successed.");
		           server = null;
	        	}
	        	catch(Exception e)
	        	{
	        		e.printStackTrace();
	        		logger.error("stop h2 error:"+e.toString());  
	        	}
	        }  
    	}
    }  
    
    public static void main(String [] args) 
    {
    	Weiboh2 h2 = new Weiboh2();
    	String keywords = "北邮";
    	try {
    		SearchResultID sri = h2.search(keywords, Constants.UNFETCH, Constants.SINA);
    		System.out.println(sri.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//h2.stopServer();
    }
    
    
    @Override
    protected void finalize() throws Throwable {
    	// TODO Auto-generated method stub
    	super.finalize();
    	this.stopServer();
    }
    
}
