let registrationForm = new Vue({
    el: '#registrationForm',
    data: {
        fd: frontendData.formData,
        error: frontendData.error
    },
    template:
        '<form method="post" action="/registration">' +

            '<div class="user-info-form">' +
                '<div class="user-info-form-caption">Registration</div>' +

                '<div class="error" v-if="error !== null">{{ error }}</div>' +

                '<div class="user-info-input-line">' +
                    '<span class="user-info-label">Username</span>' +
                    '<input class="user-info-input" type="text" maxlength="25" name="username" :value="fd.username" required autofocus/>' +
                '</div>' +

                '<div class="user-info-input-line">' +
                    '<span class="user-info-label">Name</span>' +
                    '<input class="user-info-input" type="text" maxlength="25" name="name" :value="fd.name" required/>' +
                '</div>' +

                '<div class="user-info-input-line">' +
                    '<span class="user-info-label">Surname</span>' +
                    '<input class="user-info-input" type="text" maxlength="25" name="surname" :value="fd.surname" required/>' +
                '</div>' +

                '<div class="user-info-input-line">' +
                    '<span class="user-info-label">Middle name</span>' +
                    '<input class="user-info-input" type="text" maxlength="25" name="middleName" :value="fd.middleName"/>' +
                '</div>' +

                '<div class="user-info-input-line">' +
                    '<span class="user-info-label">Passport id</span>' +
                    '<input class="user-info-input" type="text" minlength="6" maxlength="6" name="passportId" :value="fd.passportId" required/>' +
                '</div>' +

                '<div class="user-info-input-line">' +
                    '<span class="user-info-label">Passport series</span>' +
                    '<input class="user-info-input" type="text" minlength="4" maxlength="4" name="passportSeries" :value="fd.passportSeries" required/>' +
                '</div>' +

                '<div class="user-info-input-line">' +
                    '<span class="user-info-label">Password</span>' +
                    '<input class="user-info-input" type="password" maxlength="25" name="password" required/>' +
                '</div>' +

                '<div class="user-info-input-line">' +
                    '<span class="user-info-label">Confirm password</span>' +
                    '<input class="user-info-input" type="password" maxlength="25" name="passwordConfirm" required/>' +
                '</div>' +

                '<div class="user-info-input-line">' +
                    '<input class="user-info-btn" value="Ok" type="submit">' +
                '</div>' +

                '<div class="user-info-input-line">' +
                    '<a class="user-info-btn" href="/login">Login</a>' +
                '</div>' +

            '</div>' +
        '</form>'
});