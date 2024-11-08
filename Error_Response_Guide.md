
# Guide: Structuring Error Responses in the API for Consistency and Clarity

To ensure clarity and ease of handling errors in our API, we’ll use a standardized structure for error responses. This guide outlines how to format error responses for various types of errors, with examples in the **Bupa insurance** domain.

---

## General Error Response Structure

All error responses should adhere to this general structure:

```json
{
  "status": "error",
  "status_code": <HTTP_STATUS_CODE>,
  "code": "<GENERAL_ERROR_CODE>",
  "message": "<Brief human-readable description>",
  "errors": [
    {
      "field": "<fieldName or null if not applicable>",
      "message": "<Specific error description>",
      "code": "<Specific error code>"
    }
  ],
  "metadata": {
    "timestamp": "<ISO-8601 timestamp>",
    "path": "<Endpoint path>",
    "traceId": "<UUID trace ID for debugging>"
  }
}
```

### Key Fields Explained
- **status**: Always `"error"` for error responses.
- **status_code**: The HTTP status code, e.g., `400`, `404`, `500`.
- **code**: A human-readable error code that broadly categorizes the error, such as `VALIDATION_ERROR` or `DEPENDENCY_UNAVAILABLE`.
- **message**: A brief summary of the error.
- **errors**: An array of specific errors, each with a `field` (if applicable), `message`, and `code`.
- **metadata**: Additional information useful for debugging, such as `traceId` for tracing logs.

---

## Examples in the Bupa Insurance Domain

### 1. Field Validation Errors (e.g., Invalid input format for `policyNumber`)

Use this format when specific fields fail validation.

**Example Request**:
```http
POST /api/v1/policy/claim
```

**Example Response**:
```json
{
  "status": "error",
  "status_code": 400,
  "code": "VALIDATION_ERROR",
  "message": "One or more fields failed validation.",
  "errors": [
    {
      "field": "policyNumber",
      "message": "Policy number must be exactly 10 digits.",
      "code": "INVALID_FORMAT"
    },
    {
      "field": "dateOfBirth",
      "message": "Date of birth cannot be in the future.",
      "code": "INVALID_DATE"
    }
  ],
  "metadata": {
    "timestamp": "2023-10-31T12:00:00Z",
    "path": "/api/v1/policy/claim",
    "traceId": "abc123-def456"
  }
}
```

### 2. Runtime Errors (e.g., Unexpected server errors)

Runtime errors, such as uncaught exceptions or system failures, should return a generic response without exposing sensitive details.

**Example Request**:
```http
POST /api/v1/policy/update-beneficiary
```

**Example Response**:
```json
{
  "status": "error",
  "status_code": 500,
  "code": "INTERNAL_SERVER_ERROR",
  "message": "An unexpected error occurred. Please try again later.",
  "errors": [],
  "metadata": {
    "timestamp": "2023-10-31T12:00:00Z",
    "path": "/api/v1/policy/update-beneficiary",
    "traceId": "xyz789-ghi123"
  }
}
```

### 3. Dependency Errors (e.g., External service failure)

If a required service (e.g., payment or verification service) is down or unavailable, provide an appropriate error response.

**Example Request**:
```http
POST /api/v1/policy/premium-payment
```

**Example Response**:
```json
{
  "status": "error",
  "status_code": 503,
  "code": "DEPENDENCY_UNAVAILABLE",
  "message": "Payment service is currently unavailable. Please try again later.",
  "errors": [],
  "metadata": {
    "timestamp": "2023-10-31T12:00:00Z",
    "path": "/api/v1/policy/premium-payment",
    "traceId": "klm123-nop456"
  }
}
```

### 4. Invalid Query Parameter Value (e.g., Unsupported `planType`)

When a query parameter is provided with an invalid value, return a structured response with details on the parameter issue.

**Example Request**:
```http
GET /api/v1/plans?planType=unknown
```

**Example Response**:
```json
{
  "status": "error",
  "status_code": 400,
  "code": "INVALID_PARAMETER",
  "message": "Invalid value for query parameter.",
  "errors": [
    {
      "field": "planType",
      "message": "The planType parameter must be one of 'basic', 'premium', or 'platinum'.",
      "code": "UNSUPPORTED_VALUE"
    }
  ],
  "metadata": {
    "timestamp": "2023-10-31T12:00:00Z",
    "path": "/api/v1/plans",
    "traceId": "uvw987-qrs654"
  }
}
```

### 5. Business Errors (e.g., Coverage Limit Exceeded)

Business logic errors should indicate that a request was understood but could not be processed due to business rules.

**Example Request**:
```http
POST /api/v1/claim/submit
```

**Example Response**:
```json
{
  "status": "error",
  "status_code": 400,
  "code": "BUSINESS_RULE_VIOLATION",
  "message": "Claim submission failed due to policy restrictions.",
  "errors": [
    {
      "field": null,
      "message": "The claim amount exceeds the maximum allowable coverage for this policy.",
      "code": "COVERAGE_LIMIT_EXCEEDED"
    }
  ],
  "metadata": {
    "timestamp": "2023-10-31T12:00:00Z",
    "path": "/api/v1/claim/submit",
    "traceId": "xyz789-ghi123"
  }
}
```

---

## Best Practices

1. **Always Include Metadata**:
   - Use the `traceId` to correlate requests and errors for debugging.
   - Include a timestamp for logs and auditing.

2. **Be Consistent with `status_code` and `code`**:
   - Match `status_code` with HTTP status conventions (e.g., `400` for bad requests, `500` for server errors).
   - Use clear and human-readable `code` values to help clients easily identify the error type programmatically.

3. **Use Descriptive and Actionable `message` Values**:
   - Messages should be understandable to end-users when possible, especially in validation errors.

4. **Errors Array**:
   - Populate `errors` array with relevant details, whether related to fields or broader business logic.

5. **Field-Specific vs. General Errors**:
   - Use `field` in `errors` only when it’s tied to a specific input field. If the error is general or business-related, leave `field` as `null` or omit it entirely.

By following these conventions, your team can ensure that error responses are helpful, consistent, and easy to understand across different API endpoints and error types.
