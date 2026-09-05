package br.com.lactarehub.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.lactarehub.core.di.ServiceLocator
import br.com.lactarehub.domain.entity.Donor
import br.com.lactarehub.domain.entity.Testimonial
import br.com.lactarehub.domain.entity.TestimonialType
import kotlinx.coroutines.launch

/** Estado do formulário de publicação de depoimento. */
class WriteTestimonialViewModel : ViewModel() {

    var donor by mutableStateOf<Donor?>(null)
        private set
    var message by mutableStateOf("")
        private set
    var messageError by mutableStateOf<String?>(null)
        private set
    var type by mutableStateOf(TestimonialType.RECORRENTE)
        private set
    var isSubmitting by mutableStateOf(false)
        private set

    init {
        viewModelScope.launch { donor = ServiceLocator.getDonorProfile() }
    }

    fun onMessageChange(value: String) {
        message = value
        if (messageError != null) messageError = null
    }

    fun selectType(value: TestimonialType) {
        type = value
    }

    /** Publica o depoimento escrito pela doadora. */
    fun submit(onPublished: () -> Unit) {
        val currentDonor = donor ?: return

        if (message.trim().length < 20) {
            messageError = "Escreva ao menos 20 caracteres."
            return
        }

        viewModelScope.launch {
            isSubmitting = true
            ServiceLocator.submitTestimonial(
                Testimonial(
                    id = "dep-${System.currentTimeMillis()}",
                    authorName = currentDonor.fullName,
                    city = currentDonor.city,
                    state = currentDonor.state,
                    message = message.trim(),
                    type = type,
                    avatarGradientIndex = currentDonor.avatarGradientIndex,
                ),
            )
            isSubmitting = false
            onPublished()
        }
    }
}
