package com.kh.mo.shopyapp.ui.address.map

import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityCompat
import androidx.core.graphics.drawable.toBitmap
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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
        binding.currentLocationBtn.setOnClickListener { handleLocationStateAndMarkCurrentLocation() }
    }

    private fun initMap() {
        Configuration.getInstance()
            .load(requireContext(), activity?.getPreferences(AppCompatActivity.MODE_PRIVATE))
        binding.osmMap.setTileSource(TileSourceFactory.MAPNIK)
        binding.osmMap.setMultiTouchControls(true)
        mapController = binding.osmMap.controller
        mapController.setZoom(8.0)
        val compassOverlay =
            CompassOverlay(
                requireContext(),
                InternalCompassOrientationProvider(requireContext()),
                binding.osmMap
            )
        compassOverlay.enableCompass()
        val rotationGestureOverlay = RotationGestureOverlay(binding.osmMap)
        rotationGestureOverlay.isEnabled

        handleLocationStateAndMarkCurrentLocation()

        binding.osmMap.overlays.add(compassOverlay)
        binding.osmMap.overlays.add(rotationGestureOverlay)
        binding.osmMap.invalidate()
    }

    private fun handleLocationStateAndMarkCurrentLocation() {
        if (checkPermission()) {
            if (isLocationEnabled()) {
                markCurrentLocation()
            } else {
                showLocationDisabledDialog()
            }
        } else requestPermission()
    }

    private fun checkPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
            requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { permissionMap: Map<String, Boolean> ->
                if (permissionMap.any { isGranted -> isGranted.value }) {
                    if (isLocationEnabled())
                        markCurrentLocation()
                    else
                        showLocationDisabledDialog()
                } else {
                    showLocationPermissionNeededDialog()
                }
            }
        requestPermissionLauncher.launch(
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun showLocationPermissionNeededDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.permission_needed))
            .setMessage(getString(R.string.location_permission_needed_message))
            .setNegativeButton(resources.getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(resources.getString(R.string.open_setting)) { dialog, _ ->
                openAppDetailsSettings()
                dialog.dismiss()
            }
            .show()
    }

    private fun openAppDetailsSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", requireActivity().packageName, null)
        intent.data = uri
        startActivity(intent)
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            requireActivity().getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun showLocationDisabledDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.location_disabled))
            .setMessage(getString(R.string.location_disabled_message))
            .setNegativeButton(resources.getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(resources.getString(R.string.open_setting)) { dialog, _ ->
                openLocationSettings()
                dialog.dismiss()
            }
            .show()
    }

    private fun openLocationSettings() {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(intent)
    }

    private fun markCurrentLocation() {
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
        binding.osmMap.overlays.add(mMyLocationOverlay)
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