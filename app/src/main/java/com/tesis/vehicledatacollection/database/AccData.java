package com.tesis.vehicledatacollection.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AccData {
    @PrimaryKey(autoGenerate = true)
    public long id;

    // @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    public long timestamp;

    public String accX;
    public String accY;
    public String accZ;

    public AccData(String accX, String accY, String accZ) {
        this.accX = accX;
        this.accY = accY;
        this.accZ = accZ;
        this.timestamp = System.currentTimeMillis();
    }
}
