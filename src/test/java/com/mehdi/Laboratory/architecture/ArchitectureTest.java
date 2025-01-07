package com.mehdi.Laboratory.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class ArchitectureTest {

    private JavaClasses classes;

    @BeforeEach
    public void beforeEach() {
        classes = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("com.mehdi.laboratory");
    }

    @Test
    public void serviceAndRepoShouldNotDependOnWebLayer() {
        // this will fail because userService has a dependency of ArchTestController
        // Comment archTestController to see this test passed
        noClasses()
                .that()
                .resideInAnyPackage("..service..")
                .or()
                .resideInAnyPackage("..repository..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage("..controller..")
                .because("Service and repos should not depend on web layer")
                .check(classes);
    }

    @Test
    public void controllersShouldDependOnService() {
        classes()
                .that()
                .resideInAnyPackage("..controller..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage("..service..")
                .because("Every Controller needs a service (silly scenario   for test purposes :) )")
                .check(classes );

    }





}
