package com.example.bwtools.android.manager.ble

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.util.Log
import androidx.fragment.app.Fragment


/**
 * Bluetooth 관련 기능 관리
 * @author 배우람
 */
class BleManager(private val context: Context) {
    private var mBluetoothAdapter: BluetoothAdapter? = null
    private var mBluetoothManager:BluetoothManager? = null
    private var mLeScanCallback: ScanCallback? = object: ScanCallback() {
        private fun processResult(result: ScanResult?) {
            Log.d(javaClass.simpleName, "processResult() scanResult = ${result.toString()}")
            leScanListener?.onProcessResult(result)
        }

        override fun onScanFailed(errorCode: Int) {
            Log.d(javaClass.simpleName, "onScanFailed() errorCode = $errorCode")
            leScanListener?.onScanFailed(errorCode)
            isScanning = false
        }

        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            Log.d(javaClass.simpleName, "onScanResult() callbackType = $callbackType, scanResult = ${result.toString()}")
            leScanListener?.onScanResult(callbackType, result)
        }

        override fun onBatchScanResults(results: MutableList<ScanResult>?) {
            Log.d(javaClass.simpleName, "onBatchScanResults() scanResultList = ${results.toString()}")
            leScanListener?.onBatchScanResults(results)
        }
    }

    var mDevice: BluetoothDevice? = null

    /**
     * BLE ScanListener
     * @author 배우람
     */
    interface LeScanListener{
        fun onProcessResult(result: ScanResult?)
        fun onScanFailed(errorCode: Int)
        fun onScanResult(callbackType: Int, result: ScanResult?)
        fun onBatchScanResults(results: MutableList<ScanResult>?)
    }

    var leScanListener: LeScanListener? = null
    var isScanning:Boolean = false

    /**
     * 블루투스 초기화
     * @author 배우람
     */
    fun init(){
        mBluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        if(mBluetoothManager == null)
            Log.e(javaClass.simpleName, "BluetoothManager is null")

        if(mBluetoothAdapter == null)
            Log.e(javaClass.simpleName, "mBluetoothAdapter is null")
    }

    /**
     * BluetoothAdapter 반환
     * @author 배우람
     */
    fun getBluetoothAdapter() = mBluetoothAdapter

    /**
     * BluetoothLeScanner 반환
     * @author 배우람
     */
    fun getBluetoothBleScanner() = mBluetoothAdapter?.bluetoothLeScanner

    /**
     * Ble 디바이스 인지 체크
     * @author 배우람
     */
    fun getBluetoothEnable() = mBluetoothAdapter?.enable() ?: false

    /**
     * BleScanner 가능한지 체크
     * @author 배우람
     */
    fun getBluetoothScannerEnable() = mBluetoothAdapter?.bluetoothLeScanner != null

    /**
     * Ble 디바이스 인지 체크
     * @author 배우람
     */
    fun isFeatureBle() = context.packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)

    /**
     * BLE Scan 시작
     * @author 배우람
     */
    fun startBleScan(){
        if(isScanning) {
            Log.e(javaClass.simpleName, "Ble is Scanning")
            return
        }

        getBluetoothBleScanner()?.startScan(mLeScanCallback)
        isScanning = true
    }

    /**
     * BLE Scan 정지
     * @author 배우람
     */
    fun stopBleScan(){
        if(!isScanning) {
            Log.e(javaClass.simpleName, "Ble is not Scanning")
            return
        }

        getBluetoothBleScanner()?.stopScan(mLeScanCallback)
        isScanning = false
    }

    /**
     * Bluetooth Enable 요청
     * @author 배우람
     */
    fun requestBluetoothEnable(target:Fragment){
        if (mBluetoothAdapter?.isEnabled == true)
            return

        Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE).let {
            target.startActivityForResult(it, REQUEST_ENABLE_BT)
        }
    }


    /**
     * Ble 또는 Bluetooth 사용 가능한 기기 검증
     * @author 배우람
     * @return 검증 결과
     */
    fun verify(): BleVerifyResult {
        if (!isFeatureBle())
            return BleVerifyResult.NOT_FEATURE_BLE

        if(!getBluetoothEnable())
            return BleVerifyResult.BLUETOOTH_ADAPTER_IS_NULL

        if(!getBluetoothScannerEnable())
            return BleVerifyResult.BLUETOOTH_SCANNER_IS_NULL

        return BleVerifyResult.OK
    }

    companion object{
        const val REQUEST_ENABLE_BT = 2
    }
}