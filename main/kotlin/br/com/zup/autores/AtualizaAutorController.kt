package br.com.zup.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Put
import javax.transaction.Transactional

@Controller("/autores")
class AtualizaAutorController (val autorRepository: AutorRepository) {

    @Put("/{id}")
    @Transactional
    fun atualizar(@PathVariable id: Long, descricao: String): HttpResponse<Any>{
        // buscar um objeto
        val possivelAutor = autorRepository.findById(id)

        if(!possivelAutor.isPresent){
            return HttpResponse.notFound()
        }
        // atualizar o campo
        val autor = possivelAutor.get()
        autor.descricao = descricao

        // retornar ok
        return HttpResponse.ok(DetalhesDoAutorResponse(autor))
    }

}