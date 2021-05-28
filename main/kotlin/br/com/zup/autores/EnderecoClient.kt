package br.com.zup.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.client.annotation.Client

@Client("http://localhost:8081/cep/")
interface EnderecoClient {

    // http://localhost:8081/cep/{cep}
    @Get("{cep}")
    fun consulta(@QueryValue cep: String) : HttpResponse<EnderecoResponse>
}