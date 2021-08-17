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
    static final ArchRule controllersShouldBeSuffixed =
            classes()
                    .that().resideInAPackage("..controller..")
                    .or().areAnnotatedWith(RestController.class)
                    .should().haveSimpleNameEndingWith("Controller");

    @ArchTest
    static final ArchRule classesNamedControllerShouldBeInAControllerPackage =
            classes()
                    .that().haveSimpleNameContaining("Controller")
                    .should().resideInAPackage("..controller..");

    @ArchTest
    static final ArchRule servicesShouldBeSuffixed =
            classes()
                    .that().resideInAPackage("..service..")
                    .or().areAnnotatedWith(Service.class)
                    .should().haveSimpleNameEndingWith("Service")
                    .orShould().haveSimpleNameEndingWith("ServiceImpl");

    @ArchTest
    static final ArchRule servicesNamedControllerShouldBeInAControllerPackage =
            classes()
                    .that().haveSimpleNameContaining("Service")
                    .or().haveSimpleNameContaining("ServiceImpl")
                    .should().resideInAPackage("..service..");

    @ArchTest
    static final ArchRule repositoriesShouldBeSuffixed =
            classes()
                    .that().resideInAPackage("..repository..")
                    .or().areAnnotatedWith(Repository.class)
                    .should().haveSimpleNameEndingWith("Repository");

    @ArchTest
    static final ArchRule repositoriesNamedControllerShouldBeInAControllerPackage =
            classes()
                    .that().haveSimpleNameContaining("Repository")
                    .should().resideInAPackage("..repository..");
}
