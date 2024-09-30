package org.jdc.kmp.template.domain.usecase

import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import org.jdc.kmp.template.domain.Household
import org.jdc.kmp.template.domain.Individual
import org.jdc.kmp.template.domain.inline.FirstName
import org.jdc.kmp.template.domain.inline.LastName
import org.jdc.kmp.template.domain.inline.Phone
import org.jdc.kmp.template.domain.type.IndividualType
import org.jdc.kmp.template.inject.AppCoroutineDispatchers
import org.jdc.kmp.template.model.repository.IndividualRepository

class CreateIndividualTestDataUseCase(
    private val individualRepository: IndividualRepository,
    private val appCoroutineDispatchers: AppCoroutineDispatchers
) {
    suspend operator fun invoke() = withContext(appCoroutineDispatchers.default) {
        // clear any existing items
        individualRepository.deleteAllIndividuals()

        val household1 = Household(
            name = LastName("Campbell")
        )

        val individual1 = Individual(
            householdId = household1.id,
            firstName = FirstName("Jeff"),
            lastName = LastName("Campbell"),
            phone = Phone("801-555-0000"),
            individualType = IndividualType.HEAD,
            birthDate = LocalDate(1970, 1, 1),
            alarmTime = LocalTime(7, 0),
        )

        val individual1a = Individual(
            householdId = household1.id,
            firstName = FirstName("Ty"),
            lastName = LastName("Campbell"),
            phone = Phone("801-555-0001"),
            individualType = IndividualType.CHILD,
            birthDate = LocalDate(1970, 1, 1),
            alarmTime = LocalTime(7, 0),
        )

        val household2 = Household(
            name = LastName("Miller")
        )

        val individual2 = Individual(
            householdId = household2.id,
            firstName = FirstName("John"),
            lastName = LastName("Miller"),
            phone = Phone("303-555-1111"),
            individualType = IndividualType.HEAD,
            birthDate = LocalDate(1970, 1, 2),
            alarmTime = LocalTime(6, 0),
        )

        individualRepository.saveNewHousehold(household1, listOf(individual1, individual1a))
        individualRepository.saveNewHousehold(household2, listOf(individual2))
    }
}