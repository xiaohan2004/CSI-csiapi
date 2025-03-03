package org.example.csiapi.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
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
}
