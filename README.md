# 📸 S3 Image Uploader (Spring Boot)

A lightweight, production-ready Spring Boot application to upload images to an AWS S3 bucket via REST APIs. Supports both file uploads and file path-based uploads.

---

## 🚀 Features

- Upload images via `MultipartFile` or local `imagePath`
- Store in Amazon S3 with public URL return
- Global exception handling with meaningful errors
- Custom exception classes for better debugging
- Environment config via `application.yml` or environment variables
- File size validation and error handling
- Logging enabled with Lombok

---

## 📁 Project Structure

```sql
+---src
|   +---main
|   |   +---java
|   |   |   \---com
|   |   |       \---estuate
|   |   |           \---image_uploader
|   |   |               |   ImageUploaderApplication.java
|   |   |               |   
|   |   |               +---config
|   |   |               |       S3Config.java
|   |   |               |       
|   |   |               +---controller
|   |   |               |       ImageUploadController.java
|   |   |               |       
|   |   |               +---dto
|   |   |               |       FileUploadRequest.java
|   |   |               |       ImagePathRequest.java
|   |   |               |       
|   |   |               +---exception
|   |   |               |       ApiErrorResponse.java
|   |   |               |       FileUploadException.java
|   |   |               |       GlobalExceptionHandler.java
|   |   |               |       
|   |   |               \---service
|   |   |                       S3Service.java
|   |   |                       
|   |   \---resources
|   |       |   application.yml
|   |       |   
```

---

## 🧪 API Endpoints

### ➕ Upload File via Multipart
```sql
POST /api/v1/images/upload
Form Data: file (image/png, image/jpeg, etc.)
```

### 🖼️ Upload File via Path

```sql
POST /api/v1/images/upload-by-path
Body: {
"imagePath": "C:\Users\APal\Pictures\Screenshots\img.png"
}
Content-Type: application/json
```


### ✅ Response

```json
{
  "url": "https://<bucket>.s3.amazonaws.com/<image-name>.png"
}



