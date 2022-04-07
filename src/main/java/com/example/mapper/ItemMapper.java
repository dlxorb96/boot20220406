package com.example.mapper;

import java.util.List;

import com.example.dto.ItemDTO;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ItemMapper {

        // 물품등록
        @Insert({ "INSERT INTO ITEM( ICODE, INAME, ICONTENT, IPRICE, ",
                        "			IQUANTITY, IIMAGE, IIMAGESIZE, IIMAGETYPE, ",
                        "			IIMAGENAME, UEMAIL ) ",
                        "		VALUES ( SEQ_ITEM_ICODE.NEXTVAL, #{obj.iname},#{obj.icontent},",
                        "			#{obj.iprice}, #{obj.iquantity},",
                        "			#{obj.iimage, jdbcType=BLOB}, #{obj.iimagesize},#{obj.iimagetype},",
                        "			#{obj.iimagename}, #{obj.uemail} )" })
        public int insertItemOne(@Param(value = "obj") ItemDTO item);

        @Select({ "SELECT * FROM (",
                        "			SELECT ",
                        "				I.ICODE, I.INAME, I.IPRICE, ",
                        "				I.IQUANTITY, I.IREGDATE, ",
                        "				ROW_NUMBER() OVER (ORDER BY I.ICODE DESC) ROWN ",
                        "			FROM ",
                        "				ITEM I	",
                        "			WHERE ",
                        "				I.INAME LIKE '%' || #{txt} || '%'",
                        "				AND I.UEMAIL = #{email}",
                        "		) ",
                        "		WHERE ROWN BETWEEN #{start} AND #{end} " })
        public List<ItemDTO> selectItemList(
                        @Param(value = "email") String email,
                        @Param(value = "txt") String txt,
                        @Param(value = "start") int start,
                        @Param(value = "end") int end);

        @Select({ "SELECT",
                        "			COUNT(*) CNT ",
                        "		FROM ",
                        "			ITEM I	",
                        "		WHERE ",
                        "			I.INAME LIKE '%' || #{txt} || '%'",
                        "			AND I.UEMAIL = #{email}" })
        public long selectItemCount(
                        @Param(value = "email") String email,
                        @Param(value = "txt") String txt);

        @Delete({
                        "DELETE FROM ITEM WHERE ICODE=#{code} AND UEMAIL = #{email};"
        })
        public int deleteItemOne(@Param(value = "code") long code,
                        @Param(value = "email") String email);

        @Select("SELECT * FROM ITEM WHERE UEMAIL=#{email} AND ICODE=#{code}")
        public ItemDTO selectOne(@Param(value = "email") String email,
                        @Param(value = "code") long code);

        @Update({
                        "<script>",
                        "UPDATE ITEM SET ",
                        "INAME=#{obj.iname}, ICONTENT=#{obj.icontent}, IPRICE=#{obj.iprice}, ",
                        "IQUANTITY=#{obj.iquantity} ",
                        "<if test='obj.iimage != null'>",
                        ", IIMAGE=#{obj.iimage, jdbcType=BLOB} ,IIMAGESIZE=#{obj.iimagesize}, ",
                        "IIMAGETYPE=#{obj.iimagetype}, IIMAGENAME=#{obj.iimagename} ",
                        "</if>",
                        "WHERE UEMAIL=#{email} AND ICODE=#{obj.icode}",
                        "</script>"
        })
        public int updateOne(
                        @Param(value = "email") String email,
                        @Param(value = "obj") ItemDTO item);

        @Select({ "SELECT I.IIMAGE, I.IIMAGESIZE, I.IIMAGETYPE, ",
                        "I.IIMAGENAME FROM ITEM I WHERE ICODE=#{icode} " })
        public ItemDTO selectItemImage(@Param(value = "icode") long code);
}
