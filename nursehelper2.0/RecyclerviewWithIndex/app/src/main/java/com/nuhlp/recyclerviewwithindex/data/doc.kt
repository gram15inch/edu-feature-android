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
        fun getStub(size: Int): List<doc>{
            val docs = mutableListOf<doc>()
            for(r in 1..size){
                docs.add(doc("title$r","content$r"))
            }
            return docs
        }
    }
}

