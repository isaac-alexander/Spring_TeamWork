## TEAMWORK (Spring Boot Project)

TEAMWORK is a simple employee social platform where users can:
-	Post and view articles
-	Upload and view GIFs
-	Comment on posts
-	Admin can create employees
---
## Technologies Used
-   Java (Spring Boot)
-	Spring Security
-	Thymeleaf
-	MySQL
-	Cloudinary (for image upload)
-	Bootstrap 5

## Setup Instructions / ## Cloning from GitHub

- Open a terminal or Git Bash
- Navigate to the folder where you want to clone the project
- Run the clone command (replace <repo-url> with your GitHub repository URL):
- git clone <repo-url>
- Navigate into the cloned folder:
- cd <project-folder-name>
- Open the project in your IDE  IntelliJ
- Configure the database connection in application.properties (see Database Configuration)

## Setup

## 1. Create Database

## Open MySQL and run:
CREATE DATABASE teamwork_db;

## Configure Environment Variables

## 2. Create a .env file in the root folder:
- CLOUDINARY_CLOUD_NAME=your_cloud_name
- CLOUDINARY_API_KEY=your_api_key
- CLOUDINARY_API_SECRET=your_api_secret

## 3.  Update application.properties
- spring.datasource.url=jdbc:mysql://localhost:3306/"database name"?useSSL=false&serverTimezone=UTC&useLegacyDatetimeCode=false
- spring.datasource.username=root
- spring.datasource.password=YOUR_PASSWORD

## 4. Run the Project
- run the main class in your IDE.

`http://localhost:8080/`
