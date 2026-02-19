package org.jdc.kmp.template.ux.about

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import okio.FileSystem
import org.dbtools.kmp.commons.compose.navigation3.ViewModelNavigation3
import org.dbtools.kmp.commons.compose.navigation3.ViewModelNavigation3Impl
import org.dbtools.kmp.commons.ext.stateInDefault
import org.jdc.kmp.template.domain.usecase.CreateIndividualTestDataUseCase
import org.jdc.kmp.template.model.repository.IndividualRepository


@Suppress("LongParameterList")
class AboutViewModel(
    private val individualRepository: IndividualRepository,
//    private val colorService: ColorService,
//    private val workScheduler: WorkScheduler,
//    private val remoteConfig: RemoteConfig,
//    private val devicePreferenceDataSource: DevicePreferenceDataSource,
    private val createIndividualTestDataUseCase: CreateIndividualTestDataUseCase,
//    private val createIndividualLargeTestDataUseCase: CreateIndividualLargeTestDataUseCase,
    private val fileSystem: FileSystem
) : ViewModel(), ViewModelNavigation3 by ViewModelNavigation3Impl() {

    private val resetServiceEnabledFlow: Flow<Boolean> = flowOf(true) // flowOf(remoteConfig.isColorServiceEnabled())

    val uiStateFlow: StateFlow<AboutUiState> = resetServiceEnabledFlow.map {
        AboutUiState.Ready(it)
    }.stateInDefault(viewModelScope, AboutUiState.Loading)

    fun onLicensesClick() {
//        navigate(AcknowledgmentsRoute)
    }

//    fun testQueryWebServiceCall() = viewModelScope.launch {
//        Logger.i { "TypeSafe Call..." }
//        if (!remoteConfig.isColorServiceEnabled()) {
//            Logger.e { "Color Service is NOT enabled... skipping" }
//            return@launch
//        }
//
//        val response = colorService.getColorsBySafeArgs()
//        processWebServiceResponse(response)
//
//        Logger.i { "========================================================" }
//
//        processWebServiceResponse2(colorService.getColorsBySafeArgs())
//    }
//
//    fun testFullUrlQueryWebServiceCall() = viewModelScope.launch {
//        Logger.i { "Full URL Call..." }
//        val response = colorService.getColorsByFullUrl()
//        processWebServiceResponse(response)
//    }
//
//    fun testCachedUrlQueryWebServiceCall() = viewModelScope.launch {
//        Logger.i { "Cached URL Call..." }
//
//        val etag = devicePreferenceDataSource.colorsETagPref.flow.firstOrNull()
//
//        colorService.getColorsCached(etag)
//            .onSuccess {
//                this.data?.colors?.forEach {
//                    Logger.i { "Result: ${it.colorName}" }
//                }
//
//                this.etag?.let { devicePreferenceDataSource.colorsETagPref.setValue(it) }
//            }
//            .onException {
//                Logger.e(this.throwable) { "Failed to fetch Colors" }
//            }
//    }
//
//    fun testSaveQueryWebServiceCall() = viewModelScope.launch {
//        val outputFile = application.filesDir.toOkioPath() / "colors.json"
//        colorService.getColorsToFile(fileSystem, outputFile)
//
//        Logger.i { "Downloaded file contents:\n ${fileSystem.readText(outputFile)}" }
//    }
//
//    fun processWebServiceResponse(response: ApiResponse<out ColorsDto, out Unit>) {
//        response.onSuccess {
//            data.colors.forEach {
//                Logger.i { "Result: ${it.colorName}" }
//            }
//        }.onFailure {
//            Logger.e { "Web Service FAILURE ${message()}" }
//        }.onError {
//
//        }.onException {
//
//        }
//    }
//
//    fun processWebServiceResponse2(response: ApiResponse<out ColorsDto, out Unit>) {
//        response.onSuccess {
//            data.colors.forEach {
//                Logger.i { "Result: ${it.colorName}" }
//            }
//        }.onFailure {
//            Logger.e { "Web Service FAILURE (message: [${message()}]" }
//        }
//    }
//
//    fun workManagerSimpleTest() = viewModelScope.launch {
//        workScheduler.scheduleSimpleWork("test1")
//        workScheduler.scheduleSimpleWork("test2")
//
//        delay(3000)
//
//        workScheduler.scheduleSimpleWork("test3")
//    }
//
//    fun workManagerSyncTest() = viewModelScope.launch {
//        workScheduler.scheduleSync()
//        workScheduler.scheduleSync(true)
//
//        delay(3000)
//
//        workScheduler.scheduleSync()
//    }
//
//    fun testTableChange() = viewModelScope.launch {
//        // Sample tests
//        if (individualRepository.getIndividualCount() == 0) {
//            Logger.e { "No data.. cannot perform test" }
//            return@launch
//        }
//
//        // Make some changes
//        val originalName: FirstName?
//
//        val individualList = individualRepository.getAllIndividuals()
//        if (individualList.isNotEmpty()) {
//            val individual = individualList[0]
//            originalName = individual.firstName
//            Logger.i { "ORIGINAL NAME = $originalName" }
//
//            // change name
//            individualRepository.saveIndividual(individual.copy(firstName = FirstName("Bobby")))
//
//            // restore name
//            individualRepository.saveIndividual(individual.copy(firstName = originalName))
//        } else {
//            Logger.e { "Cannot find individual" }
//        }
//    }

    fun createSampleData() = viewModelScope.launch {
        createIndividualTestDataUseCase()
    }
}

sealed class AboutUiState {
    object Loading : AboutUiState()
    data class Ready(
        val resetServiceEnabled: Boolean,
    ): AboutUiState()
}
