package com.example.chashout.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostgresqlConnectionProperties {
    private String password;
    private String dbname;
    private String schema;
    private String engine;
    private Integer port;
    private String dbInstanceIdentifier;
    private String host;
    private String username;
}
