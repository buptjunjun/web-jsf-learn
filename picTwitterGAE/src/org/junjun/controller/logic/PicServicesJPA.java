package org.junjun.controller.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;
import org.junjun.bean.part1.Comment;
import org.junjun.bean.part1.Constant;
import org.junjun.bean.part1.Item;
import org.junjun.bean.part1.Tag;
import org.junjun.bean.part1.UIComment;
import org.junjun.bean.part1.User;



public class PicServicesJPA implements PicServices {

	public static final int MAXLIMT = 20;
	public static final int COMMENTLIMIT = 10;
	public  final static int ASCENDING = 0;
	public  final static int DESCENDING = 1;
	
	public PicServicesJPA() {

	}

	public Object getById (Class cls,String id)
	{
		EntityManager em = null;
		try
		{
		  em = EMF.get().createEntityManager();     
		  return em.find(cls,id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(em!=null) em.close();
		}
		
		return null;
	}
	@Override
	public List<Tag> getTag() {
		EntityManager em = null;
		try
		{
		  em = EMF.get().createEntityManager();     
		  List<Tag> tags = em.createQuery("select item from Tag item").getResultList();
		  return tags;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(em!=null) em.close();
		}
		
		return null;
	}

	@Override
	public User getUser(String id) {
		Object obj = this.getById(User.class, id);
		if(obj == null)
			return null;
		return (User)obj;
	}
	
	@Override
	public void updateUser(User user) {
		if (user == null)
			return;
		
		EntityManager em = null;
		try
		{
		  em = EMF.get().createEntityManager();     
		  em.getTransaction().begin();
		  User u = em.find(User.class,user.getId());
		  u.setDate(user.getDate());
		  u.setGender(user.getGender());
		  u.setIdSource(user.getIdSource());
		  u.setPassword(user.getPassword());
		  u.setUrl(user.getUrl());
		  u.setSource(user.getSource());
		  u.setName(user.getName());
		  u.setPic(user.getPic());
		  u.setOtherInfo(user.getOtherInfo());
		  em.getTransaction().commit();
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(em!=null) em.close();
		}
		
		
	}

	@Override
	public List<UIComment> getUIComments(String id)
	{
		// TODO Auto-generated method stub
		List<UIComment> uicomments = new ArrayList<UIComment>();
		
		if (id != null) 
		{
			List<Comment> comments = null;
			EntityManager em = EMF.get().createEntityManager();
			
			Query query =  em.createQuery("select item from Comment item where item.commentTo=:id");
			query.setParameter("id", id);
			comments = query.setMaxResults(COMMENTLIMIT).getResultList();
			
			if (comments != null) {
				for (Comment comment : comments) {
					UIComment uicomment = new UIComment();
					User user = this.getUser(comment.getCommentFrom());
					uicomment.setComment(comment);
					if (user != null) {
						uicomment.setUser(user);
						user.setId(null);
						user.setOtherInfo(null);
						user.setPassword(null);
						user.setIdSource(null);
						user.setIdSource(null);
					}
					uicomments.add(uicomment);
				}

				return uicomments;
			}
		}

		return null;
	}

	/**
	 * 
	 * @param type
	 *            cute funny or ....s
	 * @param dategt
	 *            date > dategt
	 * @param datelt
	 *            date < datelt
	 * @param rating
	 *            rating is the score of a item
	 * @param sortField
	 *            sort by sortField
	 * @param sortWay
	 * @param limit
	 * @return
	 */
	public List<Item> getItem(String type, Date dategt, Date datelt,int rating, String sortField, int sortWay, int limit) 
	{
		EntityManager em = EMF.get().createEntityManager();
		String jqpl = "select item from Item item " ;
		if(limit < 0 )
			limit = Buffer.BUFFERLIMIT;
		boolean flag = false;
		
		sortField = "date";
		/*if(rating < 0)
			rating = Integer.MAX_VALUE;*/
		
		
		if(type != null)
		{
			if(flag == false)
			{
				jqpl += " where item.type= :type";
				flag = true;
			}
			else
				jqpl += " and  item.type= :type";
		}
		
		String sqldate= "";
		String sqltotal ="";
		if(dategt != null)
		{
			if(flag == false)
			{
				sqldate += " where item.date> :dategt";
				flag = true;
			}
			else
				sqldate += " and  item.date> :dategt";
		}
		
		if(datelt != null)
		{
			if(flag == false)
			{
				sqldate += " where item.date < :datelt ";
				flag = true;
			}
			else
				sqldate += " and item.date < :datelt ";
		}
		
		if(dategt == null && datelt ==null)
		{
			if(flag == false)
			{
				sqldate += " where item.date < :yearlater ";
				flag = true;
			}
			else
				sqldate += " and item.date < :yearlater ";
		}
	/*	
		if(flag == false)
		{
			sqltotal += " where item.total< :rating ";
			flag = true;
		}
		else
			sqltotal += " and item.total< :rating ";
		*/
		if(sortField != null)
		{
			if("date".equals(sortField))
			{ 
				jqpl+=sqldate;
				//jqpl+=sqltotal;
				
			}
			else
			{
				
			//	jqpl+=sqltotal;
				jqpl+=sqldate;
						
			}
				
			jqpl+=" order by item."+sortField; 
			if(sortWay == DESCENDING)
				jqpl+=" desc ";
			else if(sortWay == ASCENDING)
				jqpl+=" asc ";
				
		}
		
			
		
		
		System.out.println(jqpl);
		Query query =  em.createQuery(jqpl);
		
		if(type != null)
			query.setParameter("type", type);
		
		if(dategt != null)
			query.setParameter("dategt", dategt,TemporalType.DATE);
		
		if(datelt != null)
			query.setParameter("datelt", datelt,TemporalType.DATE);
		
		if(dategt == null && datelt == null)
		{
			Date date = new Date();
			date = PicUtil.getDateBefore(date, -1);
			query.setParameter("yearlater", date,TemporalType.DATE);
			
		}
			
			
	/*	if(rating <=0)
			query.setParameter("rating", Integer.MAX_VALUE);
		else
			query.setParameter("rating",rating);*/
		
		System.out.println(query.toString());
		List<Item> litems = query.setMaxResults(limit).getResultList();
		
		return litems;
	}

	@Override
	public void insertComment(Comment comment) {
		// TODO Auto-generated method stub
		 insert(comment);
		Item item = this.getItem(comment.getCommentTo());
		item.setComment(item.getComment() + 1);
		this.updateItem(item);
	}

	@Override
	public void insertItem(Item item) {
		this.insert(item);
	}
	
	@Override
	public void insert(Object obj)
	{
		EntityManager em = null;
		try
		{
		  em = EMF.get().createEntityManager();     
		  em.persist(obj);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			
				if(em!=null) em.close();
		}
	}

	@Override
	public Item getItem(String id) {
		Object obj = this.getById(Item.class, id);
		if(obj == null)
			return null;
		return (Item)obj;

	}

	public void updateItem(Item item) {
		if (item == null)
			return;
		
		EntityManager em = null;
		try
		{
		  em = EMF.get().createEntityManager();     
		  em.getTransaction().begin();
		  Item i = em.find(Item.class,item.getId());
		  
		  i.setBad(item.getBad());
		  i.setCata(item.getCata());
		  i.setCollect(item.getCollect());
		  i.setComment(item.getComment());
		  i.setDate(item.getDate());
		  i.setDesc(item.getDesc());
		  i.setGood(item.getGood());
		  i.setUrl1(item.getUrl1());
		  i.setUrl(item.getUrl());
		  i.setTotal(item.getTotal());
		  i.setType(item.getType());
		  
		  em.getTransaction().commit();
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(em!=null) em.close();
		}
	}



	@Override
	public List<Item> getItemByTag(String tag) {
		if (!Buffer.containTag(tag))
			tag = null;
		List<Item> ret =  this.getItem(tag, null, null, -1, "date",DESCENDING, Buffer.BUFFERLIMIT*3);
		return ret;
	}

	@Override
	public List<Item> getItemByTagRest(String tag, String id) {
		Item item = this.getItem(id);
		if (id == null)
			return null;

		if (tag != null)
			tag = item.getType();
		List<Item> ret = this.getItem(tag,null, item.getDate(), -1, "date", DESCENDING, Buffer.BUFFERLIMIT);
		return ret;
	}

	@Override
	public List<Item> getItemByTagAndKind(String tag, String kind) {
		if (!Buffer.containTag(tag))
			tag = null;
		if (!Constant.kinds.contains(kind))
			kind = Constant.defaultKind;

		Date newestDate = Buffer.getNewestItem().getDate();
		int dayBefore = 3;
		if (Constant.daily.equals(kind)) {
			dayBefore = 3;
		} else if (Constant.weekly.equals(kind)) {
			dayBefore = 7;
		} else if (Constant.monthly.equals(kind)) {
			dayBefore = 30;
		}

		Date date = PicUtil.getDateBefore(newestDate, dayBefore);
		List<Item> ret = this.getItem(tag, date, null, -1, "total", DESCENDING, Buffer.BUFFERLIMIT*3);
		return ret;
	}

	@Override
	public Item getNextItem(String id) {
		Item item = this.getItem(id);
		if (id == null)
			return null;

		List<Item> ret = this.getItem(item.getType(),null, item.getDate(), -1, "date", DESCENDING, 1);
		if (ret == null || ret.size() == 0) {
			ret = this.getItem(item.getType(), null,new Date(), -1,"date", DESCENDING, 1);
		}

		if (ret == null || ret.size() != 1)
			return null;

		return ret.get(0);

	}

	@Override
	public Item getPreItem(String id) {

		Item item = this.getItem(id);
		if (id == null)
			return null;

		List<Item> ret =  this.getItem(item.getType(),item.getDate(),null, -1, "date", ASCENDING, 1);

		if (ret == null || ret.size() != 1)
			return null;

		return ret.get(0);
	}

}
