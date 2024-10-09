package ru.aleynikov.recyclerview.api

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.aleynikov.recyclerview.entity.ResponseCharacters
import kotlinx.parcelize.Parcelize
import ru.aleynikov.recyclerview.entity.Character
import ru.aleynikov.recyclerview.entity.Info
import ru.aleynikov.recyclerview.entity.Location
import ru.aleynikov.recyclerview.entity.Origin

@Parcelize
@JsonClass(generateAdapter = true)
data class ResponseCharactersDto(
    @Json(name = "info") override val info: InfoDto?,
    @Json(name = "results") override val results: List<CharacterDto>
) : ResponseCharacters, Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class InfoDto(
    @Json(name = "count") override val count: Int?,
    @Json(name = "pages") override val pages: Int?,
    @Json(name = "next") override val next: String?
) : Info, Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class CharacterDto(
    @Json(name = "id") override val id: Int,
    @Json(name = "name") override val name: String,
    @Json(name = "status") override val status: String?,
    @Json(name = "species") override val species: String,
    @Json(name = "type") override val type: String?,
    @Json(name = "gender") override val gender: String?,
    @Json(name = "origin") override val origin: OriginDto,
    @Json(name = "location") override val location: LocationDto?,
    @Json(name = "image") override val image: String,
    @Json(name = "episode") override val episode: List<String>,
    @Json(name = "url") override val url: String,
    @Json(name = "created") override val created: String
) : Character, Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class OriginDto(
    @Json(name = "name") override val name: String, @Json(name = "url") override val url: String
) : Origin, Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class LocationDto(
    @Json(name = "name") override val name: String?, @Json(name = "url") override val url: String?
) : Location, Parcelable

