<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Login</title>
    <link rel="stylesheet" href="/static/styles/styles.css">
    <link rel="stylesheet" href="/static/styles/register.css">
</head>
<body>
    <div class="container">
        <div class="header-container">
            <div class="logo-container">
                <span id="logo__item"><a href="${pageContext.request.contextPath}/">TrackMate</a></span>
            </div>
        </div>
        <div class="main-container">
            <div class="register-container">
                <div class="register-form-container">
                    <form method="post" class="register-form">
                        <input class="register__controls register-input__item" type="text" name="fullName" id="fullName" placeholder="Enter your name">
                        <input class="register__controls register-input__item" type="email" name="email" id="email" placeholder="Enter a email">
                        <input class="register__controls register-input__item" type="password" name="password" id="password" placeholder="Enter a password">
                        <input class="register__controls" type="submit" id="submit" value="Sign up">

                    </form>
                </div>
            </div>
        </div>
        <div class="footer-container"></div>
    </div>
</body>
</html>