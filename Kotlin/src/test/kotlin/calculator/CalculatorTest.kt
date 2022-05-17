package calculator

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe

class CalculatorTest: AnnotationSpec() {
    @Test
    fun `+연산자는 좌우 숫자를 계산한다`() {
        val sut = Calculator()

        val result = sut.calculate("3 + 4")

        result shouldBe 7
    }

    @Test
    fun `3개 이상의 숫자도 계산한다`() {
        val sut = Calculator()

        val result = sut.calculate("3 + 4 + 5")

        result shouldBe 12
    }

    @Test
    fun `Null 입력 시 IllegalArgumentException가 발생한다`() {
        val sut = Calculator()

        val exception = shouldThrow<IllegalArgumentException> {
            sut.calculate(null)
        }

        exception.message shouldBe "빈 문자열은 입력할 수 없습니다."
    }

    @Test
    fun test() {
        data class Book(var name: String, var writer: String, var price: Int)
        val arr = mutableListOf<Book>()
        arr.add(Book("name1", "writer1", 12000))
        arr.add(Book("name2", "writer2", 21000))
        arr.add(Book("name3", "writer3", 31000))
        arr.add(Book("name4", "writer4", 28000))
        arr.add(Book("name4", "writer5", 23000))

        println("=============================")
        var arr2 = arr.groupBy { isNumeric(it.name) }
        arr2.forEach{
            println(it.key)
            println(it.value)
        }

    }
}