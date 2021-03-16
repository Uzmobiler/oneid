package uz.mobiler.oneid

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import uz.mobiler.oneid.utils.Status
import uz.mobiler.oneid.databinding.ActivityMainBinding
import uz.mobiler.oneid.viewmodels.MainViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    var redirect_url = ""
    var responceType = "one_code"
    var clientId = ""
    var scope = ""
    var state = ""
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val url =
                "http://sso.egov.uz:8443/sso/oauth/Authorization.do?response_type=$responceType&client_id=$clientId&redirect_uri=$redirect_url&scope=$scope&state=$state"
        binding.webView.webViewClient = MyWebViewClient(binding.root.context)
        binding.webView.loadUrl(url)
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.domStorageEnabled = true
    }

    inner class MyWebViewClient internal constructor(private val context: Context) :
            WebViewClient() {
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
        ): Boolean {
            val url: String = request?.url.toString()
            if (url.contains("${redirect_url}?code=")) {
                val index1 = url.indexOf("code=")
                val index2 = url.indexOf("&state")
                val code = url.substring(index1 + 5, index2)
                mainViewModel.getCode(code).observe(this@MainActivity, Observer {
                    if (it.status == Status.SUCCESS) {
                        Log.d(TAG, "shouldOverrideUrlLoading: ${it.data}")
                        val intent = Intent(this@MainActivity, SecondActivity::class.java)
                        startActivity(intent)
                    }
                })
            } else {
                view?.loadUrl(url)
            }
            return true
        }

        override fun onReceivedError(
                view: WebView,
                request: WebResourceRequest,
                error: WebResourceError
        ) {
            Toast.makeText(context, "Got Error! $error", Toast.LENGTH_SHORT).show()
        }
    }
}