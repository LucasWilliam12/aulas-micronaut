package br.com.zup

import br.com.zup.autores.EnderecoClient
import br.com.zup.autores.EnderecoResponse
import br.com.zup.autores.NovoAutorRequest
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import javax.inject.Inject

@MicronautTest
internal class CadastrarAutorControllerTest {

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @field:Inject
    lateinit var enderecoClient: EnderecoClient

    @Test
    fun `deve casastrar um novo autor` (){
        // cenario
        val novoAutorRequest = NovoAutorRequest("Lucas William", "lucas@hotmail.com", "meu livro", "777-777", "37b")

        val enderecoResponse = EnderecoResponse("Sabará", "Campina Grande", "PB")
        Mockito.`when`(enderecoClient.consulta(novoAutorRequest.cep)).thenReturn(HttpResponse.ok(enderecoResponse))

        val request = HttpRequest.POST("/autores", novoAutorRequest)
        // Acao
        val response = client.toBlocking().exchange(request, Any::class.java)
        // Corretude (Assertions)
        assertEquals(HttpStatus.CREATED, response.status)
        assertTrue(response.headers.contains("Location"))
        assertTrue(response.header("Location")!!.matches("/autores/\\d".toRegex()))
    }

    @MockBean(EnderecoClient::class)
    fun enderecoMock(): EnderecoClient {
        return Mockito.mock(EnderecoClient::class.java)
    }
}