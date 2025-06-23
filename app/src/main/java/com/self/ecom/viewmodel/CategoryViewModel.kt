package com.self.ecom.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.self.ecom.model.CategoryModel
import com.self.ecom.repository.CategoryRepository
//import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.collections.find

class CategoryViewModel (
) : ViewModel() {

    var selectedCategory = mutableStateOf<CategoryModel?>(null)
        private set

    var categories by mutableStateOf<List<CategoryModel>?>(emptyList())
        private set

    fun setAllCategories(list: List<CategoryModel>?) {
        categories = list
    }

    fun loadCategoryById(id: String) {
        selectedCategory.value = getCategoryById(id)
    }

    fun getCategoryById(id: String): CategoryModel? {
        return categories?.find { it.id .equals(id)  }
    }
}
