package cuidevin.com.blog.service.impl;

import cuidevin.com.blog.dao.CommentRepository;
import cuidevin.com.blog.entity.Comment;
import cuidevin.com.blog.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort = Sort.by("createTime");
        List<Comment> comments = commentRepository.findByBlogId(blogId, sort);
        return eachComment(comments);

    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }

    /**
     * 循环每个顶级的评论节点
     *
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment, c);
            commentsView.add(c);
        }

        return commentsView;
    }

    @Override
    public Comment findByID(Long id) {
        Comment comment = commentRepository.findById(id).get();
        return comment;
    }

    @Transactional
    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }


}
