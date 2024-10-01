# main.tf

# Create a service account
resource "google_service_account" "cloud_run_sa" {
  account_id   = var.service_account_id
  display_name = "Cloud Run Service Account"
  project      = var.project_id
}

# Bind IAM role: monitoring.metricWriter
resource "google_project_iam_member" "metric_writer_binding" {
  project = var.project_id
  member  = "serviceAccount:${google_service_account.cloud_run_sa.email}"
  role    = "roles/monitoring.metricWriter"
}

# Bind IAM role: cloudtrace.agent
resource "google_project_iam_member" "trace_agent_binding" {
  project = var.project_id
  member  = "serviceAccount:${google_service_account.cloud_run_sa.email}"
  role    = "roles/cloudtrace.agent"
}

# Bind IAM role: logging.logWriter
resource "google_project_iam_member" "log_writer_binding" {
  project = var.project_id
  member  = "serviceAccount:${google_service_account.cloud_run_sa.email}"
  role    = "roles/logging.logWriter"
}

# Optional: Output the service account email
output "cloud_run_service_account_email" {
  value = google_service_account.cloud_run_sa.email
}
