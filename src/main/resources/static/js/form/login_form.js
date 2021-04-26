let loginForm = new Vue({
    el: '#loginForm',
    data: {
        // error: frontendData.error,
        // notification: frontendData.notification,
    },
    template:
        '<div>' +
            '<form method="post" action="/login">' +
                // '<p v-if="error !== null">{{ error }}</p>' +
                // '<p v-if="notification !== null">{{ notification }}</p>' +

                '<div>' +
                    '<span>Username</span>' +
                    '<input type="text" maxlength="25" name="username" required autofocus>' +
                '</div>' +

                '<div>' +
                    '<span>Password</span>' +
                    '<input type="password" maxlength="25" name="password" required >' +
                '</div>' +

                '<div>' +
                    '<input value="OK" type="submit">' +
                '</div>' +
            '</form>' +
            '<a href="/registration">Registration</a>' +
        '</div>'
});