package producer

import java.nio.ByteBuffer

import org.apache.kafka.common.serialization.Serializer

class CustomSerializerScala extends Serializer[Customer] {
  override def serialize(topic: String, data: Customer): Array[Byte] = {

    var serializedName:Array[Byte] = null
    var stringSize = 0
    if (data == null) return null
    else if (data.customerName != null) {
      serializedName = data.customerName.getBytes("UTF-8")
      stringSize = serializedName.length
    }
    else {
      serializedName = new Array[Byte](0)
      stringSize = 0
    }

    val buffer = ByteBuffer.allocate(4 + 4 + stringSize)
    buffer.putInt(data.customerId)
    buffer.putInt(stringSize)
    buffer.put(serializedName)

    buffer.array


  }
}
