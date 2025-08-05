# Fake API Test (Java, TestNG, Rest Assured, Allure)

## Overview
This project is a **Java-based API testing framework** using:
- **TestNG** (Test Framework)
- **Rest Assured** (HTTP Client for API testing)
- **Allure** (Test Reporting)
- **Maven** (Build and Dependency Management)

It automates testing of the [FakeREST API](https://fakerestapi.azurewebsites.net/) with comprehensive test cases covering CRUD operations for Books and Authors.

---

## Features

- Automated API Testing for GET, POST, PUT, DELETE requests  
- Parallel test execution using TestNG  
- Allure Reporting for detailed test execution results  
- Dockerized test execution for consistency across environments  
- GitHub Actions CI/CD integration with Allure report deployment to GitHub Pages

---

## Prerequisites

- Java 18 or higher
- Maven 3.8+
- Docker (for containerized test runs)
- GitHub Actions (CI configured)

---

## Installation

 Clone the repository:
```sh
git clone https://github.com/Sayed-Othman/fake-api-test-with-docker.git
cd fake-api-test-with-docker
```


---
## Running Tests

### Locally (with Maven):
```sh
mvn clean test
```
### Using Docker:

Build the image:
```sh
docker build -t fake-api-test:latest .
```
Run tests in container:

```sh
docker run --rm \
  -e BASE_URL=https://fakerestapi.azurewebsites.net/api/v1 \
  -v "$(pwd)/target/allure-results:/app/target/allure-results" \
  fake-api-test:latest mvn test
```
---
**Note for Windows users:** Use the following in PowerShell:

```powershell
docker run --rm `
  -e BASE_URL=https://fakerestapi.azurewebsites.net/api/v1 `
  -v "${PWD}\target\allure-results:/app/target/allure-results" `
  fake-api-test:latest mvn test
```

## Allure Reporting

Generate Report:
```sh
allure generate target/allure-results --clean -o allure-report
```
Open Report:
```sh
allure open allure-report
```

---
## Continuous Integration (CI)

This project uses GitHub Actions to:

- Build and run tests in Docker

- Copy Allure results

- Generate and deploy report to GitHub Pages

### Allure report will be available at:

https://sayedothman.github.io/fake-api-test-with-docker/
