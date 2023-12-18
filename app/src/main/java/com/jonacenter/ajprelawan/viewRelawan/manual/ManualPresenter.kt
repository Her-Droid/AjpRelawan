import com.jonacenter.ajprelawan.api.ApiService
import com.jonacenter.ajprelawan.data.AddDataRequest
import com.jonacenter.ajprelawan.data.ApiResponse
import com.jonacenter.ajprelawan.viewRelawan.manual.ManualContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ManualPresenter(private val view: ManualContract.View, private val apiService: ApiService) :
    ManualContract.Presenter {

    override fun onSubmitButtonClicked(
        nik: String,
        namaRelawan: String,
        nama: String,
        koordinator: String,
        tandeman: String,
        notelp: String,
        noTps: String,
        kecamatan: String,
        kabupaten: String
    ) {
        view.showProgressBar()

        // Make the API call using Retrofit
        try {
            val requestData = AddDataRequest(
                nik,
                nama,
                namaRelawan,
                koordinator,
                tandeman,
                notelp,
                noTps,
                kecamatan,
                kabupaten
            )

            val call = apiService.postAddData(requestData)
            call.enqueue(object : Callback<ApiResponse> { // Replace ResponseBody with ApiResponse
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    view.hideProgressBar()

                    if (response.isSuccessful) {
                        view.showToast("Berhasil Input Data")
                        view.navigateToResultRelawan()
                    } else {
                        view.showToast("Gagal mengirim data. Response server: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    view.hideProgressBar()
                    view.showToast("Error: ${t.message}")
                }
            })
        } catch (e: Exception) {
            view.hideProgressBar()
            view.showToast("Error: ${e.message}")
        }
    }
}
