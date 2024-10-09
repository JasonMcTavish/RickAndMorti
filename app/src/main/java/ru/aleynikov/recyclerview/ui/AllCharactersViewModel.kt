package ru.aleynikov.recyclerview.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import ru.aleynikov.recyclerview.api.CharacterDto
import ru.aleynikov.recyclerview.api.FilterParams
import ru.aleynikov.recyclerview.api.RnMPagingSource
import ru.aleynikov.recyclerview.domain.GetCharactersUseCase
import javax.inject.Inject

@HiltViewModel
class AllCharactersViewModel @Inject constructor(
    private val getCharacterUseCase: GetCharactersUseCase
) : ViewModel() {

    lateinit var pagedAdapter: RnMAdapter

    private val filterParams = FilterParams(
        status = "", gender = ""
    )

    val pagedCharacters: Flow<PagingData<CharacterDto>> =
        Pager(config = PagingConfig(pageSize = 10), pagingSourceFactory = {
            RnMPagingSource(getCharacterUseCase, filterParams)
        }).flow.cachedIn(viewModelScope)

    init {
        RnMPagingSource(getCharacterUseCase, filterParams)
    }

    fun setFilterParams(status: String, gender: String) {
        filterParams.paramStatus = status
        filterParams.paramGender = gender
        pagedAdapter.refresh()
    }
}