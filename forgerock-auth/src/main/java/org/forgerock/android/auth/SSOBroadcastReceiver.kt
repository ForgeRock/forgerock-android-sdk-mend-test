/*
 * Copyright (c) 2022 ForgeRock. All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package org.forgerock.android.auth

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import org.forgerock.android.auth.BroadcastConst.broadcastPackageKey

/**
 * Broadcast receiver to receive the logout SSO message
 */

class SSOBroadcastReceiver(private val instance: Config = Config.getInstance(),
                           private val configHelper: ConfigInterface = ConfigHelper()): BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.getStringExtra(broadcastPackageKey) != context?.packageName
            && context != null
            && intent?.action == context.resources?.getString(R.string.forgerock_sso_logout)) {
            try {
                instance.tokenManager.revoke(null)
            }
            catch (e: Exception) {
                configHelper.loadFromPreference(context)?.let {
                    instance.init(context, it)
                    instance.tokenManager.revoke(null)
                }

            }
        }
    }
}