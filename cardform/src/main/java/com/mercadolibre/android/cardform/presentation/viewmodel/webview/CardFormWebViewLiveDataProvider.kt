package com.mercadolibre.android.cardform.presentation.viewmodel.webview

import androidx.lifecycle.MutableLiveData
import com.mercadolibre.android.cardform.base.MutableSingleLiveData
import com.mercadolibre.android.cardform.presentation.model.ScreenState
import com.mercadolibre.android.cardform.presentation.model.WebUiState
import com.mercadolibre.android.cardform.presentation.model.WebViewData

internal object CardFormWebViewLiveDataProvider {
    val webUiStateLiveData = MutableLiveData<WebUiState>()
    val screenStateMutableLiveData = MutableLiveData<ScreenState>()
    val loadWebViewMutableLiveData = MutableSingleLiveData<WebViewData>()
    val canGoBackMutableLiveData = MutableLiveData<Boolean>()
    val cardResultMutableLiveData = MutableSingleLiveData<String>()
    val finishAssociationCardMutableLiveData = MutableSingleLiveData<Unit>()
}