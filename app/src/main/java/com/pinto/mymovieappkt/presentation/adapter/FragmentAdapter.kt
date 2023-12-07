package com.pinto.mymovieappkt.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.pinto.mymovieappkt.presentation.screen.favorites.FavoritesFragment
import com.pinto.mymovieappkt.presentation.screen.favorites.favorite_movies.FavoriteMoviesFragment
import com.pinto.mymovieappkt.presentation.screen.favorites.favorite_tvs.FavoriteTvsFragment
import com.pinto.mymovieappkt.presentation.screen.home.HomeFragment
import com.pinto.mymovieappkt.presentation.screen.home.movie_explore.MovieExploreFragment
import com.pinto.mymovieappkt.presentation.screen.home.tv_explore.TvExploreFragment
import com.pinto.mymovieappkt.presentation.screen.search.SearchFragment
import com.pinto.mymovieappkt.presentation.screen.setting.SettingsFragment
import com.pinto.mymovieappkt.utils.Constants

class FragmentAdapter(
    private val fragment: Fragment,
) : FragmentStateAdapter(fragment.childFragmentManager, fragment.viewLifecycleOwner.lifecycle) {
    private val homeFragments = listOf(MovieExploreFragment(), TvExploreFragment())
    private val favoritesFragments = listOf(FavoriteMoviesFragment(), FavoriteTvsFragment())
    private val searchFragments = listOf(SearchFragment())
    private val settingFragments = listOf(SearchFragment())


    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (fragment) {
            is HomeFragment -> homeFragments[position]
            is FavoritesFragment -> favoritesFragments[position]
            is SearchFragment -> searchFragments[position]
            is SettingsFragment -> settingFragments[position]
            else -> throw IllegalArgumentException(Constants.ILLEGAL_ARGUMENT_FRAGMENT_TYPE)
        }
    }
}