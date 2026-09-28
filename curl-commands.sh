#!/bin/bash
# curl-commands.sh
# Cristian Manzo
# September 27, 2026
# Mac/Linux version of curl-commands.bat. Run it top to bottom on a fresh start of the app,
# because the IDs below depend on the order the records are created.

# ===== Tasks =====
# 1. POST /tasks: create a task
curl -s -w "\nHTTP %{http_code}\n" -X POST http://localhost:8080/tasks -H "Content-Type: application/json" -d '{"title": "New Task", "description": "Task description", "status": "PENDING", "dueDate": "2024-09-15"}'

# 2. GET /tasks: get all tasks
curl -s -w "\nHTTP %{http_code}\n" -X GET http://localhost:8080/tasks

# 3. GET /tasks/1: get task 1
curl -s -w "\nHTTP %{http_code}\n" -X GET http://localhost:8080/tasks/1

# 4. PUT /tasks/1: update task 1
curl -s -w "\nHTTP %{http_code}\n" -X PUT http://localhost:8080/tasks/1 -H "Content-Type: application/json" -d '{"title": "Updated Task", "description": "Updated description", "status": "COMPLETED", "dueDate": "2024-10-01"}'

# ===== Users =====
# 5. POST /users: create a user (the response is a DTO, so no password)
curl -s -w "\nHTTP %{http_code}\n" -X POST http://localhost:8080/users -H "Content-Type: application/json" -d '{"username": "alice", "password": "password123"}'

# 6. GET /users: get all users
curl -s -w "\nHTTP %{http_code}\n" -X GET http://localhost:8080/users

# 7. GET /users/1: get user 1
curl -s -w "\nHTTP %{http_code}\n" -X GET http://localhost:8080/users/1

# 8. PUT /users/1: update user 1 (still no password in the response)
curl -s -w "\nHTTP %{http_code}\n" -X PUT http://localhost:8080/users/1 -H "Content-Type: application/json" -d '{"username": "alice_updated", "password": "newpass456"}'

# 9. DELETE /users/1: delete user 1
curl -s -w "\nHTTP %{http_code}\n" -X DELETE http://localhost:8080/users/1

# ===== Priorities =====
# 10. GET /priorities: get all priorities (LOW, MEDIUM, HIGH from data.sql)
curl -s -w "\nHTTP %{http_code}\n" -X GET http://localhost:8080/priorities

# 11. GET /priorities/1: get priority 1 (LOW)
curl -s -w "\nHTTP %{http_code}\n" -X GET http://localhost:8080/priorities/1

# 12. GET /priorities/2: get priority 2 (MEDIUM)
curl -s -w "\nHTTP %{http_code}\n" -X GET http://localhost:8080/priorities/2

# 13. GET /priorities/3: get priority 3 (HIGH)
curl -s -w "\nHTTP %{http_code}\n" -X GET http://localhost:8080/priorities/3

# ===== Tasks =====
# 14. POST /tasks: create a task with priority HIGH (id 3)
curl -s -w "\nHTTP %{http_code}\n" -X POST http://localhost:8080/tasks -H "Content-Type: application/json" -d '{"title": "Finish project", "description": "Complete milestone 1", "status": "PENDING", "dueDate": "2024-09-30", "priority": {"id": 3}}'

# ===== Subtasks =====
# 15. POST /subtasks: create a subtask for task 1
curl -s -w "\nHTTP %{http_code}\n" -X POST http://localhost:8080/subtasks -H "Content-Type: application/json" -d '{"title": "Write unit tests", "status": "PENDING", "task": {"id": 1}}'

# 16. GET /subtasks: get all subtasks
curl -s -w "\nHTTP %{http_code}\n" -X GET http://localhost:8080/subtasks

# 17. GET /subtasks/1: get subtask 1
curl -s -w "\nHTTP %{http_code}\n" -X GET http://localhost:8080/subtasks/1

# 18. PUT /subtasks/1: update subtask 1 (set status to COMPLETED)
curl -s -w "\nHTTP %{http_code}\n" -X PUT http://localhost:8080/subtasks/1 -H "Content-Type: application/json" -d '{"title": "Write unit tests", "status": "COMPLETED", "task": {"id": 1}}'

# 19. DELETE /subtasks/1: delete subtask 1
curl -s -w "\nHTTP %{http_code}\n" -X DELETE http://localhost:8080/subtasks/1

# ===== Relationships =====
# 20. POST /users: create user bob
curl -s -w "\nHTTP %{http_code}\n" -X POST http://localhost:8080/users -H "Content-Type: application/json" -d '{"username": "bob", "password": "bobpass789"}'

# 21. POST /tasks: create a task with priority MEDIUM assigned to bob (user 2)
curl -s -w "\nHTTP %{http_code}\n" -X POST http://localhost:8080/tasks -H "Content-Type: application/json" -d '{"title": "Plan sprint", "description": "Set goals for next sprint", "status": "IN_PROGRESS", "dueDate": "2024-10-15", "priority": {"id": 2}, "users": [{"id": 2}]}'

# 22. POST /subtasks: add a subtask to task 3
curl -s -w "\nHTTP %{http_code}\n" -X POST http://localhost:8080/subtasks -H "Content-Type: application/json" -d '{"title": "Draft sprint goals", "task": {"id": 3}}'

# 23. GET /tasks/3: task 3 shows its priority, subtask, and assigned user
curl -s -w "\nHTTP %{http_code}\n" -X GET http://localhost:8080/tasks/3

# 24. GET /users/2: bob shows the task he is assigned to
curl -s -w "\nHTTP %{http_code}\n" -X GET http://localhost:8080/users/2

# ===== Errors =====
# 25. GET /tasks/99: a task that does not exist returns 404
curl -s -w "\nHTTP %{http_code}\n" -X GET http://localhost:8080/tasks/99

# 26. POST /subtasks: a subtask for a task that does not exist returns 404
curl -s -w "\nHTTP %{http_code}\n" -X POST http://localhost:8080/subtasks -H "Content-Type: application/json" -d '{"title": "Orphan subtask", "task": {"id": 99}}'
