package org.jdc.kmp.template.model.db.converter

import androidx.room3.ColumnTypeConverter
import kotlin.time.Instant
import org.jdc.kmp.template.domain.inline.CreatedTime
import org.jdc.kmp.template.domain.inline.Email
import org.jdc.kmp.template.domain.inline.FirstName
import org.jdc.kmp.template.domain.inline.HouseholdId
import org.jdc.kmp.template.domain.inline.IndividualId
import org.jdc.kmp.template.domain.inline.LastModifiedTime
import org.jdc.kmp.template.domain.inline.LastName
import org.jdc.kmp.template.domain.inline.Phone

@Suppress("TooManyFunctions")
object DataValueClassTypeConverters {
    @ColumnTypeConverter
    fun fromStringToIndividualId(value: String): IndividualId = IndividualId(value)
    @ColumnTypeConverter
    fun fromIndividualIdToString(value: IndividualId): String = value.value

    @ColumnTypeConverter
    fun fromStringToHouseholdId(value: String): HouseholdId = HouseholdId(value)
    @ColumnTypeConverter
    fun fromHouseholdIdToString(value: HouseholdId): String = value.value

    @ColumnTypeConverter
    fun fromStringToFirstName(value: String): FirstName = FirstName(value)
    @ColumnTypeConverter
    fun fromFirstNameToString(value: FirstName): String = value.value

    @ColumnTypeConverter
    fun fromStringToLastName(value: String): LastName = LastName(value)
    @ColumnTypeConverter
    fun fromLastNameToString(value: LastName): String = value.value

    @ColumnTypeConverter
    fun fromStringToPhone(value: String): Phone = Phone(value)
    @ColumnTypeConverter
    fun fromPhoneToString(value: Phone): String = value.value

    @ColumnTypeConverter
    fun fromStringToEmail(value: String): Email = Email(value)
    @ColumnTypeConverter
    fun fromEmailToString(value: Email): String = value.value

    @ColumnTypeConverter
    fun fromStringToCreatedTime(value: String): CreatedTime = CreatedTime(Instant.parse(value))
    @ColumnTypeConverter
    fun fromCreatedTimeToString(value: CreatedTime): String = value.value.toString()

    @ColumnTypeConverter
    fun fromStringToLastModifiedTime(value: String): LastModifiedTime = LastModifiedTime(Instant.parse(value))
    @ColumnTypeConverter
    fun fromLastModifiedTimeToString(value: LastModifiedTime): String = value.value.toString()
}