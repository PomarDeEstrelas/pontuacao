package com.example.pontuacao

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import com.example.pontuacao.api.PontuacaoAPI
import com.example.pontuacao.api.RetrofitClient
import com.example.pontuacao.model.Pontuacao

import kotlinx.android.synthetic.main.activity_lista.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class ListaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista)
        setSupportActionBar(toolbar)


        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            salvarPontuacao()

        }
        carregarPontuacao()

    }

    private fun salvarPontuacao(){

        RetrofitClient.getPontuacaoAPI()?.enviarPontuacao(Pontuacao("","FIAP",30000))
            ?.enqueue(object : Callback<Void>{
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.i("ERRO", t.message)
                }

                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if(response.isSuccessful){
                        Log.i("SUCESSO", "DADOS SALVOS COM SUCESSO")
                        carregarPontuacao()
                    }else{
                        Log.i("ERROOOO","ACHO QUE DEU ERRADO NENE")
                    }
                }
            })
    }

    private fun carregarPontuacao() {

        RetrofitClient.getPontuacaoAPI()?.buscarPontos()
            ?.enqueue(object : Callback<List<Pontuacao>> {
                override fun onResponse(call: Call<List<Pontuacao>>, response: Response<List<Pontuacao>>) {
                    if (response.isSuccessful) {
                        val pontuacoes = response.body()
                        pontuacoes?.map {
                            Log.i("PONTOS", "${it.nome} - ${it.pontos}")
                        }
                    } else {
                        Log.i("ERRO", "Erro ao carregar pontos")
                    }
                }

                override fun onFailure(call: Call<List<Pontuacao>>, t: Throwable) {
                    Log.i("ERRO", t.message)
                }
            })

    }

}
