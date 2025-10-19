package com.bj.securecode.lint_rules

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.LintFix
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.SourceCodeScanner
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UField
import org.jetbrains.uast.ULocalVariable
import org.jetbrains.uast.UParameter

@Suppress("UnstableApiUsage")
class CapitalizedVariableDetector : Detector(), SourceCodeScanner {

    override fun getApplicableUastTypes(): List<Class<out UElement>> = listOf(
        ULocalVariable::class.java,
        UField::class.java,
        UParameter::class.java
    )

    override fun createUastHandler(context: JavaContext): UElementHandler {
        return object : UElementHandler() {
            override fun visitLocalVariable(node: ULocalVariable) {
                checkName(context, node.name, node)
            }

            override fun visitField(node: UField) {
                checkName(context, node.name, node)
            }

            override fun visitParameter(node: UParameter) {
                checkName(context, node.name, node)
            }
        }
    }

    private fun checkName(context: JavaContext, name: String?, node: UElement) {
        if (name.isNullOrEmpty()) return
        if (name[0].isUpperCase()) {
            val suggested = name.replaceFirstChar { it.lowercase() }
            val fix = LintFix.create()
                .replace()
                .text(name)
                .with(suggested)
                .autoFix()
                .build()

            context.report(
                ISSUE_CAPITALIZED_VAR,
                node,
                context.getLocation(node),
                "Variable/parameter/field names should start with a lowercase letter.",
                fix
            )
        }
    }

    companion object {
        val ISSUE_CAPITALIZED_VAR: Issue = Issue.create(
            id = "CapitalizedVariableName",
            briefDescription = "Variable names must start with lowercase",
            explanation = """
                Variable, parameter, and field names should start with a lowercase letter 
                according to Kotlin/Java style guidelines.
            """.trimIndent(),
            category = Category.CORRECTNESS,
            priority = 5,
            severity = Severity.WARNING,
            implementation = Implementation(
                CapitalizedVariableDetector::class.java,
                Scope.JAVA_FILE_SCOPE
            )
        )
    }
}
