package data.post

import data.ObjectMapper
import domain.post.DomainPostParameters

internal class PostRequestMapper : ObjectMapper<DomainPostParameters, PostParameters> {
    override fun from(source: DomainPostParameters) = PostParameters(source.something)
}
