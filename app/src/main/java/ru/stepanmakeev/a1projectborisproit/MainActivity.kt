package ru.stepanmakeev.a1projectborisproit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.widget.Button


class MainActivity : AppCompatActivity() {



    private val signInLauncher = registerForActivityResult( // создали объект авторизации экрана
        FirebaseAuthUIActivityResultContract(),
    ) { resultCallback ->
        this.onSignInResult(resultCallback) // запуск самого экрана
    }

    private lateinit var database: DatabaseReference // создали объект для записи в БД

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intentToAnotherScreen = Intent(this, MoviesActivity::class.java) //переменная val, метод Intent, отправляемся из this, в MoviesActivity
        startActivity(intentToAnotherScreen) // запускаем верхний метод

        // Choose authentication providers Выберите поставщиков аутентификации
//        database = Firebase.database.reference // инициализация базы данных
//
//        // ВСЕ КОММЕНТЫ ИДУТ КОММЕНТ А СНИЗУ ЧТО КОММЕНТИРУЕТСЯ
//        //инициализировали объект кнопки
//        //указали объект кнопки на элемент внутри XML
//        //   val registerButton = findViewById<Button>(R.id.main_acivity_register_input)
//        //к нашей кнопкe объекту регистерБаттон прикрепили обработчик кнопок
//        //  val loginButton = findViewById<Button>(R.id.main_acivity_login_input)
//        //  loginButton.setOnClickListener {
//        //     val intentLogin = Intent(this, LoginActivity::class.java)
//        //     startActivity(intentLogin)
//        //  }
//        val providers = arrayListOf(                       // ВЕСЬ КУСОК КОДА СНИЗУ ВЫЗЫВАЕТСЯ ДЛЯ РЕГИСТРАЦИИ
//            AuthUI.IdpConfig.EmailBuilder().build()) // создали список регистрации который мы используем
//
//        // Create and launch sign-in intent
//        val signInIntent = AuthUI.getInstance()
//            .createSignInIntentBuilder()
//            .setAvailableProviders(providers)
//            .build() // cоздали интент для экрана firebase auth
//        signInLauncher.launch(signInIntent) // запустили экран firebase auth
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse // результат с экрана firebase auth
        if (result.resultCode == RESULT_OK) { // если результат ОК
            Log.d("testlogs","Регистрация успешна, пользователь ${response?.email}") //просто проверка логов в Logcat

            // Успешно выполнен вход в систему ЕСЛИ РЕГИСТРАЦИЯ ВЫПОЛНЕНА УСПЕШНО СНИЗУ
            val authUser = FirebaseAuth.getInstance().currentUser //Если RESULT_OK то мы сделаем нового пользователя в виде объекта authUser
            authUser?.let { // Если он существует, то мы сохраняем его в БД
                val email = it.email.toString() // извлекаем email нашего пользователя
                val uid = it.uid // извлекаем uid нашего пользователя
                val firebaseUser = User(email, uid) // создаем новый объект User с параметрами email и uid

                database.child("users").child(uid).setValue(firebaseUser) // сохраняем нашего пользователя в Firebase Realtime ( ЗДЕСЬ КОНСТРУКЦИЯ ПАПОК )
                // в глобальной папке users делаем папку с названием результата uid (поэтому она чайлд, дочерняя папка), и в дочернюю папку закидываем firebaseUser (это переменная где хранится User(email uid)
                val intentToAnotherScreen = Intent(this, MoviesActivity::class.java) //переменная val, метод Intent, отправляемся из this, в MoviesActivity
                startActivity(intentToAnotherScreen) // запускаем верхний метод
            }

            // ...
        } else { // если результат не ОК должны обработать ошибку
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }
}

