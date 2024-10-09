package ru.aleynikov.recyclerview.data

import ru.aleynikov.recyclerview.api.ResponseCharactersDto
import ru.aleynikov.recyclerview.api.RnMAPI
import javax.inject.Inject

class RnMRepository @Inject constructor(private val charactersAPI: RnMAPI) {
    suspend fun getAllCharacters(
        count: Int,
        pages: Int,
        status: String,
        gender: String
    ): ResponseCharactersDto {
        return charactersAPI.getAllCharacters(count, pages, status, gender)
    }
}