package com.blog.mapper;

import com.blog.pojo.Paper;
import com.blog.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BlogMapper {
    @Insert({"insert into papertext(id,papertext) values(#{paperTextId},#{paperText})"})
    public void InsertPaperText(Paper paper);
    @Insert({"insert into paper(id,userid,time,paperpicture,papertext_id,papertitle) values(#{id},#{userId},#{time},#{paperPicture},#{paperTextId},#{papertitle})"})
    public void InsertPaper(Paper paper);
    @Select({"select a.id,a.time,a.papertitle,a.paperpicture,left(b.papertext,20) papertext from paper a,papertext b where a.papertext_id = b.id and a.userid = #{id}"})
    public List<Paper> searchSimplyPaper(@Param("id")String id);
}
