# Define variables
variable "project_id" {
  description = "The GCP project ID"
  type        = string
}

variable "service_account_id" {
  description = "The ID of the service account"
  type        = string
}

# Create a service account
resource "google_service_account" "cloud_run_sa" {
  account_id   = var.service_account_id
  display_name = "Cloud Run Service Account"
  project      = var.project_id
}

# Bind IAM roles to the service account
resource "google_project_iam_member" "monitoring_writer" {
  project = var.project_id
  member  = "serviceAccount:${google_service_account.cloud_run_sa.email}"
  role    = "roles/monitoring.metricWriter"
}

resource "google_project_iam_member" "trace_agent" {
  project = var.project_id
  member  = "serviceAccount:${google_service_account.cloud_run_sa.email}"
  role    = "roles/cloudtrace.agent"
}

resource "google_project_iam_member" "log_writer" {
  project = var.project_id
  member  = "serviceAccount:${google_service_account.cloud_run_sa.email}"
  role    = "roles/logging.logWriter"
}
