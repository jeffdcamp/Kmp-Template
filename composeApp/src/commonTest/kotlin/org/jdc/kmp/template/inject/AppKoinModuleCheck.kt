package org.jdc.kmp.template.inject

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.modifierprovider.withoutAbstractModifier
import com.lemonappdev.konsist.api.ext.list.withAllParentsOf
import com.lemonappdev.konsist.api.verify.assertTrue
import kotlinx.coroutines.CoroutineDispatcher
import org.jdc.kmp.template.ux.individual.IndividualRoute
import org.jdc.kmp.template.ux.individualedit.IndividualEditRoute
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.instance.InstanceFactory
import org.koin.core.module.Module
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import org.koin.test.verify.verify
import kotlin.reflect.KClass
import kotlin.test.Test

class AppKoinModuleCheck {

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun checkKoinModule() {
        val extraTypes = listOf(
            Context::class,
            Application::class,

            DataStore::class, // Implementations of DataStore is done in the single<UserDataStore>
            CoroutineDispatcher::class, // Implementations of CoroutineDispatcher is done in the single<AppCoroutineDispatchers>

            // Route items that are supplied by koinViewModel<XxxViewModel> { parametersOf(key) }
            IndividualRoute::class,
            IndividualEditRoute::class,
        )

        // Combine all modules into one "wrapper" module for verification.
        // This ensures all modules will know about definitions provided by other modules (Example: appModule can see definitions in datastoreModule)
        module {
            includes(getAllKoinModules())
        }.verify(extraTypes)
    }

    @Test
    fun `verify no duplicate definitions in Koin modules`() {
        koinApplication {
            allowOverride(false) // STRICT MODE: Fail on duplicates
            modules(getAllKoinModules())
        }
    }

    @OptIn(KoinInternalApi::class)
    @Test
    fun `verify all ViewModels are registered in Koin`() {
        findMissingRegisteredTypes(ViewModel::class)
    }

    private fun findMissingRegisteredTypes(baseClass: KClass<*>) {
        // 1. Pre-fetch all files that look like Koin Modules
        val koinModules = getAllKoinModules()

        val koinDefinitions: List<String> = getRegisteredClassNames(koinModules)
        // koinDefinitions.forEach { println("def: $it") }

        // 2. Run the check
        Konsist.scopeFromProject()
            .classes()
            .withAllParentsOf(baseClass, indirectParents = true)
            .withoutAbstractModifier()
            .assertTrue { classDeclaration ->
//                println("+++ ${classDeclaration.name}")
                val isRegistered = koinDefinitions.any { it == classDeclaration.name }

                if (!isRegistered) {
                    println("FAILED: ${classDeclaration.name} is not registered in any Koin module.")
                }
                isRegistered
            }
    }

    @OptIn(KoinInternalApi::class)
    fun getRegisteredClassNames(modules: List<Module>): List<String> {
        return modules
            .flatMap { module ->
                module.mappings.values // Get all BeanDefinitions
            }
            .map { instanceFactory : InstanceFactory<*> ->
                // Extract the KClass simple name
                instanceFactory.beanDefinition.primaryType.simpleName.orEmpty()
            }
            .distinct() // Remove duplicates if multiple modules define the same type
    }
}
