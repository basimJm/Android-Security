package com.bj.securecode.lint_rules

import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Context
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.Location
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import java.util.EnumSet

@Suppress("UnstableApiUsage")
class NoOuterDependencyDetector :
    Detector(),
    Detector.OtherFileScanner {

    override fun getApplicableFiles(): EnumSet<Scope> = EnumSet.of(Scope.OTHER)

    override fun run(context: Context) {
        val file = context.file
        val name = file.name.lowercase()

        if (!name.endsWith(".gradle") && !name.endsWith(".gradle.kts")) return

        val content = file.readText()
        val badPatterns = listOf("github.com", "vanniktech", "git://", "git+https://")

        for (pattern in badPatterns) {
            if (content.contains(pattern, ignoreCase = true)) {
                context.report(
                    ISSUE_GITHUB_DEPENDENCY,
                    Location.create(file),
                    "🚫 Found forbidden repository reference '$pattern' in ${file.name}. " +
                            "Use only approved repositories (e.g., MavenCentral)."
                )
            }
        }
    }

    companion object {
        @JvmField
        val ISSUE_GITHUB_DEPENDENCY: Issue = Issue.create(
            id = "NoGithubDependency",
            briefDescription = "Prohibit GitHub/JitPack dependencies",
            explanation = """
                Avoid fetching dependencies directly from GitHub, git URLs, .
                These may contain unverified or mutable code.
                Use approved repositories like MavenCentral or internal artifact mirrors.
            """.trimIndent(),
            category = Category.SECURITY,
            priority = 8,
            severity = Severity.ERROR,
            implementation = Implementation(
                NoOuterDependencyDetector::class.java,
                Scope.OTHER_SCOPE
            )
        )
    }
}
