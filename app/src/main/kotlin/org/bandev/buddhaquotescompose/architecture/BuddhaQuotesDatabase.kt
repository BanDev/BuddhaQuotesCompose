package org.bandev.buddhaquotescompose.architecture

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import org.bandev.buddhaquotescompose.architecture.lists.ListOfQuotes
import org.bandev.buddhaquotescompose.architecture.lists.ListOfQuotesDao
import org.bandev.buddhaquotescompose.architecture.quotes.Quote
import org.bandev.buddhaquotescompose.architecture.quotes.QuoteDao
import org.bandev.buddhaquotescompose.architecture.quotes.QuoteStore

@Database(
    version = 1,
    entities = [Quote::class, ListOfQuotes::class, BuddhaQuotesDatabase.ListQuote::class],
    exportSchema = false
)
abstract class BuddhaQuotesDatabase : RoomDatabase() {

    /** Provide access to the [QuoteDao] */
    abstract fun quote(): QuoteDao

    /** Provide access to the [ListOfQuotesDao] */
    abstract fun list(): ListOfQuotesDao

    /** Provide access to the [ListQuoteDao] */
    abstract fun listQuote(): ListQuoteDao

    companion object {
        @Volatile
        private var Instance: BuddhaQuotesDatabase? = null

        fun getDatabase(context: Context): BuddhaQuotesDatabase = Instance ?: synchronized(this) {
            Room.databaseBuilder(context.applicationContext, BuddhaQuotesDatabase::class.java, "db")
                .addCallback(
                    object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            repeat(QuoteStore.quotes.size) {
                                db.execSQL("INSERT INTO quote DEFAULT VALUES")
                            }
                            db.execSQL("INSERT INTO list (title) VALUES ('Favourites')")
                        }
                    }
                )
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
        @Query("SELECT * FROM list_quote WHERE id = :listId ORDER BY `order` ASC")
        suspend fun getFrom(listId: Int): MutableList<ListQuote>

        /** Count the quotes in a list */
        @Query("SELECT COUNT(id) FROM list_quote WHERE `id` = :listId AND `quote_id` = :quoteId")
        suspend fun has(quoteId: Int, listId: Int): Int

        /** Add a quote to a list */
        @Query("INSERT INTO list_quote (`id`, `quote_id`, `order`) VALUES (:listId, :quoteId, :order)")
        suspend fun addTo(listId: Int, quoteId: Int, order: Double)

        /** Remove a quote from a list */
        @Query("DELETE FROM list_quote WHERE `id` = :listId AND `quote_id` = :quoteId")
        suspend fun removeFrom(listId: Int, quoteId: Int)

        /** Count the quotes in a list */
        @Query("SELECT COUNT(id) FROM list_quote WHERE `id` = :listId")
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
        @PrimaryKey(autoGenerate = true) val id: Int, // The unique connection id
        @ColumnInfo(name = "quote_id") val quoteId: Int, // The id of the quote
        @ColumnInfo val order: Double, // The order (ASC)
    )
}
