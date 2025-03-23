package ru.stepanmakeev.a1projectborisproit

data class PopularMovies(
    val page: Int,
    val results: List<ResultX>,
    val total_pages: Int,
    val total_results: Int
)