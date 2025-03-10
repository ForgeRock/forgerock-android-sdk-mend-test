/*
 * Copyright (c) 2022 - 2023 ForgeRock. All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */
package org.forgerock.android.auth.devicebind

import java.security.PrivateKey

/**
 * State of the Device Binding errors
 */
private const val TIMEOUT = "Timeout"
private const val ABORT = "Abort"
private const val UNSUPPORTED = "Unsupported"

sealed interface DeviceBindingStatus

abstract class DeviceBindingErrorStatus(var message: String,
                                        val clientError: String,
                                        val errorCode: Int? = null) :
    DeviceBindingStatus {

    data class Timeout(private val errorMessage: String = "Authentication Timeout",
                       private val errorType: String = TIMEOUT,
                       private val code: Int? = null) :
        DeviceBindingErrorStatus(errorMessage, errorType, code)


    data class Abort(private val errorMessage: String = "User Terminates the Authentication",
                     private val errorType: String = ABORT,
                     private val code: Int? = null) :
        DeviceBindingErrorStatus(errorMessage, errorType, code)

    data class Unsupported(private var errorMessage: String = "Device not supported. Please verify the biometric or Pin settings",
                           private val errorType: String = UNSUPPORTED,
                           private val code: Int? = null) :
        DeviceBindingErrorStatus(errorMessage, errorType, code)

    data class UnRegister(private val errorMessage: String = "PublicKey or PrivateKey Not found in Device",
                          private val errorType: String = UNSUPPORTED,
                          private val code: Int? = null) :
        DeviceBindingErrorStatus(errorMessage, errorType, code)

    data class UnAuthorize(private val errorMessage: String = "Invalid Credentials",
                           private val errorType: String = UNSUPPORTED,
                           private val code: Int? = null) :
        DeviceBindingErrorStatus(errorMessage, errorType, code)

    data class Unknown(private val errorMessage: String = "Unknown",
                       private val errorType: String = ABORT,
                       private val code: Int? = null) :
        DeviceBindingErrorStatus(errorMessage, errorType, code)
}

data class Success(val privateKey: PrivateKey) : DeviceBindingStatus

/**
 * Exceptions for device binding
 */
class DeviceBindingException : Exception {
    val status: DeviceBindingErrorStatus

    constructor(status: DeviceBindingErrorStatus) : super(status.message) {
        this.status = status
    }
    constructor(status: DeviceBindingErrorStatus, cause: Throwable?) : super(status.message,
        cause) {
        this.status = status
    }
}
