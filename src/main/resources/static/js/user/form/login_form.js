let loginForm = new Vue({
    el: '#loginForm',
    data: {
        error: frontendData.error,
        notification: frontendData.notification,
    },
    template:
        '<form method="post" action="/login">' +

            '<div class="user-info-form">' +
                '<div class="user-info-form-caption">Login to SUPER TRIP</div>' +

                '<div class="error" v-if="error !== null">{{ error }}</div>' +
                '<div class="notification" v-if="notification !== null">{{ notification }}</div>' +

                '<div class="user-info-input-line">' +
                    '<span class="user-info-label">Username</span>' +
                    '<input class="user-info-input" type="text" maxlength="25" name="username" required autofocus>' +
                '</div>' +

                '<div class="user-info-input-line">' +
                    '<span class="user-info-label">Password</span>' +
                    '<input class="user-info-input" type="password" maxlength="25" name="password" required >' +
                '</div>' +

                '<div class="user-info-input-line">' +
                    '<input class="user-info-btn" value="Ok" type="submit">' +
                '</div>' +

                '<div class="user-info-input-line">' +
                    '<a class="user-info-btn" href="/registration">Registration</a>' +
                '</div>' +

            '</div>' +
        '</form>'
});