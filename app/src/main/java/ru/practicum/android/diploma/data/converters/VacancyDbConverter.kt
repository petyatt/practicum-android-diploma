package ru.practicum.android.diploma.data.converters

import androidx.room.TypeConverter
import ru.practicum.android.diploma.data.converters.AreaDbConverter.fromAreaEntity
import ru.practicum.android.diploma.data.converters.AreaDbConverter.toAreaEntity
import ru.practicum.android.diploma.data.converters.EmployerDbConverter.fromEmployerEntity
import ru.practicum.android.diploma.data.converters.EmployerDbConverter.toEmployerEntity
import ru.practicum.android.diploma.data.converters.EmploymentDbConverter.fromEmploymentEntity
import ru.practicum.android.diploma.data.converters.EmploymentDbConverter.toEmploymentEntity
import ru.practicum.android.diploma.data.converters.SalaryDbConverter.fromSalaryEntity
import ru.practicum.android.diploma.data.converters.SalaryDbConverter.toSalaryEntity
import ru.practicum.android.diploma.data.db.entity.VacancyEntity
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyDbConverter {
    @TypeConverter
    fun convertFromEntity(vacancyEntity: VacancyEntity): Vacancy {
        return Vacancy(
            id = vacancyEntity.id,
            name = vacancyEntity.name,
            salary = fromSalaryEntity(vacancyEntity.salary),
            employer = fromEmployerEntity(vacancyEntity.employer),
            area = fromAreaEntity(vacancyEntity.area),
            employment = fromEmploymentEntity(vacancyEntity.employment)
        )
    }

    @TypeConverter
    fun convertToEntity(vacancy: Vacancy): VacancyEntity {
        return VacancyEntity(
            id = vacancy.id,
            name = vacancy.name,
            salary = toSalaryEntity(vacancy.salary),
            employer = toEmployerEntity(vacancy.employer),
            area = toAreaEntity(vacancy.area),
            employment = toEmploymentEntity(vacancy.employment)
        )
    }

    @TypeConverter
    fun convert(listVacancyEntity: List<VacancyEntity>): List<Vacancy> {
        return listVacancyEntity.map { vacancyEntity ->
            convertFromEntity(vacancyEntity)
        }
    }
}
