package com.example.logistics.myBatis.dao;

import com.example.logistics.myBatis.model.Truck;
import org.mybatis.cdi.Mapper;

import java.util.List;
@Mapper
public interface TruckMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PUBLIC.TRUCK
     *
     * @mbg.generated Sat Apr 12 03:06:25 EEST 2025
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PUBLIC.TRUCK
     *
     * @mbg.generated Sat Apr 12 03:06:25 EEST 2025
     */
    int insert(Truck record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PUBLIC.TRUCK
     *
     * @mbg.generated Sat Apr 12 03:06:25 EEST 2025
     */
    Truck selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PUBLIC.TRUCK
     *
     * @mbg.generated Sat Apr 12 03:06:25 EEST 2025
     */
    List<Truck> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PUBLIC.TRUCK
     *
     * @mbg.generated Sat Apr 12 03:06:25 EEST 2025
     */
    int updateByPrimaryKey(Truck record);
    List<Truck> findByCompany(Long companyId);
}