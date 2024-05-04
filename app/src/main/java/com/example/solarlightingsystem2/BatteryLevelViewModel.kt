import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.solarlightingsystem2.R

class BatteryLevelViewModel : ViewModel() {
    private val _batteryLevel = MutableLiveData<Int>()
    val batteryLevel: LiveData<Int> = _batteryLevel

    private val _batteryIconResource = MutableLiveData<Int>()
    val batteryIconResource: LiveData<Int> = _batteryIconResource

    private val _seekBarProgress = MutableLiveData<Int>()
    val seekBarProgress: LiveData<Int> = _seekBarProgress

    init {
        setBatteryLevel(50)
    }

    fun setBatteryLevel(level: Int) {
        _batteryLevel.value = level
        updateBatteryIcon(level)
    }

    fun setSeekBarProgress(progress: Int) {
        _seekBarProgress.value = progress
    }

    private fun updateBatteryIcon(percentage: Int) {
        val resourceId = getBatteryIconResource(percentage)
        _batteryIconResource.value = resourceId
    }

    private fun getBatteryIconResource(percentage: Int): Int {
        return when {
            percentage >= 85 -> R.drawable.fifth
            percentage >= 65 -> R.drawable.fourth
            percentage >= 40 -> R.drawable.third
            percentage >= 1 -> R.drawable.second
            else -> R.drawable.first
        }
    }
}