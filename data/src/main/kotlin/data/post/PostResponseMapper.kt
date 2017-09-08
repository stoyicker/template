package data.post

import domain.ObjectMapper
import domain.api.post.DomainPostResult

internal class PostResponseMapper : ObjectMapper<PostResponse, DomainPostResult> {
    override fun from(source: PostResponse) = DomainPostResult(source.something)
}
