package com.tesis.vehicledatacollection.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AccDataDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insertAccData(AccData data);

    @Query("SELECT * FROM AccData")
    public List<AccData> getAllData();
}
