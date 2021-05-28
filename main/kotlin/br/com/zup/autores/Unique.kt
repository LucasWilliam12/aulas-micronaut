package br.com.zup.autores

import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.validation.validator.constraints.ConstraintValidator
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext
import javax.inject.Singleton
import javax.validation.Constraint
import kotlin.annotation.AnnotationRetention.*
import kotlin.annotation.AnnotationTarget.*

@MustBeDocumented
@Target(FIELD, CONSTRUCTOR)
@Retention(RUNTIME)
@Constraint(validatedBy = [EmailContraint::class])
annotation class Unique(val message: String = "Email já cadastrado")

@Singleton
class EmailContraint (val autorRepository: AutorRepository): ConstraintValidator<Unique, String> {

    override fun isValid(
        value: String?,
        annotationMetadata: AnnotationValue<Unique>,
        context: ConstraintValidatorContext
    ): Boolean {
        if(value == null){
            return false
        }
        val possivelAutor = autorRepository.buscaPorEmail(value)
        if(possivelAutor.isPresent){
            return false
        }

        return true
    }

}