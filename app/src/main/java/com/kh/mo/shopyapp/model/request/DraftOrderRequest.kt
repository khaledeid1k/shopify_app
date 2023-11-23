package com.kh.mo.shopyapp.model.request

data class DraftOrderRequest(
    val draft_order: DraftOrderDetailsRequest
)

data class CustomerDraftRequest(val id: Long)

data class DraftOrderDetailsRequest(
    val line_items: List<LineItems> =  listOf(LineItems(
        quantity = 1,
        price = 0.0,
        title = "n",
        product_id = "",
        variant_id = null,
    )),
    val customer: CustomerDraftRequest
)

data class LineItems(
    val quantity: Int=1,
    val price: Double = 0.0,
    val title: String? = null,
    val product_id: String = "",
    val variant_id: Int? ,

    )

