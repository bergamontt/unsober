
# ✎ᝰ Unsober

![Java](https://img.shields.io/badge/Java-SE%2021%2B-orange)
![SpringBoot](https://img.shields.io/badge/Spring%20Boot-v3.5.5-green)
![Postgres](https://img.shields.io/badge/Postgres-v18-blue)
![React](https://img.shields.io/badge/React%20TS-v19.1.1-deepskyblue)

Unsober is an improved version of the automated registration system, which allows students to easily register for courses and groups and view their individually generated study plan and calendar.


## 📋 Features

- User-friendly graphical interface
- Enrollment in elective courses for the formation of an individual study plan
- Enrollment in subject groups for timetable creation
- Viewing information about disciplines
- Viewing your personalised calendar
- Download your personalised schedule and INP in .xlsx format


## 🛠️ Run Locally

### Prerequisites:

  - Git v2.47.1+
  - Java JDK 21+
  - Gradle v8.13.4+
  - npm 11.6.1+

### Instruction:

1. Clone the project
  ```bash
    git clone https://github.com/bergamontt/unsober.git
    cd unsober
  ```

2. Run the backend
  ```bash
    cd backend
    ./gradlew bootRun
  ```
  The backend will be running at `http://localhost:8080/api`

3. Run the frontend
  ```bash
    cd frontend
    npm install
    npm run dev
  ```
  The frontend will be running at `http://localhost:5173`


## 📝 Running Tests

To run tests for the backend, use the following commands

```bash
  cd backend
  ./gradlew test
```


## 📚 API Reference
OpenAPI docs can be accessed at ```http://localhost:8080/api/v3/api-docs```

