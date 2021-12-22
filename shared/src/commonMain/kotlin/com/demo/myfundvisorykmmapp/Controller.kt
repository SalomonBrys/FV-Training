package com.demo.myfundvisorykmmapp

import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class Controller {

    sealed interface Model {
        object Loading : Model
        data class Items(val items: List<HNItem>) : Model
    }

    sealed interface Intent {
    }

    private var _model: MutableStateFlow<Model> = MutableStateFlow(Model.Loading)
    val model: StateFlow<Model> get() = _model.asStateFlow()

    val client = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                ignoreUnknownKeys = true
            })
        }
    }

    init {
        MainScope().launch {
            val ids = client.request<List<Long>>("https://hacker-news.firebaseio.com/v0/topstories.json").take(100)
            val items = ids.asFlow()
                .flatMapMerge(25) {
                    flow {
//                        println("Requesting https://hacker-news.firebaseio.com/v0/item/${it}.json")
                        val item = client.request<HNItem>("https://hacker-news.firebaseio.com/v0/item/${it}.json")
//                        println("Got item $item")
                        emit(item)
                    }
                }
                .toList()
            _model.tryEmit(Model.Items(items))
        }
    }

    fun process(intent: Intent) {
//        when (intent) {
//        }
    }

}
