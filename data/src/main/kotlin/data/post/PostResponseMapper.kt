package data.post

import data.ObjectMapper
import domain.post.DomainPostResult

internal class PostResponseMapper : ObjectMapper<PostResponse, DomainPostResult> {
    override fun from(source: PostResponse) = DomainPostResult(source.something)
}
