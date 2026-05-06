package org.controllers.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class RestIndexController {


    @RequestMapping(method = RequestMethod.GET, path = {"/", ""})
    protected ApiVersion showVersion() {
        ApiVersion version = new ApiVersion();
        version.setName("R2-D2 Galactic Kitchen API");
        version.setVersion("v1.0");
        return version;
    }


    private static class ApiVersion {

        private String name;
        private String version;

        public String getName() { return name; }
        public String getVersion() { return version; }
        public void setName(String name) { this.name = name; }
        public void setVersion(String version) { this.version = version; }
    }
}
