package cz.uhk.umte.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import cz.uhk.umte.ui.book.BookDetailScreen
import cz.uhk.umte.ui.book.BookScreen
import cz.uhk.umte.ui.home.HomeScreen
import cz.uhk.umte.ui.movie.MovieDetailScreen
import cz.uhk.umte.ui.movie.MovieScreen
import cz.uhk.umte.ui.series.SeriesDetailScreen
import cz.uhk.umte.ui.series.SeriesScreen

@Composable
fun AppContainer(
    controller: NavHostController,
) {

    NavHost(
        navController = controller,
        startDestination = DestinationHome,
    ) {
        // Graph navigation

        composable(
            route = DestinationHome,
        ) {
            HomeScreen(
                parentController = controller,
            )
        }

        composable(
            route = DestinationMovie
        ) {
            MovieScreen(onNavigateDetail = { movieId ->
                controller.navigateMovieDetailScreen(movieId)
            },
                )
        }

        composable(
            route = DestinationMovieDetail,
            arguments = listOf(navArgument(ArgId) { type = NavType.LongType })
        ) { navBackStackEntry ->
            MovieDetailScreen(
                movieId = navBackStackEntry.arguments?.getLong(ArgId),
                onNavigateDetail = {
                    controller.navigateMovieScreen()
                },
            )
        }

        composable(
            route = DestinationBook
        ) {
            BookScreen(onNavigateDetail = { bookId ->
                controller.navigateBookDetailScreen(bookId)
            },
            )
        }

        composable(
            route = DestinationBookDetail,
            arguments = listOf(navArgument(ArgId) { type = NavType.LongType })
        ) { navBackStackEntry ->
            BookDetailScreen(
                bookId = navBackStackEntry.arguments?.getLong(ArgId),
                onNavigateDetail = {
                    controller.navigateBookScreen()
                },
            )
        }

        composable(
            route = DestinationSeries
        ) {
            SeriesScreen(onNavigateDetail = { seriesId ->
                controller.navigateSeriesDetailScreen(seriesId)
            },
            )
        }

        composable(
            route = DestinationSeriesDetail,
            arguments = listOf(navArgument(ArgId) { type = NavType.LongType })
        ) { navBackStackEntry ->
            SeriesDetailScreen(
                seriesId = navBackStackEntry.arguments?.getLong(ArgId),
                onNavigateDetail = {
                    controller.navigateSeriesScreen()
                },
            )
        }
    }
}

private const val ArgId = "argMovieId"

private const val DestinationHome = "home"
private const val DestinationMovie = "movie"
private const val DestinationMovieDetail = "movie/{$ArgId}"
private const val DestinationBook = "book"
private const val DestinationBookDetail = "book/{$ArgId}"
private const val DestinationSeries = "series"
private const val DestinationSeriesDetail = "series/{$ArgId}"


fun NavHostController.navigateMovieScreen() =
    navigate(DestinationMovie)

fun NavHostController.navigateMovieDetailScreen(movieId: Long) =
    navigate(DestinationMovieDetail.replaceArgLong(ArgId, movieId))

fun NavHostController.navigateBookScreen() =
    navigate(DestinationBook)

fun NavHostController.navigateBookDetailScreen(bookId: Long) =
    navigate(DestinationBookDetail.replaceArgLong(ArgId, bookId))

fun NavHostController.navigateSeriesScreen() =
    navigate(DestinationSeries)

fun NavHostController.navigateSeriesDetailScreen(seriesId: Long) =
    navigate(DestinationSeriesDetail.replaceArgLong(ArgId, seriesId))

private fun String.replaceArgLong(argName: String, value: Long) =
    replace("{$argName}", value.toString())
