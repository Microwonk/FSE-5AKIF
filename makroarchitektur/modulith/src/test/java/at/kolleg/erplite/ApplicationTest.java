package at.kolleg.erplite;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

class ApplicationTest {

    @Test
    void writeDocumentationSnippets() {

        var modules = ApplicationModules.of(ErpliteApplication.class);

        new Documenter(modules)
                .writeModulesAsPlantUml()
                .writeIndividualModulesAsPlantUml();
    }
}