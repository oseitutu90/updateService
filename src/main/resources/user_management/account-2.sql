CREATE TABLE user_management.indicator_values (
                                                  id BIGSERIAL PRIMARY KEY,
                                                  account_classification VARCHAR(255) NOT NULL UNIQUE,
                                                  unique_id UUID NOT NULL UNIQUE,
                                                  indicator JSON
);