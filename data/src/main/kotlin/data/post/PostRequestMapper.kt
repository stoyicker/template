package data.post

import domain.ObjectMapper
import domain.post.DomainPostParameters

internal class PostRequestMapper : ObjectMapper<DomainPostParameters, PostParameters> {
    override fun from(source: DomainPostParameters) = PostParameters(source.something)
}
