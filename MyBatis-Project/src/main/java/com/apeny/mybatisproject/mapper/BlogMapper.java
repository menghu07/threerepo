package com.apeny.mybatisproject.mapper;

import com.apeny.mybatisproject.domain.Blog;

public interface BlogMapper {
	Blog selectBlog(int id);
}