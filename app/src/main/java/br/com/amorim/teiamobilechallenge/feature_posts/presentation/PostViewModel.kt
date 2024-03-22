package br.com.amorim.teiamobilechallenge.feature_posts.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import br.com.amorim.teiamobilechallenge.feature_posts.data.local.PostEntity
import br.com.amorim.teiamobilechallenge.feature_posts.data.mappers.toPost
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    pager: Pager<Int, PostEntity>
) : ViewModel() {
    val postPagingFlow = pager
        .flow
        .map { pagingData ->
            pagingData.map { it.toPost() }
        }
        .cachedIn(viewModelScope)
}
