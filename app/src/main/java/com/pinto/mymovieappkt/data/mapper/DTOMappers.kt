package com.pinto.mymovieappkt.data.mapper

import com.pinto.mymovieappkt.data.remote.dto.CompanyDTO
import com.pinto.mymovieappkt.data.remote.dto.CountryDTO
import com.pinto.mymovieappkt.data.remote.dto.CreatorDTO
import com.pinto.mymovieappkt.data.remote.dto.CreditsDTO
import com.pinto.mymovieappkt.data.remote.dto.EpisodeDTO
import com.pinto.mymovieappkt.data.remote.dto.EpisodeDetailDTO
import com.pinto.mymovieappkt.data.remote.dto.ExternalDTO
import com.pinto.mymovieappkt.data.remote.dto.GenreDTO
import com.pinto.mymovieappkt.data.remote.dto.ImageDTO
import com.pinto.mymovieappkt.data.remote.dto.ImageListDTO
import com.pinto.mymovieappkt.data.remote.dto.LanguageDTO
import com.pinto.mymovieappkt.data.remote.dto.MovieCreditsDTO
import com.pinto.mymovieappkt.data.remote.dto.MovieDTO
import com.pinto.mymovieappkt.data.remote.dto.MovieDetailDTO
import com.pinto.mymovieappkt.data.remote.dto.MovieListDTO
import com.pinto.mymovieappkt.data.remote.dto.PersonDTO
import com.pinto.mymovieappkt.data.remote.dto.PersonDetailDTO
import com.pinto.mymovieappkt.data.remote.dto.PersonListDTO
import com.pinto.mymovieappkt.data.remote.dto.SeasonDTO
import com.pinto.mymovieappkt.data.remote.dto.SeasonDetailDTO
import com.pinto.mymovieappkt.data.remote.dto.TvCreditsDTO
import com.pinto.mymovieappkt.data.remote.dto.TvDTO
import com.pinto.mymovieappkt.data.remote.dto.TvDetailDTO
import com.pinto.mymovieappkt.data.remote.dto.TvListDTO
import com.pinto.mymovieappkt.data.remote.dto.VideoDTO
import com.pinto.mymovieappkt.data.remote.dto.VideoListDTO
import com.pinto.mymovieappkt.domain.model.Company
import com.pinto.mymovieappkt.domain.model.Country
import com.pinto.mymovieappkt.domain.model.Creator
import com.pinto.mymovieappkt.domain.model.Credits
import com.pinto.mymovieappkt.domain.model.Episode
import com.pinto.mymovieappkt.domain.model.EpisodeDetail
import com.pinto.mymovieappkt.domain.model.External
import com.pinto.mymovieappkt.domain.model.Genre
import com.pinto.mymovieappkt.domain.model.Image
import com.pinto.mymovieappkt.domain.model.ImageList
import com.pinto.mymovieappkt.domain.model.Language
import com.pinto.mymovieappkt.domain.model.Movie
import com.pinto.mymovieappkt.domain.model.MovieCredits
import com.pinto.mymovieappkt.domain.model.MovieDetail
import com.pinto.mymovieappkt.domain.model.MovieList
import com.pinto.mymovieappkt.domain.model.Person
import com.pinto.mymovieappkt.domain.model.PersonDetail
import com.pinto.mymovieappkt.domain.model.PersonList
import com.pinto.mymovieappkt.domain.model.Season
import com.pinto.mymovieappkt.domain.model.SeasonDetail
import com.pinto.mymovieappkt.domain.model.Tv
import com.pinto.mymovieappkt.domain.model.TvCredits
import com.pinto.mymovieappkt.domain.model.TvDetail
import com.pinto.mymovieappkt.domain.model.TvList
import com.pinto.mymovieappkt.domain.model.Video
import com.pinto.mymovieappkt.domain.model.VideoList

fun CompanyDTO.toCompany(): Company = Company(
    name = name,
    originCountry = originCountry
)

fun CountryDTO.toCountry(): Country = Country(
    name = name
)

fun CreatorDTO.toCreator(): Creator = Creator(
    name = name
)

fun CreditsDTO.toCredits(): Credits = Credits(
    cast = cast.map { it.toPerson() },
    crew = crew.map { it.toPerson() },
    guestStars = guestStars?.map { it.toPerson() }
)

fun EpisodeDTO.toEpisode(): Episode = Episode(
    airDate = airDate,
    episodeNumber = episodeNumber,
    name = name,
    seasonNumber = seasonNumber,
    stillPath = stillPath,
    voteAverage = voteAverage,
    voteCount = voteCount
)

fun EpisodeDetailDTO.toEpisodeDetail(): EpisodeDetail = EpisodeDetail(
    airDate = airDate,
    credits = credits.toCredits(),
    images = images.toImageList(),
    name = name,
    overview = overview,
    runtime = runtime,
    stillPath = stillPath,
    videos = videos.toVideoList(),
    voteAverage = voteAverage,
    voteCount = voteCount
)

fun ExternalDTO.toExternal(): External = External(
    facebookId = facebookId,
    imdbId = imdbId,
    instagramId = instagramId,
    twitterId = twitterId
)

fun GenreDTO.toGenre(): Genre = Genre(
    id = id,
    name = name
)

fun ImageDTO.toImage(): Image = Image(
    filePath = filePath
)

fun ImageListDTO.toImageList(): ImageList = ImageList(
    backdrops = backdrops?.map { it.toImage() },
    posters = posters?.map { it.toImage() },
    profiles = profiles?.map { it.toImage() },
    stills = stills?.map { it.toImage() }
)

fun LanguageDTO.toLanguage(): Language = Language(
    englishName = englishName
)

fun MovieDTO.toMovie(): Movie = Movie(
    character = character,
    id = id,
    job = job,
    overview = overview,
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title,
    voteAverage = voteAverage
)

fun MovieCreditsDTO.toMovieCredits(): MovieCredits = MovieCredits(
    cast = cast.map { it.toMovie() },
    crew = crew.map { it.toMovie() }
)

fun MovieDetailDTO.toMovieDetail(): MovieDetail = MovieDetail(
    budget = budget,
    credits = credits.toCredits(),
    externalIds = externalIds.toExternal(),
    genres = genres.map { it.toGenre() },
    homepage = homepage,
    id = id,
    images = images.toImageList(),
    originalTitle = originalTitle,
    overview = overview,
    posterPath = posterPath,
    productionCompanies = productionCompanies.map { it.toCompany() },
    productionCountries = productionCountries.map { it.toCountry() },
    recommendations = recommendations.toMovieList(),
    releaseDate = releaseDate,
    revenue = revenue,
    runtime = runtime,
    spokenLanguages = spokenLanguages.map { it.toLanguage() },
    status = status,
    title = title,
    videos = videos.toVideoList(),
    voteAverage = voteAverage,
    voteCount = voteCount
)

fun MovieListDTO.toMovieList(): MovieList = MovieList(
    results = results.map { it.toMovie() },
    totalResults = totalResults
)

fun PersonDTO.toPerson(): Person = Person(
    character = character,
    department = department,
    id = id,
    job = job,
    knownForDepartment = knownForDepartment,
    name = name,
    profilePath = profilePath
)

fun PersonDetailDTO.toPersonDetail(): PersonDetail = PersonDetail(
    alsoKnownAs = alsoKnownAs,
    biography = biography,
    birthday = birthday,
    deathday = deathday,
    externalIds = externalIds.toExternal(),
    gender = gender,
    homepage = homepage,
    id = id,
    images = images.toImageList(),
    knownForDepartment = knownForDepartment,
    movieCredits = movieCredits.toMovieCredits(),
    name = name,
    placeOfBirth = placeOfBirth,
    profilePath = profilePath,
    tvCredits = tvCredits.toTvCredits()
)

fun PersonListDTO.toPersonList(): PersonList = PersonList(
    results = results.map { it.toPerson() },
    totalResults = totalResults
)

fun SeasonDTO.toSeason(): Season = Season(
    airDate = airDate,
    episodeCount = episodeCount,
    name = name,
    posterPath = posterPath,
    seasonNumber = seasonNumber
)

fun SeasonDetailDTO.toSeasonDetail(): SeasonDetail = SeasonDetail(
    airDate = airDate,
    credits = credits.toCredits(),
    episodes = episodes.map { it.toEpisode() },
    images = images.toImageList(),
    name = name,
    overview = overview,
    posterPath = posterPath,
    seasonNumber = seasonNumber,
    videos = videos.toVideoList()
)

fun TvDTO.toTv(): Tv = Tv(
    character = character,
    firstAirDate = firstAirDate,
    id = id,
    job = job,
    name = name,
    overview = overview,
    posterPath = posterPath,
    voteAverage = voteAverage
)

fun TvCreditsDTO.toTvCredits(): TvCredits = TvCredits(
    cast = cast.map { it.toTv() },
    crew = crew.map { it.toTv() }
)

fun TvDetailDTO.toTvDetail(): TvDetail = TvDetail(
    createdBy = createdBy.map { it.toCreator() },
    credits = credits.toCredits(),
    episodeRunTime = episodeRunTime,
    externalIds = externalIds.toExternal(),
    firstAirDate = firstAirDate,
    genres = genres.map { it.toGenre() },
    homepage = homepage,
    id = id,
    images = images.toImageList(),
    inProduction = inProduction,
    lastAirDate = lastAirDate,
    name = name,
    networks = networks.map { it.toCompany() },
    nextEpisodeToAir = nextEpisodeToAir?.toEpisode(),
    numberOfEpisodes = numberOfEpisodes,
    numberOfSeasons = numberOfSeasons,
    originalName = originalName,
    overview = overview,
    posterPath = posterPath,
    productionCompanies = productionCompanies.map { it.toCompany() },
    productionCountries = productionCountries.map { it.toCountry() },
    recommendations = recommendations.toTvList(),
    seasons = seasons.map { it.toSeason() },
    spokenLanguages = spokenLanguages.map { it.toLanguage() },
    status = status,
    videos = videos.toVideoList(),
    voteAverage = voteAverage,
    voteCount = voteCount
)

fun TvListDTO.toTvList(): TvList = TvList(
    results = results.map { it.toTv() },
    totalResults = totalResults
)

fun VideoDTO.toVideo(): Video = Video(
    key = key,
    name = name,
    publishedAt = publishedAt,
    site = site,
    type = type
)

fun VideoListDTO.toVideoList(): VideoList = VideoList(
    results = results.map { it.toVideo() }
)