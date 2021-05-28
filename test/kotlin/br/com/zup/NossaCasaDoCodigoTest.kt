package br.com.zup
import br.com.zup.autores.*
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.net.http.HttpResponse
import javax.inject.Inject

@MicronautTest
internal class NossaCasaDoCodigoTest {

    @field:Inject
    lateinit var repository: AutorRepository

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    lateinit var autor: Autor

    @BeforeEach
    internal fun setup(){

        val enderecoResponse = EnderecoResponse(rua = "Sabará", cidade = "Campina Grande", estado = "Paraiba")
        val endereco = Endereco(enderecoResponse, numero = "10b")
        autor = Autor(nome = "Lucas", email = "lucas@hotmail.com", descricao = "Olá mundo", endereco = endereco)
        repository.save(autor)

    }

    @AfterEach
    internal fun tearDown(){
        repository.deleteAll()
    }

    @Test
    fun `deve buscar um autor quando um email valido eh informado`() {
        val response = client.toBlocking().exchange("/autores?email=${autor.email}", DetalhesDoAutorResponse::class.java)

        assertEquals(HttpStatus.OK, response.status)
        assertNotNull(response.body())
        assertEquals(autor.nome, response.body()!!.nome)
        assertEquals(autor.descricao, response.body()!!.descricao)
        assertEquals(autor.email, response.body()!!.email)
    }

}
