package com.purered.pr1digitalad_riteaid_aos

import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.purered.pr1digitalad_riteaid_aos.ui.theme.PR1DigitalAdRiteAidAOSTheme

import android.view.View
import com.purered.pr1digitalad.DigitalAd
import com.purered.pr1digitalad.DigitalAdInput
import com.purered.pr1digitalad.ActionPayload
import org.json.JSONObject
import org.json.JSONArray

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

 var digitalAd: DigitalAd? = null;


        val cartItems = listOf<Map<String, Any>>(
            mapOf( "productId" to "0350512", "quantity" to 1 ),
            mapOf( "productId" to "0385494", "quantity" to 2 )
        );
        val clippedCoupons = listOf<Map<String, Any>>(
            mapOf( "couponId" to "70f6af3f-1b6d-452e-b699-d62020b8c7e4",  "id" to "8ea69008-aa35-4d2b-a075-9012b45e835f"  ),
        );

        val options:DigitalAdInput =   DigitalAdInput(  "pgH7QzFHJx4w46fI~5Uzi4RvtTwlEXp3",
            "10074",
            "",
            cartItems,
            clippedCoupons,
            environment = "QA",
            ""

        ){
                payload: ActionPayload ->


            println("Call Back Handler: ${payload.actionName} with object: $payload")



                    when (payload.actionName) {
                        "onClipCoupon" -> {

                            // Clipped coupons will be accessed from the payload.value
                            println("Performing action for onClipCoupon with payload: ${payload.value}")



                            if(digitalAd != null){
                                //eventCallback

                                payload.status = "success"; // very important to set the status to success or error and to Show the message

                              //   Update the payload value with the new clipped coupons if needed
                                payload.value = listOf<Map<String, Any>>(
                                    mapOf( "couponId" to "70f6af3f-1b6d-452e-b699-d62020b8c7e4",  "id" to "8ea69008-aa35-4d2b-a075-9012b45e835f"  ),
                                );


                                digitalAd!!.dispatch(payload)


                                //Or
//                                digitalAd!!.dispatch(
//                                    ActionPayload(
//                                        status = "success",
//                                        value =  listOf<Map<String, Any>>(
//                                            mapOf( "couponId" to "70f6af3f-1b6d-452e-b699-d62020b8c7e4",  "id" to "8ea69008-aa35-4d2b-a075-9012b45e835f"  ),
//                                        ),
//                                        actionContext = payload.actionContext,
//                                        actionName = payload.actionName
//
//                                    ))



                            }



                        }
                        "onUnclipCoupon" -> {
                            // Perform action for onUnclipCoupon
                            println("Performing action for onUnclipCoupon")
                        }
                        else -> {
                            // Default case or handle other cases
                            println("Unknown case: $payload.actionName with object: $payload")
                        }
                    }
        }







        // Initialise DigitalAd with the required options parameter of type DigitalAdInput
          digitalAd = DigitalAd(this, options)

        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT )
        digitalAd.updateLayoutParams(layoutParams)

        //Obtain a reference to the Digital Ad view using the `viewRef` property, which can be used to render the ad on the screen.
        val adViewRef = digitalAd.viewRef;
        findViewById<LinearLayout>(R.id.container).addView(adViewRef)



        val btnValidate: View = findViewById(R.id.btnvalidate)


        btnValidate.setOnClickListener { view: View ->


            //  To update the clipped coupons, call the dispatch method with the action name and the new clipped coupons.
            digitalAd.dispatch(ActionPayload( actionName = "setClippedCoupons", value = clippedCoupons))

        }




    }
}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PR1DigitalAdRiteAidAOSTheme {
        Greeting("Android")
    }
}