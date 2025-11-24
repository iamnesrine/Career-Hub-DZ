<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Login</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container d-flex justify-content-center align-items-center" style="min-height:100vh;">
    <div class="card shadow-sm p-4" style="width: 400px;">
        <h3 class="text-center mb-4">Login</h3>

        <!-- Error notification -->
        <c:if test="${not empty error}">
            <div id="errorAlert" class="alert alert-danger alert-dismissible fade show" role="alert">
                ${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>

        <!-- Login form -->
        <form action="login" method="post">
            <div class="mb-3">
                <label for="email" class="form-label">Email address</label>
                <input type="email" id="email" name="email" class="form-control" required>
            </div>

            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input type="password" id="password" name="password" class="form-control" required>
            </div>

            <div class="d-grid mb-3">
                <button type="submit" class="btn btn-primary">Login</button>
            </div>
        </form>

        <!-- Register link -->
        <div class="text-center">
            <p class="mb-0">
                Don’t have an account?
                <a href="register" class="text-primary">Register</a>
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