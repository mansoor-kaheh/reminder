package com.devmantech.reminders.suites;

import org.junit.platform.suite.api.*;

@Suite
@IncludeEngines("junit-jupiter")
@SelectPackages("com.devmantech.reminders")
@IncludeClassNamePatterns(".*Test.*")
@ExcludePackages("com.devmantech.reminders.suites")
@ExcludeClassNamePatterns(".*Suite.*")
@SuiteDisplayName("Master Test Suite, runs all tests")
class MasterTestSuite {
}
