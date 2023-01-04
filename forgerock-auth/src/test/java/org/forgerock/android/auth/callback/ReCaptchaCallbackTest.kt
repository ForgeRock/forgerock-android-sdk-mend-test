/*
 * Copyright (c) 2023 ForgeRock. All rights reserved.
 *
 *  This software may be modified and distributed under the terms
 *  of the MIT license. See the LICENSE file for details.
 */
package org.forgerock.android.auth.callback

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.json.JSONException
import org.json.JSONObject
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ReCaptchaCallbackTest {
    @Test
    @Throws(JSONException::class)
    fun basicTest() {
        val raw = JSONObject(""" {
            "type": "ReCaptchaCallback",
            "output": [
                {
                    "name": "recaptchaSiteKey",
                    "value": "6Lf3tbYUAAAAAEm78fAOFRKb-n1M67FDtmpczIBK"
                }
            ],
            "input": [
                {
                    "name": "IDToken1",
                    "value": ""
                }
            ]
        }""")
        val reCaptchaCallback = ReCaptchaCallback(raw, 0)
        Assert.assertEquals("6Lf3tbYUAAAAAEm78fAOFRKb-n1M67FDtmpczIBK",
            reCaptchaCallback.reCaptchaSiteKey)
    }
}