package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.data.converters.AreasConverter
import ru.practicum.android.diploma.data.converters.IndustriesConverter
import ru.practicum.android.diploma.data.impl.filters.AreasRepositoryImpl
import ru.practicum.android.diploma.data.impl.filters.IndustriesRepositoryImpl
import ru.practicum.android.diploma.domain.api.filters.AreasRepository
import ru.practicum.android.diploma.domain.api.filters.IndustriesInteractor
import ru.practicum.android.diploma.domain.api.filters.IndustriesRepository
import ru.practicum.android.diploma.domain.impl.IndustriesInteractorImpl
import ru.practicum.android.diploma.ui.filter.FilterViewModel
import ru.practicum.android.diploma.ui.filter.industry.IndustryViewModel

val filter = module {

    viewModel {
        FilterViewModel(get())
    }

    single<IndustriesInteractor> {
        IndustriesInteractorImpl(repository = get())
    }

    single<IndustriesRepository> {
        IndustriesRepositoryImpl(networkClient = get(), converter = IndustriesConverter())
    }

    single<AreasRepository> {
        AreasRepositoryImpl(networkClient = get(), converter = AreasConverter())
    }

    viewModel {
        IndustryViewModel(interactor = get())
    }
}
