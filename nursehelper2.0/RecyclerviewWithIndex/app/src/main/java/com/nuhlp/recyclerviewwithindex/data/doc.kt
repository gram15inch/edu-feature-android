package com.nuhlp.recyclerviewwithindex.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "document")
data class doc(
    @PrimaryKey
    @ColumnInfo(name = "title")
    val docTitle: String,
    @ColumnInfo(name = "contents")
    val docContents: String,
){
    companion object{
        fun getStub(m: Int,mode: Int): List<doc>{
            val docs = mutableListOf<doc>()
            for(d in 1..30) {
                if (d % mode == 0)
                    docs.add(doc("title$d", "content \r\n$m/$d"))
            }



            return docs
        }
    }
}

