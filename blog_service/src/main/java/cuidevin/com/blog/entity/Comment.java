package cuidevin.com.blog.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue
    private Long id;
    private String content;
    private Long blogId;
    private Long userId;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Transient
    private User user;


}

