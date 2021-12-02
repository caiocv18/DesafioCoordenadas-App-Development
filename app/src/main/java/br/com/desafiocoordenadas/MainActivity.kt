package br.com.desafiocoordenadas

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    val ID_PERMISSION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        val button_coordenadas: Button = findViewById(R.id.btn_coordenadas)
        val textView_coordenadas: TextView = findViewById(R.id.textview_coordenadas)

        var latitude : String = "46.414382"
        var longitude : String = "10.013988"


        button_coordenadas.setOnClickListener(){
            requestPermission()

            if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                textView_coordenadas.text = "Opa, você não tem permissão para acessar minhas coordenadas"
            }else{
                fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
                    if (location != null){
                        latitude = location?.latitude.toString()
                        longitude = location?.longitude.toString()
                        textView_coordenadas.text = latitude + "/" + longitude
                    }else{
                        textView_coordenadas.text = "Coordenada vazia :("
                    }
                }
            }

            val button_compartilhar: Button = findViewById(R.id.btn_compartilhar)
            button_compartilhar.setOnClickListener(){// Create a Uri from an intent string. Use the result to create an Intent.
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(
                        "http:/a/maps.google.com/maps?"
                                + "saddr=" + latitude + "," + longitude
                    )
                )

                intent.setClassName(
                    "com.google.android.apps.maps",
                    "com.google.android.maps.MapsActivity"
                )
                startActivity(intent)
            }

        }
    }

    fun requestPermission(){
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION), ID_PERMISSION)

    }
}