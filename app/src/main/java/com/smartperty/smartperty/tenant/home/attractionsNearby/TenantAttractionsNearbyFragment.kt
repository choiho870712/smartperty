package com.smartperty.smartperty.tenant.home.attractionsNearby


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.smartperty.smartperty.R
import com.smartperty.smartperty.tools.SwipeHelper
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.activity_landlord.*
import kotlinx.android.synthetic.main.tenant_fragment_attractions_nearby.view.*

/**
 * A simple [Fragment] subclass.
 */
class TenantAttractionsNearbyFragment : Fragment(), OnMapReadyCallback, LocationSource.OnLocationChangedListener {

    private lateinit var root:View
    private lateinit var mMap: GoogleMap
    private lateinit var myMapView: MapView
    private lateinit var myLocation:LatLng
    private lateinit var locationManager: LocationManager
    private val MIN_TIME: Long = 400
    private val MIN_DISTANCE = 1000f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GlobalVariables.attractionList = GlobalVariables.estate.attractionList
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()

        root = inflater.inflate(R.layout.tenant_fragment_attractions_nearby, container, false)

        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()

        if (GlobalVariables.loginUser.permission.property == "A") {
            GlobalVariables.toolBarUtils.setAddButtonVisibility(true)
            GlobalVariables.activity.toolbar.setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.button_add -> {
                        // setup dialog builder
                        root.findNavController().navigate(
                            R.id.action_tenantAttractionsNearbyFragment2_to_nearbyAddFragment
                        )

                        true
                    }
                    else -> false
                }
            }
        }

        myMapView = root.map_view
        myMapView.getMapAsync(this)
        myMapView.onCreate(arguments)

        val attractionAdapter = TenantAttractionNearbyAdapter(
            requireActivity(), root, GlobalVariables.attractionList
        )
        root.recycler_list.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = attractionAdapter
        }

        if (GlobalVariables.loginUser.permission.property == "A") {
            var swipeHelper = object : SwipeHelper(requireContext(), root.recycler_list) {
                override fun instantiateUnderlayButton(
                    viewHolder: RecyclerView.ViewHolder?,
                    underlayButtons: MutableList<UnderlayButton>?
                ) {
                    underlayButtons?.add(UnderlayButton(
                        "Delete",
                        0,
                        Color.parseColor("#FF3C30")
                    ) {
                        // setup dialog builder
                        val builder = android.app.AlertDialog.Builder(requireActivity())
                        builder.setTitle("確定要刪除嗎？")

                        builder.setPositiveButton("是") { _, _ ->
                            val position = viewHolder!!.adapterPosition
                            val item = GlobalVariables.attractionList[position]
                            GlobalVariables.attractionList.remove(item)
                            attractionAdapter.notifyDataSetChanged()
                            Thread {
                                GlobalVariables.api.deleteAttractionInformation(
                                    GlobalVariables.estate.landlord!!.id,
                                    GlobalVariables.estate.objectId,
                                    position
                                )
                            }.start()
                        }

                        // create dialog and show it
                        requireActivity().runOnUiThread {
                            val dialog = builder.create()
                            dialog.show()
                        }
                    })
//                underlayButtons?.add(UnderlayButton(
//                    "Edit",
//                    0,
//                    Color.parseColor("#FF9502")
//                ) {
//
//                })
//                underlayButtons?.add(UnderlayButton(
//                    "Cancel",
//                    0,
//                    Color.parseColor("#C7C7CB")
//                ) {

//                })
                }
            }
        }


        return root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (ContextCompat.checkSelfPermission(
                GlobalVariables.activity, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED) {

            mMap.isMyLocationEnabled = true
        }

        locationManager = GlobalVariables.activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(
                GlobalVariables.activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                GlobalVariables.activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        locationManager.requestLocationUpdates(
            LocationManager.NETWORK_PROVIDER,
            MIN_TIME,
            MIN_DISTANCE,
            LocationListener {
                myLocation = LatLng(it.latitude, it.longitude)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    LatLng(it.latitude, it.longitude), 16.0f))
                myMapView.onResume()
            }
        ) //You can also use LocationManager.GPS_PROVIDER and LocationManager.PASSIVE_PROVIDER

//        if (ContextCompat.checkSelfPermission(
//                GlobalVariables.activity, Manifest.permission.ACCESS_FINE_LOCATION
//            )
//            == PackageManager.PERMISSION_GRANTED) {
//
//            mMap.isMyLocationEnabled = true
//
//            val sydney = LatLng(25.0527073, 121.5161885)
////            mMap.addMarker(MarkerOptions().position(sydney).title("永昌大樓")).showInfoWindow()
//            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16.0f))
//            myMapView.onResume()
//        }
//        else {
//            // Add a marker in Sydney and move the camera
//            val sydney = LatLng(25.0527073, 121.5161885)
//            mMap.addMarker(MarkerOptions().position(sydney).title("永昌大樓")).showInfoWindow()
//            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16.0f))
//            myMapView.onResume()
//        }
    }

    override fun onLocationChanged(p0: Location?) {
        if (p0 != null) {
            myLocation = LatLng(p0.latitude, p0.longitude)
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 16.0f))
            myMapView.onResume()
        }
    }
}
