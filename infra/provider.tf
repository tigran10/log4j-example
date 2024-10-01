# provider.tf

provider "google" {
  project = var.project_id
  region  = "us-central1"  # Change to your preferred region
}
