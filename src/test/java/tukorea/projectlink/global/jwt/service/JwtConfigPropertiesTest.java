package tukorea.projectlink.global.jwt.service;

import tukorea.projectlink.global.jwt.JwtConfigProperties;

public class JwtConfigPropertiesTest extends JwtConfigProperties {
    public JwtConfigPropertiesTest() {
        super(
                "YXNlZmphb2lzZWo7Zm9haXdqZjI5b29pYXc7amVpZjtqYXdvZTtmamF3ZWZhd2VmamF3b2llO2ZqYW93ZWZqYWt3ajtlb2ZqYXdvaWVm",
                10000L,
                "Authorization",
                10000L,
                "Authorization-refresh"
        );
    }
}
