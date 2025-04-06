package org.example.csiapi.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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

    @Insert("INSERT INTO status (device_id, start_timestamp, end_timestamp, status, confidence) " +
            "VALUES (#{deviceId}, #{startTimestamp}, #{endTimestamp}, #{status}, #{confidence})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Status status);

    @Update("UPDATE status SET device_id = #{deviceId}, start_timestamp = #{startTimestamp}, " +
            "end_timestamp = #{endTimestamp}, status = #{status}, confidence = #{confidence} " +
            "WHERE id = #{id}")
    int update(Status status);

    @Delete("DELETE FROM status WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT * FROM status WHERE id = #{id}")
    Status findById(Long id);
}