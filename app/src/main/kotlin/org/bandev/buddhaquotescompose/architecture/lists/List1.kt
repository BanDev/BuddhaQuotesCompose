package org.bandev.buddhaquotescompose.architecture.lists

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A list in the application.
 *
 * Each list has its own id, so we don't need
 * to worry about clashing names like
 * previously. They also have their own user
 * changeable icon, so the id of that is also
 * stored in the db.
 *
 * Each list also has a boolean to describe
 * if the list is 'system' or not. At the
 * moment, this is used for deciding if a list
 * can be deleted e.g. Favourites cannot be but
 * a custom user generated one could be.
 */

@Entity(tableName = "list")
data class List1(
    @PrimaryKey val id: Int, // The unique list id
    @ColumnInfo(typeAffinity = 2) val title: String, // The title of the list
    @ColumnInfo(defaultValue = "0") val system: Boolean, // Is it a 'special' list?
    @ColumnInfo(defaultValue = "0") val icon: Int // The chosen icon of the list
)