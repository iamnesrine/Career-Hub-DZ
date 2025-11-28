<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reset Password</title>
    <!-- Boxicons -->
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@100..900&display=swap" rel="stylesheet">
    <style>
        * { font-family: "Outfit", sans-serif; }
        body {
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            background: url(images/bg.jpg) no-repeat;
            background-size: cover;
            background-position: center;
        }
        .login-container {
            width: 380px;
            background: transparent;
            border: 2px solid rgba(255, 255, 255, .2);
            backdrop-filter: blur(9px);
            color: white;
            border-radius: 14px;
            padding: 10px 40px;
        }
        .login-container h1 {
            font-size: 32px;
            font-weight: 800;
            text-align: center;
            margin-bottom: 10px;
        }
        .description {
            text-align: center;
            font-size: 14px;
            color: #e0e0e0;
            margin-bottom: 20px;
        }
        input, button {
            width: 100%;
            height: 50px;
            margin: 10px 0;
            background: transparent;
            border: 2px solid rgba(255, 255, 255, .2);
            border-radius: 60px;
            font-size: 15px;
            color: white;
            text-indent: 15px;
        }
        button {
            background-color: #02a152;
            border: none;
            cursor: pointer;
            transition: 0.3s ease;
            font-weight: bold;
        }
        button:hover { background-color: #45a049; }
        a {
            text-align: center;
            text-decoration: none;
            color: white;
            font-size: 14px;
        }
        a:hover { text-decoration: underline; }
        .back-link { display: block; text-align: center; margin-top: 15px; }
        /* Error/Success Alerts */
        .error-box, .success-box {
            padding: 10px 15px;
            border-radius: 8px;
            margin-bottom: 10px;
            text-align: center;
            font-weight: bold;
        }
        .error-box { background-color: rgba(255, 0, 0, 0.7); }
        .success-box { background-color: rgba(0, 128, 0, 0.7); }
    </style>
</head>
<body>
<div class="login-container">
    <h1>Reset Password</h1>
    <p class="description">Enter your new password below.</p>

    <!-- Success / Error messages -->
    <c:if test="${not empty success}">
        <div class="success-box" id="successAlert">${success}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="error-box" id="errorAlert">${error}</div>
    </c:if>

    <!-- Reset form -->
    <form action="resetpassword" method="post">
        <input type="hidden" name="token" value="${token}">
        <input type="password" name="password" placeholder="Enter new password" required>
        <button type="submit">Update Password</button>
    </form>

    <div class="back-link">
        <a href="login"><i class='bx bx-arrow-back'></i> Back to Sign In</a>
    </div>
</div>

<!-- Auto-dismiss alerts -->
<script>
    const errorBox = document.getElementById("errorAlert");
    const successBox = document.getElementById("successAlert");
    if (errorBox) setTimeout(() => { errorBox.style.display = "none"; }, 3000);
    if (successBox) setTimeout(() => { successBox.style.display = "none"; }, 5000);
</script>
</body>
</html>
