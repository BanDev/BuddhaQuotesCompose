/**

Buddha Quotes
Copyright (C) 2021  BanDev

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.

 */

package org.bandev.buddhaquotescompose.architecture

import android.content.Context
import androidx.room.*
import org.bandev.buddhaquotescompose.architecture.lists.List1
import org.bandev.buddhaquotescompose.architecture.lists.ListDao
import org.bandev.buddhaquotescompose.architecture.quotes.Quote
import org.bandev.buddhaquotescompose.architecture.quotes.QuoteDao

@Database(
    version = 1,
    entities = [Quote::class, List1::class, BuddhaQuotesDatabase.ListQuote::class],
    exportSchema = false
)
abstract class BuddhaQuotesDatabase : RoomDatabase() {

    /** Provide access to the [QuoteDao] */
    abstract fun quote(): QuoteDao

    /** Provide access to the [ListDao] */
    abstract fun list(): ListDao

    /** Provide access to the [ListQuoteDao] */
    abstract fun listQuote(): ListQuoteDao

    companion object {
        @Volatile
        private var Instance: BuddhaQuotesDatabase? = null

        fun getDatabase(context: Context): BuddhaQuotesDatabase = Instance ?: synchronized(this) {
            Room.databaseBuilder(context.applicationContext, BuddhaQuotesDatabase::class.java, "db")
                .createFromAsset("db.db")
                .fallbackToDestructiveMigration()
                .build()
                .also { Instance = it }
        }
    }

    /**
     * Interact with the database's record
     * linking table to add, remove and count
     * up all of the quotes that a list has.
     */

    @Dao
    interface ListQuoteDao {

        /** Get every single quote from a list */
        @Query("SELECT * FROM list_quote WHERE list_id = :listId ORDER BY `order` ASC")
        suspend fun getFrom(listId: Int): MutableList<ListQuote>

        /** Count the quotes in a list */
        @Query("SELECT COUNT(id) FROM list_quote WHERE `list_id` = :listId AND `quote_id` = :quoteId")
        suspend fun has(quoteId: Int, listId: Int): Int

        /** Add a quote to a list */
        @Query("INSERT INTO list_quote (`list_id`, `quote_id`, `order`) VALUES (:listId, :quoteId, :order)")
        suspend fun addTo(listId: Int, quoteId: Int, order: Double)

        /** Remove a quote from a list */
        @Query("DELETE FROM list_quote WHERE `list_id` = :listId AND `quote_id` = :quoteId")
        suspend fun removeFrom(listId: Int, quoteId: Int)

        /** Count the qoutes in a list */
        @Query("SELECT COUNT(id) FROM list_quote WHERE `list_id` = :listId")
        suspend fun count(listId: Int): Int

    }

    /**
     * So that we can place quotes in the order,
     * that they were added, a custom Many <-> Many
     * solution is required.
     *
     * ListQuote should never be exposed to UI levels
     * and links together quotes and their lists with
     * also an ordering number.
     *
     * When defining an order, remember that bigger
     * numbers are shown last and you can use decimal
     * values.
     */

    @Entity(tableName = "list_quote")
    data class ListQuote(
        @PrimaryKey val id: Int, // The unique connection id
        @ColumnInfo(name = "list_id") val listId: Int, // The id of the list
        @ColumnInfo(name = "quote_id") val quoteId: Int, // The id of the quote
        @ColumnInfo val order: Double, // The order (ASC)
    )

}