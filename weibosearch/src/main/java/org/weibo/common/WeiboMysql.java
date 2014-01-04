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


public class WeiboMysql 
{
	private static Logger logger = Logger.getLogger(WeiboMysql.class);
    private String url = "jdbc:mysql://localhost:3306/weibo";  
    private String user = "root";  
    private String password = "";  
    private Connection conn =  null;
    
    private String sql_insert_weiboid ="INSERT INTO WEIBOID VALUES (? , ? , ? , ? , ?,?)";
    private String sql_query_weiboid_by_key_time = "select * from WEIBOID where TYPE=? and FLAG=? AND KEYWORD=? ORDER BY  FETCHDATE  LIMIT 1 ";	
    private String sql_update_weiboid ="UPDATE WEIBOID SET FLAG=? WHERE PRIMARYKEY=? and TYPE=?";
    private String sql_delete_weiboid ="delete from WEIBOID where PRIMARYKEY=?";
    
    public WeiboMysql() 
    {
    		// check if the server is not running, create and start it 
    		/*sql_insert_weiboid = ParamStore.getMessage("sql_insert_weiboid");
    		sql_query_weiboid_by_key_time = ParamStore.getMessage("sql_query_weiboid_by_key_time");
    		sql_update_weiboid = ParamStore.getMessage("sql_update_weiboid");*/
    		
    		 try {
				this.conn = DriverManager.getConnection(this.url,user, password);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
    		
    		 if(this.conn!=null)
    			 return;
    	
	}
    
    /**
     * delete a record according to primarykey
     * @param primaykey
     * @return
     */
    public String delete(String primaykey)
    {
      	PreparedStatement pstmt  = null;
    	try
    	{
    	 
	    	pstmt = conn.prepareStatement(this.sql_delete_weiboid);
	    	
	    	if(StringUtils.isEmpty(primaykey))
	    	   return null;
	    	
			pstmt.setString(1, primaykey);          							// keyword		 									
	    	pstmt.execute();

	    	logger.info("delete "+ "primarykey="+primaykey);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		logger.error("delete "+ "primarykey="+primaykey+"  error:"+e.getMessage());
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
     * update the flag of a item
     * @param type
     * @param id
     * @param flag
     * @return
     */
    public String update(int type,String primaykey, int flag) 
    {
    	PreparedStatement pstmt  = null;
    	try
    	{
    	 
	    	pstmt = conn.prepareStatement(this.sql_update_weiboid);
	    	
	    	if(StringUtils.isEmpty(primaykey))
	    	   return null;
	    	
		
			pstmt.setInt(1, flag);              						 //id
			pstmt.setString(2, primaykey);          							// keyword
			pstmt.setInt(3,type);   									// type
	    	pstmt.execute();
	    	logger.info("update "+ (type==1?"sian":"tx")+":"+primaykey);

    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		logger.error("update "+ (type==1?"sian":"tx")+":"+primaykey+"  error:"+e.getMessage());
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
	    		
	    		String primaryKey = WeiboUtil.encode(keyword)+id;   		
	    		pstmt.setString(6, primaryKey); 						    //primaryKey
	    		
	    		try
	    		{
	    			// perform insert operation
	    			pstmt.execute();
	    			logger.info("insert:"+id+","+keyword+","+searchIDs.getType()+","+date);
	    		}
	    		catch(Exception e)
	    		{
	    			logger.error(e.toString());
	    		}
	    	}
    	
    	}catch(Exception e)
    	{
    		e.printStackTrace();
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
    	pstmt.setString(3,keyword);
    	
    	ResultSet rs = pstmt.executeQuery();
    	while(rs.next())
    	{
    		String id = rs.getString(1);
    		sri.getIds().add(id);
    	}
    		
    	return sri;
    }
  
  
    
    public static void main(String [] args) 
    {
    	WeiboMysql h2 = new WeiboMysql();
    	String keywords = "北邮 haha";
    	try {
    		//SearchResultID sri = h2.search(keywords, Constants.UNFETCH, Constants.SINA);
    		h2.delete("e0d3da31ceb0a0fdbbe06e232c4b8c443662810632367086");
    		
    		//System.out.println(sri.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//h2.stopServer();
    }
    
    
}
