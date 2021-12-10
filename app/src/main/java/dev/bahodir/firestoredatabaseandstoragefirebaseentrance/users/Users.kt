package dev.bahodir.firestoredatabaseandstoragefirebaseentrance.users

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

class Users : Serializable {
    var id: String? = null
    var name: String? = null
    var from: String? = null
    var to: String? = null
    var type: String? = null
    var info: String? = null
    var photo: String? = null
    var isLike: Boolean? = false

    constructor(
        id: String?,
        name: String?,
        from: String?,
        to: String?,
        type: String?,
        info: String?,
        photo: String?,
        isLike: Boolean?
    ) {
        this.id = id
        this.name = name
        this.from = from
        this.to = to
        this.type = type
        this.info = info
        this.photo = photo
        this.isLike = isLike
    }
    constructor(
        id: String?,
        name: String?,
        from: String?,
        to: String?,
        type: String?,
        info: String?,
        photo: String?
    ) {
        this.id = id
        this.name = name
        this.from = from
        this.to = to
        this.type = type
        this.info = info
        this.photo = photo
    }

    constructor(
        name: String?,
        from: String?,
        to: String?,
        type: String?,
        info: String?,
        photo: String?,

    ) {
        this.name = name
        this.from = from
        this.to = to
        this.type = type
        this.info = info
        this.photo = photo
    }


    constructor()
}