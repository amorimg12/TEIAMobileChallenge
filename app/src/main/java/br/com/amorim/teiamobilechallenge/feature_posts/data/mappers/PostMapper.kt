package br.com.amorim.teiamobilechallenge.feature_posts.data.mappers

import br.com.amorim.teiamobilechallenge.feature_posts.data.local.PostEntity
import br.com.amorim.teiamobilechallenge.feature_posts.data.remote.PostDto
import br.com.amorim.teiamobilechallenge.feature_posts.domain.model.Post

fun PostDto.toPostEntity(): PostEntity {
    return PostEntity(
        id = id,
        userId = userId,
        title = title,
        body = body
    )
}

fun PostEntity.toPost(): Post {
    return Post(
        id = id,
        userId = userId,
        title = title,
        body = body
    )
}