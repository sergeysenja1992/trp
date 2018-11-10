package ua.pp.ssenko.stories.utls

import java.time.LocalDate

class RangeByDate(val startDate: LocalDate, val endDate: LocalDate): Iterable<LocalDate> {
        override fun iterator() = object: Iterator<LocalDate> {
                private var currentDate = startDate

                override fun next(): LocalDate {
                        val result = currentDate
                        currentDate = currentDate.plusDays(1)
                        return result
                }

                override fun hasNext(): Boolean {
                        return currentDate.isBefore(endDate)
                }
        }
}