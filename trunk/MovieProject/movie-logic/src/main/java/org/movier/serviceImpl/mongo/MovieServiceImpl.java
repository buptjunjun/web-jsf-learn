package org.movier.serviceImpl.mongo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.movier.bean.Movie;
import org.movier.service.MovieService;

public class MovieServiceImpl implements MovieService{

	public Movie getMovie(String id) {
		// TODO Auto-generated method stub
		Movie m =  this.mock();
		return m;
	}

	public String updateMovie(Movie movie) {
		// TODO Auto-generated method stub
		return null;
	}

	public String addMovie(Movie movie) {
		// TODO Auto-generated method stub
		return null;
	}

	public String delMovie(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Movie> getMovie(Date date, int limit) {
		// TODO Auto-generated method stub
		return null;
	}



	public List<Movie> searchMovie(String keywords) {
		// TODO Auto-generated method stub
		Movie m =  this.mock();
		List<Movie> ret = new ArrayList<Movie>();
		ret.add(m);
		ret.add(m);
		ret.add(m);
		return ret;
	}

	private Movie mock()
	{
		Movie movie = new Movie();
		movie.setId("id001");
		movie.setDirector("junjun");
		movie.setDate(new Date());
		movie.setDescription("哈蒙德（理查德•阿滕伯勒 Richard Attenborough 饰）立志要建立一个非同寻常的公园：恐龙将是这个公园的主角。他把众多科学家收归旗下，利用琥珀里面困住的远古蚊子体内的血液，提取出恐龙的基因信息，利用这些信息培育繁殖恐龙。结果如愿以偿，他把怒布拉岛建立成了一个恐龙公园，坚信可以从中赚取大钱。然而，科学家们则忧心忡忡。不幸的事情果然发生了。虽然公园有电脑系统管理，但却因为被员工破坏而造成了无法挽救的失控：所有的恐龙逃出了控制区，人们纷纷逃窜却逃不过恐龙的魔爪。恐龙自相残杀，人们亦死难无数，最后幸存者寥寥，只得四人逃出生天。怒布拉岛上空弥漫着恐怖的气息。");
		movie.setName("侏罗纪公园3");
		movie.setEnName("Jurassic Park 3");
		movie.setScore(4.5f);
		movie.setTimespan(102);
		return movie;
	}
}
