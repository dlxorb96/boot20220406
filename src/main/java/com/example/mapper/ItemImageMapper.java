package com.example.mapper;

import java.util.List;

import com.example.dto.ItemimageDTO;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ItemImageMapper {

    // 아이템이미지코드 찾기
    @Select({ "SELECT IMGCODE FROM ITEMIMAGE WHERE ICODE=#{icode}" })
    public List<Long> selectItemImageList(@Param(value = "icode") long code);

    // 아이템 이미지 하나 넣기
    @Select({ " SELECT * FROM ITEMIMAGE WHERE IMGCODE=#{imgcode} " })
    public ItemimageDTO selectItemImageOne(@Param(value = "imgcode") long icode);

    // 아이템 이미지 수정
    @Update({
            "<script>",
            " UPDATE ITEMIMAGE SET ",
            "IIMAGE = #{obj.iimage, jdbcType=BLOB}, ",
            "IIMAGESIZE = #{obj.iimagesize}, ",
            "IIMAGETYPE = #{obj.iimagetype}, ",
            "IIMAGENAME = #{obj.iimagename} ",
            "WHERE IMGCODE = #{obj.imgcode} ",
            "</script>"
    })
    public int updateItemImageOne(@Param(value = "obj") ItemimageDTO obj);

    // 일괄등록
    @Insert({
            "<script>",
            "INSERT ALL",
            " <foreach collection='list' item='obj' separator=' '> ",
            " INTO ITEMIMAGE( IMGCODE, IIMAGE, IIMAGESIZE, ",
            " IIMAGETYPE, IIMAGENAME, ICODE) ",
            " VALUES ( #{obj.imgcode}, ",
            " #{obj.iimage, jdbcType=BLOB}, ",
            " #{obj.iimagesize}, #{obj.iimagetype}, ",
            " #{obj.iimagename}, #{obj.icode}  ) ",
            "</foreach>",
            " SELECT * FROM DUAL ",
            "</script>"
    })
    public int insertItemImageBatch(@Param(value = "list") ItemimageDTO list);

    // @Delete({""})

}