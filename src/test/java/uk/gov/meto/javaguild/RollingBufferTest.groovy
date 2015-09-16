package uk.gov.meto.javaguild

import spock.lang.Specification;

/**
 */
class RollingBufferTest extends Specification {

    def "GetAverage for less than 5 values"() {
        given:
            def buffer = new App.RollingBuffer()
        when:
            buffer.add(5)
            buffer.add(4)
            buffer.add(3)
            buffer.add(2)
        then:
            buffer.getAverage() == 0
    }

    def "GetAverage for 5 values"() {
        given:
            def buffer = new App.RollingBuffer()
        when:
            buffer.add(5)
            buffer.add(4)
            buffer.add(3)
            buffer.add(2)
            buffer.add(1)
        then:
            buffer.getAverage() == (5 + 4 + 3 + 2 + 1)/5 as double
    }

    def "GetAverage is rolling buffer"() {
        given:
            def buffer = new App.RollingBuffer()
        when:
            buffer.add(5)
            buffer.add(4)
            buffer.add(3)
            buffer.add(2)
            buffer.add(1)
            buffer.add(10)
        buffer.add(20)
        then:
            buffer.getAverage() == (3 + 2 + 1 + 10 + 20)/5 as double
    }


}
