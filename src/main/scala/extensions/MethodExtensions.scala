package extensions

import java.lang.reflect.Method
import scala.language.implicitConversions

object MethodExtensions {
  class MethodExtensionImpl(method: Method) {

    def getValue[TItem <: Object](item: TItem) = method.invoke(item)
  }

  implicit def toMethodExtensionImpl(method: Method) = new MethodExtensionImpl(method)
}