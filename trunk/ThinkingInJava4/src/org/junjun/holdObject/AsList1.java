package org.junjun.holdObject;

import java.util.Arrays;
import java.util.List;


class Snow {}
class Powder extends Snow {}
class Light extends Powder {}
class Heavy extends Powder {}
class Crusty extends Snow {}
class Slush extends Snow {}

public class AsList1 
{

	public static void main(String [] args)
	{
		List<Snow> snow1 = Arrays.asList(new Snow(), new Powder(), new Slush());
		// Arrays.asList( new Powder(), new Light()); 返回的是 List<powder> 所以不行
		/*List<Snow> snow2 = Arrays.asList( new Powder(), new Light());*/
		List<Powder> snow2 = Arrays.asList( new Powder(), new Light());
		
		//加上范型后就可以了
		List<Snow> snow3 = Arrays.<Snow>asList( new Light(), new Light());
	}

}
