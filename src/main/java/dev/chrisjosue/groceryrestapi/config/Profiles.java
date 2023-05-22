package dev.chrisjosue.groceryrestapi.config;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Profiles {
    public final static String TEST_ENV = "TEST_ENV";
    public final static String JWT_AUTH_ENV = "JWT_AUTH_ENV";
}
