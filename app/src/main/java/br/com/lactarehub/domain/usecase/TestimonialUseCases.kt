package br.com.lactarehub.domain.usecase

import br.com.lactarehub.domain.entity.Testimonial
import br.com.lactarehub.domain.entity.TestimonialType
import br.com.lactarehub.domain.repository.TestimonialRepository

class ListTestimonials(private val repository: TestimonialRepository) {
    suspend operator fun invoke(type: TestimonialType? = null): List<Testimonial> {
        val testimonials = repository.listTestimonials()
        if (type == null) return testimonials
        return testimonials.filter { it.type == type }
    }
}

class SubmitTestimonial(private val repository: TestimonialRepository) {
    suspend operator fun invoke(testimonial: Testimonial): List<Testimonial> =
        repository.submit(testimonial)
}
