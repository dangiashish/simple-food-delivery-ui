package com.codebyashish.foodui.models

class BinsInventoryModel {
    var id: Int = 0
    var binType: String = ""
    var imageUrl: Int? = null
    var rate: Int = 0
    var totalStock: Int = 0

    var currentStock: Int = 0
    var useStock: Int = 0
    var projectId: String? = null
    var createdBy: String? = null
    var createDate: String? = null
    var isActive: Boolean = false

    var selectedQuantity: Int = 0
}
