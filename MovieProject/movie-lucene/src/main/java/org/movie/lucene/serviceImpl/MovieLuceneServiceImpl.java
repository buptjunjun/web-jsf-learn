package org.movie.lucene.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.junjun.lucene.BaseIndexer;
import org.junjun.lucene.BaseLucene;
import org.junjun.lucene.BaseSearcher;
import org.junjun.lucene.Indexer;
import org.junjun.lucene.Searcher;
import org.junjun.lucene.util.ObjectToField;
import org.junjun.lucene.util.converter;
import org.movie.lucene.service.MovieLuceneService;
import org.movier.bean.Movie;

public class MovieLuceneServiceImpl implements MovieLuceneService{
	private String path = null;
	private Indexer indexer = null;
	private Searcher searcher = null;
	private BaseLucene base = null;
	public MovieLuceneServiceImpl(String path)
	{
		this.path = path;
		base = new BaseLucene(path);
		
	}

	
	public String index(Movie movie) {
		
		if(this.indexer == null)
		{
			synchronized(this)
			{
				if(this.indexer ==null )
					this.indexer = new BaseIndexer(base);
			}
		}
		this.indexer.index(toDoc(movie));
		this.indexer.refresh();
		System.out.println("indexed:"+movie);
		return null;
	}

	public List<Movie> search(String query) {
		// TODO Auto-generated method stub
		if(this.searcher == null)
		{
			synchronized(this)
			{
				if(this.searcher ==null )
					this.searcher =    new BaseSearcher( base);
			}
		}
		
		List<Document> docs = this.searcher.SearchField(query, "name");
		List<Movie> ret = new ArrayList<Movie>(0);
		if(docs!=null)
		{
			for(Document doc :docs)
			{
				ret.add(toMovie(doc));
			}
		}
		return ret;
	}
	
	
	public Document toDoc(Movie movie)
	{
		return converter.Object2Doc(movie);
	}
	

	/**
	 * convert a doc to a Movie
	 * @param doc
	 * @return
	 */
	static public Movie toMovie(Document doc)
	{
		Movie movie = new Movie();
		java.lang.reflect.Field[] fields = movie.getClass().getDeclaredFields();
		for(java.lang.reflect.Field f: fields)
		{
			ObjectToField otf = f.getAnnotation(ObjectToField.class);
			
			if(otf == null)
				continue;
			f.setAccessible(true);
			String docfieldname = otf.fieldName();
			String objfieldname = f.getName();
			String type = otf.type();
			try
			{
				Object docfieldValue =doc.get(docfieldname) ;
				if(docfieldValue == null) continue;
				
				if ("Date".equals(type))
				{
					
					f.set(movie, new Date(Long.parseLong((String)docfieldValue)));
				}
				else if ("String".equals(type))
				{
					
					f.set(movie, (String)docfieldValue);
				}
				else if ("Integer".equals(type))
				{
					
					f.setInt(movie, Integer.parseInt((String)docfieldValue));
				}
				else if ("List".equals(type))
				{
					String tmpValue = (String)docfieldValue;
					tmpValue = tmpValue.replaceAll("\\[|\\]", "");
					String [] strs = tmpValue.split("\\s+");
					if(strs != null)
					{
						f.set(movie, Arrays.asList(strs));
					}
				}
				else if ("Float".equals(type))
				{
					f.setFloat(movie, Float.parseFloat((String)docfieldValue));
				}
				
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} 
		}
		return movie;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
