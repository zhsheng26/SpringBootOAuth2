package cn.eusunpower.support

import cn.eusunpower.model.base.BaseEntity
import java.util.*
import javax.persistence.PrePersist

class EntityListener {
    @PrePersist
    fun saveUpdateTime(entity: BaseEntity) {
        entity.setUpdateDate(Calendar.getInstance().time)
    }
}