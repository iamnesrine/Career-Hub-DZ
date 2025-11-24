<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Main Page</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<!-- Navigation Bar -->
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
  <div class="container-fluid">
    <a class="navbar-brand" href="#">Main Page</a>
    <div class="collapse navbar-collapse">
      <ul class="navbar-nav ms-auto">
        <!-- Déconnexion button -->
        <li class="nav-item">
          <form action="login" method="post" class="d-inline">
            <button type="submit" class="btn btn-outline-light">Déconnexion</button>
          </form>
        </li>
      </ul>
    </div>
  </div>
</nav>

<!-- Centered Content -->
<div class="container d-flex justify-content-center align-items-center" style="min-height:80vh;">
    <h2 class="text-center">Hello this is the main page</h2>
</div>

</body>
</html>