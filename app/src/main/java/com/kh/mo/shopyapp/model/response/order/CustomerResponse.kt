package com.kh.mo.shopyapp.model.response.order


import com.google.gson.annotations.SerializedName
import com.kh.mo.shopyapp.model.response.orderdetails.DefaultAddressResponse

data class CustomerResponse(
    @SerializedName("default_address")
    val defaultAddress: DefaultAddressResponse?,
    @SerializedName("accepts_marketing")
    val acceptsMarketing: Boolean?,
    @SerializedName("accepts_marketing_updated_at")
    val acceptsMarketingUpdatedAt: String?,
    @SerializedName("admin_graphql_api_id")
    val adminGraphqlApiId: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("currency")
    val currency: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("email_marketing_consent")
    val emailMarketingConsent: EmailMarketingConsent?,
    @SerializedName("first_name")
    val firstName: String?,
    @SerializedName("id")
    val id: Long?,
    @SerializedName("last_name")
    val lastName: String?,
    @SerializedName("marketing_opt_in_level")
    val marketingOptInLevel: Any?,
    @SerializedName("multipass_identifier")
    val multipassIdentifier: Any?,
    @SerializedName("note")
    val note: Any?,
    @SerializedName("phone")
    val phone: Any?,
    @SerializedName("sms_marketing_consent")
    val smsMarketingConsent: Any?,
    @SerializedName("state")
    val state: String?,
    @SerializedName("tags")
    val tags: String?,
    @SerializedName("tax_exempt")
    val taxExempt: Boolean?,
    @SerializedName("tax_exemptions")
    val taxExemptions: List<Any?>?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("verified_email")
    val verifiedEmail: Boolean?
)