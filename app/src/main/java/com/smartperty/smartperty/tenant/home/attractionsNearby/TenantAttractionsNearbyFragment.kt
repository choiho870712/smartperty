package com.smartperty.smartperty.tenant.home.attractionsNearby


import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import com.smartperty.smartperty.R
import com.smartperty.smartperty.tenant.home.attractionsNearby.data.TenantAttractionNearbyItem
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.tenant_fragment_attractions_nearby.view.*

/**
 * A simple [Fragment] subclass.
 */
class TenantAttractionsNearbyFragment : Fragment(), OnMapReadyCallback {

    private lateinit var root:View
    private lateinit var mMap: GoogleMap
    private lateinit var myMapView: MapView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()

        root = inflater.inflate(R.layout.tenant_fragment_attractions_nearby, container, false)

        myMapView = root.map_view
        myMapView.getMapAsync(this)
        myMapView.onCreate(arguments)

        val attractionList = mutableListOf(
            TenantAttractionNearbyItem(name = "公園"),
            TenantAttractionNearbyItem(name = "美食街"),
            TenantAttractionNearbyItem(name = "公車站"),
            TenantAttractionNearbyItem(name = "美食街2")
        )

        root.recycler_list.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = TenantAttractionNearbyAdapter(requireActivity(), root, attractionList)
        }

        return root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        if (ContextCompat.checkSelfPermission(
                GlobalVariables.activity, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {

            mMap.isMyLocationEnabled = true
        }

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(25.0527073, 121.5161885)
        mMap.addMarker(MarkerOptions().position(sydney).title("永昌大樓")).showInfoWindow()
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16.0f))
        myMapView.onResume()
    }
}
