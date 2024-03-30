package com.devmantech.reminders.suites;

import org.junit.jupiter.api.Tag;
import org.junit.platform.suite.api.ExcludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SelectPackages("com.devmantech.reminders")
@ExcludeTags("integration")
@SuiteDisplayName("Suite of Unit tests")
@Tag("test-suite")
class UnitTestSuite {
}
