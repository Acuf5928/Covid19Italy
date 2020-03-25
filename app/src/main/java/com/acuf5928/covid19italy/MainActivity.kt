package com.acuf5928.covid19italy

import android.os.Bundle
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import org.json.JSONObject

const val LINK_NATION = "https://raw.githubusercontent.com/pcm-dpc/COVID-19/master/dati-json/dpc-covid19-ita-andamento-nazionale-latest.json"
const val LINK_REGION = "https://raw.githubusercontent.com/pcm-dpc/COVID-19/master/dati-json/dpc-covid19-ita-regioni-latest.json"

class MainActivity : AppCompatActivity() {
    private var index = 0
    private var url = LINK_NATION

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
    private lateinit var allData: ConstraintLayout
    private lateinit var notConneted: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setView()
        updateView()
    }

    private fun setView() {
        dataText = findViewById(R.id.data)
        sintomiText = findViewById(R.id.sintomi)
        intensivaText = findViewById(R.id.intensiva)
        ospedaliText = findViewById(R.id.ospedali)
        isolamentoText = findViewById(R.id.isolamento)
        totalePositiviText = findViewById(R.id.totalePositivi)
        nuoviPositiviText = findViewById(R.id.nuoviPositivi)
        dimessiText = findViewById(R.id.dimessi)
        decedutiText = findViewById(R.id.deceduti)
        totaleText = findViewById(R.id.totaleCasi)
        tamponiText = findViewById(R.id.tamponi)
        selectRegion = findViewById(R.id.spinner)
        allData = findViewById(R.id.allData)
        notConneted = findViewById(R.id.notConnected)

        selectRegion.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (position == 0) {
                    index = 0
                    url = LINK_NATION
                } else {
                    index = position - 1
                    url = LINK_REGION
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
                    notConneted.visibility = INVISIBLE
                    allData.visibility = VISIBLE

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
                    notConneted.visibility = VISIBLE
                    allData.visibility = INVISIBLE
                }
            }
        })
    }
}
