package br.com.chicorialabs.astranovos.presentation.ui.home

import androidx.lifecycle.*
import br.com.chicorialabs.astranovos.core.RemoteException
import br.com.chicorialabs.astranovos.core.State
import br.com.chicorialabs.astranovos.data.model.Post
import br.com.chicorialabs.astranovos.data.repository.PostRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: PostRepository) : ViewModel() {

    private val _progressBarVisible = MutableLiveData<Boolean>(false)
    val progressBarVisible: LiveData<Boolean>
        get() = _progressBarVisible

    fun showProgressBar() {
        _progressBarVisible.value = true
    }

    fun hideProgressBar() {
        _progressBarVisible.value = false
    }

    private val _snackbar = MutableLiveData<String?>(null)
    val snackbar: LiveData<String?>
        get() = _snackbar

    fun onSnackBarShown() {
        _snackbar.value = null
    }

    private val _listPost = MutableLiveData<State<List<Post>>>()
    val listPost: LiveData<State<List<Post>>>
        get() = _listPost

    init {
        fetchPosts()
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            repository.listPosts()
                .onStart {
                    _listPost.postValue(State.Loading)
                    delay(800) // apenas para efeito cosmético
                }
                .catch {
                    val exception = RemoteException("Unable to connect to SpaceFlight News API")
                    _listPost.postValue(State.Error(exception))
                    _snackbar.postValue(exception.message)
                }
                .collect {
                    _listPost.postValue(State.Success(it))
                }
        }
    }

    val helloText = Transformations.map(listPost) {
        when(it) {
            State.Loading -> { "🚀 Loading latest news..."}
            is State.Error -> { "Houston, we've had a problem! :'("}
            else -> {""} //uma string vazia
        }
    }
}
