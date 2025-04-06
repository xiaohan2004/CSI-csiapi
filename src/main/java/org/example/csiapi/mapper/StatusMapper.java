package org.example.csiapi.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.csiapi.pojo.Status;

import java.util.List;

@Mapper
public interface StatusMapper {

    @Select("SELECT * FROM status ORDER BY id DESC LIMIT 1")
    Status getNewestStatusById();

    @Select("SELECT * FROM status ORDER BY start_timestamp DESC LIMIT 1")
    Status getNewestStatusByStart();

    @Select("SELECT * FROM status ORDER BY end_timestamp DESC LIMIT 1")
    Status getNewestStatusByEnd();

    @Select("SELECT * FROM status WHERE #{timestamp} >= status.start_timestamp and #{timestamp} <= status.end_timestamp LIMIT 1")
    Status getStatusByTimestamp(Long timestamp);

    @Select("SELECT * FROM status WHERE start_timestamp <= #{endTime} AND end_timestamp >= #{startTime} ORDER BY start_timestamp ASC")
    List<Status> getStatusBetween(Long startTime, Long endTime);

    @Select("SELECT COUNT(*) FROM status")
    Long getCount();

    @Select("SELECT * FROM status ORDER BY id DESC LIMIT #{pageSize} OFFSET #{offset}")
    List<Status> getStatusListDesc(int offset, Integer pageSize);

    @Select("SELECT * FROM status ORDER BY id ASC LIMIT #{pageSize} OFFSET #{offset}")
    List<Status> getStatusListAsc(int offset, Integer pageSize);
}