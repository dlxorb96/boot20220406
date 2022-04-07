package com.example.mapper;

import com.example.dto.BoardDTO;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface BoardMapper {
    @Insert({ " INSERT INTO BOARD(BNO, BTITLE, BCONTENT, BHIT, BREGDATE, BTYPE,UEMAIL) ",
            " VALUES(SEQ_BOARD_NO.NEXTVAL, #{brd.btitle}, #{brd.bcontent}, ",
            "#{brd.bhit}, CURRENT_DATE, #{brd.btype}, #{brd.uemail})" })

    public int insertBoardOne(@Param(value = "brd") BoardDTO board);

    @Delete({ " DELETE FROM BOARD WHERE BNO=#{bno} " })
    public int deleteBoardOne(@Param(value = "bno") long bno);

    @Update({ "UPDATE BOARD SET BTITLE=#{obj.btitle}, BCONTENT=#{obj.bcontent} ",
            "  WHERE BNO=#{obj.bno}" })
    public int updateBoardOne(@Param(value = "obj") BoardDTO board);
}
