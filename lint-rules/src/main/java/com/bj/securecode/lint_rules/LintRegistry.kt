package com.bj.securecode.lint_rules

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.Issue

@Suppress("UnstableApiUsage")
class LintRegistry : IssueRegistry() {
    override val issues: List<Issue> = listOf(
        NoOuterDependencyDetector.ISSUE_GITHUB_DEPENDENCY,
//        CapitalizedVariableDetector.ISSUE_CAPITALIZED_VAR,
    )

    // Match your lint-api major version (31 for AGP 8.x)
    override val api: Int = com.android.tools.lint.detector.api.CURRENT_API
}
