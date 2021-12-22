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
        data class Form(val errorMessage: String? = null) : Model
        object LoggingIn : Model
        data class LoggedIn(val username: String) : Model
    }

    sealed interface Intent {
        data class LogIn(val username: String, val password: String) : Intent
    }

    private var _model: MutableStateFlow<Model> = MutableStateFlow(Model.Form())
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
            val ids = client.request<List<Long>>("https://hacker-news.firebaseio.com/v0/topstories.json").take(10)
            val items = ids.asFlow()
                .flatMapMerge(20) {
                    flow {
                        println("Requesting https://hacker-news.firebaseio.com/v0/item/${it}.json")
                        val item = client.request<HNItem>("https://hacker-news.firebaseio.com/v0/item/${it}.json")
                        println("Got item $item")
                        emit(item)
                    }
                }
                .toList()
            println("HTTP RESPONSE:")
            println(items)
            println("------------------------------------------")
        }
    }

    fun process(intent: Intent) {
        when (intent) {
            is Intent.LogIn -> {
                _model.tryEmit(Model.LoggingIn)

                runDelayed(3000) {
                    _model.tryEmit(
                        if (intent.username == "john" && intent.password == "doe") Model.LoggedIn("john")
                        else Model.Form("Bad password!")
                    )
                }
            }
        }
    }

}
