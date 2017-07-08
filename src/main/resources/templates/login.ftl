<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
>
    <head>
        <title>Spring Security Examp</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
        <link rel="stylesheet" href="/css/main.css">
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
    </head>
    <body>
        <#if param?? && param.error>
        <div>Invalid user and password.</div>
        </#if>

        <#if param?? && param.logout>
        <div>You have been logged out.</div>
        </#if>

        <div class="container">
            <div class="row">
                <div class="col-sm-6 col-md-4 col-md-offset-4">
                    <h1 class="text-center login-title">Sign in to continue</h1>
                    <div class="account-wall">
                        <img class="profile-img" src="/img/profile.png" alt="" />
                        <form class="form-signin" action="/login" method="post">
                            <input name="username" type="text" class="form-control" placeholder="username" required autofocus />
                            <input name="password" type="password" class="form-control" placeholder="password" required />
                            <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
