REM curl-commands.bat
REM Cristian Manzo
REM September 27, 2026
REM cURL tests for the Task Manager API. Run them in order on a fresh start of the app,
REM because the IDs below depend on the order the records are created.

REM ===== Tasks =====
REM 1. POST /tasks: create a task
curl -X POST http://localhost:8080/tasks ^
-H "Content-Type: application/json" ^
-d "{\"title\": \"New Task\", \"description\": \"Task description\", \"status\": \"PENDING\", \"dueDate\": \"2024-09-15\"}"

REM 2. GET /tasks: get all tasks
curl -X GET http://localhost:8080/tasks

REM 3. GET /tasks/1: get task 1
curl -X GET http://localhost:8080/tasks/1

REM 4. PUT /tasks/1: update task 1
curl -X PUT http://localhost:8080/tasks/1 ^
-H "Content-Type: application/json" ^
-d "{\"title\": \"Updated Task\", \"description\": \"Updated description\", \"status\": \"COMPLETED\", \"dueDate\": \"2024-10-01\"}"

REM ===== Users =====
REM 5. POST /users: create a user (the response is a DTO, so no password)
curl -X POST http://localhost:8080/users ^
-H "Content-Type: application/json" ^
-d "{\"username\": \"alice\", \"password\": \"password123\"}"

REM 6. GET /users: get all users
curl -X GET http://localhost:8080/users

REM 7. GET /users/1: get user 1
curl -X GET http://localhost:8080/users/1

REM 8. PUT /users/1: update user 1 (still no password in the response)
curl -X PUT http://localhost:8080/users/1 ^
-H "Content-Type: application/json" ^
-d "{\"username\": \"alice_updated\", \"password\": \"newpass456\"}"

REM 9. DELETE /users/1: delete user 1
curl -X DELETE http://localhost:8080/users/1

REM ===== Priorities =====
REM 10. GET /priorities: get all priorities (LOW, MEDIUM, HIGH from data.sql)
curl -X GET http://localhost:8080/priorities

REM 11. GET /priorities/1: get priority 1 (LOW)
curl -X GET http://localhost:8080/priorities/1

REM 12. GET /priorities/2: get priority 2 (MEDIUM)
curl -X GET http://localhost:8080/priorities/2

REM 13. GET /priorities/3: get priority 3 (HIGH)
curl -X GET http://localhost:8080/priorities/3

REM ===== Tasks =====
REM 14. POST /tasks: create a task with priority HIGH (id 3)
curl -X POST http://localhost:8080/tasks ^
-H "Content-Type: application/json" ^
-d "{\"title\": \"Finish project\", \"description\": \"Complete milestone 1\", \"status\": \"PENDING\", \"dueDate\": \"2024-09-30\", \"priority\": {\"id\": 3}}"

REM ===== Subtasks =====
REM 15. POST /subtasks: create a subtask for task 1
curl -X POST http://localhost:8080/subtasks ^
-H "Content-Type: application/json" ^
-d "{\"title\": \"Write unit tests\", \"status\": \"PENDING\", \"task\": {\"id\": 1}}"

REM 16. GET /subtasks: get all subtasks
curl -X GET http://localhost:8080/subtasks

REM 17. GET /subtasks/1: get subtask 1
curl -X GET http://localhost:8080/subtasks/1

REM 18. PUT /subtasks/1: update subtask 1 (set status to COMPLETED)
curl -X PUT http://localhost:8080/subtasks/1 ^
-H "Content-Type: application/json" ^
-d "{\"title\": \"Write unit tests\", \"status\": \"COMPLETED\", \"task\": {\"id\": 1}}"

REM 19. DELETE /subtasks/1: delete subtask 1
curl -X DELETE http://localhost:8080/subtasks/1

REM ===== Relationships =====
REM 20. POST /users: create user bob
curl -X POST http://localhost:8080/users ^
-H "Content-Type: application/json" ^
-d "{\"username\": \"bob\", \"password\": \"bobpass789\"}"

REM 21. POST /tasks: create a task with priority MEDIUM assigned to bob (user 2)
curl -X POST http://localhost:8080/tasks ^
-H "Content-Type: application/json" ^
-d "{\"title\": \"Plan sprint\", \"description\": \"Set goals for next sprint\", \"status\": \"IN_PROGRESS\", \"dueDate\": \"2024-10-15\", \"priority\": {\"id\": 2}, \"users\": [{\"id\": 2}]}"

REM 22. POST /subtasks: add a subtask to task 3
curl -X POST http://localhost:8080/subtasks ^
-H "Content-Type: application/json" ^
-d "{\"title\": \"Draft sprint goals\", \"task\": {\"id\": 3}}"

REM 23. GET /tasks/3: task 3 shows its priority, subtask, and assigned user
curl -X GET http://localhost:8080/tasks/3

REM 24. GET /users/2: bob shows the task he is assigned to
curl -X GET http://localhost:8080/users/2

REM ===== Errors =====
REM 25. GET /tasks/99: a task that does not exist returns 404
curl -X GET http://localhost:8080/tasks/99

REM 26. POST /subtasks: a subtask for a task that does not exist returns 404
curl -X POST http://localhost:8080/subtasks ^
-H "Content-Type: application/json" ^
-d "{\"title\": \"Orphan subtask\", \"task\": {\"id\": 99}}"
