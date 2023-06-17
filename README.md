# User Management System

A simple Spring Boot application that provides RESTful APIs for managing users. It utilizes Spring Data JPA for data access and MySQL as the database. It also includes an in-memory implementation for learning purposes.

## Functionalities

- Create an user in the system
- Fetch the user details by user id
- Edit/Modify the user details
- Delete the user (soft delete)
- Restore the deleted user
- Permanently delete the user

## API Endpoints

| Endpoint                      | Method | Request Body | Parameters                    | Description                       |
| ----------------------------- | ------ | ------------ | ----------------------------- | --------------------------------- |
| `/api/v1/users`               | GET    | N/A          | `includeDeleted` (bool)       | Get all users                     |
| `/api/v1/users/{id}`          | GET    | N/A          | `id`, `includeDeleted` (bool) | Get a user by ID                  |
| `/api/v1/users`               | POST   | `UserDto`    | N/A                           | Add a new user                    |
| `/api/v1/users/{id}`          | PUT    | `UserDto`    | `id`                          | Update a user by ID               |
| `/api/v1/users/{id}`          | DELETE | N/A          | `id`                          | Soft delete a user by ID          |
| `/api/v1/users/{id}/undelete` | PUT    | N/A          | `id`                          | Restore a soft deleted user by ID |
| `/api/v1/users/{id}/hard`     | DELETE | N/A          | `id`                          | Permanently delete a user by ID   |

## User Schema

Defined by User entity class having following properties:

### Properties

| Name     | Type    | Description                            |
| -------- | ------- | -------------------------------------- |
| id       | Long    | The unique identifier for the user.    |
| username | String  | The username of the user.              |
| fullName | String  | The full name of the user.             |
| email    | String  | The email address of the user.         |
| deleted  | boolean | Indicates whether the user is deleted. |

### Example

```json
{
  "id": 1,
  "username": "rahul",
  "fullName": "Rahul Kumar",
  "email": "rahul@example.com",
  "deleted": false
}
```
