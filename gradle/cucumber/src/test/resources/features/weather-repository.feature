Feature: Weather repository provides weather data

  Scenario: NO_KEY_01: Repository is not able to fetch data when API key is missing
    Given a weather repository is created without API key
    When weather data is requested for London
    Then an exception is received

  @EndToEnd
  Scenario: API_KEY_01: Repository can fetch and map temperature data when API key is present
    Given a weather repository is created with API key
    When weather data is requested for London
    Then temperature data is mapped

  @EndToEnd
  Scenario: API_KEY_02: Repository can fetch and map humidity data when API key is present
    Given a weather repository is created with API key
    When weather data is requested for London
    Then humidity data is mapped

  @EndToEnd
  Scenario: API_KEY_03: Repository can fetch and map wind speed data when API key is present
    Given a weather repository is created with API key
    When weather data is requested for London
    Then wind speed data is mapped

  Scenario: MAP_ONLY_01: Repository can map prefetched data
    Given a weather repository is created without API key using prefetched data
    When weather data is requested for London
    Then prefetched data is mapped
