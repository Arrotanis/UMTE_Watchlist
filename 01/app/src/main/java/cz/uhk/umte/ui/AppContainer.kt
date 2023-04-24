package cz.uhk.umte.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import cz.uhk.umte.ui.home.HomeScreen
import cz.uhk.umte.ui.movie.MovieDetailScreen
import cz.uhk.umte.ui.movie.MovieScreen

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
            arguments = listOf(navArgument(ArgMovieId) { type = NavType.LongType })
        ) { navBackStackEntry ->
            MovieDetailScreen(
                movieId = navBackStackEntry.arguments?.getLong(ArgMovieId),
                onNavigateDetail = {
                    controller.navigateMovieScreen()
                },
            )
        }
    }
}

private const val ArgMovieId = "argMovieId"

private const val DestinationHome = "home"
private const val DestinationMovie = "movie"
private const val DestinationMovieDetail = "movie/{$ArgMovieId}"


fun NavHostController.navigateMovieScreen() =
    navigate(DestinationMovie)

fun NavHostController.navigateMovieDetailScreen(movieId: Long) =
    navigate(DestinationMovieDetail.replaceArgLong(ArgMovieId, movieId))

private fun String.replaceArgLong(argName: String, value: Long) =
    replace("{$argName}", value.toString())
