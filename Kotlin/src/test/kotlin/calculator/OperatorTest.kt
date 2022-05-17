package calculator

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class OperatorTest : DescribeSpec ({
    describe("evaluate") {
        it("+연산자는 두 숫자를 합한다.") {
            val sut = Operator.PLUS
            val result = sut.evaluate(3,4)

            result shouldBe 7
        }
        it("-연산자는 두 숫자를 뺀다.") {
            val sut = Operator.MINUS
            val result = sut.evaluate(5,3)

            result shouldBe 2
        }
        it("*연산자는 두 숫자를 곱한다.") {
            val sut = Operator.MULTIPLY
            val result = sut.evaluate(2,2)

            result shouldBe 4
        }
        it("/연산자는 두 숫자를 나눈다.") {
            val sut = Operator.DIVIDE
            val result = sut.evaluate(4,2)

            result shouldBe 2
        }
    }

    describe("find") {
        it("+가 입력되면 PLUS객체를 반환한다.") {
            val symbol = "+"
            val result = Operator.find(symbol)

            result shouldBe Operator.PLUS
        }
        it("4칙연산 외 다른 연사자가 입력되면 IllegalArgumentException가 발생한다") {
            val symbol = "x"
            val exception = shouldThrow<IllegalArgumentException> {
                Operator.find(symbol)
            }

            exception.message shouldBe "+, -, *, /외에는 사용할 수 없습니다."
        }
    }
})