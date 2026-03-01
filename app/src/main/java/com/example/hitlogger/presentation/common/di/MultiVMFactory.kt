package com.example.hitlogger.presentation.common.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class MultiVMFactory @Inject constructor(
    private val viewModelFactories: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Ищем провайдер напрямую по modelClass
        val provider = viewModelFactories[modelClass]
            ?: viewModelFactories.entries.firstOrNull { (key, _) ->
                modelClass.isAssignableFrom(key)
            }?.value
            ?: throw IllegalArgumentException("Unknown ViewModel class: $modelClass")

        return provider.get() as T
    }
}