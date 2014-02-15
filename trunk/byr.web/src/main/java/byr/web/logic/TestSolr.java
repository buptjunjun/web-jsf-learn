package byr.web.logic;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class TestSolr {

	/**
	 * @param args
	 * @throws SolrServerException 
	 */
	public static void main(String[] args) throws SolrServerException {
		
		String serverUrl ="http://localhost:8983/solr/collection1";
		SolrServer solr = new HttpSolrServer(serverUrl);
		SolrQuery solrQuery = new SolrQuery("photo play photo");
		
		// highting  max number of snippet
		solrQuery.setHighlight(true).setHighlightSnippets(3);
		
		//field which will be hightlight
		solrQuery.setParam("hl.fl", "name,features");
		
		solrQuery.setStart(0);
		solrQuery.setRows(10); 
		QueryResponse resp = solr.query(solrQuery); 
		SolrDocumentList hits = resp.getResults();
		
		for(SolrDocument doc: hits)
		{
			String id = (String) doc.getFieldValue("id");
			String name= (String) doc.getFieldValue("name");
			//String features= (String) doc.getFieldValue("features");
			
			
			/*List<String> titleSnippes = resp.getHighlighting().get(id).get("name");
			for(String nippet:titleSnippes)
			{
				
			}*/
			
			List<String> contentSnippes= resp.getHighlighting().get(id).get("features");
			
			if(contentSnippes!=null)
			for(String nippet:contentSnippes)
			{
				System.out.println(nippet);
			}
			
		}
	}

}
