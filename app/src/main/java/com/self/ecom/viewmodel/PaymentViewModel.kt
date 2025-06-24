package com.self.ecom.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PaymentViewModel : ViewModel() {
    private val _paymentSuccess = MutableStateFlow(false)
    val paymentSuccess = _paymentSuccess.asStateFlow()

    fun notifyPaymentSuccess() {
        _paymentSuccess.value = true
    }

    fun resetPaymentSuccess() {
        _paymentSuccess.value = false
    }
}
