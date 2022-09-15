package cuidevin.com.blog.entity;

import lombok.Data;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.util.Date;

@Data
public class Comment {

    private Long id;
    private String content;
    private Long blogId;
    private Long userId;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Transient
    private User user;
}

