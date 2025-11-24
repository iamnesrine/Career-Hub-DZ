<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Register</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container d-flex justify-content-center align-items-center" style="min-height:100vh;">
    <div class="card shadow-sm p-4" style="width: 450px;">
        <h3 class="text-center mb-4">Register</h3>

        <!-- Error notification -->
        <c:if test="${not empty error}"> <!-- check if variable error is not empty -->
            <div id="errorAlert" class="alert alert-danger alert-dismissible fade show" role="alert">
                ${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>

        <!-- Registration form -->
        <form action="register" method="post">
            <!-- Name -->
            <div class="mb-3">
                <label for="name" class="form-label">Full Name</label>
                <input type="text" id="name" name="name" class="form-control" required>
            </div>

            <!-- Email -->
            <div class="mb-3">
                <label for="email" class="form-label">Email address</label>
                <input type="email" id="email" name="email" class="form-control" required>
            </div>

            <!-- Password -->
            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input type="password" id="password" name="password" class="form-control" required>
            </div>

            <!-- Role -->
            <div class="mb-3">
                <label for="role" class="form-label">Role</label>
                <select id="role" name="role" class="form-select" required>
                    <option value="seeker">Seeker</option>
                    <option value="organization">Organization</option>
                </select>
            </div>

            <!-- Birthdate (required for seekers) -->
            <div class="mb-3">
                <label for="birthdate" class="form-label">Birth Date</label>
                <input type="date" id="birthdate" name="birthdate" class="form-control">
                <small class="text-muted">Required if you are a seeker.</small>
            </div>

            <!-- Submit -->
            <div class="d-grid mb-3">
                <button type="submit" class="btn btn-primary">Register</button>
            </div>
        </form>

        <!-- Login link -->
        <div class="text-center">
            <p class="mb-0">
                Already have an account?
                <a href="login" class="text-primary">Login</a>
            </p>
        </div>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

<!-- ✅ Auto-dismiss error after 3 seconds -->
<script>
    document.addEventListener("DOMContentLoaded", function() {
        const alert = document.getElementById("errorAlert");
        if (alert) {
            setTimeout(() => {
                const bsAlert = new bootstrap.Alert(alert);
                bsAlert.close();
            }, 3000); // 3 seconds
        }
    });
</script>
</body>
</html>