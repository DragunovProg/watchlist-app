<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Login</title>
    <link rel="stylesheet" href="/static/styles/styles.css">
    <link rel="stylesheet" href="/static/styles/authorization.css">
</head>
<body>
    <div class="container">
        <div class="header-container">
            <div class="logo-container">
                <span id="logo__item"><a href="${pageContext.request.contextPath}/">TrackMate</a></span>
            </div>
        </div>
        <div class="main-container">
            <div class="login-container">
                <div class="login-form-container">
                    <form method="post" class="login-form">
                        <input class="login__controls login-input__item" type="email" name="email" id="email" placeholder="Enter a email">
                        <input class="login__controls login-input__item" type="password" name="password" id="password" placeholder="Enter a password">
                        <span class =login-error__item>${error}</span>
                        <input class="login__controls" type="submit" id="submit" value="Sign in">
                        <span class="register__item">Not have account ?<a href="${pageContext.request.contextPath}/register">Create account</a></span>
                    </form>
                </div>

            </div>
        </div>
        <div class="footer-container"></div>
    </div>
</body>
</html>