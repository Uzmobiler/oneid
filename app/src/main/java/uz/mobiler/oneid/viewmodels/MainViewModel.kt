package uz.mobiler.oneid.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.mobiler.oneid.utils.Resource
import uz.mobiler.oneid.data.api.ApiHelperForRegister
import uz.mobiler.oneid.data.models.OneIdResponce
import uz.mobiler.oneid.utils.NetworkHelper
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val networkHelper: NetworkHelper,
    private val apiHelperForRegister: ApiHelperForRegister
) : ViewModel() {

    fun getCode(code1: String): MutableLiveData<Resource<OneIdResponce>> {
        val codeData = MutableLiveData<Resource<OneIdResponce>>()
        viewModelScope.launch {
            codeData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                apiHelperForRegister.getAuthData(code1).let {
                    if (it.isSuccessful) {
                        codeData.postValue(Resource.success(it.body()))

                    } else codeData.postValue(Resource.error(it.errorBody().toString(), null))
                }
            } else codeData.postValue(Resource.error("No internet connection", null))
        }
        return codeData
    }
}