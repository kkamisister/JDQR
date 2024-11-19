package com.example.backend.common.util;

import java.util.List;

public class TagParser {

	public static List<String> parseTags(String tags){
		return JsonUtil.readList(tags,String.class);
	}

}
