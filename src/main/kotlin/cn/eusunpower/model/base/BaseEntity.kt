package cn.eusunpower.model.base

import cn.eusunpower.support.EntityListener
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.hibernate.annotations.GenericGenerator
import java.io.Serializable
import java.util.*
import javax.persistence.*

@MappedSuperclass
@EntityListeners(EntityListener::class)
@JsonIgnoreProperties("createDate", "updateDate", "deleted")
open class BaseEntity : Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "t_id")
    var id: String = ""
    var description: String = ""
    var ext: String = ""
    val isNew: Boolean
        get() = id.isEmpty()

    @Temporal(value = TemporalType.TIMESTAMP)
    private lateinit var updateDate: Date


    @Temporal(value = TemporalType.TIMESTAMP)
    var createDate = Calendar.getInstance().time

    var status: Boolean = false

    var deleted: Boolean = false

    fun setUpdateDate(time: Date) {
        updateDate = time
    }
}