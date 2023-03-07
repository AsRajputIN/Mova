package com.muratozturk.metflix.ui.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.muratozturk.metflix.data.model.FilterResult
import com.muratozturk.metflix.domain.model.MovieUI
import com.muratozturk.metflix.domain.model.SerieUI
import com.muratozturk.metflix.domain.use_case.explore.GetDiscoverMoviesUseCase
import com.muratozturk.metflix.domain.use_case.explore.GetDiscoverSeriesUseCase
import com.muratozturk.metflix.domain.use_case.explore.GetSearchMovieUseCase
import com.muratozturk.metflix.domain.use_case.explore.GetSearchSerieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val getDiscoverMoviesUseCase: GetDiscoverMoviesUseCase,
    private val getDiscoverSeriesUseCase: GetDiscoverSeriesUseCase,
    private val getSearchMovieUseCase: GetSearchMovieUseCase,
    private val getSearchSerieUseCase: GetSearchSerieUseCase
) : ViewModel() {

    private val _discoverMovies: MutableStateFlow<PagingData<MovieUI>> =
        MutableStateFlow(PagingData.empty())
    val discoverMovies
        get() = _discoverMovies.asStateFlow()


    private val _discoverSeries: MutableStateFlow<PagingData<SerieUI>> =
        MutableStateFlow(PagingData.empty())
    val discoverSeries
        get() = _discoverSeries.asStateFlow()


    fun getDiscoverMovies(filterResult: FilterResult?) = viewModelScope.launch {
        getDiscoverMoviesUseCase(filterResult).cachedIn(viewModelScope).collectLatest {
            _discoverMovies.emit(it)
            Timber.d("getDiscoverMovies $it")
        }
    }

    fun getDiscoverSeries(filterResult: FilterResult?) = viewModelScope.launch {
        getDiscoverSeriesUseCase(filterResult).cachedIn(viewModelScope).collectLatest {
            _discoverSeries.emit(it)
            Timber.d("getDiscoverSeries $it")

        }
    }

    fun getSearchMovie(query: String, includeAdult: Boolean) = viewModelScope.launch {
        getSearchMovieUseCase(query, includeAdult).cachedIn(viewModelScope).collectLatest {
            _discoverMovies.emit(it)
        }
    }

    fun getSearchSerie(query: String, includeAdult: Boolean) = viewModelScope.launch {
        getSearchSerieUseCase(query, includeAdult).cachedIn(viewModelScope).collectLatest {
            _discoverSeries.emit(it)
        }
    }
}