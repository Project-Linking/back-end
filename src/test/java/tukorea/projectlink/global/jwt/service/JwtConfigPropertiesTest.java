package tukorea.projectlink.global.jwt.service;

import tukorea.projectlink.jwt.JwtProvider;

public class JwtConfigPropertiesTest extends JwtProvider {
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
