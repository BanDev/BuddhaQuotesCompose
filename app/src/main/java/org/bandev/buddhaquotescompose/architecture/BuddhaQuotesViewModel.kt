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

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.bandev.buddhaquotescompose.items.ListData
import org.bandev.buddhaquotescompose.items.ListIcon
import org.bandev.buddhaquotescompose.items.Quote
import java.util.Calendar

/**
 * A level of abstraction between the ui
 * and the db layers. Launches coroutines
 * to the repository.
 *
 * @author Jack Devey
 * @date 27/07/21
 */

class BuddhaQuotesViewModel(application: Application) : AndroidViewModel(application) {

    private val repo: Repository = Repository(application)

    /**
     * Interact with the quote table of the
     * database to allow for querying quotes
     * and liking or unliking them.
     */

    private var _selectedQuote = MutableStateFlow(Quote())
    val selectedQuote: StateFlow<Quote> = _selectedQuote.asStateFlow()

    private var _dailyQuote = MutableStateFlow(Quote())
    val dailyQuote: StateFlow<Quote> = _dailyQuote.asStateFlow()

    fun setNewQuote(quote: Quote) {
        _selectedQuote.value = quote
    }

    fun toggleLikedOnSelectedQuote() {
        _selectedQuote.update { quote ->
            quote.copy(isLiked = !quote.isLiked)
        }
    }

    private val _quotes = MutableStateFlow<List<Quote>>(emptyList())
    val quotes: StateFlow<List<Quote>> = _quotes.asStateFlow()

    init {
        viewModelScope.launch {
            Quotes().run {
                _selectedQuote.value = getRandom()
                _dailyQuote.value = getDaily()
                _quotes.value = getAll()
            }
        }
    }

    inner class Quotes {

        private val quotes: Repository.Quotes = repo.Quotes()

        /** Get one singular quote */
        suspend fun get(id: Int): Quote = withContext(Dispatchers.IO) {
            quotes.get(id)
        }

        /** Get all quotes */
        suspend fun getAll(): List<Quote> = withContext(Dispatchers.IO) {
            quotes.getAll()
        }

        /** Get a random quote */
        suspend fun getRandom(): Quote = get((1..quotes.count()).random())

        /** Get the quote of the day */
        suspend fun getDaily(): Quote {
            return get(Calendar.getInstance().get(Calendar.DAY_OF_YEAR) % quotes.count())
        }

        /** Set the like state of a quote */
        suspend fun setLike(id: Int, liked: Boolean) = withContext(Dispatchers.IO) {
            if (liked) quotes.like(id) else quotes.unlike(id)
        }
    }

    /**
     * Interact with the list table of the
     * database to allow for adding or removing
     * list records and renaming and updating
     * some UI elements like title and icon.
     */

    private val _lists = MutableStateFlow<List<ListData>>(emptyList())
    val lists: StateFlow<List<ListData>> = _lists.asStateFlow()

    init {
        viewModelScope.launch {
            _lists.value = Lists().getAll().toMutableList()
        }
    }


    inner class Lists {
        private val _lists: Repository.Lists = repo.Lists()

        /** Get one singular list */
        suspend fun get(id: Int): ListData = withContext(Dispatchers.IO) {
            _lists.get(id)
        }

        /** Get all lists */
        suspend fun getAll(): List<ListData> = withContext(Dispatchers.IO) {
            _lists.getAll()
        }

        /** Rename a list */
        suspend fun rename(id: Int, title: String) = withContext(Dispatchers.IO) {
            _lists.rename(id, title)
        }

        /** Update a list's icon */
        suspend fun updateIcon(id: Int, icon: ListIcon): ListData = withContext(Dispatchers.IO) {
            _lists.updateIcon(id, icon)
            _lists.get(id)
        }

        /** New empty list */
        suspend fun new(title: String): ListData = withContext(Dispatchers.IO) {
            _lists.new(title).also { this@BuddhaQuotesViewModel._lists.value = this@BuddhaQuotesViewModel._lists.value.plus(ListData(lists.value.size, title)) }
        }

        /** Delete a list */
        suspend fun delete(id: Int) = withContext(Dispatchers.IO) {
            if (id != 0) {
                _lists.delete(id)
                this@BuddhaQuotesViewModel._lists.value = this@BuddhaQuotesViewModel._lists.value.filterNot { it.id == id }
            }
        }

    }

    /**
     * Interact with the database's record
     * linking table to add, remove and count
     * up all of the quotes that a list has.
     */

    inner class ListQuotes {

        private val listQuotes: Repository.ListQuotes = repo.ListQuotes()

        /** Get just one list */
        fun getFrom(id: Int, after: (quotes: List<Quote>) -> Unit) {
            viewModelScope.launch(Dispatchers.IO) {
                after(listQuotes.getFrom(id))
            }
        }

        /** If the quote exists */
        fun exists(quote: Quote, list: ListData, after: (has: Boolean) -> Any) {
            viewModelScope.launch(Dispatchers.IO) {
                after(listQuotes.has(quote.id, list.id))
            }
        }

        /** Add a quote to a list */
        fun addTo(id: Int, quote: Quote) {
            viewModelScope.launch(Dispatchers.IO) {
                listQuotes.addTo(id, quote)
            }
        }

        /** Add a quote to a list from just quote id */
        fun addTo(listId: Int, quoteId: Int) {
            viewModelScope.launch(Dispatchers.IO) {
                listQuotes.addTo(listId, quoteId)
            }
        }

        /** Remove a quote from a list */
        fun removeFrom(id: Int, quote: Quote) {
            viewModelScope.launch(Dispatchers.IO) {
                listQuotes.removeFrom(id, quote)
            }
        }

        /** Count the quotes in a list */
        fun count(id: Int, after: (size: Int) -> Unit) {
            viewModelScope.launch(Dispatchers.IO) {
                after(listQuotes.count(id))
            }
        }

    }

}