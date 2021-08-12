package arch;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(
        packages = "br.com.base",
        importOptions = ImportOption.DoNotIncludeTests.class
)
public class NamingConventionTest {
    @ArchTest
    static final ArchRule controllers_should_be_suffixed =
            classes()
                    .that().resideInAPackage("..controller..")
                    .or().areAnnotatedWith(RestController.class)
                    .should().haveSimpleNameEndingWith("Controller");

    @ArchTest
    static final ArchRule classes_named_controller_should_be_in_a_controller_package =
            classes()
                    .that().haveSimpleNameContaining("Controller")
                    .should().resideInAPackage("..controller..");

    @ArchTest
    static final ArchRule services_should_be_suffixed =
            classes()
                    .that().resideInAPackage("..service..")
                    .or().areAnnotatedWith(Service.class)
                    .should().haveSimpleNameEndingWith("Service")
                    .orShould().haveSimpleNameEndingWith("ServiceImpl");

    @ArchTest
    static final ArchRule services_named_controller_should_be_in_a_controller_package =
            classes()
                    .that().haveSimpleNameContaining("Service")
                    .or().haveSimpleNameContaining("ServiceImpl")
                    .should().resideInAPackage("..service..");

    @ArchTest
    static final ArchRule repositories_should_be_suffixed =
            classes()
                    .that().resideInAPackage("..repository..")
                    .or().areAnnotatedWith(Repository.class)
                    .should().haveSimpleNameEndingWith("Repository");

    @ArchTest
    static final ArchRule repositories_named_controller_should_be_in_a_controller_package =
            classes()
                    .that().haveSimpleNameContaining("Repository")
                    .should().resideInAPackage("..repository..");
}
