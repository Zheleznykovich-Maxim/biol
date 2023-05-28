<#import "blocks/template.ftlh" as t>
<@t.template user>
<h1 style="text-align: center">Регистрация</h1>
<hr>
<form action="/registration" method="post">
    <div class="form-group">
        <label for="username">Имя пользователя</label>
        <input type="text" value="${user.username!}" class="form-control
    <#if springBindingResult?has_content && springBindingResult.hasFieldErrors("username")>is-invalid
        <#elseif springBindingResult?has_content && !springBindingResult.hasFieldErrors("username")>is-valid
        </#if>" required id="username" name="username">
        <#if springBindingResult?has_content && springBindingResult.hasFieldErrors("username")>
            <div class="invalid-feedback">
                <#list springBindingResult.getFieldErrors("username") as error>
                    ${error.defaultMessage}<br>
                </#list>
            </div>
        </#if>
    </div>
    <div class="form-group">
        <label for="email">Электронная почта</label>
        <input type="email" value="${user.email!}" class="form-control
        <#if springBindingResult?has_content && springBindingResult.hasFieldErrors("email")>is-invalid
        <#elseif springBindingResult?has_content && !springBindingResult.hasFieldErrors("email")>is-valid
        </#if>" id="email" required name="email">
        <#if springBindingResult?has_content && springBindingResult.hasFieldErrors("phoneNumber")>
            <div class="invalid-feedback">
                <#list springBindingResult.getFieldErrors("phoneNumber") as error>
                    ${error.defaultMessage}<br>
                </#list>
            </div>
        </#if>
    </div>
<div class="form-group">
    <label for="phoneNumber">Номер телефона</label>
    <input type="text" value="${user.phoneNumber!}" class="form-control
        <#if springBindingResult?has_content && springBindingResult.hasFieldErrors("phoneNumber")>is-invalid
    <#elseif springBindingResult?has_content && !springBindingResult.hasFieldErrors("phoneNumber")>is-valid
    </#if>" id="phoneNumber" required name="phoneNumber">
    <#if springBindingResult?has_content && springBindingResult.hasFieldErrors("phoneNumber")>
        <div class="invalid-feedback">
            <#list springBindingResult.getFieldErrors("phoneNumber") as error>
                ${error.defaultMessage}<br>
            </#list>
        </div>
    </#if>
</div>

<div class="form-group">
        <label for="password">Пароль</label>
        <input type="password" value="${user.password!}" class="form-control
        <#if springBindingResult?has_content && springBindingResult.hasFieldErrors("password")>is-invalid
        <#elseif springBindingResult?has_content && !springBindingResult.hasFieldErrors("password")>is-valid
        </#if>" id="password" required name="password">
        <#if springBindingResult?has_content && springBindingResult.hasFieldErrors("password")>
            <div class="invalid-feedback">
                <#list springBindingResult.getFieldErrors("password") as error>
                    ${error.defaultMessage}<br>
                </#list>
            </div>
        </#if>
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <button type="submit" style="width: 100%" class="btn btn-dark">Зарегистрироваться</button>
</form>
<#if errorMessage??>
<h2 style="color: red">${errorMessage}</h2>
</#if>
<script>
    // Обработчик события загрузки страницы
    window.addEventListener('load', function () {
        // Получение ссылки на форму регистрации по ее идентификатору
        const form = document.getElementById('registrationForm');

        // Обработчик события отправки формы
        form.addEventListener('submit', function (event) {
            // Отмена стандартной отправки формы
            event.preventDefault();

            // Проверка валидности формы
            const isValid = form.checkValidity();

            // Добавление класса 'was-validated' к форме для применения стилей
            form.classList.add('was-validated');

            // Если форма валидна, продолжить отправку формы
            if (isValid) {
                form.submit();
            }
        });
    });
</script>
</@t.template>