package cuidevin.com.blog.service;


import cuidevin.com.blog.entity.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);

    Comment findByID(Long id);

    void deleteComment(Long id);
}
