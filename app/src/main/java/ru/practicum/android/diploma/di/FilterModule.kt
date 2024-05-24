package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.data.converters.IndustriesConverter
import ru.practicum.android.diploma.data.impl.filters.IndustriesRepositoryImpl
import ru.practicum.android.diploma.domain.api.filters.IndustriesRepository
import ru.practicum.android.diploma.ui.filter.FilterViewModel

val filter = module {

    viewModel {
        FilterViewModel()
    }

    single<IndustriesRepository> {
        IndustriesRepositoryImpl(networkClient = get(), converter = IndustriesConverter())
    }
}
