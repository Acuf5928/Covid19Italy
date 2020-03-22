package com.acuf5928.codingavirus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import org.json.JSONObject

const val linkNation = "https://raw.githubusercontent.com/pcm-dpc/COVID-19/master/dati-json/dpc-covid19-ita-andamento-nazionale-latest.json"
const val linkRegion = "https://raw.githubusercontent.com/pcm-dpc/COVID-19/master/dati-json/dpc-covid19-ita-regioni-latest.json"

class MainActivity : AppCompatActivity() {
    private var index = 0
    private var url = linkNation

    private lateinit var dataText: TextView
    private lateinit var sintomiText: TextView
    private lateinit var intensivaText: TextView
    private lateinit var ospedaliText: TextView
    private lateinit var isolamentoText: TextView
    private lateinit var totalePositiviText: TextView
    private lateinit var nuoviPositiviText: TextView
    private lateinit var dimessiText: TextView
    private lateinit var decedutiText: TextView
    private lateinit var totaleText: TextView
    private lateinit var tamponiText: TextView
    private lateinit var selectRegion: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setView()
        updateView()
    }

    private fun setView() {
        dataText = findViewById(R.id.text)
        sintomiText = findViewById(R.id.text4)
        intensivaText = findViewById(R.id.text5)
        ospedaliText = findViewById(R.id.text6)
        isolamentoText = findViewById(R.id.text7)
        totalePositiviText = findViewById(R.id.text8)
        nuoviPositiviText = findViewById(R.id.text9)
        dimessiText = findViewById(R.id.text10)
        decedutiText = findViewById(R.id.text11)
        totaleText = findViewById(R.id.text12)
        tamponiText = findViewById(R.id.text13)
        selectRegion = findViewById(R.id.spinner)

        selectRegion.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        index = 0
                        url = linkNation
                    }
                    else -> {
                        index = position - 1
                        url = linkRegion
                    }
                }
                updateView()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }
    }

    private fun updateView() {
        MyJsonParser().start(url, object : MyJsonParser.Result {
            override fun onComplete(json: MutableList<JSONObject>) {
                runOnUiThread {
                    dataText.text = json[index].getString("data")
                    sintomiText.text = json[index].getString("ricoverati_con_sintomi")
                    intensivaText.text = json[index].getString("terapia_intensiva")
                    ospedaliText.text = json[index].getString("totale_ospedalizzati")
                    isolamentoText.text = json[index].getString("isolamento_domiciliare")
                    totalePositiviText.text = json[index].getString("totale_attualmente_positivi")
                    nuoviPositiviText.text = json[index].getString("nuovi_attualmente_positivi")
                    dimessiText.text = json[index].getString("dimessi_guariti")
                    decedutiText.text = json[index].getString("deceduti")
                    totaleText.text = json[index].getString("totale_casi")
                    tamponiText.text = json[index].getString("tamponi")
                }
            }

            override fun onError(e: Exception) {
                runOnUiThread {
                    findViewById<TextView>(R.id.text).text = "NON CONNESSO"
                }
            }

        })
    }
}
