package domain.api.post

import domain.ObjectMapper

internal class DomainPostParameterMapper : ObjectMapper<String, DomainPostParameters> {
    override fun from(source: String) = DomainPostParameters(source)
}
