package ru.practicum.android.diploma.data.converters

import androidx.room.TypeConverter
import ru.practicum.android.diploma.data.converters.AddressDbConverter.fromAddressEntity
import ru.practicum.android.diploma.data.converters.AddressDbConverter.toAddressEntity
import ru.practicum.android.diploma.data.converters.AreaDbConverter.fromAreaEntity
import ru.practicum.android.diploma.data.converters.AreaDbConverter.toAreaEntity
import ru.practicum.android.diploma.data.converters.ContactsDbConverter.fromContactsEntity
import ru.practicum.android.diploma.data.converters.ContactsDbConverter.toContactsEntity
import ru.practicum.android.diploma.data.converters.EmployerDbConverter.fromEmployerEntity
import ru.practicum.android.diploma.data.converters.EmployerDbConverter.toEmployerEntity
import ru.practicum.android.diploma.data.converters.EmploymentDbConverter.fromEmploymentEntity
import ru.practicum.android.diploma.data.converters.EmploymentDbConverter.toEmploymentEntity
import ru.practicum.android.diploma.data.converters.ExperienceDbConverter.fromExperienceEntity
import ru.practicum.android.diploma.data.converters.ExperienceDbConverter.toExperienceEntity
import ru.practicum.android.diploma.data.converters.SalaryDbConverter.fromSalaryEntity
import ru.practicum.android.diploma.data.converters.SalaryDbConverter.toSalaryEntity
import ru.practicum.android.diploma.data.converters.ScheduleDbConverter.fromScheduleEntity
import ru.practicum.android.diploma.data.converters.ScheduleDbConverter.toScheduleEntity
import ru.practicum.android.diploma.data.db.entity.VacancyDescriptionEntity
import ru.practicum.android.diploma.domain.models.VacancyDescription

class VacancyDbConverter {
    @TypeConverter
    fun convertFromEntity(vacancyEntity: VacancyDescriptionEntity): VacancyDescription {
        return VacancyDescription(
            id = vacancyEntity.id,
            name = vacancyEntity.name,
            salary = fromSalaryEntity(vacancyEntity.salary),
            employer = fromEmployerEntity(vacancyEntity.employer),
            area = fromAreaEntity(vacancyEntity.area),
            url = vacancyEntity.url,
            address = fromAddressEntity(vacancyEntity.address),
            experience = fromExperienceEntity(vacancyEntity.experience),
            employment = fromEmploymentEntity(vacancyEntity.employment),
            schedule = fromScheduleEntity(vacancyEntity.schedule),
            keySkills = listOf(),
            contacts = fromContactsEntity(vacancyEntity.contacts),
            description = vacancyEntity.description
        )
    }

    @TypeConverter
    fun convertToEntity(vacancy: VacancyDescription): VacancyDescriptionEntity {
        return VacancyDescriptionEntity(
            id = vacancy.id,
            name = vacancy.name,
            salary = toSalaryEntity(vacancy.salary),
            employer = toEmployerEntity(vacancy.employer),
            area = toAreaEntity(vacancy.area),
            url = vacancy.url,
            address = toAddressEntity(vacancy.address),
            experience = toExperienceEntity(vacancy.experience),
            employment = toEmploymentEntity(vacancy.employment),
            schedule = toScheduleEntity(vacancy.schedule),
            contacts = toContactsEntity(vacancy.contacts),
            description = vacancy.description
        )
    }

    @TypeConverter
    fun convert(listVacancyEntity: List<VacancyDescriptionEntity>): List<VacancyDescription> {
        return listVacancyEntity.map { vacancyEntity ->
            convertFromEntity(vacancyEntity)
        }
    }
}
