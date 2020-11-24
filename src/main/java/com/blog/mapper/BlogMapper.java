package com.blog.mapper;

import com.blog.pojo.Paper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BlogMapper {
    @Insert({"insert into papertext(id,papertext) values(#{paperTextId},#{paperText})"})
    public void InsertPaperText(Paper paper);
    @Insert({"insert into papertext(id,userid,time,paperpicture,papertextid) values(#{paperTextId},#{paperText})"})
    public void InsertPaper(Paper paper);
}
