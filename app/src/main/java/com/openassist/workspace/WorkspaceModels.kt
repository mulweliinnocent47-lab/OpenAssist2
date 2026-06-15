package com.openassist.workspace

enum class ArtifactType(val label: String) {
    Code("Code"),
    Image("Image"),
    Document("Document"),
    Project("Project"),
    Data("Data"),
    Archive("Archive"),
    Spreadsheet("Spreadsheet"),
    Presentation("Presentation"),
    Diagram("Diagram"),
}

enum class CanvasType(val title: String) {
    Code("Code Canvas"),
    Document("Document Editor"),
    Image("Image Canvas"),
    Project("Project Explorer"),
    File("File Viewer"),
    Console("Output Console"),
}

data class WorkspaceArtifact(
    val id: String,
    val name: String,
    val path: String,
    val type: ArtifactType,
    val language: String? = null,
    val preview: String = "",
)

data class PythonRunRequest(
    val scriptName: String,
    val estimatedRuntime: String = "Short",
    val resourceUsage: String = "Low CPU / low memory",
    val networkAllowed: Boolean = false,
    val requiresApproval: Boolean = true,
)

object WorkspaceCatalog {
    val supportedCodeLanguages = listOf(
        "Python", "Kotlin", "Java", "JavaScript", "TypeScript", "HTML", "CSS", "JSON", "XML", "Markdown", "SQL", "Shell",
    )

    val generatedFileTypes = listOf("TXT", "PDF", "DOCX", "PPTX", "CSV", "XLSX", "JSON", "ZIP", "HTML", "Markdown")

    val starterArtifacts = listOf(
        WorkspaceArtifact("code-python", "script.py", "workspace/code/script.py", ArtifactType.Code, "Python", "print('Hello from OpenAssist')"),
        WorkspaceArtifact("project-website", "website", "workspace/projects/website/", ArtifactType.Project, preview = "HTML/CSS/JS starter website"),
        WorkspaceArtifact("document-plan", "business_plan.md", "workspace/documents/business_plan.md", ArtifactType.Document, "Markdown", "# Business Plan"),
        WorkspaceArtifact("image-concept", "concept.png", "workspace/images/concept.png", ArtifactType.Image, preview = "Generated image preview"),
        WorkspaceArtifact("archive-export", "project_export.zip", "workspace/archives/project_export.zip", ArtifactType.Archive, preview = "ZIP export package"),
    )

    fun shouldOpenCanvas(contentLength: Int, artifactType: ArtifactType): Boolean =
        contentLength > 1_500 || artifactType in setOf(ArtifactType.Code, ArtifactType.Project, ArtifactType.Document, ArtifactType.Image)
}
