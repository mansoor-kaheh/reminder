package com.devmantech.reminders.suites;

import org.junit.jupiter.api.Tag;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SelectPackages("com.devmantech.reminders")
@IncludeTags("integration")
@SuiteDisplayName("Suite of Integration tests that require initialization of ApplicationContext")
@Tag("test-suite")
class IntegrationTestSuite {
}
