package ru.aleynikov.recyclerview.api

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.aleynikov.recyclerview.domain.GetCharactersUseCase


class RnMPagingSource(
    private val getCharacterUseCase: GetCharactersUseCase,
    private val filterParams: FilterParams
) : PagingSource<Int, CharacterDto>() {
    override fun getRefreshKey(state: PagingState<Int, CharacterDto>): Int = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterDto> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            getCharacterUseCase.executeCharacters(
                count = 10,
                pages = page,
                status = filterParams.paramStatus,
                gender = filterParams.paramGender
            )
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it.results,
                    prevKey = null,
                    nextKey = if (it.results.isEmpty()) null else page + 1
                )
            },
            onFailure = {
                LoadResult.Error(it)
            }
        )
    }

    private companion object {
        private const val FIRST_PAGE = 1
    }
}