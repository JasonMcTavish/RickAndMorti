package ru.aleynikov.recyclerview.domain

import ru.aleynikov.recyclerview.api.ResponseCharactersDto
import ru.aleynikov.recyclerview.data.RnMRepository
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(private val rnMRepository: RnMRepository) {
    suspend fun executeCharacters(
        count: Int, pages: Int, status: String, gender: String
    ): ResponseCharactersDto {
        return rnMRepository.getAllCharacters(count, pages, status, gender)
    }
}