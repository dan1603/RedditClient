package com.kalashnyk.denys.redditapp.repository.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*
import android.widget.TextView
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.BindingAdapter

@Entity(tableName = "postDao",
        indices = [Index(value = ["subreddit"], unique = false)])
data class RedditPostEntity(
        @PrimaryKey
        @SerializedName("name")
        val name: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("score")
        val score: Int,
        @SerializedName("author")
        val author: String,
        @SerializedName("subreddit")
        @ColumnInfo(collate = ColumnInfo.NOCASE)
        val subreddit: String,
        @SerializedName("num_comments")
        val num_comments: Int,
        @SerializedName("created_utc")
        val created: Long,
        val thumbnail: String?,
        val url: String?) : BaseObservable()
{
        var indexInResponse: Int = -1

        @Bindable
        fun getFormattedDate(): String {
                return SimpleDateFormat("dd/MM/yyyy").format(Date(this.created * 1000L))
        }

        companion object {
                @BindingAdapter("android:text")
                @JvmStatic
                fun setText(view: TextView, value: Int) {
                        view.text = Integer.toString(value)
                }

                @InverseBindingAdapter(attribute = "android:text")
                fun getText(view: TextView): Int {
                        return Integer.parseInt(view.text.toString())
                }
        }
}