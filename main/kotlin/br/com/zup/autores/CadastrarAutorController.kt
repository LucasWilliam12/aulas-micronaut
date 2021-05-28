package br.com.zup.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import javax.validation.Valid

@Validated
@Controller("/autores")
class CadastrarAutorController (val autorRepository: AutorRepository,
                                val enderecoClient: EnderecoClient) {

    @Post
    fun cadastra(@Valid @Body request: NovoAutorRequest): HttpResponse<Any>{

        // Fazer uma requisição para um serviço externo
        val enderecoResponse: HttpResponse<EnderecoResponse> = enderecoClient.consulta(request.cep)

        val autor = request.toModel(enderecoResponse.body()!!)
        autorRepository.save(autor)

        val uri = UriBuilder.of("/autores/{id}")
                            .expand(mutableMapOf("id" to autor.id))

        return HttpResponse.created(uri)
    }
}