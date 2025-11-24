<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign In / Sign Up</title>

    <!-- Boxicons -->
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@100..900&display=swap" rel="stylesheet">

    <style>
        * {
            font-family: "Outfit", sans-serif;
        }

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
            font-size: 39px;
            font-weight: 800;
            text-align: center;
        }

        .social-icons {
            text-align: center;
            margin-bottom: 20px;
        }

        .social-icons .icon {
            display: inline-block;
            font-size: 20px;
            color: white;
            margin: 0 10px;
            border: 2px solid rgba(255, 255, 255, .2);
            border-radius: 16px;
            padding: 7px 10px;
            transition: 0.3s ease;
        }

        .social-icons .icon:hover {
            color: #dcdcdc;
        }

        input,
        select,
        button {
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

        select {
            padding-left: 15px;
            appearance: none;
        }

        select option {
            color: black; /* options readable */
        }

        /* Birthdate placeholder trick */
        input[type="date"]::-webkit-calendar-picker-indicator {
            opacity: 0;
            display: none;
        }

        button {
            background-color: #02a152;
            border: none;
            cursor: pointer;
            transition: 0.3s ease;
        }

        button:hover {
            background-color: #45a049;
        }

        a {
            text-align: center;
            text-decoration: underline;
            color: white;
        }

        .hidden {
            display: none;
        }

        /* Error alert */
        .error-box {
            background-color: rgba(255, 0, 0, 0.7);
            padding: 10px 15px;
            border-radius: 8px;
            margin-bottom: 10px;
            text-align: center;
            font-weight: bold;
        }

        /* Success alert */
        .success-box {
            background-color: rgba(0, 128, 0, 0.7);
            padding: 10px 15px;
            border-radius: 8px;
            margin-bottom: 10px;
            text-align: center;
            font-weight: bold;
        }

        small {
            display: block;
            margin-top: -5px;
            margin-bottom: 10px;
            color: #dcdcdc;
            font-size: 12px;
            text-align: left;
        }
    </style>
</head>

<body>

    <div class="login-container">

        <!-- Success Message -->
        <c:if test="${not empty success}">
            <div class="success-box" id="successAlert">
                ${success}
            </div>
            <c:remove var="success" scope="session"/>
        </c:if>

        <!-- Error Message -->
        <c:if test="${not empty error}">
            <div class="error-box" id="errorAlert">
                ${error}
            </div>
        </c:if>

        <!-- LOGIN FORM -->
        <div class="login-form" id="loginForm">
            <h1>Sign In</h1>

            <div class="social-icons">
                <a href="#" class="icon"><i class='bx bxl-google-plus'></i></a>
                <a href="#" class="icon"><i class='bx bxl-facebook'></i></a>
                <a href="#" class="icon"><i class='bx bxl-github'></i></a>
                <a href="#" class="icon"><i class='bx bxl-linkedin'></i></a>
            </div>

            <form action="login" method="post">
                <input type="email" name="email" placeholder="Email" required>
                <input type="password" name="password" placeholder="Password" required>
                <button type="submit">Sign In</button>
            </form>

            <p>Don't have an account?
                <a href="#" id="showSignup">Sign Up</a>
            </p>
        </div>

        <!-- SIGNUP FORM -->
        <div class="signup-form hidden" id="signupForm">
            <h1>Create Account</h1>

            <div class="social-icons">
                <a href="#" class="icon"><i class='bx bxl-google-plus'></i></a>
                <a href="#" class="icon"><i class='bx bxl-facebook'></i></a>
                <a href="#" class="icon"><i class='bx bxl-github'></i></a>
                <a href="#" class="icon"><i class='bx bxl-linkedin'></i></a>
            </div>

            <form action="register" method="post">
                <input type="text" name="name" placeholder="Name" required>
                <input type="email" name="email" placeholder="Email" required>
                <input type="password" name="password" placeholder="Password" required>

                <!-- Role -->
                <select name="role" required>
                    <option value="" disabled selected>Select Role</option>
                    <option value="seeker">Seeker</option>
                    <option value="organization">Organization</option>
                </select>

                <!-- Birthdate with placeholder -->
                <input type="text" name="birthdate" id="birthdate" placeholder="Birthdate"
                       onfocus="(this.type='date')" onblur="if(!this.value)this.type='text'">
                <small>Required if you are a seeker.</small>

                <button type="submit">Sign Up</button>
            </form>

            <p>Already have an account?
                <a href="#" id="showLogin">Sign In</a>
            </p>
        </div>

    </div>

    <!-- JS FOR FORM SWITCH + AUTO-DISMISS ALERTS -->
    <script>
        const loginForm = document.getElementById('loginForm');
        const signupForm = document.getElementById('signupForm');
        const showSignupBtn = document.getElementById('showSignup');
        const showLoginBtn = document.getElementById('showLogin');

        showSignupBtn.addEventListener('click', () => {
            loginForm.classList.add('hidden');
            signupForm.classList.remove('hidden');
        });

        showLoginBtn.addEventListener('click', () => {
            signupForm.classList.add('hidden');
            loginForm.classList.remove('hidden');
        });

        // Auto-dismiss alerts
        const errorBox = document.getElementById("errorAlert");
        const successBox = document.getElementById("successAlert");
        if (errorBox) {
            setTimeout(() => { errorBox.style.display = "none"; }, 3000);
        }
        if (successBox) {
            setTimeout(() => { successBox.style.display = "none"; }, 3000);
        }
    </script>

</body>
</html>