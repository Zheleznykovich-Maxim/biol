<#import "blocks/template.ftlh" as t>
<@t.template user>
<h1 style="text-align: center">Авторизация</h1>
<hr>
<p class="fst-italic text-center fs-4">${message!""}</p>
<form action="/login " method="post">
    <div class="form-group">
        <label for="name">Электронная почта</label>
        <input type="text" class="form-control <#if RequestParameters.error??>is-invalid</#if>" id="name" name="username">
        <div id="name" class="invalid-feedback">
            Неверныя электронная почта
        </div>
    </div>
    <div class="form-group">
        <label for="password">Пароль</label>
        <input type="password" class="form-control <#if RequestParameters.error??>is-invalid</#if>" id="password" name="password">
        <div id="password" class="invalid-feedback">
            Неверный пароль
        </div>
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <button type="submit" style="width: 100%" class="btn btn-dark">Войти</button>
</form>
<hr>
<h2>Нет аккаунта BUYSELL? <a href="/registration">Зарегистрируйся!</a></h2>
</@t.template>