# Table of Contents
- [API Standards Guide](#api-standards-guide)
  - [Introduction](#introduction)
  - [URL Paths](#url-paths)
  - [Query Parameters](#query-parameters)
  - [Versioning](#versioning)
  - [Pagination and Partial Response](#pagination-and-partial-response)
  - [Attribute Naming](#attribute-naming)
  - [Tips for Search](#tips-for-search)
  - [Handling Errors](#handling-errors)
  - [Tips for Handling Exceptional Behavior](#tips-for-handling-exceptional-behavior)


# API Standards Guide

## Introduction
Our API is designed for simplicity, consistency, and developer experience. The primary response structure includes `data` for main content and `meta` for metadata, such as pagination details. These standards ensure predictable and developer-friendly APIs.

---

## URL Paths
- **Use Nouns**: Use nouns to describe resources (e.g., `/appointments`, `/patients`).
- **Avoid Verbs**: Avoid verbs in paths unless representing an action-based endpoint without a specific resource (e.g., `/convert` for currency conversion).
- **Plural Nouns**: Use plural nouns for collections (e.g., `/appointments`) and singular nouns for single resources (`/appointments/{id}`).
- **Concrete Names**: Prefer specific, concrete names like `/appointments` instead of ambiguous terms like `/items`.

---

## Query Parameters
- **Pagination**: Use `page` and `per_page` parameters for pagination.
  - Example: `GET /api/v1/appointments?page=2&per_page=20`
- **Filtering and Search**: Include filter parameters directly in the query string for relevant resource types.
  - **Appointment Slot Filtering**: To filter available appointments by specific criteria, such as date range, practitioner gender, or appointment format, use a structure like this:
    ```
    GET /api/v1/appointments/slots?appointment-format=audio_only&practitioner-gender=female&from-date=2024-10-10&to-date=2024-10-24
    ```
  - **Practitioner Search**: Use a similar structure for filtering practitioners based on specific requirements:
    ```
    GET /api/v1/appointments/practitioners/genders?service-category=8&service-type=142&appointment-format=audio_only
    ```
- **Sorting**: Use a `sort` parameter with a minus sign (`-`) for descending order. If no sign is provided, the sort order is ascending.
  - Example: `GET /api/v1/appointments?sort=-start_time` (descending by `start_time`)
- **Including Related Data**: Use the `include` parameter to embed related resources, like doctor or patient details, directly in the response.
  - Example: `GET /api/v1/appointments?include=doctor`

---

## Versioning
- **URL-Based Versioning**: Place the version number at the beginning of the URL path.
  - Example: `GET /api/v1/appointments`
- **Simple Ordinal Versions**: Use versions like `v1`, `v2`, without minor versions like `v1.1`.

---

## Pagination and Partial Response
- **Response Structure for Collections**: For lists, wrap data in a `data` array and include pagination metadata in a `meta` object.
  ```json
  {
    "data": [
      { "appointment_id": "123", "date": "2024-10-10", "time": "10:00", ... },
      { "appointment_id": "124", "date": "2024-10-10", "time": "11:00", ... }
    ],
    "meta": {
      "current_page": 1,
      "last_page": 10,
      "per_page": 10,
      "from": 1,
      "to": 10,
      "total": 100,
      "links": {
        "previous": null,
        "next": "/api/v1/appointments?page=2"
      }
    }
  }

- **Single Resource Response**: For single items, wrap the data in a data object.

```json
{
  "data": {
    "appointment_id": "123",
    "patient_id": "456",
    "doctor": { "id": "789", "name": "Dr. Smith" },
    ...
  }
}
``` 
- **Partial Responses**: Use a fields parameter to specify only the fields required in the response.
```json
{
  "data": {
    "appointment_id": "123",
    "patient_id": "456",
    "doctor": { "id": "789", "name": "Dr. Smith" },
    ...
  }
}
```


## Attribute Naming

- **JSON Naming Conventions**: Use camelCase for attribute names to align with JavaScript conventions.
- **Avoid Special Characters**: Stick to letters and numbers for compatibility across systems.
- **Consistency: Use the same**: attribute names across endpoints (e.g., startTime across resources to represent appointment start time).

## Tips for Search

- **Global and Scoped Search**: Use `q` for general search terms. For scoped search within a resource, specify the relevant field.
- **Example (Global Search)**: `GET /api/v1/search?q=patient`
- **Example (Scoped Search)**: `GET /api/v1/appointments?q=completed`

Handling Errors
- **Standard Error Structure**: Refer to our Error Response Guide for detailed error-handling standards. Example error structure:

```json
{
  "errors": [
    {
      "type": "ValidationError",
      "field": "appointment_date",
      "message": "The appointment date must be a valid date.",
      "code": "4001"
    }
  ],
  "status_code": 400,
  "reason_phrase": "Validation Error"
}
```


## Tips for Handling Exceptional Behavior

- **Fallback Handling**: In cases where clients can’t handle certain *HTTP* status codes, consider returning a `200` status code with a descriptive errors object in the response payload.
- **Suppress Response Codes (if necessary)**: For certain client limitations, provide optional parameters (e.g., `suppress_response_codes=true`) to avoid displaying errors directly.

