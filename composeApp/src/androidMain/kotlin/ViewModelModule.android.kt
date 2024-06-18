import org.jdc.kmp.template.ux.directory.DirectoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

actual val viewModelModule = module {
    viewModelOf(::DirectoryViewModel)
}