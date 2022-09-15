package cuidevin.com.blog.service;

import cuidevin.com.blog.entity.Blog;

import java.util.List;

public interface BlogService {

    Blog getBlog(Long id);

    Blog getAndConvert(Long id);


    List<Blog> listRecommendBlogTop(Integer size);

    Long countBlog();

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id, Blog blog);

    void deleteBlog(Long id);
}
