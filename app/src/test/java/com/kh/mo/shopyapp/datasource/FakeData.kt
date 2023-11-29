package com.kh.mo.shopyapp.datasource

import com.kh.mo.shopyapp.model.entity.FavoriteEntity
import com.kh.mo.shopyapp.model.entity.ImageEntity
import com.kh.mo.shopyapp.model.entity.OptionEntity
import com.kh.mo.shopyapp.model.entity.VariantEntity
import com.kh.mo.shopyapp.model.response.address.AddressResponse
import com.kh.mo.shopyapp.model.response.address.AddressesResponse
import com.kh.mo.shopyapp.model.response.ads.DiscountCode
import com.kh.mo.shopyapp.model.response.ads.DiscountCodeResponse
import com.kh.mo.shopyapp.model.response.allproducts.AllProductsResponse
import com.kh.mo.shopyapp.model.response.allproducts.ImageResponse
import com.kh.mo.shopyapp.model.response.allproducts.OptionResponse
import com.kh.mo.shopyapp.model.response.allproducts.ProductResponse
import com.kh.mo.shopyapp.model.response.allproducts.VariantResponse
import com.kh.mo.shopyapp.model.response.barnds.BrandsResponse
import com.kh.mo.shopyapp.model.response.barnds.RuleResponse
import com.kh.mo.shopyapp.model.response.barnds.SmartCollectionRespnse
import com.kh.mo.shopyapp.model.response.create_customer.CustomerResponse
import com.kh.mo.shopyapp.model.response.create_customer.CustomerX
import com.kh.mo.shopyapp.model.response.currency.Rates
import com.kh.mo.shopyapp.model.response.draft_order.Customer
import com.kh.mo.shopyapp.model.response.draft_order.DefaultAddress
import com.kh.mo.shopyapp.model.response.draft_order.DraftOrderDetailsResponse
import com.kh.mo.shopyapp.model.response.draft_order.DraftOrderResponse
import com.kh.mo.shopyapp.model.response.draft_order.EmailMarketingConsent
import com.kh.mo.shopyapp.model.response.draft_order.LineItem
import com.kh.mo.shopyapp.model.response.draft_order.SmsMarketingConsent
import com.kh.mo.shopyapp.model.response.draft_order.TaxLineX
import com.kh.mo.shopyapp.model.response.login.Login
import com.kh.mo.shopyapp.model.response.maincategory.CustomCollectionResponse
import com.kh.mo.shopyapp.model.response.maincategory.MainCategoryResponse
import com.kh.mo.shopyapp.model.response.order.OrdersResponse
import com.kh.mo.shopyapp.model.response.orderdetails.CurrentSubtotalPriceSet
import com.kh.mo.shopyapp.model.response.orderdetails.CurrentTotalDiscountsSet
import com.kh.mo.shopyapp.model.response.orderdetails.CurrentTotalPriceSet
import com.kh.mo.shopyapp.model.response.orderdetails.CurrentTotalTaxSet
import com.kh.mo.shopyapp.model.response.orderdetails.PresentmentMoney
import com.kh.mo.shopyapp.model.response.orderdetails.ShopMoney
import com.kh.mo.shopyapp.model.response.orderdetails.SubtotalPriceSet
import com.kh.mo.shopyapp.model.response.orderdetails.TotalDiscountsSet
import com.kh.mo.shopyapp.model.response.orderdetails.TotalLineItemsPriceSet
import com.kh.mo.shopyapp.model.response.orderdetails.TotalPriceSet
import com.kh.mo.shopyapp.model.response.orderdetails.TotalShippingPriceSet
import com.kh.mo.shopyapp.model.response.orderdetails.TotalTaxSet
import com.kh.mo.shopyapp.model.response.osm.AddressDetailsResponse
import com.kh.mo.shopyapp.model.response.osm.NominatimResponse

object FakeData {
    val fakeAllProductsResponse: AllProductsResponse = AllProductsResponse(
        products = listOf(
            ProductResponse(
                adminGraphqlApiId = "fake_admin_graphql_api_id",
                bodyHtml = "Fake product description",
                createdAt = "2023-11-20T12:30:00Z",
                handle = "fake-product-handle",
                id = 123456789,
                image = ImageResponse(
                    adminGraphqlApiId = "fake_admin_graphql_api_id",
                    createdAt = "2023-11-20T12:30:00Z",
                    height = 200,
                    id = 987654321,
                    position = 1,
                    productId = 123456789,
                    src = "https://fake-image-url.com"
                ),
                images = listOf(
                    ImageResponse(
                        adminGraphqlApiId = "fake_admin_graphql_api_id",
                        createdAt = "2023-11-20T12:30:00Z",
                        height = 200,
                        id = 987654321,
                        position = 1,
                        productId = 123456789,
                        src = "https://fake-image-url-1.com"
                    ),
                    ImageResponse(
                        adminGraphqlApiId = "fake_admin_graphql_api_id",
                        createdAt = "2023-11-20T12:30:00Z",
                        height = 200,
                        id = 987654322,
                        position = 2,
                        productId = 123456789,
                        src = "https://fake-image-url-2.com"
                    )
                ),
                options = listOf(
                    OptionResponse(
                        id = 987654321,
                        name = "Fake Option Name",
                        position = 1,
                        productId = 123456789,
                        values = listOf("Value 1", "Value 2", "Value 3")
                    ),
                    OptionResponse(
                        id = 987654322,
                        name = "Fake Option Name 2",
                        position = 2,
                        productId = 123456789,
                        values = listOf("Value A", "Value B", "Value C")
                    )
                ),
                productType = "Fake Product Type",
                publishedAt = "2023-11-20T12:30:00Z",
                publishedScope = "web",
                status = "active",
                tags = "fake, tags",
                templateSuffix = "",
                title = "Fake Product Title",
                updatedAt = "2023-11-20T12:30:00Z",
                variants = listOf(
                    VariantResponse(
                        barcode = "fake_barcode",
                        compareAtPrice = 19.99,
                        createdAt = "2023-11-20T12:30:00Z",
                        fulfillmentService = "manual",
                        grams = 500,
                        id = 987654321,
                        imageId = 123456789,
                        inventoryItemId = 543210987,
                        inventoryManagement = "shopify",
                        inventoryPolicy = "deny",
                        inventoryQuantity = 50,
                        oldInventoryQuantity = 25,
                        option1 = "Fake Option 1",
                        option2 = "Fake Option 2",
                        option3 = "Fake Option 3",
                        position = 1,
                        price = "29.99",
                        productId = 123456789,
                        requiresShipping = true,
                        sku = "fake_sku",
                        taxable = true,
                        title = "Fake Variant Title",
                        updatedAt = "2023-11-20T12:30:00Z",
                        weight = 0.75,
                        weightUnit = "kg"
                    ),
                    VariantResponse(
                        barcode = "fake_barcode_2",
                        compareAtPrice = 24.99,
                        createdAt = "2023-11-20T12:30:00Z",
                        fulfillmentService = "manual",
                        grams = 700,
                        id = 987654322,
                        imageId = 123456790,
                        inventoryItemId = 543210988,
                        inventoryManagement = "shopify",
                        inventoryPolicy = "deny",
                        inventoryQuantity = 40,
                        oldInventoryQuantity = 20,
                        option1 = "Fake Option A",
                        option2 = "Fake Option B",
                        option3 = "Fake Option C",
                        position = 2,
                        price = "34.99",
                        productId = 123456789,
                        requiresShipping = true,
                        sku = "fake_sku_2",
                        taxable = true,
                        title = "Fake Variant Title 2",
                        updatedAt = "2023-11-20T12:30:00Z",
                        weight = 1.0,
                        weightUnit = "kg"
                    )
                ),
                vendor = "Fake Vendor"
            )
        )
    )

    val fakeCustomer = Customer(
        accepts_marketing = false,
        accepts_marketing_updated_at = "",
        admin_graphql_api_id = "",
        created_at = "",
        currency = "",
        default_address = DefaultAddress(
            address1 = "",
            address2 = "",
            city = "",
            company = null,
            country = "",
            country_code = "",
            country_name = "",
            customer_id = 0,
            default = false,
            first_name = "",
            id = 0,
            last_name = "",
            name = "",
            phone = "",
            province = "",
            province_code = "",
            zip = null
        ),
        email = "",
        email_marketing_consent = EmailMarketingConsent(
            consent_updated_at = null,
            opt_in_level = "",
            state = ""
        ),
        first_name = "",
        id = 0,
        last_name = "",
        last_order_id = null,
        last_order_name = null,
        marketing_opt_in_level = null,
        multipass_identifier = null,
        note = null,
        orders_count = 0,
        phone = "",
        sms_marketing_consent = SmsMarketingConsent(
            consent_collected_from = "",
            consent_updated_at = null,
            opt_in_level = "",
            state = ""
        ),
        state = "",
        tags = "",
        tax_exempt = false,
        tax_exemptions = emptyList(),
        total_spent = "",
        updated_at = "",
        verified_email = false
    )

    val fakeDraftOrderResponse = DraftOrderResponse(
        draft_order = DraftOrderDetailsResponse(
            admin_graphql_api_id = "fake_admin_id",
            applied_discount = null,
            billing_address = null,
            completed_at = null,
            created_at = "2023-11-29T12:34:56",
            currency = "USD",
            customer = fakeCustomer,
            email = "fake@email.com",
            id = 123456789L,
            invoice_sent_at = null,
            invoice_url = "https://fakeinvoiceurl.com",
            line_items = listOf(
                LineItem(
                    admin_graphql_api_id = "fake_line_item_admin_id",
                    applied_discount = null,
                    custom = false,
                    fulfillment_service = "manual",
                    gift_card = false,
                    grams = 500,
                    id = 987654321L,
                    name = "Fake Product",
                    price = "19.99",
                    product_id = 789012345L,
                    properties = null,
                    quantity = 2,
                    requires_shipping = true,
                    sku = "FP123",
                    tax_lines = listOf(
                        TaxLineX(
                            price = "3.00",
                            rate = 0.15,
                            title = "VAT"
                        )
                    ),
                    taxable = true,
                    title = "Fake Product Title",
                    variant_id = 456789012L,
                    variant_title = "Fake Variant Title",
                    vendor = "Fake Vendor"
                )
            ),
            name = "Fake Order",
            note = null,
            note_attributes = null,
            order_id = null,
            payment_terms = null,
            shipping_address = null,
            status = "pending",
            subtotal_price = "39.98",
            tags = "fake, order",
            tax_exempt = false,
            tax_lines = listOf(
                TaxLineX(
                    price = "3.00",
                    rate = 0.15,
                    title = "VAT"
                )
            ),
            taxes_included = false,
            total_price = "42.98",
            total_tax = "3.00",
            updated_at = "2023-11-30T09:45:00"
        )
    )

    val fakeBrandsResponse = BrandsResponse(
        smartCollections = listOf(
            SmartCollectionRespnse(
                adminGraphqlApiId = "",
                bodyHtml = "",
                disjunctive = false,
                handle = "",
                id = 0,
                image = com.kh.mo.shopyapp.model.response.barnds.ImageResponse(
                    alt = "",
                    createdAt = "",
                    height = 0,
                    src = "",
                    width = 0
                ),
                publishedAt = "",
                publishedScope = "",
                rules = listOf(
                    RuleResponse(
                        column = "",
                        condition = "",
                        relation = ""
                    )
                ),
                sortOrder = "",
                templateSuffix = "",
                title = "",
                updatedAt = ""
            )
        )
    )

    val fakeMainCategoryResponse = MainCategoryResponse(
        customCollections = listOf(
            CustomCollectionResponse(
                adminGraphqlApiId = "",
                bodyHtml = "",
                handle = "",
                id = 0,
                image = com.kh.mo.shopyapp.model.response.maincategory.ImageResponse(
                    alt = "",
                    createdAt = "",
                    height = 0,
                    src = "",
                    width = 0
                ),
                publishedAt = "",
                publishedScope = "",
                sortOrder = "",
                templateSuffix = "",
                title = "",
                updatedAt = ""
            )
        )
    )

    val fakeDiscountCodeResponse = DiscountCodeResponse(
        discountCode = DiscountCode(
            code = "",
            createdAt = "",
            id = 0,
            priceRuleId = 0,
            updatedAt = "",
            usageCount = 0
        )
    )

    val fakeCustomerResponse = CustomerResponse(
        CustomerX(
            accepts_marketing = false,
            accepts_marketing_updated_at = "",
            addresses = emptyList(),
            admin_graphql_api_id = "",
            created_at = "",
            currency = "",
            email = "",
            first_name = "",
            id = null,
            last_name = "",
            last_order_id = null,
            last_order_name = null,
            marketing_opt_in_level = null,
            multipass_identifier = null,
            note = null,
            orders_count = null,
            phone = null,
            sms_marketing_consent = null,
            state = "",
            tags = "",
            tax_exempt = false,
            tax_exemptions = emptyList(),
            total_spent = "",
            updated_at = "",
            verified_email = false
        )
    )

    val fakeLogin = Login(
        customers = listOf(
            com.kh.mo.shopyapp.model.response.login.Customer(
                accepts_marketing = false,
                accepts_marketing_updated_at = "",
                addresses = emptyList(),
                admin_graphql_api_id = "",
                created_at = "",
                currency = "",
                email = "",
                email_marketing_consent = null,
                first_name = "",
                id = null,
                last_name = "",
                last_order_id = null,
                last_order_name = null,
                marketing_opt_in_level = null,
                multipass_identifier = null,
                note = null,
                orders_count = null,
                phone = null,
                sms_marketing_consent = null,
                state = "",
                tags = "",
                tax_exempt = false,
                tax_exemptions = emptyList(),
                total_spent = "",
                updated_at = "",
                verified_email = false
            )
        )
    )

    val fakeRates = Rates(
        EGP = "30.9",
        EUR = "0.9",
        GBP = "0.78",
        USD = "1.0"
    )

    val fakeAddressResponse = AddressResponse(
        address1 = "",
        address2 = "",
        city = "",
        company = "",
        country = "",
        countryCode = "",
        countryName = "",
        customerId = 1L,
        default = false,
        firstName = "",
        id = 1L,
        lastName = "",
        name = "",
        phone = "",
        province = "",
        provinceCode = "",
        zip = null
    )

    val fakeAddressesResponse = AddressesResponse(
        addressResponses = listOf(
            fakeAddressResponse
        )
    )

    val fakeNominatimResponse = NominatimResponse(
        placeId = null,
        licence = "",
        osmType = "",
        osmId = null,
        lat = "",
        lon = "",
        mClass = "",
        type = "",
        placeRank = null,
        importance = null,
        addresstype = "",
        name = "",
        displayName = "",
        address = AddressDetailsResponse(
            city = "",
            houseNumber = "",
            road = "",
            village = "",
            region = "",
            ISO = "",
            postcode = "",
            country = "",
            countryCode = ""
        ),
        boundingbox = arrayListOf()
    )

    val fakeOrderResponse = com.kh.mo.shopyapp.model.response.order.OrderResponse(
        adminGraphqlApiId = "",
        appId = 0,
        billingAddress = "",
        browserIp = "",
        buyerAcceptsMarketing = false,
        cancelReason = "",
        cancelledAt = "",
        cartToken = "",
        checkoutId = "",
        checkoutToken = "",
        clientDetails = "",
        closedAt = "",
        company = "",
        confirmationNumber = "",
        confirmed = false,
        contactEmail = "",
        createdAt = "",
        currency = "",
        currentSubtotalPrice = "",
        currentSubtotalPriceSet = com.kh.mo.shopyapp.model.response.order.CurrentSubtotalPriceSet(
            presentmentMoney = com.kh.mo.shopyapp.model.response.order.PresentmentMoney(
                amount = "",
                currencyCode = ""
            ),
            shopMoney = com.kh.mo.shopyapp.model.response.order.ShopMoney(
                amount = "",
                currencyCode = ""
            )
        ),
        currentTotalAdditionalFeesSet = "",
        currentTotalDiscounts = "",
        currentTotalDiscountsSet = com.kh.mo.shopyapp.model.response.order.CurrentTotalDiscountsSet(
            shopMoney = com.kh.mo.shopyapp.model.response.order.ShopMoney(
                amount = "",
                currencyCode = ""
            ),
            presentmentMoney = com.kh.mo.shopyapp.model.response.order.PresentmentMoney(
                amount = "",
                currencyCode = ""
            )
        ),
        currentTotalDutiesSet = "",
        currentTotalPrice = "",
        currentTotalPriceSet = com.kh.mo.shopyapp.model.response.order.CurrentTotalPriceSet(
            shopMoney = com.kh.mo.shopyapp.model.response.order.ShopMoney(
                amount = "",
                currencyCode = ""
            ),
            presentmentMoney = com.kh.mo.shopyapp.model.response.order.PresentmentMoney(
                amount = "",
                currencyCode = ""
            )
        ),
        currentTotalTax = "",
        currentTotalTaxSet = com.kh.mo.shopyapp.model.response.order.CurrentTotalTaxSet(
            shopMoney = com.kh.mo.shopyapp.model.response.order.ShopMoney(
                amount = "",
                currencyCode = ""
            ),
            presentmentMoney = com.kh.mo.shopyapp.model.response.order.PresentmentMoney(
                amount = "",
                currencyCode = ""
            )
        ),
        customerResponse = null,
        customerLocale = "",
        deviceId = "",
        discountApplications = emptyList(),
        discountCodes = emptyList(),
        email = "",
        estimatedTaxes = false,
        financialStatus = "",
        fulfillmentStatus = "",
        fulfillments = emptyList(),
        id = 0,
        landingSite = "",
        landingSiteRef = "",
        lineItems = emptyList(),
        locationId = "",
        merchantOfRecordAppId = "",
        name = "",
        note = "",
        noteAttributes = emptyList(),
        number = 0,
        orderNumber = 0,
        orderStatusUrl = "",
        originalTotalAdditionalFeesSet = "",
        originalTotalDutiesSet = "",
        paymentGatewayNames = emptyList(),
        paymentTerms = "",
        phone = "",
        poNumber = "",
        presentmentCurrency = "",
        processedAt = "",
        reference = "",
        referringSite = "",
        refunds = emptyList(),
        shippingAddress = "",
        shippingLines = emptyList(),
        sourceIdentifier = "",
        sourceName = "",
        sourceUrl = "",
        subtotalPrice = "",
        subtotalPriceSet = null,
        totalLineItemsPrice = "",
        totalLineItemsPriceSet = null,
        totalOutstanding = "",
        totalPrice = "",
        totalPriceSet = null,
        totalShippingPriceSet = null,
        totalTax = "",
        totalTaxSet = null,
        totalTipReceived = "",
        totalWeight = 0,
        updatedAt = "",
        userId = "",
        tags = "",
        taxExempt = false,
        taxLines = emptyList(),
        taxesIncluded = false,
        test = false,
        token = "",
        totalDiscounts = "",
        totalDiscountsSet = null
    )

    val fakeOrdersResponse = OrdersResponse(emptyList())

    val fakeFavoriteEntity = FavoriteEntity(
        productId = 9876,
        customerId = 1234,
        images = listOf(
            ImageEntity(src = "https://example.com/image1.jpg"),
            ImageEntity(src = "https://example.com/image2.jpg")
        ),
        productType = "Electronics",
        image = ImageEntity(src = "https://example.com/main_image.jpg"),
        title = "Fake Product",
        variants = listOf(
            VariantEntity(
                id = 1,
                price = "99.99",
                productId = 9876,
                title = "Variant 1",
                weightUnit = "kg"
            ),
            VariantEntity(
                id = 2,
                price = "109.99",
                productId = 9876,
                title = "Variant 2",
                weightUnit = "kg"
            )
        ),
        options = listOf(
            OptionEntity(values = listOf("Red", "Blue", "Green"))
        ),
        vendor = "FakeVendor",
        status = "In Stock"
    )

}