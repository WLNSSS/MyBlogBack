package com.blog.mapper;

import com.blog.pojo.Paper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BlogMapper {
    @Insert({"insert into papertext(id,papertext) values(#{paperTextId},#{paperText})"})
    public void InsertPaperText(Paper paper);
    @Insert({"insert into paper(id,userid,time,paperpicture,papertext_id,papertitle) values(#{id},#{userId},#{time},#{paperPicture},#{paperTextId},#{papertitle})"})
    public void InsertPaper(Paper paper);
}
