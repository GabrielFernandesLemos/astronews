package br.com.chicorialabs.astranovos.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.chicorialabs.astranovos.core.RemoteException
import br.com.chicorialabs.astranovos.core.PostState
import br.com.chicorialabs.astranovos.data.repository.PostRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: PostRepository
) : ViewModel() {

    private val _progressBarVisible = MutableLiveData<Boolean>(false)
    val progressBarVisible: LiveData<Boolean> = _progressBarVisible

    fun showProgressBar() {
        _progressBarVisible.value = true
    }

    fun hideProgressBar() {
        _progressBarVisible.value = false
    }

    private val _snackbar = MutableLiveData<String?>(null)
    val snackbar: LiveData<String?> = _snackbar

    fun onSnackBarShown() {
        _snackbar.value = null
    }

    private val _listPost = MutableLiveData<PostState>()
    val listPost: LiveData<PostState> = _listPost

    init {
        fetchPosts()
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            repository.listPosts()
                .onStart {
                    _listPost.postValue(PostState.Loading)
                }
                .catch {
                    val exception = RemoteException("Unable to connect to SpaceFlight News API")
                    _listPost.postValue(PostState.Error(exception))
                    _snackbar.postValue(exception.message)
                }
                .collect { listPost ->
                    _listPost.value = PostState.Success(listPost)
                }
        }
    }

}
