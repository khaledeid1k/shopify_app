package com.kh.mo.shopyapp.ui.address.map

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import com.kh.mo.shopyapp.R
import com.kh.mo.shopyapp.databinding.FragmentMapBinding
import com.kh.mo.shopyapp.ui.base.BaseFragment
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

class MapFragment : BaseFragment<FragmentMapBinding, MapViewModel>() {
    private val TAG = "TAG MapFragment"

    override val layoutIdFragment = R.layout.fragment_map
    private lateinit var mapController: IMapController
    private lateinit var mMyLocationOverlay: MyLocationNewOverlay

    override fun getViewModelClass() = MapViewModel::class.java


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMap()
        addMapEventReceiver()
    }

    private fun initMap() {
        Configuration.getInstance()
            .load(requireContext(), activity?.getPreferences(AppCompatActivity.MODE_PRIVATE))
        binding.osmMap.setTileSource(TileSourceFactory.MAPNIK)
        binding.osmMap.setMultiTouchControls(true)
        mapController = binding.osmMap.controller

        mapController.setZoom(8.0)
        mMyLocationOverlay =
            MyLocationNewOverlay(GpsMyLocationProvider(requireContext()), binding.osmMap)
        mMyLocationOverlay.enableMyLocation()
        mMyLocationOverlay.setPersonIcon(
            AppCompatResources.getDrawable(
                requireContext(),
                R.drawable.location_pin
            )?.toBitmap(100, 100)
        )
        mMyLocationOverlay.isDrawAccuracyEnabled = true
        mMyLocationOverlay.runOnFirstFix {
            requireActivity().runOnUiThread {
                mapController.setCenter(mMyLocationOverlay.myLocation)
                mapController.animateTo(mMyLocationOverlay.myLocation)
            }
        }

        val compassOverlay =
            CompassOverlay(
                requireContext(),
                InternalCompassOrientationProvider(requireContext()),
                binding.osmMap
            )
        compassOverlay.enableCompass()
        val rotationGestureOverlay = RotationGestureOverlay(binding.osmMap)
        rotationGestureOverlay.isEnabled

        binding.osmMap.overlays.add(compassOverlay)
        binding.osmMap.overlays.add(mMyLocationOverlay)
        binding.osmMap.overlays.add(rotationGestureOverlay)
        binding.osmMap.invalidate()
    }

    private fun addMapEventReceiver() {
        val mapEventsReceiver = object : MapEventsReceiver {
            override fun singleTapConfirmedHelper(p: GeoPoint): Boolean {

                return true
            }

            override fun longPressHelper(p: GeoPoint): Boolean {
                return false
            }
        }
        val eventOverlay = MapEventsOverlay(mapEventsReceiver)
        binding.osmMap.overlays.add(eventOverlay)
    }
}