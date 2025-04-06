package org.example.csiapi.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Options;
import org.example.csiapi.pojo.RawData;

import java.util.List;

@Mapper
public interface RawDataMapper {
    @Select("SELECT * FROM raw_data ORDER BY timestamp DESC LIMIT 1")
    RawData getNewestRawData();

    @Select("SELECT * FROM raw_data ORDER BY timestamp DESC LIMIT #{pageSize} OFFSET #{offset}")
    List<RawData> getRawDataListDesc(Integer offset, Integer pageSize);

    @Select("SELECT * FROM raw_data ORDER BY timestamp ASC LIMIT #{pageSize} OFFSET #{offset}")
    List<RawData> getRawDataListAsc(Integer offset, Integer pageSize);

    // 查找时间戳在timestamp-350和timestamp+350之间的数据，返回差值最小的一条数据
    @Select("SELECT * FROM raw_data WHERE ABS(timestamp - #{timestamp}) <= 350 ORDER BY ABS(timestamp - #{timestamp}) LIMIT 1")
    RawData getRawDataByTimestamp(Long timestamp);

    @Select("SELECT * FROM raw_data WHERE timestamp BETWEEN #{startTime} AND #{endTime} ORDER BY timestamp ASC")
    List<RawData> getRawDataBetween(Long startTime, Long endTime);

    @Select("SELECT COUNT(*) FROM raw_data")
    Long getCount();

    @Insert("INSERT INTO raw_data (device_id, timestamp, csi_data, processed) " +
            "VALUES (#{deviceId}, #{timestamp}, #{csiData}, #{processed})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(RawData rawData);

    @Update("UPDATE raw_data SET device_id = #{deviceId}, timestamp = #{timestamp}, " +
            "csi_data = #{csiData}, processed = #{processed} WHERE id = #{id}")
    int update(RawData rawData);

    @Delete("DELETE FROM raw_data WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT * FROM raw_data WHERE id = #{id}")
    RawData findById(Long id);
}
